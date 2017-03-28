package br.com.resvut42.marvin.servico;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.BancoPeriodo;
import br.com.resvut42.marvin.repositorio.RepBanco;

/****************************************************************************
 * Classe Serviço Regras de negócio do Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 01/03/2017
 ****************************************************************************/
@Service
public class SerBanco {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepBanco repBanco;

	/****************************************************************************
	 * Retorna se existe algum banco cadastro
	 ****************************************************************************/
	public boolean exiteBanco() {
		if (repBanco.count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Banco banco) throws Exception {
		try {
			repBanco.save(banco);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Banco banco) throws Exception {
		try {
			repBanco.delete(banco);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Banco> listarTodos() {
		return repBanco.findAll();
	}

	/****************************************************************************
	 * Ordena lista de Periodos pela data
	 * @param ordem - C-Crescente(default) e D-Decrescente
	 ****************************************************************************/
	public List<BancoPeriodo> ordenaPeriodo(List<BancoPeriodo> bancoPeriodos, String ordem){
		
        for (int i = 0; i < bancoPeriodos.size(); i++) {
            for (int j = i; j < bancoPeriodos.size(); j++) {

                if (comparaData(bancoPeriodos.get(i).getDataInicio(), bancoPeriodos.get(j).getDataFinal(), ordem)) {
                    BancoPeriodo temp = bancoPeriodos.get(j);
                    bancoPeriodos.set(j, bancoPeriodos.get(i));
                    bancoPeriodos.set(i, temp);
                }
                
            }
        }		
		
		return bancoPeriodos;		
	}
	
	private boolean comparaData(Date inicio, Date fim, String ordem){		
		if(ordem.equalsIgnoreCase("D")){
			return(inicio.compareTo(fim) < 0);
		}else{
			return(inicio.compareTo(fim) > 0);
		}			
	}
	
}
