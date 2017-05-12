package br.com.resvut42.marvin.cnabRetorno;

import org.primefaces.model.UploadedFile;

import br.com.resvut42.marvin.enums.Febraban;
import br.com.resvut42.marvin.enums.LayoutCnab;

/****************************************************************************
 * Classe para definir layout Cnab de retorno a ser utilizado
 * 
 * @author: Bob-Odin - 28/04/2017
 ****************************************************************************/
public class SelecionarCnabRetorno {

	public static CnabRetorno getRetorno(UploadedFile arquivo) throws Exception {
		try {
			
			CnabRetorno cnab000 = new CnabRetorno000000();
			cnab000.setArquivo(arquivo);
			cnab000.defineBancoLayout();

			if (cnab000.getCabecalho().getFebraban() == Febraban.F_237
					&& cnab000.getCabecalho().getLayoutCnab() == LayoutCnab.L400) {

				CnabRetorno cnab = new CnabRetorno237400();
				cnab.setArquivo(arquivo);
				return cnab;

			} else {
				throw new Exception("CNAB de retorno não configurado!");
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
