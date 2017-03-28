package br.com.resvut42.marvin.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.repositorio.RepBancoLcto;

/****************************************************************************
 * Classe Serviço Regras de negócio dos Lançamentos Bancarios:
 * 
 * @author Bob-Odin - 01/03/2017
 ****************************************************************************/
@Service
public class SerBancoLcto {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepBancoLcto repBancoLcto;

	/****************************************************************************
	 * Retorna se existe algum lançamento já efetuado
	 ****************************************************************************/
	public boolean exiteLcto() {
		if (repBancoLcto.count() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
