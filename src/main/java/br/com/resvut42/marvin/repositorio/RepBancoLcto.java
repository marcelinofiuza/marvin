package br.com.resvut42.marvin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.resvut42.marvin.entidade.BancoLcto;

/****************************************************************************
 * Classe Repositório para Lançamentos bancários:
 * 
 * @author Bob-Odin - 22/03/2017
 ****************************************************************************/
@Repository
public interface RepBancoLcto extends JpaRepository<BancoLcto, Long> {

}
