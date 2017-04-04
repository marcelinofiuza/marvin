package br.com.resvut42.marvin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.resvut42.marvin.entidade.Cliente;

/****************************************************************************
 * Classe Repositório da entidade Cliente Desenvolvido por:
 * 
 * @author Gustavo - 30/03/2017
 ****************************************************************************/
@Repository
public interface RepCliente extends JpaRepository<Cliente, Long> {

}