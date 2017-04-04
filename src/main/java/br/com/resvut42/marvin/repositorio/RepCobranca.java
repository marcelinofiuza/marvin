package br.com.resvut42.marvin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.resvut42.marvin.entidade.Cobranca;

/****************************************************************************
 * Classe Repositório da entidade Cobranca Desenvolvido por:
 * 
 * @author Bob-Odin - 02/04/2017
 ****************************************************************************/
@Repository
public interface RepCobranca extends JpaRepository<Cobranca, Long> {

}
