package br.com.resvut42.marvin.migracao;

import java.io.File;

import org.jamel.dbf.DbfReader;
import org.jamel.dbf.utils.DbfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.entidade.Contato;
import br.com.resvut42.marvin.entidade.Endereco;
import br.com.resvut42.marvin.enums.Estado;
import br.com.resvut42.marvin.enums.Febraban;
import br.com.resvut42.marvin.servico.SerBanco;
import br.com.resvut42.marvin.servico.SerConta;

/****************************************************************************
 * Classe para migrar dados do Banco ADMCON -> MARVIN
 * 
 * @author Bob-Odin - 14/03/2017
 ****************************************************************************/
@Component
public class MigrarBanco {

	private final String arquivo = "dbf/DBC01.DBF";
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

			ClassLoader classLoader = new MigrarConta().getClass().getClassLoader();
			File file = new File(classLoader.getResource(arquivo).getFile());
			dbfReader = new DbfReader(file);

			for (int i = 0; i < dbfReader.getRecordCount(); i++) {
				Object[] row = dbfReader.nextRecord();

				String bc1codban = row[0].toString();
				String bc1conta = row[1].toString();
				String bc1num = new String(DbfUtils.trimLeftSpaces((byte[]) row[2]));
				String bc1nome = new String(DbfUtils.trimLeftSpaces((byte[]) row[3]));
//				String bc1fant = new String(DbfUtils.trimLeftSpaces((byte[]) row[4]));
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

				Endereco endereco = new Endereco();
				endereco.setLogradouro(bc1ender);
				endereco.setBairro(bc1bairro);
				endereco.setCidade(bc1cid);
				endereco.setCep(bc1cep);
				endereco.setUf(converteEstado(bc1uf));

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

				serBanco.salvar(banco);
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Erro na importação dos Bancos!");
		}
	}

	/****************************************************************************
	 * Converte String em Febraban
	 ****************************************************************************/	
	private Febraban converteFebraban(String codFebraban) {
		Febraban cod = Febraban.valueOf("F_"+codFebraban);
		return cod;
	}

	/****************************************************************************
	 * Converte String em Estado
	 ****************************************************************************/	
	private Estado converteEstado(String uf) {
		Estado estado = Estado.valueOf(uf);
		return estado;
	}

	/****************************************************************************
	 * Converte String em Conta (busca no banco de dados)
	 ****************************************************************************/	
	private Conta converteConta(String reduzida) {
		Conta conta = serConta.buscarPorReduzida(reduzida);
		return conta;
	}
}
