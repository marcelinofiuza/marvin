package br.com.resvut42.marvin.migracao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.servico.SerConta;
import br.com.resvut42.marvin.util.FacesMessages;

/****************************************************************************
 * Classe controle para View da Importação
 * 
 * @author: Bob-Odin - 15/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleMigracao implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private DashboardModel model;
	
	private boolean contaMigrada;

	@Autowired
	private SerConta serConta;
	
	@Autowired
	private FacesMessages mensagens;
	@Autowired
	MigrarConta migrarConta;

	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	@PostConstruct
	public void init() {
		model = new DefaultDashboardModel();
		DashboardColumn column1 = new DefaultDashboardColumn();
		// DashboardColumn column2 = new DefaultDashboardColumn();
		// DashboardColumn column3 = new DefaultDashboardColumn();

		column1.addWidget("conta");
		// column1.addWidget("finance");

		// column2.addWidget("");
		// column2.addWidget("");
		
		// column3.addWidget("");

		model.addColumn(column1);
		// model.addColumn(column2);
		// model.addColumn(column3);
		
		//Verica se efetua a migração
		contaMigrada = serConta.exiteConta();
		
	}

	/****************************************************************************
	 * Executa a importação da conta
	 ****************************************************************************/
	public void migrarConta() {
		try {
			migrarConta.executar();
			mensagens.info("Migração das Contas efetuada com sucesso!");		
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		
		init();
	}

	/****************************************************************************
	 * Gets e Sets
	 ****************************************************************************/

	public DashboardModel getModel() {
		return model;
	}

	public boolean isContaMigrada() {
		return contaMigrada;
	}	
}
