package br.com.resvut42.marvin.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/****************************************************************************
 * Classe genérica para criação de relatório
 * 
 * @author: Bob-Odin - 31/05/2017
 ****************************************************************************/
public class Relatorio {

	public String arquivoJasper;
	public HttpServletResponse response;
	public FacesContext context;
	public ByteArrayOutputStream baos;
	public InputStream stream;
	public Map<String, Object> parameters;
	public String path = "/br/com/resvut42/marvin/jasper/";
	public List<Object> lista;

	/************************************************************************
	 * Seta parametros do relatorio
	 ************************************************************************/
	public void setParametro(String parametro, Object valor) {
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		parameters.put(parametro, valor);
	}

	/************************************************************************
	 * Seta arquivo jasper (Jrxml) arquivo deve estar em :
	 * /br/com/resvut42/marvin/jasper/
	 ************************************************************************/
	public void setArquivoJrxml(String arquivo) {
		this.arquivoJasper = arquivo;
	}

	/************************************************************************
	 * Seta Lista de dados a ser impresso
	 ************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaDados(List lista) {
		this.lista = lista;
	}

	/************************************************************************
	 * Executa relatorio de saida
	 ************************************************************************/
	public void geraRelatorio() throws Exception {

		if (this.arquivoJasper == null) {
			throw new Exception("Arquivo jasper não informado");
		}

		if (this.lista == null) {
			lista = new ArrayList<Object>();
		}

		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) context.getExternalContext().getResponse();
		this.baos = new ByteArrayOutputStream();

		try {

			stream = this.getClass().getResourceAsStream(path + arquivoJasper + ".jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(stream);

			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters,
					new JRBeanCollectionDataSource(lista));
			JasperExportManager.exportReportToPdfStream(print, baos);

			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setHeader("Content-disposition", "inline; filename=" + arquivoJasper + ".pdf");

			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();

			context.responseComplete();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		} catch (JRException e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}

	}

}
