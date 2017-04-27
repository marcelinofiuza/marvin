package br.com.resvut42.marvin.servico;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.BancoLcto;
import br.com.resvut42.marvin.entidade.Boleto;
import br.com.resvut42.marvin.entidade.Cliente;
import br.com.resvut42.marvin.entidade.Receber;
import br.com.resvut42.marvin.repositorio.RepReceber;

/****************************************************************************
 * Classe Serviço Regras de negócio dos Lançamentos a Receber:
 * 
 * @author Bob-Odin - 20/04/2017
 ****************************************************************************/
@Service
public class SerReceber {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepReceber repReceber;
	@Autowired
	SerBancoLcto serBancoLcto;	
	
	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Receber receber) throws Exception {
		try {
			validarSalvar(receber);
			repReceber.save(receber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e salvar Lista
	 ****************************************************************************/
	public void salvar(List<Receber> listaReceber) throws Exception {
		try {
			for (Receber receber : listaReceber) {
				validarSalvar(receber);
			}			
			repReceber.save(listaReceber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar antes de Salvar
	 ****************************************************************************/
	public void validarSalvar(Receber receber) throws Exception {		
		try {
	
			BigDecimal totalBase = new BigDecimal(0);

			for (BancoLcto bancoLcto : receber.getBaixas()) {
				//soma total base
				totalBase = totalBase.add(bancoLcto.getValorBase());
				//Valida o novo lancamento bancario
				if(bancoLcto.getIdLcto() == null){
					serBancoLcto.validarSalvar(bancoLcto);
				}				
			}
			
			//A soma do valor Base, não pode ser maior que o valor do título
			if(totalBase.compareTo(receber.getValor()) > 0){
				throw new Exception("Soma valor base não pode ser maior que valor do título");
			}
				
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}			
	}
	
	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Receber receber) throws Exception {
		try {
			validaExcluir(receber);
			repReceber.delete(receber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir uma lista de receber
	 ****************************************************************************/
	public void excluir(List<Receber> listaReceber) throws Exception {
		try {
			for (Receber receber : listaReceber) {
				validaExcluir(receber);
			}
			repReceber.delete(listaReceber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Validar a exclusão de receber
	 ****************************************************************************/
	public void validaExcluir(Receber receber) throws Exception {
		//verifica se já existe baixa no titulo
		if(receber.getBaixas().size() != 0){
			throw new Exception("o documento "+receber.getDocumento()+" já está com baixa!");
		}
	}
	
	/****************************************************************************
	 * Estornar baixa de titulos
	 ****************************************************************************/
	public void estornarBaixa(Receber receber, Long idBaixa) throws Exception {

		try {
			
			BancoLcto bancoLcto = null;
			
			//Verifica se o idBaixa está na lista de baixa do receber
			boolean contem = false;
			for (BancoLcto baixa : receber.getBaixas()) {
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
			receber.getBaixas().remove(bancoLcto);
			
			repReceber.save(receber);			
			serBancoLcto.excluir(bancoLcto);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}	

	/****************************************************************************
	 * Metodo para Listar lançamentos a receber por cliente
	 ****************************************************************************/
	public List<Receber> listarPorCliente(Cliente cliente) {
		return repReceber.findByCliente(cliente);
	}

	/****************************************************************************
	 * Metodo para Listar lançamentos a receber por boleto
	 ****************************************************************************/
	public List<Receber> listarPorBoleto(Boleto boleto) {
		return repReceber.findByBoleto(boleto);
	}
}
