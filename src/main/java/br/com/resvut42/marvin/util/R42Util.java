package br.com.resvut42.marvin.util;

import java.io.File;

import org.jamel.dbf.DbfReader;

public class R42Util {

	/****************************************************************************
	 * Valida CPF
	 * 
	 * @param cpf
	 * 			- Numero do cpf a ser validado
	 * @param ponto
	 * 			- true retorna com ponto, false sem ponto
	 * @return - CPF, null se inválido
	 * 
	 ****************************************************************************/
	public static String validaCPF(String numCpf, boolean ponto) {

		String cpf = numCpf.replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", "").replace("-","");
		
		if (cpf.length() == 11) {
			Integer digito1 = calcularDigito(cpf.substring(0,9), "CPF");
			Integer digito2 = calcularDigito(cpf.substring(0,9)+digito1, "CPF");
			if(cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString())){
				if(ponto){
					return pontosCpf(cpf);
				}else{
					return cpf;
				}
			}
		}
		return null;
	}

	/****************************************************************************
	 * Valida CNPJ
	 * 
	 * @param cnpj
	 * 			- Numero do cnpj a ser validado
	 * @param ponto
	 * 			- true retorna com ponto, false sem ponto
	 * @return - CNPJ, null se inválido
	 * 
	 ****************************************************************************/
	public static String validaCNPJ(String numCnpj, boolean ponto) {

		String cnpj = numCnpj.replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", "").replace("-","");

		if (cnpj.length() == 14) {
			Integer digito1 = calcularDigito(cnpj.substring(0,12), "CNPJ");
			Integer digito2 = calcularDigito(cnpj.substring(0,12)+digito1, "CNPJ");
			if(cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString())){
				if(ponto){
					return pontosCnpj(cnpj);
				}else{
					return cnpj;
				}
			}
		}
		return null;
	}
	
	/****************************************************************************
	 * Calcula Digito modulo 11 para cpf ou cnpj
	 * 
	 * @param numero
	 *            - Numero a ser calculado o digito
	 * @param tipo
	 *            - tipo de calculo (CPF ou CNPJ) - default (CNPJ)
	 * @return - digito calculado
	 * 
	 ****************************************************************************/
	public static int calcularDigito(String numero, String tipo) {
		int[] peso;
		if (tipo.equalsIgnoreCase("CPF")) {
			int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
			peso = pesoCPF;
		} else {
			int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
			peso = pesoCNPJ;
		}

		int soma = 0;
		for (int indice = numero.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(numero.substring(indice, indice + 1));
			soma += digito * peso[peso.length - numero.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	/****************************************************************************
	 * Coloca pontos no CPF
	 * 
	 * @param numero
	 *            - Numero do cpf (12355589665)
	 * @return - CPF com ponto (123.555.896-65)
	 * 
	 ****************************************************************************/
	public static String pontosCpf(final String cpf) {

		String newCpf = cpf.replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", "").replace("-","");	
		
		return newCpf.substring(0, 3) + "." + 
				newCpf.substring(3, 6) + "." + 
				newCpf.substring(6, 9) + "-" + 
				newCpf.substring(9, 11);

	}
	
	/****************************************************************************
	 * Coloca pontos no CNPJ
	 * 
	 * @param numero
	 *            - Numero do cnpj (12225896000165)
	 * @return - CNPJ com ponto (12.225.896/0001-65)
	 * 
	 ****************************************************************************/
	public static String pontosCnpj(final String cnpj) {

		String newCnpj = cnpj.replace('(', ' ').replace(')', ' ').replaceAll("[ ./-]", "").replace("-","");	
		
		return newCnpj.substring(0, 2) + "." + 
				newCnpj.substring(2, 5) + "." + 
				newCnpj.substring(5, 8) + "/" + 
				newCnpj.substring(8, 12) + "-" +
				newCnpj.substring(12,14);

	}	
	
	/****************************************************************************
	 * Retorna o arquivo DbfReader
	 * 
	 * @param Arquivo
	 *            - path do arquivo "c:\temp\dbf\arquivo.dbf"
	 * @return - DbfReader
	 * 
	 ****************************************************************************/
	public static DbfReader lerDbf(String arquivo) throws Exception{	

//      "/src/resources/"+arquivo		
//		ClassLoader classLoader = new MigrarConta().getClass().getClassLoader();	
//		File file = new File(classLoader.getResource(arquivo).getFile());
		
		File file = new File(arquivo);
		try {		
			DbfReader dbfReader = new DbfReader(file);
			return dbfReader;		
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Não foi possível abrir o arquivo " + file.getAbsolutePath());
		}		 
	}
}
