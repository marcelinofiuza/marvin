package br.com.resvut42.marvin.enums;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

/****************************************************************************
 * Classe controle para resgates de enums
 * 
 * @author: Bob-Odin - 22/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleEnums implements Serializable {

	private static final long serialVersionUID = 1L;

	/****************************************************************************
	 * -- Lista de opções de enums
	 ****************************************************************************/

	public Febraban[] getFebraban() {
		return Febraban.values();
	}

	public Estado[] getEstados() {
		return Estado.values();
	}

	public AnaliticaSintetica[] getTiposConta() {
		return AnaliticaSintetica.values();
	}

	public AtivaItativa[] getStatusConta() {
		return AtivaItativa.values();
	}

	public Natureza[] getNaturezas() {
		return Natureza.values();
	}

	public RamoAtividade[] getRamosAtividade() {
		return RamoAtividade.values();
	}

	public DebitoCredito[] getDebitoCredito() {
		return DebitoCredito.values();
	}
	
	public OrigemLcto[] getOrigemLcto(){
		return OrigemLcto.values();
	}
}
