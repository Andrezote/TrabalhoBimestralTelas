package br.univel.orcamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

import br.univel.produto.Produto;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class MeuReportCustom implements JRDataSource{
	
	private Iterator<Produto> it;
	private Produto selecionado;
	private BigDecimal total = new BigDecimal(0);
	

	public MeuReportCustom(List<Produto> list, BigDecimal bd) {
		this.it = list.iterator();
		this.total = bd.setScale(2, RoundingMode.DOWN);
	}

	@Override
public Object getFieldValue(JRField field) throws JRException {
		if ("id_produto".equals(field.getName())) {
			return String.valueOf(selecionado.getId());
		}
		if ("nome".equals(field.getName())) {
			return String.valueOf(selecionado.getDescricao());
		}
		if ("valor".equals(field.getName())) {
			return String.valueOf(selecionado.getValorDolar());
		}
		if ("qtd".equals(field.getName())) {
			return String.valueOf(selecionado.getQtd().multiply(selecionado.getQtd()));
		}
		if ("valor_total".equals(field.getName())) {
			return String.valueOf(total);
		}
		
		throw new RuntimeException("Deu ruim!");
	}

	@Override
	public boolean next() throws JRException {
		//return contador++ < 100;
		if (this.it.hasNext()) {
			this.selecionado = it.next();
			return true;
		}
		return false;
	}

}
