package br.com.resvut42.marvin.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.resvut42.marvin.entidade.Fornecedor;

/****************************************************************************
 * Classe Repositório da entidade Fornecedor Desenvolvido por:
 * 
 * @author Gustavo - 30/03/2017
 ****************************************************************************/
@Repository
public interface RepFornecedor extends JpaRepository<Fornecedor, Long> {
	
	/****************************************************************************
	 * Retornar uma lista de fornecedor filtrando por Like RazaoSocial e Fantasia
	 ****************************************************************************/
	List<Fornecedor> findByRazaoSocialContainingAndFantasiaContaining(String razaoSocial, String fantasia);

	/****************************************************************************
	 * Retornar um fornecedor fazendo busca pela fantasia
	 ****************************************************************************/		
	Fornecedor findByFantasia(String fantasia);

}
