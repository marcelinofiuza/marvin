package br.com.resvut42.marvin.controle;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.resvut42.marvin.cnabRetorno.CnabRetorno;
import br.com.resvut42.marvin.cnabRetorno.RetornoCabecalho;
import br.com.resvut42.marvin.cnabRetorno.RetornoItem;
import br.com.resvut42.marvin.cnabRetorno.SelecionarCnabRetorno;
import br.com.resvut42.marvin.entidade.Banco;
import br.com.resvut42.marvin.entidade.BancoLcto;
import br.com.resvut42.marvin.entidade.BancoPeriodo;
import br.com.resvut42.marvin.entidade.Receber;
import br.com.resvut42.marvin.enums.DebitoCredito;
import br.com.resvut42.marvin.enums.OrigemLcto;
import br.com.resvut42.marvin.servico.SerBanco;
import br.com.resvut42.marvin.servico.SerReceber;
import br.com.resvut42.marvin.util.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Boleto Retorno
 * 
 * @author: Bob-Odin - 28/04/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleBoletoRetorno implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private RetornoCabecalho cabecalho;
	private List<RetornoItem> itens;

	@Autowired
	SerReceber serReceber;
	@Autowired
	SerBanco serBanco;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Confirma a seleção do arquivo
	 ****************************************************************************/
	public void upload(FileUploadEvent event) {
		try {
			UploadedFile uploadedFile = event.getFile();
			CnabRetorno cnab = SelecionarCnabRetorno.getRetorno(uploadedFile);
			cabecalho = cnab.getCabecalho();
			itens = cnab.getItens();
			buscaReceber();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}
	}

	/****************************************************************************
	 * Selecionar o Receber pelo documento
	 ****************************************************************************/
	public void buscaReceber() {
		for (RetornoItem retornoItem : itens) {
			Long idBoleto = Long.parseLong(retornoItem.getNumeroBoleto().substring(0, 9));
			Long idItem = Long.parseLong(retornoItem.getNumeroBoleto().substring(9, 18));
			retornoItem.setReceber(serReceber.buscarPorBoletoEBoletoItem(idBoleto, idItem));
		}
	}

	/****************************************************************************
	 * Processar Baixa dos titulos ok
	 ****************************************************************************/
	public void baixarTitulos() {
		List<Receber> listaReceber;
		try {
			listaReceber = new ArrayList<Receber>();
			for (RetornoItem retornoItem : itens) {
				
				Receber receber = retornoItem.getReceber();				
				Banco banco = receber.getBoleto().getBanco();
				
				BancoPeriodo periodo = serBanco.selecionaPeriodo(banco, retornoItem.getDataPagamento());
				
				if (periodo != null) {

					BancoLcto bancoLcto = new BancoLcto();
					bancoLcto.setBancoPeriodo(periodo);
					bancoLcto.setConta(receber.getCliente().getConta());
					bancoLcto.setOrigemLcto(OrigemLcto.DCR);
					bancoLcto.setTipoLcto(DebitoCredito.CREDITO);
					
					
					
					
				} else {
					FacesContext.getCurrentInstance().validationFailed();
					mensagens.error("Banco sem periodo aberto para o lançamento!");
					break;
				}
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/
	public RetornoCabecalho getCabecalho() {
		return cabecalho;
	}

	public List<RetornoItem> getItens() {
		return itens;
	}

}
