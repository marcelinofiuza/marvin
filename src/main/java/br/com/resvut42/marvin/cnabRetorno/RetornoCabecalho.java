package br.com.resvut42.marvin.cnabRetorno;

import java.util.Date;

import br.com.resvut42.marvin.enums.Febraban;
import br.com.resvut42.marvin.enums.LayoutCnab;

public class RetornoCabecalho {

	private String arquivo;
	private String codigoEmpresa;
	private String nomeEmpresa;
	private Febraban febraban;
	private LayoutCnab layoutCnab;
	private Date dataGravacao;
	private Date dataCredito;

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public Febraban getFebraban() {
		return febraban;
	}

	public void setFebraban(Febraban febraban) {
		this.febraban = febraban;
	}

	public LayoutCnab getLayoutCnab() {
		return layoutCnab;
	}

	public void setLayoutCnab(LayoutCnab layoutCnab) {
		this.layoutCnab = layoutCnab;
	}

	public Date getDataGravacao() {
		return dataGravacao;
	}

	public void setDataGravacao(Date dataGravacao) {
		this.dataGravacao = dataGravacao;
	}

	public Date getDataCredito() {
		return dataCredito;
	}

	public void setDataCredito(Date dataCredito) {
		this.dataCredito = dataCredito;
	}

}
