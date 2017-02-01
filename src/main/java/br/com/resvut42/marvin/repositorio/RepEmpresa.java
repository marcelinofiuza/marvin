package br.com.resvut42.marvin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.resvut42.marvin.entidade.Empresa;

/****************************************************************************/
//Classe Repositório da entidade Empresa
//Desenvolvido por : Bob-Odin 
//Criado em 01/02/2017 
/****************************************************************************/

@Repository
public interface RepEmpresa extends JpaRepository<Empresa, Long> {

}
