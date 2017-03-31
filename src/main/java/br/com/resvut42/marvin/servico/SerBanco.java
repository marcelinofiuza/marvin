package br.com.resvut42.marvin.servico;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.BancoLcto;
import br.com.resvut42.marvin.entidade.BancoPeriodo;
import br.com.resvut42.marvin.repositorio.RepBanco;
import br.com.resvut42.marvin.util.R42Data;

/****************************************************************************
 * Classe Serviço Regras de negócio do Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 01/03/2017
 ****************************************************************************/
@Service
public class SerBanco {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepBanco repBanco;

	/****************************************************************************
	 * Retorna se existe algum banco cadastro
	 ****************************************************************************/
	public boolean exiteBanco() {
		if (repBanco.count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Banco banco) throws Exception {
		try {
			validarSalvar(banco);
			repBanco.save(banco);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Banco banco) throws Exception {
		try {
			validarExclusao(banco);
			repBanco.delete(banco);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public Banco buscarPorId(Long id) {
		return repBanco.findOne(id);
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Banco> listarTodos() {
		return repBanco.findAll();
	}

	/****************************************************************************
	 * Metodo para montar os saldos dos lançamentos
	 ****************************************************************************/
	public void montaSaldo(Banco banco, Date inicio, Date fim) {

		Date dataDe = R42Data.inicioMes(inicio);
		Date dataAte = R42Data.fimMes(fim);

		BigDecimal saldoInicial = new BigDecimal(0);

		for (BancoPeriodo periodo : banco.getPeriodos()) {
			
			//se é abertura, existe saldo inicial
			if(periodo.isAbertura()){
				saldoInicial = periodo.getSaldoInicial();
			}
						
			// se o periodo é antes do solicitado e está fechado, não processa
			if (periodo.getDataInicio().compareTo(dataDe) < 0 && periodo.isFechado()) {				
				continue;
			}

			if (periodo.getDataFinal().compareTo(dataAte) > 0) {
				continue;
			}

			periodo.setSaldoInicial(saldoInicial);

			for (BancoLcto lancamento : periodo.getLancamentos()) {
				BigDecimal saldo = saldoInicial.add(lancamento.getValorLctoConvertido());
				lancamento.setSaldo(saldo);
				saldoInicial = saldo;
			}

		}
	}

	/****************************************************************************
	 * Metodo para montar os saldos dos lançamentos
	 ****************************************************************************/
	public void montaSaldo(Banco banco, Long idPeriodo) {
		Date inicio = banco.getPeriodo(idPeriodo).getDataInicio();
		Date fim = banco.getPeriodo(idPeriodo).getDataFinal();		
		montaSaldo(banco, inicio, fim);	
	}

	/****************************************************************************
	 * Metodo para montar os saldos dos lançamentos
	 ****************************************************************************/
	public void montaSaldo(Banco banco) {
		if (banco.getPeriodos().size() != 0) {
			int tam = banco.getPeriodos().size() - 1;
			Date inicio = banco.getPeriodos().get(0).getDataInicio();
			Date fim = banco.getPeriodos().get(tam).getDataFinal();
			montaSaldo(banco, inicio, fim);
		}
	}

	/****************************************************************************
	 * Metodo para retornar o periodo do banco a partir da data
	 ****************************************************************************/
	public BancoPeriodo selecionaPeriodo(Banco banco, Date data) {
		for (BancoPeriodo periodo : banco.getPeriodos()) {
			if (R42Data.dentroPeriodo(data, periodo)) {
				return periodo;
			}
		}
		return null;
	}

	/****************************************************************************
	 * Valida se o banco pode ser excluido
	 ****************************************************************************/
	public void validarSalvar(Banco banco) throws Exception {
		//Ajusta saldo inicial
		boolean fechado = true;
		for (BancoPeriodo periodo : banco.getPeriodos()) {							
			if(periodo.isFechado()){
				periodo.setAbertura(true);
				fechado = true;
			}else if(fechado){
				periodo.setAbertura(true);
				fechado = false;
			}else{
				periodo.setAbertura(false);
				periodo.setSaldoInicial(new BigDecimal(0));
			}
		}
		
	}
		
	/****************************************************************************
	 * Valida se o banco pode ser excluido
	 ****************************************************************************/
	public void validarExclusao(Banco banco) throws Exception {
		if (banco.getPeriodos().size() != 0) {
			throw new Exception("Necessário excluir os periodos antes!");
		}
	}
}
