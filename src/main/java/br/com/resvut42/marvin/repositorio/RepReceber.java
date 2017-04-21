package br.com.resvut42.marvin.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.resvut42.marvin.entidade.Boleto;
import br.com.resvut42.marvin.entidade.Cliente;
import br.com.resvut42.marvin.entidade.Receber;

/****************************************************************************
 * Classe Repositório para Lançamentos a receber
 * 
 * @author Bob-Odin - 20/04/2017
 ****************************************************************************/
@Repository
public interface RepReceber extends JpaRepository<Receber, Long> {

	/****************************************************************************
	 * Retornar uma lista de receber por cliente
	 ****************************************************************************/
	List<Receber> findByCliente(Cliente cliente);

	/****************************************************************************
	 * Retornar uma lista de receber por boleto
	 ****************************************************************************/
	List<Receber> findByBoleto(Boleto boleto);
	
}
