package br.com.resvut42.marvin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.resvut42.marvin.entidade.Fornecedor;


@Repository
public interface RepFornecedor extends JpaRepository<Fornecedor, Long> {

}
