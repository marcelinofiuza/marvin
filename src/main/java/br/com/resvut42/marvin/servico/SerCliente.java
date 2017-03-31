package br.com.resvut42.marvin.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.Cliente;
import br.com.resvut42.marvin.repositorio.RepCliente;

@Service
public class SerCliente {
	
	
	
	@Autowired
	RepCliente repCliente ;
	
	
	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Cliente cliente) throws Exception {
		try {
			repCliente.save(cliente);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Cliente cliente) throws Exception {
		try {
			repCliente.delete(cliente);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public Cliente buscarPorId(Long id) {
		return repCliente.findOne(id);
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Cliente> listarTodos() {
		return repCliente.findAll();
	}

}
