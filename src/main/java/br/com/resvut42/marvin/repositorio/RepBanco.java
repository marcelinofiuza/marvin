package br.com.resvut42.marvin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.resvut42.marvin.entidade.Banco;

/****************************************************************************
 * Classe Repositório da entidade Banco Desenvolvido por:
 * 
 * @author Bob-Odin - 01/03/2017
 ****************************************************************************/
@Repository
public interface RepBanco extends JpaRepository<Banco, Long> {

}
