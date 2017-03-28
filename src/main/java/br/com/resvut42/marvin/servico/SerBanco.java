package br.com.resvut42.marvin.servico;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.BancoLcto;
import br.com.resvut42.marvin.entidade.BancoPeriodo;
import br.com.resvut42.marvin.repositorio.RepBanco;

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
	public void montaSaldo(Banco banco) {

		BigDecimal saldoInicial = banco.getPeriodos().get(0).getSaldoInicial();

		for (BancoPeriodo periodo : banco.getPeriodos()) {

			// //Aqui fazer a verificação de periodo já fechado
			// if(periodo.isFechado()){
			// saldoInicial = periodo.getSaldoFinal();
			// }

			for (BancoLcto lancamento : periodo.getLancamentos()) {
				BigDecimal saldo = saldoInicial.add(lancamento.getValorLctoConvertido());
				lancamento.setSaldo(saldo);
				saldoInicial = saldo;
			}
		}
	}

}
