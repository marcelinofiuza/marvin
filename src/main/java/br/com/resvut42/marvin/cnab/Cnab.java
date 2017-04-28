package br.com.resvut42.marvin.cnab;

import br.com.resvut42.marvin.enums.Febraban;
import br.com.resvut42.marvin.enums.LayoutCnab;

/****************************************************************************
 * Classe para definir layout Cnab a ser utilizado
 * 
 * @author: Bob-Odin - 17/04/2017
 ****************************************************************************/
public class Cnab {

	public static BaseCnab getLayout(Febraban febraban, LayoutCnab layoutCnab){		
		BaseCnab cnab = null;
		
		if(febraban == Febraban.F_237 && layoutCnab == LayoutCnab.L400){
			return new Cnab237400();
		}
		
		return cnab;
	}
	
}
