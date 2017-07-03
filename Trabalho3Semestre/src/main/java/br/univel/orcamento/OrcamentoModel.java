package br.univel.orcamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.univel.produto.Produto;
import br.univel.produto.ProdutoDao;

public class OrcamentoModel extends AbstractTableModel{
	
	private List<Produto> lista;
	private BigDecimal valorTotal = new BigDecimal("0");
	private BigDecimal valorDolar = new BigDecimal("0");
	private BigDecimal qtd = new BigDecimal("0");
	private ConexaoDB con;
	
	public OrcamentoModel(List<Produto> list, BigDecimal vlrDolar) {
		if(list == null || list.size() < 0){
			this.lista = new ArrayList<>();
		}else{
			this.lista = list;
		}
		this.valorDolar = vlrDolar;
	}



	@Override
	public int getRowCount() {
		int rowC = (lista.size() + 1);
		return rowC;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Produto";
		case 1:
			return "Valor Unitario(R$)";
		case 2:
			return "Valor completo(R$)";
		case 3:
			return "Quantidade";
		}
		return "Coluna nao implementada";
	}

	@Override
	public Object getValueAt(int row, int col) {
		if(row < lista.size()){
			Produto p = lista.get(row);
			switch (col) {
			case 0:
				return p.getDescricao();
			case 1:
				return "R$" + p.getValorDolar().multiply(valorDolar).setScale(2, RoundingMode.DOWN);
			case 2:
				return "R$" + p.getValorDolar().multiply(valorDolar).multiply(p.getQtd()).setScale(2, RoundingMode.DOWN);
			case 3:
				return p.getQtd();
			}
		}else{
			switch (col) {
			case 0:
				return "Total: ";
			case 1:
				return "R$" + valorTotal.multiply(valorDolar).setScale(2, RoundingMode.DOWN);
			case 2:
				return "";
			case 3:
				return "";
			}
		}
		return new Exception("nao deu hue");
	}


	public Produto getProduto(int row) {
		return lista.get(row);
	}

	public void removeProduto(int idx) {
		lista.remove(idx);
		this.fireTableDataChanged();
	}



	public void setValorTotal(BigDecimal bd) {
		this.valorTotal = bd;
	}

}
