package br.com.resvut42.marvin.pesquisa;

import java.util.HashMap;
import java.util.Map;

import org.primefaces.context.RequestContext;

public abstract class AbstrataPesquisa {

	/****************************************************************************
	 * Retorna o registro selecionado na lista
	 ****************************************************************************/
	public void selecionar(Object entidade) {
		RequestContext.getCurrentInstance().closeDialog(entidade);
	}

	/****************************************************************************
	 * Abre o xhtml em forma de dialogo
	 ****************************************************************************/
	public void abrirDialogo(String paginaPesquisa) {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentWidth", "800");
		opcoes.put("contentHeight", "450");
		opcoes.put("width", "800");
		opcoes.put("height", "450");

		RequestContext.getCurrentInstance().openDialog("resources/ajudapesquisa/" + paginaPesquisa, opcoes, null);
	}
}
