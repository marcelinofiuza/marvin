package br.com.resvut42.marvin.cnabRemessa;

import br.com.resvut42.marvin.enums.Febraban;
import br.com.resvut42.marvin.enums.LayoutCnab;

/****************************************************************************
 * Classe para definir layout Cnab a ser utilizado
 * 
 * @author: Bob-Odin - 17/04/2017
 ****************************************************************************/
public class SelecionarCnabRemessa {

	public static CnabRemessa getLayout(Febraban febraban, LayoutCnab layoutCnab){		
		CnabRemessa cnab = null;
		
		if(febraban == Febraban.F_237 && layoutCnab == LayoutCnab.L400){
			return new Cnab237400();
		}
		
		return cnab;
	}
	
}
