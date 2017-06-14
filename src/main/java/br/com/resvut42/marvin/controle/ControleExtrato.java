package br.com.resvut42.marvin.controle;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.BancoLcto;
import br.com.resvut42.marvin.relatorio.RelExtrato;
import br.com.resvut42.marvin.servico.SerBanco;
import br.com.resvut42.marvin.util.FacesMessages;
import br.com.resvut42.marvin.util.R42Data;

/****************************************************************************
 * Classe controle para extrato bancário
 * 
 * @author: Bob-Odin - 08/06/2017
 ****************************************************************************/
@Named
@ManagedBean
public class ControleExtrato implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/

	private static final long serialVersionUID = 1L;

	private Banco banco;
	private Date dataInicio;
	private Date dataFinal;
	private List<BancoLcto> lancamentos;

	@Autowired
	SerBanco serBanco;
	@Autowired
	FacesMessages mensagens;
	
	public void iniciar() {
		banco = new Banco();
		dataInicio = null;
		dataFinal = null;
		lancamentos = null; //new ArrayList<>();
	}

	/****************************************************************************
	 * Resgata o Banco selecionado no dialogo
	 ****************************************************************************/
	public void bancoSelecionado(SelectEvent event) {
		banco = (Banco) event.getObject();
	}

	/****************************************************************************
	 * Efetua a pesquisa de lançamentos bancário
	 ****************************************************************************/
	public void pesquisar() {
		
		boolean localErro = false;
		
		if (getBanco() == null || getBanco().getIdBanco() == null || getDataInicio() == null || getDataFinal() == null) {			
			mensagens.error("Informar todos os campos obrigatórios");	
			localErro = true;			
		} else if(R42Data.inicialMaiorFinal(getDataInicio(), getDataFinal())){
			mensagens.error("Data final menor que data inicial");
			localErro = true;
		} else {
			lancamentos = serBanco.lancamentos(getBanco(), getDataInicio(), getDataFinal());
			localErro = false;
		}
		
		if(localErro){
			FacesContext.getCurrentInstance().validationFailed();
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-dlg", "frm:selecaoBanco"));			
		}
	
	}

	/****************************************************************************
	 * Imprime o extrato bancario
	 ****************************************************************************/	
	public void imprimir(){
		try {
			RelExtrato relatorio = new RelExtrato();
			relatorio.setParametros("banco", getBanco().getIdeDescricao());
			relatorio.setParametros("periodo", R42Data.dateToString(dataInicio) +" - "+ R42Data.dateToString(dataFinal));
			relatorio.setLista(getLancamentos());
			relatorio.gerar();
		} catch (Exception e) {
			e.printStackTrace();
			mensagens.error(e.getMessage());
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
		}		
	}
	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<BancoLcto> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<BancoLcto> lancamentos) {
		this.lancamentos = lancamentos;
	}

}
