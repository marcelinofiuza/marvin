package br.com.resvut42.marvin.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import br.com.resvut42.marvin.entidade.Cliente;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;


/****************************************************************************
 * Classe para criação de relatório
 * 
 * @author: Bob-Odin - 31/05/2017
 ****************************************************************************/
public class Relatorio {

	private String arquivoJasper;
	private HttpServletResponse response;
	private FacesContext context;
	private ByteArrayOutputStream baos;
	private InputStream stream;
	private Map<String,Object> parameters;

	/************************************************************************
	 * Abrir relatório
	 * @param arquivoJasper = nome do arquivo jrxml sem extenção
	 ************************************************************************/
	public Relatorio(String arquivoJasper) {
		this.arquivoJasper = arquivoJasper;
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) context.getExternalContext().getResponse();
		this.parameters = new HashMap<String,Object>();		
	}

	/************************************************************************
	 * Executa relatorio de saida
	 ************************************************************************/	
	public void getRelatorio(List<Cliente> lista){
		
		baos = new ByteArrayOutputStream();
		
        try {
        	        	
        	stream = Relatorio.class.getResourceAsStream("/jasper/Clientes.jasper");
        	JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream); 
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(lista));
			JasperExportManager.exportReportToPdfStream(print, baos);
			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");
			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			context.responseComplete();
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}

}
