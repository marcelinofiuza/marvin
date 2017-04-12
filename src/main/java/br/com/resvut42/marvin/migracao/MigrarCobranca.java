package br.com.resvut42.marvin.migracao;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jamel.dbf.DbfReader;
import org.jamel.dbf.utils.DbfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.resvut42.marvin.entidade.Cliente;
import br.com.resvut42.marvin.entidade.Cobranca;
import br.com.resvut42.marvin.entidade.CobrancaItem;
import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.servico.SerCliente;
import br.com.resvut42.marvin.servico.SerCobranca;
import br.com.resvut42.marvin.servico.SerConta;

/****************************************************************************
 * Classe para migrar dados de cobrança dos Clientes ADMCON -> MARVIN
 * 
 * @author Bob-Odin - 02/04/2017
 ****************************************************************************/
@Component
public class MigrarCobranca {

	private final String arquivo = "dbf/DCL01.DBF";
	private DbfReader dbfReader;

	private Map<String, Cobranca> mapCobranca = new HashMap<>();

	@Autowired
	SerConta serConta;
	@Autowired
	SerCliente serCliente;
	@Autowired
	SerCobranca serCobranca;

	private String contaPadrao = "14.0";

	private String clcodigo;
	private BigDecimal clfracao;
	private BigDecimal clfracao2;
	private BigDecimal clfracao3;
	private BigDecimal clfracao4;
	private int cldiapag;
	private String clgrupo;

	/****************************************************************************
	 * Executa a migração do arquivo DCL01.DBF para Entidade Cliente
	 ****************************************************************************/
	public void executar() throws Exception {
		try {
			ClassLoader classLoader = new MigrarConta().getClass().getClassLoader();
			File file = new File(classLoader.getResource(arquivo).getFile());
			dbfReader = new DbfReader(file);

			for (int i = 0; i < dbfReader.getRecordCount(); i++) {
				Object[] row = dbfReader.nextRecord();
				clcodigo = new String(DbfUtils.trimLeftSpaces((byte[]) row[0]));
				clfracao = new BigDecimal(row[36].toString());
				clfracao2 = new BigDecimal(row[37].toString());
				clfracao3 = new BigDecimal(row[38].toString());
				clfracao4 = new BigDecimal(row[39].toString());
				clgrupo = new String(DbfUtils.trimLeftSpaces((byte[]) row[40]));
				cldiapag = new Double(row[43].toString()).intValue();

				// Verifica se ja existe informações para o grupo
				if (mapCobranca.get(clgrupo) == null) {
					montaCabecalho();
				}
				montaItem();
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Erro na importação das cobranças!");
		}

		for (Map.Entry<String, Cobranca> entry : mapCobranca.entrySet()) {
			serCobranca.salvar(entry.getValue());
		}

	}

	/****************************************************************************
	 * Monta dados de cabeçalho
	 ****************************************************************************/
	private void montaCabecalho() {

		Cobranca cobranca = new Cobranca();
		cobranca.setDescricao("GRUPO DE COBRANÇA " + clgrupo);
		cobranca.setConta(converteConta(contaPadrao));
		mapCobranca.put(clgrupo, cobranca);

	}

	/****************************************************************************
	 * Monta dados do item
	 ****************************************************************************/
	private void montaItem() {

		Cobranca cobranca = mapCobranca.get(clgrupo);

		CobrancaItem item = new CobrancaItem();
		item.setCliente(buscaCliente(clcodigo));
		item.setDiaVencimento(cldiapag);
		item.setValor(clfracao4);
		item.setFracao1(clfracao);
		item.setFracao2(clfracao2);
		item.setFracao3(clfracao3);
		if(item.getCliente() != null && item.getCliente().getIdCliente() != null){
			cobranca.addItem(item);
		}
	}

	/****************************************************************************
	 * Converte String em Conta (busca no banco de dados)
	 ****************************************************************************/
	private Conta converteConta(String reduzida) {
		Conta conta = serConta.buscarPorReduzida(reduzida);
		return conta;
	}

	/****************************************************************************
	 * Busca o cliente pela unidade
	 ****************************************************************************/
	private Cliente buscaCliente(String unidade) {
		Cliente cliente = serCliente.buscarPorUnidade(clcodigo);
		return cliente;
	}
}
