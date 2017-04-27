package br.com.resvut42.marvin.servico;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.BancoLcto;
import br.com.resvut42.marvin.entidade.Fornecedor;
import br.com.resvut42.marvin.entidade.Pagar;
import br.com.resvut42.marvin.repositorio.RepPagar;

/****************************************************************************
 * Classe Serviço Regras de negócio dos Lançamentos a Pagar:
 * 
 * @author Thayro Rodrigues - 24/04/2017
 ****************************************************************************/
@Service
public class SerPagar {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepPagar repPagar;
	@Autowired
	SerBancoLcto serBancoLcto;
	
	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Pagar pagar) throws Exception {
		try {
			validarSalvar(pagar);
			repPagar.save(pagar);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Metodo para Validar antes de Salvar
	 ****************************************************************************/
	public void validarSalvar(Pagar pagar) throws Exception {
		try {
			
			BigDecimal totalBase = new BigDecimal(0);
			
			for (BancoLcto bancoLcto : pagar.getBaixas()) {
				//soma total base
				totalBase = totalBase.add(bancoLcto.getValorBase());
				//Valida o novo lancamento bancario
				if(bancoLcto.getIdLcto() == null){
					serBancoLcto.validarSalvar(bancoLcto);
				}				
			}			
			
			//A soma do valor Base, não pode ser maior que o valor do título
			if(totalBase.compareTo(pagar.getValor()) > 0){
				throw new Exception("Soma valor base não pode ser maior que valor do título");
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Pagar pagar) throws Exception {
		try {
			validaExcluir(pagar);
			repPagar.delete(pagar);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Validar antes da  exclusão
	 ****************************************************************************/
	public void validaExcluir(Pagar pagar) throws Exception {
		//verifica se já existe baixa no titulo
		if(pagar.getBaixas().size() != 0){
			throw new Exception("o documento "+pagar.getDocumento()+" já está com baixa!");
		}
	}
	
	/****************************************************************************
	 * Estornar baixa de titulos
	 ****************************************************************************/
	public void estornarBaixa(Pagar pagar, Long idBaixa) throws Exception {
		try {
			
			BancoLcto bancoLcto = null;
			
			//Verifica se o idBaixa está na lista de baixa do receber
			boolean contem = false;
			for (BancoLcto baixa : pagar.getBaixas()) {
				if(baixa.getIdLcto() == idBaixa){
					bancoLcto = baixa;					
					contem = true;
					break;
				}
			}
			if(!contem){
				throw new Exception("Id não está na lista de baixas");
			}
						
			//Verifica se o lançamento pode ser excluido
			serBancoLcto.validarExcluir(bancoLcto);			
			pagar.getBaixas().remove(bancoLcto);
			
			repPagar.save(pagar);
			serBancoLcto.excluir(bancoLcto);
						
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Metodo para Listar lançamentos a pagar por fornecedor
	 ****************************************************************************/
	public List<Pagar> listarPorFornecedor(Fornecedor fornecedor){
		return repPagar.findByFornecedor(fornecedor);
	}
}
