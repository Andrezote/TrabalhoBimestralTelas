package br.univel.orcamento;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.univel.cliente.Cliente;
import br.univel.produto.Produto;
import br.univel.produto.TelaPrincipal;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ReportManager {

	private static final String JASPER_CONTATO = "C:\\Users\\Andre Tosetto\\JaspersoftWorkspaceV2\\MyReports\\Enois.jasper";

	public void imprimir() {
		JasperPrint jasperPrintPDF = getPrint();
		Locale locale = Locale.getDefault();
		JasperViewer.viewReport(jasperPrintPDF, false, locale);
	}
	

	public void exportar() {
		JasperPrint jasperPrintPDF = getPrint();
		
		SimpleDateFormat sdf = new SimpleDateFormat("(yyyy-MM-dd)_HH-mm-ss");
		String data = sdf.format(new Date());

		String nomePdf = "relatorio_contatos_" + data + ".pdf";
		
		try {
			JasperExportManager.exportReportToPdfFile(jasperPrintPDF, nomePdf);
			Desktop.getDesktop().open(new File(nomePdf));
		} catch (JRException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private JasperPrint getPrint() {
		Connection con = ConexaoDB.getInstance().getConnection();
		try {
			return JasperFillManager.fillReport(JASPER_CONTATO, null, con);
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void exportarCustom(Orcamento o) {
		PainelOrcamento po = new PainelOrcamento();
		TelaPrincipal tp = new TelaPrincipal();
		
		
		String strFile = "C:\\Users\\Andre Tosetto\\JaspersoftWorkspaceV2\\MyReports\\Enois.jasper";
		
		OrcamentoDao dao = new OrcamentoDao();
		List<Produto> lista = dao.SelectProdutoOrcamento(o);
		BigDecimal bd = po.somar(lista);
		bd = bd.multiply(tp.getValorDolar());
		JRDataSource customDs = new MeuReportCustom(lista, bd);
		
		
		JasperPrint jp;
		
		SimpleDateFormat sdf = new SimpleDateFormat("(yyyy-MM-dd)_HH-mm-ss");
		String data = sdf.format(new Date());

		String nomePdf = "relatorio_contatos_" + data + ".pdf";
		
		
		try {
			jp = JasperFillManager.fillReport(strFile, null, customDs);
			JasperExportManager.exportReportToPdfFile(jp, nomePdf);
			Desktop.getDesktop().open(new File(nomePdf));
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
