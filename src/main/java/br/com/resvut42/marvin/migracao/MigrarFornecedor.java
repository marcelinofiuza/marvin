package br.com.resvut42.marvin.migracao;

import org.jamel.dbf.DbfReader;
import org.jamel.dbf.utils.DbfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.resvut42.marvin.entidade.Conta;
//import br.com.resvut42.marvin.entidade.Contato;
import br.com.resvut42.marvin.entidade.Endereco;
import br.com.resvut42.marvin.entidade.Fornecedor;
import br.com.resvut42.marvin.servico.SerConta;
import br.com.resvut42.marvin.servico.SerFornecedor;
import br.com.resvut42.marvin.util.R42Util;

/****************************************************************************
 * Classe para migrar dados dos Fornecedores ADMCON -> MARVIN
 * 
 * @author Bob-Odin - 14/04/2017
 ****************************************************************************/
@Component
public class MigrarFornecedor {

	private final String arquivo = "DFR01.DBF";
	private DbfReader dbfReader;

	@Autowired
	SerConta serConta;
	@Autowired
	SerFornecedor serFornecedor;

	/****************************************************************************
	 * Executa a migração do arquivo DFR01.DBF para Entidade Fornecedor
	 ****************************************************************************/
	public void executar() throws Exception {
		try {

			dbfReader = R42Util.lerDbf(arquivo);

			for (int i = 0; i < dbfReader.getRecordCount(); i++) {
				Object[] row = dbfReader.nextRecord();

				String frcodigo = new String(DbfUtils.trimLeftSpaces((byte[]) row[0]));
				String frrazao = new String(DbfUtils.trimLeftSpaces((byte[]) row[1]));
				String frfantas = new String(DbfUtils.trimLeftSpaces((byte[]) row[2]));
				String frender = new String(DbfUtils.trimLeftSpaces((byte[]) row[3]));
				String frbair = new String(DbfUtils.trimLeftSpaces((byte[]) row[4]));
				String frcep = new String(DbfUtils.trimLeftSpaces((byte[]) row[5]));
				String frcidade = new String(DbfUtils.trimLeftSpaces((byte[]) row[6]));
				String fruf = new String(DbfUtils.trimLeftSpaces((byte[]) row[7]));
//				String frfone = new String(DbfUtils.trimLeftSpaces((byte[]) row[8]));
//				String frfax = new String(DbfUtils.trimLeftSpaces((byte[]) row[9]));
				String frinest = new String(DbfUtils.trimLeftSpaces((byte[]) row[10]));
				String frcgc = new String(DbfUtils.trimLeftSpaces((byte[]) row[11]));
				String frconta = row[12].toString();
//				String frcontato = new String(DbfUtils.trimLeftSpaces((byte[]) row[13]));
//				String frgerlcto = new String(DbfUtils.trimLeftSpaces((byte[]) row[14]));
//				String fragencia = new String(DbfUtils.trimLeftSpaces((byte[]) row[15]));
//				String frccorrent = new String(DbfUtils.trimLeftSpaces((byte[]) row[16]));
				
				if(frcodigo.isEmpty()){
					continue;
				}
				
				String logradouro = "";
				String numero = "";
				String complemento = "";				
				String[] parts = frender.split(",");				
				if(parts.length >= 1){
					logradouro = parts[0].trim();				
				}				
				if(parts.length > 1){
					numero = parts[1].trim();
					if(numero.length() > 10){
						numero = numero.substring(0, 10);
					}
				}				
				if(parts.length > 2){
					complemento = parts[2].trim();
					if(complemento.length() > 20){
						complemento = complemento.substring(0, 20);
					}					
				}					
				
				Endereco endereco = new Endereco();
				endereco.setCep(frcep);
				endereco.setTipoLogradouro("");
				endereco.setLogradouro(logradouro);
				endereco.setNumero(numero);
				endereco.setComplemento(complemento);
				endereco.setBairro(frbair);
				endereco.setCidade(frcidade);
				endereco.setUf(R42Util.converteEstado(fruf));				
				
//				Contato contato = new Contato();
//				contato.setNomeContato(frcontato);
//				contato.setTelefone(frfone);
//				contato.setCelular(frfax);
				
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setFrCodigo(frcodigo);
				fornecedor.setRazaoSocial(frrazao);
				fornecedor.setFantasia(frfantas);
				fornecedor.setCnpj(R42Util.validaCNPJ(frcgc, true));
				fornecedor.setCpf(R42Util.validaCPF(frcgc, true));				
				fornecedor.setInsEstadual(frinest);
				fornecedor.setEndereco(endereco);
//				fornecedor.addContato(contato);
				fornecedor.setConta(converteConta(frconta));
				
				serFornecedor.salvar(fornecedor);
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Converte String em Conta (busca no banco de dados)
	 ****************************************************************************/	
	private Conta converteConta(String reduzida) {
		Conta conta = serConta.buscarPorReduzida(reduzida);
		return conta;
	}
}
