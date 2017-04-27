package br.com.resvut42.marvin.cnab;

import br.com.resvut42.marvin.enums.Febraban;

/****************************************************************************
 * Classe para definir layout Cnab a ser utilizado
 * 
 * @author: Bob-Odin - 17/04/2017
 ****************************************************************************/
public class Cnab {

	public static BaseCnab getLayout(Febraban febraban, String layout){		
		BaseCnab cnab = null;
		
		if(febraban.getCodigo().equals("237") && layout.equals("400")){
			return new Cnab237400();
		}
		
		return cnab;
	}
	
}
