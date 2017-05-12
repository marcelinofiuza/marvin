package br.com.resvut42.marvin.cnabRemessa;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import br.com.resvut42.marvin.entidade.Boleto;
import br.com.resvut42.marvin.entidade.BoletoItem;
import br.com.resvut42.marvin.entidade.Carteira;
import br.com.resvut42.marvin.entidade.Empresa;
import br.com.resvut42.marvin.util.R42Util;

/****************************************************************************
 * Classe Base para geração arquivo CNAB
 * 
 * @author: Bob-Odin - 17/04/2017
 ****************************************************************************/
public abstract class CnabRemessa {

	protected Boleto boleto;
	protected Carteira carteira;
	protected Empresa empresa;
	protected BoletoItem boletoItem;
	protected PrintWriter arquivo;

	protected Long linha = new Long(0);

	protected abstract void header();

	protected abstract void detalhe();

	protected abstract void footer();

	/****************************************************************************
	 * Seta os boletos a serem gerados
	 ****************************************************************************/
	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

	/****************************************************************************
	 * Seta o item do boleto
	 ****************************************************************************/
	public void setBoletoItem(BoletoItem boletoItem) {
		this.boletoItem = boletoItem;
	}

	/****************************************************************************
	 * Seta a carteira ser utilizada
	 ****************************************************************************/
	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}
	
	/****************************************************************************
	 * Gera o arquivo de saida
	 ****************************************************************************/	
	public void gerarArquivo(PrintWriter arquivo){
		this.arquivo = arquivo;		
		header();
		detalhe();
		footer();		
	}

	/****************************************************************************
	 * Retorna uma string com os numero de spaco informado
	 ****************************************************************************/
	protected String getSpace(int space) {
		String espaco = "";
		espaco = String.format("%-" + space + "s", espaco);
		return espaco;
	}

	/****************************************************************************
	 * Retorna uma string com os numero de spaco informado
	 ****************************************************************************/
	protected String getZero(int quantidade) {
		return StringUtils.leftPad("", quantidade, "0");
	}

	/****************************************************************************
	 * Retorna o codigo Febraban do banco
	 ****************************************************************************/
	protected String getCodigoFebraban() {
		return boleto.getBanco().getFebraban().getCodigo();
	}

	/****************************************************************************
	 * Retorna o codigo mestre da carteira
	 ****************************************************************************/
	protected String getCodigoMestre(int tamanho) {
		String codMestre = StringUtils.leftPad(carteira.getCodMestre(), tamanho, "0");
		return codMestre;
	}

	/****************************************************************************
	 * Configura data DDMMAA
	 ****************************************************************************/
	protected String configuraData(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		return sdf.format(data.getTime());
	}

	/****************************************************************************
	 * Retorna o nome da empresa
	 ****************************************************************************/
	protected String getNomeEmpresa(int tamanho) {
		String formato = "%-80s";
		String nome = String.format(formato, empresa.getRazaoSocial());
		return nome.substring(0, tamanho);
	}

	/****************************************************************************
	 * Retorna a sequencia do arquivo
	 ****************************************************************************/
	protected String getSequenciaArquivo() {
		Long seqArquivo = boleto.getBanco().getSeqArquivo();
		if (seqArquivo == null) {
			seqArquivo = new Long(0);
		}
		seqArquivo++;
		String numero = seqArquivo.toString();
		return StringUtils.leftPad(numero, 7, "0");
	}

	/****************************************************************************
	 * Retorna o numero da proxima linha
	 ****************************************************************************/
	protected String getProximaLinha() {
		linha++;
		String numero = linha.toString();
		return StringUtils.leftPad(numero, 6, "0");
	}

	/****************************************************************************
	 * Retorna a identificação da empresa
	 ****************************************************************************/
	protected String getIdentEmpresa(int tamanho) {
		String ident = "";
		ident = ident + StringUtils.leftPad(carteira.getCodCarteira(), 3, "0");
		ident = ident + StringUtils.leftPad(carteira.getAgencia(), 5, "0");
		ident = ident + StringUtils.leftPad(carteira.getConta(), 8, "0");
		ident = StringUtils.leftPad(ident, tamanho, "0");
		
		return ident;
	}

	/****************************************************************************
	 * Retorna a identificação do titulo no sistema
	 ****************************************************************************/
	protected String getNumeroBoleto() {
		String numero = "";
		numero = numero + StringUtils.leftPad(boleto.getIdBoleto().toString(), 9, "0");
		numero = numero + StringUtils.leftPad(boletoItem.getIdItem().toString(), 9, "0");
		numero = numero + StringUtils.leftPad(boletoItem.getCliente().getIdCliente().toString(), 7, "0");
		return numero;
	}

	/****************************************************************************
	 * Retorna a identificação do nosso numero + digito
	 ****************************************************************************/
	protected String getNossoNumero() {
		String nossoNumero = "";
		nossoNumero = nossoNumero + StringUtils.leftPad(boletoItem.getIdItem().toString(), 11, "0");
		nossoNumero = nossoNumero + calculoDigito(nossoNumero);
		return nossoNumero;
	}

	/****************************************************************************
	 * Calcular digito
	 ****************************************************************************/
	protected String calculoDigito(String nossoNumero) {
		int[] peso = { 2, 7, 6, 5, 4, 3, 2, 7, 6, 5, 4, 3, 2 };
		String numero = StringUtils.leftPad(carteira.getCodCarteira(), 2, "0");
		numero = numero + nossoNumero;
		int soma = 0;
		for (int indice = numero.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(numero.substring(indice, indice + 1));
			soma += digito * peso[peso.length - numero.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? "P" : String.valueOf(soma);
	}

	/****************************************************************************
	 * Retorna a identificação do nosso numero + digito
	 ****************************************************************************/
	protected String getDocumento() {
		String documento = "";
		documento = StringUtils.leftPad(boletoItem.getIdItem().toString(), 10, "0");
		return documento;
	}

	/****************************************************************************
	 * Retorna a data de vencimento formatada
	 ****************************************************************************/
	protected String getVctoTitulo() {
		return configuraData(boletoItem.getVencimento());
	}

	/****************************************************************************
	 * Retorna o valor do titulo
	 ****************************************************************************/
	protected String getValorTitulo() {
		String valorTitulo = boletoItem.getTotalItem().toString();
		valorTitulo = R42Util.removePontos(valorTitulo);
		valorTitulo = StringUtils.leftPad(valorTitulo, 13, "0");
		return valorTitulo;
	}

	/****************************************************************************
	 * Retorna a data de emissão do titulo
	 ****************************************************************************/
	protected String getDataEmissao() {
		return configuraData(boleto.getLancamento());
	}

	/****************************************************************************
	 * Retorna o tipo mais mais o numero da inscrição (01CPF/02CNPJ)
	 ****************************************************************************/
	protected String getInscricao() {

		String inscricao = "99" + getZero(14);

		if (boletoItem.getCliente().getCnpj() != null && !boletoItem.getCliente().getCnpj().isEmpty()) {
			inscricao = R42Util.removePontos(boletoItem.getCliente().getCnpj());
			inscricao = StringUtils.leftPad(inscricao, 14, "0");
			inscricao = "02" + inscricao;
			return inscricao;
		}

		if (boletoItem.getCliente().getCpf() != null && !boletoItem.getCliente().getCpf().isEmpty()) {
			inscricao = R42Util.removePontos(boletoItem.getCliente().getCpf());
			inscricao = StringUtils.leftPad(inscricao, 14, "0");
			inscricao = "01" + inscricao;
			return inscricao;
		}

		return inscricao;
	}

	/****************************************************************************
	 * Retorna o nome do pagador (Cliente)
	 ****************************************************************************/
	protected String getNomeCliente(int tamanho) {
		String formato = "%-80s";
		String nome = String.format(formato, boletoItem.getCliente().getRazaoSocial());
		return nome.substring(0, tamanho);
	}

	/****************************************************************************
	 * Retorna o endereço do pagador (Cliente)
	 ****************************************************************************/
	protected String getEnderecoCliente(int tamanho) {
		String formato = "%-100s";

		String endereco = boletoItem.getCliente().getEndereco().getTipoLogradouro() + " "
				+ boletoItem.getCliente().getEndereco().getLogradouro() + ", "
				+ boletoItem.getCliente().getEndereco().getNumero() + ", "
				+ boletoItem.getCliente().getEndereco().getComplemento();

		endereco = endereco.trim();
		endereco = String.format(formato, endereco);

		return endereco.substring(0, tamanho);
	}

	/****************************************************************************
	 * Retorna o cep do endereço do pagador (Cliente)
	 ****************************************************************************/
	protected String getCepEndereco() {
		String cep = R42Util.removePontos(boletoItem.getCliente().getEndereco().getCep());
		cep = StringUtils.leftPad(cep, 8, "0");
		return cep;
	}

	/****************************************************************************
	 * Retorna a linha de mensagem - String linha (1,2,3,4,5,6,7,8)
	 ****************************************************************************/
	protected String getMensagem(String linha, int tamanho) {

		String formato = "%-100s";
		String mensagem = "";

		if (linha.equals("1")) {
			mensagem = boleto.getInsLinha01();
		} else if (linha.equals("2")) {
			mensagem = boleto.getInsLinha02();
		} else if (linha.equals("3")) {
			mensagem = boleto.getInsLinha03();
		} else if (linha.equals("4")) {
			mensagem = boleto.getInsLinha04();
		} else if (linha.equals("5")) {
			mensagem = boleto.getInsLinha05();
		} else if (linha.equals("6")) {
			mensagem = boleto.getInsLinha06();
		} else if (linha.equals("7")) {
			mensagem = boleto.getInsLinha07();
		} else {
			mensagem = boleto.getInsLinha08();
		}

		String valor0 = R42Util.converteValor("R$", boletoItem.getValor0());
		String valor1 = R42Util.converteValor("R$", boletoItem.getValor1());
		String valor2 = R42Util.converteValor("R$", boletoItem.getValor2());
		String valor3 = R42Util.converteValor("R$", boletoItem.getValor3());

		mensagem = mensagem.replace("{VALOR0}", valor0);
		mensagem = mensagem.replace("{VALOR1}", valor1);
		mensagem = mensagem.replace("{VALOR2}", valor2);
		mensagem = mensagem.replace("{VALOR3}", valor3);

		mensagem = String.format(formato, mensagem);
		return mensagem.substring(0, tamanho);

	}

}
