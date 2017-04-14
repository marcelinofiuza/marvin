package br.com.resvut42.marvin.migracao;

import java.util.ArrayList;
import java.util.List;

import org.jamel.dbf.DbfReader;
import org.jamel.dbf.utils.DbfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.enums.AnaliticaSintetica;
import br.com.resvut42.marvin.enums.AtivaItativa;
import br.com.resvut42.marvin.enums.Natureza;
import br.com.resvut42.marvin.servico.SerConta;
import br.com.resvut42.marvin.util.R42Util;

/****************************************************************************
 * Classe para migrar do plano de contas ADMCON -> MARVIN
 * 
 * @author Bob-Odin - 14/03/2017
 ****************************************************************************/
@Component
public class MigrarConta {

	private final String arquivo = "c:\\temp\\dbf\\DCT01.DBF";
	private int lenCt1Conta;
	private DbfReader dbfReader;
	private List<String[]> registro;

	@Autowired
	SerConta serConta;

	/****************************************************************************
	 * Executa a migração do arquivo DCT01.DBF para Entidade Conta
	 ****************************************************************************/
	public void executar() throws Exception {

		try {

			dbfReader = R42Util.lerDbf(arquivo);

			try {
				processaDados();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

			if (!registro.isEmpty()) {

				for (int i = 0; i < 5; i++) {
					
					int nivel = i + 1;

					for (String[] ct1Registro : registro) {

						if (Integer.parseInt(ct1Registro[2]) == nivel) {
							try {
								serConta.salvar(montaConta(ct1Registro));
							} catch (Exception e) {
								throw new Exception(e.getMessage());
							}
						}
					}
				}
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/****************************************************************************
	 * Pega os dados importantes, e transforma em hashMap
	 ****************************************************************************/
	private void processaDados() throws Exception {

		registro = new ArrayList<>();
		int linRegistro = 0;

		try {

			for (int i = 0; i < dbfReader.getRecordCount(); i++) {
				linRegistro = i;
				Object[] row = dbfReader.nextRecord();
				String[] linha = new String[10];

				String ct1Conta = new String(DbfUtils.trimLeftSpaces((byte[]) row[0]));
				String ct1Chave = new String(DbfUtils.trimLeftSpaces((byte[]) row[2]));
				String ct1Nivel = row[3].toString();
				ct1Nivel = ct1Nivel.substring(0, ct1Nivel.lastIndexOf("."));
				String ct1Tipo = new String(DbfUtils.trimLeftSpaces((byte[]) row[4]));
				String ct1ContaR = row[5].toString();
				ct1ContaR = ct1ContaR.substring(0, ct1ContaR.lastIndexOf("."));
				String ct1Dvr = row[6].toString();
				ct1Dvr = ct1Dvr.substring(0, ct1Dvr.lastIndexOf("."));
				String ct1Descr = new String(DbfUtils.trimLeftSpaces((byte[]) row[7]));
				String ct1Status = row[8].toString();
				ct1Status = ct1Status.substring(0, ct1Status.lastIndexOf("."));
				String ct1Nat = row[9].toString();
				ct1Nat = ct1Nat.substring(0, ct1Nat.lastIndexOf("."));

				linha[0] = ct1Conta.replace(".", "");
				linha[1] = ct1Chave;
				linha[2] = ct1Nivel;
				linha[3] = ct1Tipo;
				linha[4] = ct1ContaR + "." + ct1Dvr;
				linha[6] = ct1Descr;
				linha[7] = ct1Status;
				linha[8] = ct1Nat;

				registro.add(linha);
				lenCt1Conta = linha[0].length();
			}

			BubbleSort();
			
		} catch (Exception e) {
			throw new Exception("Erro no processo do registro " + String.valueOf(linRegistro));
		}
	}

	/****************************************************************************
	 * Monta dados da conta, a partir da linha de conta
	 ****************************************************************************/
	private Conta montaConta(String[] linha) {

		Conta conta = new Conta();
		if (linha[2].equals("1")) { // se for nivel 1, para os demais, cria
									// automatico
			conta.setChave(Integer.parseInt(linha[8]));
		}
		conta.setDescricao(linha[6]);
		conta.setTipoConta(converteTipo(linha[3]));
		conta.setStatus(converteStatus(linha[7]));
		conta.setNatureza(converteNatureza(linha[8]));
		conta.setReduzida(linha[4]);
		conta.setContaPai(buscaContaPai(linha));

		return conta;
	}

	/****************************************************************************
	 * Faz a conversão do tipo de conta
	 ****************************************************************************/
	private AnaliticaSintetica converteTipo(String tipo) {
		if (tipo.equals("S")) {
			return AnaliticaSintetica.SINTETICA;
		} else {
			return AnaliticaSintetica.ANALITICA;
		}
	}

	/****************************************************************************
	 * Faz a conversão do status da conta
	 ****************************************************************************/
	private AtivaItativa converteStatus(String status) {
		if (status.equals("1")) {
			return AtivaItativa.ATIVA;
		} else {
			return AtivaItativa.INATIVA;
		}
	}

	/****************************************************************************
	 * Faz a conversão da natureza da conta
	 ****************************************************************************/
	private Natureza converteNatureza(String natureza) {
		if (natureza.equals("1")) {
			return Natureza.ATIVO;
		} else if (natureza.equals("2")) {
			return Natureza.PASSIVO;
		} else if (natureza.equals("3")) {
			return Natureza.DESPESA;
		} else {
			return Natureza.RECEITA;
		}
	}

	/****************************************************************************
	 * Busca conta pai já cadastrada
	 ****************************************************************************/
	private Conta buscaContaPai(String[] linha) {

		Conta contaPai = null;

		if (!linha[2].equals("1")) { // CT1NIVEL
			String[] linhaPai = chaveNivelPai(linha[1]);
			contaPai = serConta.buscarPorReduzida(linhaPai[4]);
		}
		return contaPai;
	}

	/****************************************************************************
	 * Retorna chave do nivel pai
	 ****************************************************************************/
	private String[] chaveNivelPai(String chaveFilho) {

		String chave = chaveFilho.substring(0, chaveFilho.length()-1);

		while (chave.length() < lenCt1Conta) {
			chave = chave + "0";
		}

		for (String[] strings : registro) {
			if (strings[0].equals(chave)) {
				return strings;
			}
		}

		return null;
	}

	/****************************************************************************
	 * Ordena por conta
	 ****************************************************************************/
	private void BubbleSort() {

		boolean troca = false;
		for (int i = registro.size() - 1; i > 0; i--) {
			troca = false;
			for (int j = 0; j < i; j++) {
				String[] registroJ = registro.get(j);
				String[] registroJ1 = registro.get(j + 1);
				if (registroJ[0].compareToIgnoreCase(registroJ1[0]) > 0) {
					registro.set(j, registroJ1);
					registro.set(j + 1, registroJ);
					troca=true;
				}
			}
			if(!troca){
				return;
			}
		}

	}
}
