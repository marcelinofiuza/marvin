package br.com.resvut42.marvin.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.resvut42.marvin.entidade.Fornecedor;
import br.com.resvut42.marvin.entidade.Pagar;

/****************************************************************************
 * Classe Repositório da entidade Pagar Desenvolvido por:
 * 
 * @author Thayro Rodrigues - 24/04/2017
 ****************************************************************************/

@Repository
public interface RepPagar extends JpaRepository<Pagar, Long> {

	/****************************************************************************
	 * Retornar uma lista de pagar por fornecedor
	 ****************************************************************************/
	List<Pagar> findByFornecedor(Fornecedor fornecedor);
	
}
