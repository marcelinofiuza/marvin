package br.com.resvut42.marvin.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.BancoLcto;
import br.com.resvut42.marvin.enums.OrigemLcto;
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
			validarSalvar(bancoLcto);
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
			validarExcluir(bancoLcto);
			repBancoLcto.delete(bancoLcto.getIdLcto());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Verifica os dados antes de salvar
	 ****************************************************************************/
	public void validarSalvar(BancoLcto bancoLcto) throws Exception {
		// Verifica se informou a conta contábil
		if (bancoLcto.getConta().getIdConta() == null) {
			throw new Exception("Conta é obrigatório!");
		}

		if (!R42Data.dentroPeriodo(bancoLcto.getDataLcto(), bancoLcto.getBancoPeriodo())) {
			throw new Exception("Data fora do periodo de lançamento!");
		}

		if (bancoLcto.getBancoPeriodo().isFechado()) {
			throw new Exception("Periodo de lançamento do banco " + bancoLcto.getBancoPeriodo().getBanco().getIdBanco()
					+ " fechado!");
		}

		// verifica se é transferencia
		if (bancoLcto.getOrigemLcto() == OrigemLcto.TRF) {			
			if (bancoLcto.getTransferencia() != null) {				
				if(bancoLcto.getBancoPeriodo().getBanco().getIdBanco() == bancoLcto.getTransferencia().getBancoPeriodo().getBanco().getIdBanco()){
					throw new Exception("Banco destino igual banco origem");
				}				
				validarSalvar(bancoLcto.getTransferencia());
			}
		}
	}

	/****************************************************************************
	 * Verifica os dados antes de excluir
	 ****************************************************************************/
	public void validarExcluir(BancoLcto bancoLcto) throws Exception {

		if (!R42Data.dentroPeriodo(bancoLcto.getDataLcto(), bancoLcto.getBancoPeriodo())) {
			throw new Exception("Data fora do periodo de lançamento!");
		}

		if (bancoLcto.getBancoPeriodo().isFechado()) {
			throw new Exception("Periodo de lançamento do banco " + bancoLcto.getBancoPeriodo().getBanco().getIdBanco()
					+ " fechado!");
		}

		// verifica se é transferencia
		if (bancoLcto.getOrigemLcto() == OrigemLcto.TRF) {
			if (bancoLcto.getTransferencia() != null) {
				validarSalvar(bancoLcto.getTransferencia());
			} else {
				throw new Exception("Transferência deverá ser excluida no banco de origem!");
			}
		}
	}
}
