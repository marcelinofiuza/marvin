package br.com.resvut42.marvin.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.BancoLcto;
import br.com.resvut42.marvin.repositorio.RepBancoLcto;
import br.com.resvut42.marvin.util.R42Data;

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

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(BancoLcto bancoLcto) throws Exception {
		try {
			validar(bancoLcto);
			repBancoLcto.save(bancoLcto);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Metodo para Validar e excluir lançamento
	 ****************************************************************************/
	public void excluir(BancoLcto bancoLcto) throws Exception {
		try {
			validar(bancoLcto);
			repBancoLcto.delete(bancoLcto.getIdLcto());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}	

	/****************************************************************************
	 * Verifica os dados antes de salvar
	 ****************************************************************************/
	public void validar(BancoLcto bancoLcto) throws Exception{
		// Verifica se informou a conta contábil
		if (bancoLcto.getConta().getIdConta() == null) {
			throw new Exception("Conta é obrigatório!");
		}
		
		if(!R42Data.dentroPeriodo(bancoLcto.getDataLcto(), bancoLcto.getBancoPeriodo())){
			throw new Exception("Data fora do periodo de lançamento!");
		}
		
		if(bancoLcto.getBancoPeriodo().isFechado()){
			throw new Exception("Periodo de lançamento fechado!");
		}	
	}
	
}
