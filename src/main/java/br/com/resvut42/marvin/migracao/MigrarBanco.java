package br.com.resvut42.marvin.migracao;

import java.math.BigDecimal;

import org.jamel.dbf.DbfReader;
import org.jamel.dbf.utils.DbfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.Carteira;
import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.entidade.Contato;
import br.com.resvut42.marvin.entidade.Endereco;
import br.com.resvut42.marvin.enums.Febraban;
import br.com.resvut42.marvin.enums.LayoutCnab;
import br.com.resvut42.marvin.servico.SerBanco;
import br.com.resvut42.marvin.servico.SerConta;
import br.com.resvut42.marvin.util.R42Util;

/****************************************************************************
 * Classe para migrar dados do Banco ADMCON -> MARVIN
 * 
 * @author Bob-Odin - 14/03/2017
 ****************************************************************************/
@Component
public class MigrarBanco {

	private final String arquivo = "DBC01.DBF";
	private DbfReader dbfReader;

	@Autowired
	SerConta serConta;
	@Autowired
	SerBanco serBanco;

	/****************************************************************************
	 * Executa a migração do arquivo DBC01.DBF para Entidade Banco
	 ****************************************************************************/
	public void executar() throws Exception {

		try {

			dbfReader = R42Util.lerDbf(arquivo);
			
			for (int i = 0; i < dbfReader.getRecordCount(); i++) {
				Object[] row = dbfReader.nextRecord();

				String bc1codban = row[0].toString();
				String bc1conta = row[1].toString();
				String bc1num = new String(DbfUtils.trimLeftSpaces((byte[]) row[2]));
				String bc1nome = new String(DbfUtils.trimLeftSpaces((byte[]) row[3]));
				String bc1agnum = new String(DbfUtils.trimLeftSpaces((byte[]) row[5]));
				String bc1agnome = new String(DbfUtils.trimLeftSpaces((byte[]) row[6]));
				String bc1contacr = new String(DbfUtils.trimLeftSpaces((byte[]) row[7]));
				String bc1contato = new String(DbfUtils.trimLeftSpaces((byte[]) row[8]));
				String bc1ender = new String(DbfUtils.trimLeftSpaces((byte[]) row[9]));
				String bc1bairro = new String(DbfUtils.trimLeftSpaces((byte[]) row[10]));
				String bc1cid = new String(DbfUtils.trimLeftSpaces((byte[]) row[11]));
				String bc1uf = new String(DbfUtils.trimLeftSpaces((byte[]) row[12]));
				String bc1cep = new String(DbfUtils.trimLeftSpaces((byte[]) row[13]));
				String bc1fone = new String(DbfUtils.trimLeftSpaces((byte[]) row[14]));

				BigDecimal bc1cdage = new BigDecimal(0);
				BigDecimal bc1cdconta = new BigDecimal(0);
				BigDecimal bc1cdmest = new BigDecimal(0);
				BigDecimal bc1cdcart = new BigDecimal(0);
				BigDecimal bc1cd2age = new BigDecimal(0);
				BigDecimal bc1cd2cont = new BigDecimal(0);
				BigDecimal bc1cd2mest = new BigDecimal(0);
				BigDecimal bc1cd2cart = new BigDecimal(0);

				if (row[20] != null) {
					bc1cdage = new BigDecimal((double) row[20]);
				}
				if (row[21] != null) {
					bc1cdconta = new BigDecimal((double) row[21]);
				}
				if (row[22] != null) {
					bc1cdmest = new BigDecimal((double) row[22]);
				}
				if (row[23] != null) {
					bc1cdcart = new BigDecimal((double) row[23]);
				}
				if (row[24] != null) {
					bc1cd2age = new BigDecimal((double) row[24]);
				}
				if (row[25] != null) {
					bc1cd2cont = new BigDecimal((double) row[25]);
				}
				if (row[26] != null) {
					bc1cd2mest = new BigDecimal((double) row[26]);
				}
				if (row[27] != null) {
					bc1cd2cart = new BigDecimal((double) row[27]);
				}

				Endereco endereco = new Endereco();
				endereco.setLogradouro(bc1ender);
				endereco.setBairro(bc1bairro);
				endereco.setCidade(bc1cid);
				endereco.setCep(bc1cep);
				endereco.setUf(R42Util.converteEstado(bc1uf));

				Contato contato = new Contato();
				contato.setNomeContato(bc1contato);
				contato.setTelefone(bc1fone);

				Banco banco = new Banco();
				banco.setIdMigracao(bc1codban);
				banco.setFebraban(converteFebraban(bc1num));
				banco.setDescricao(bc1nome);
				banco.setAgencia(bc1agnum);
				banco.setNomeAgencia(bc1agnome);
				banco.setNumeroConta(bc1contacr);
				banco.setEndereco(endereco);
				banco.addContato(contato);
				banco.setConta(converteConta(bc1conta));

				if (bc1cdcart.compareTo(new BigDecimal(0)) != 0) {
					Carteira carteira1 = new Carteira();
					carteira1.setDescricao("CARTEIRA " + bc1cdcart.toPlainString());
					carteira1.setAgencia(bc1cdage.toPlainString());
					carteira1.setConta(bc1cdconta.toPlainString());
					carteira1.setCodMestre(bc1cdmest.toPlainString());
					carteira1.setCodCarteira(bc1cdcart.toPlainString());
					carteira1.setLayoutCnab(LayoutCnab.L400);
					banco.addCarteira(carteira1);
				}

				if (bc1cd2cart.compareTo(new BigDecimal(0)) != 0) {
					Carteira carteira2 = new Carteira();
					carteira2.setDescricao("CARTEIRA " + bc1cd2cart.toPlainString());
					carteira2.setAgencia(bc1cd2age.toPlainString());
					carteira2.setConta(bc1cd2cont.toPlainString());
					carteira2.setCodMestre(bc1cd2mest.toPlainString());
					carteira2.setCodCarteira(bc1cd2cart.toPlainString());
					carteira2.setLayoutCnab(LayoutCnab.L400);
					banco.addCarteira(carteira2);					
				}

				serBanco.salvar(banco);

			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Converte String em Febraban
	 ****************************************************************************/
	private Febraban converteFebraban(String codFebraban) {
		Febraban cod = null;
		if(codFebraban != null && !codFebraban.isEmpty()){
			cod = Febraban.valueOf("F_" + codFebraban);
		}
		return cod;
	}

	/****************************************************************************
	 * Converte String em Conta (busca no banco de dados)
	 ****************************************************************************/
	private Conta converteConta(String reduzida) {
		Conta conta = serConta.buscarPorReduzida(reduzida);
		return conta;
	}
}
