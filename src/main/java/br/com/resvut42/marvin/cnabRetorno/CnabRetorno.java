package br.com.resvut42.marvin.cnabRetorno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.primefaces.model.UploadedFile;

import br.com.resvut42.marvin.enums.Febraban;
import br.com.resvut42.marvin.enums.LayoutCnab;

/****************************************************************************
 * Classe Base para leitura de arquivo retorno
 * 
 * @author: Bob-Odin - 28/04/2017
 ****************************************************************************/
public abstract class CnabRetorno {
	
	protected RetornoCabecalho cabecalho;
	protected List<RetornoItem> listaItens;

	protected UploadedFile uploadedFile;

	protected abstract void processaCabecalho() throws Exception;
	protected abstract void processaItens() throws Exception;
		
	/****************************************************************************
	 * seta o arquivo de retorno
	 ****************************************************************************/
	public void setArquivo(UploadedFile arquivo){
		this.uploadedFile = arquivo;
	}

	/****************************************************************************
	 * Pega o buffer do arquivo
	 ****************************************************************************/	
	public BufferedReader getBuffer() throws Exception {
		try {
			return new BufferedReader(new InputStreamReader(this.uploadedFile.getInputstream()));
		} catch (Exception e) {
			throw new Exception("Erro ao abir o arquivo " + this.uploadedFile.getFileName());
		}
	}
	
	/****************************************************************************
	 * Retorna dados do cabeçalho
	 ****************************************************************************/
	public RetornoCabecalho getCabecalho() throws Exception {
		try {
			processaCabecalho();
			return cabecalho;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Retorna lista de itens
	 ****************************************************************************/
	public List<RetornoItem> getItens() throws Exception {
		try {
			processaItens();
			return listaItens;
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Define Banco e Layout
	 ****************************************************************************/	
	public void defineBancoLayout() throws Exception {
		try {
			
			BufferedReader bufferedReader = getBuffer();
			String linha = bufferedReader.readLine();
			String buffer = String.valueOf(linha.length());
			
			cabecalho.setLayoutCnab(converteLayoutCnab(buffer));
			
			if (cabecalho.getLayoutCnab() == LayoutCnab.L400) {
				cabecalho.setFebraban(converteFebraban(linha.substring(76, 79)));
			} else if (cabecalho.getLayoutCnab() == LayoutCnab.L240) {
				throw new Exception("Buffer de "+linha.length()+" posições não configurado!");
			} else {
				throw new Exception("Buffer de "+linha.length()+" posições não configurado!");
			}	

		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}
	}	
	
	/****************************************************************************
	 * 
	 * Metodos privados
	 * 
	 ****************************************************************************/	

	/****************************************************************************
	 * Converte String em Febraban
	 ****************************************************************************/
	protected Febraban converteFebraban(String codFebraban) {
		Febraban cod = null;
		if (codFebraban != null && !codFebraban.isEmpty()) {
			cod = Febraban.valueOf("F_" + codFebraban);
		}
		return cod;
	}

	/****************************************************************************
	 * Converte String em LayoutCnab
	 ****************************************************************************/
	protected LayoutCnab converteLayoutCnab(String buffer) {
		LayoutCnab cod = null;
		if (buffer != null && !buffer.isEmpty()) {
			cod = LayoutCnab.valueOf("L" + buffer);
		}
		return cod;
	}
	
	/****************************************************************************
	 * Converte String em BigDecimal
	 ****************************************************************************/
	protected BigDecimal converteValor(String valor){		
		BigInteger inteiro = new BigInteger(valor);		
		BigDecimal valorConvertido = new BigDecimal(inteiro, 2);		
		return valorConvertido;
	}
	
	
}
