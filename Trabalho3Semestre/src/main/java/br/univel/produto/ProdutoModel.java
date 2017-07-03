package br.univel.produto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProdutoModel extends AbstractTableModel{
	
	private List<Produto> list;
	private BigDecimal valorDolar;
	
	public ProdutoModel(List<Produto> lista, BigDecimal valor) {
		this.list = lista;
		this.valorDolar = valor;
	}
	

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "ID";
		case 1:
			return "DESCRICAO";
		case 2:
			return "VALOR DOLAR";
		case 3:
			return "VALOR REAL";
		}
		return "Coluna nao declarada";
	}

	@Override
	public Object getValueAt(int row, int col) {
		Produto p = list.get(row);
		switch (col) {
		case 0:
			return p.getId();
		case 1:
			return p.getDescricao();
		case 2:
			return p.getValorDolar();
		case 3:
			return p.getValorDolar().multiply(valorDolar).setScale(2, RoundingMode.DOWN);
		}
		return new Exception("Deu merda");
	}

	public Produto getProduto(int i) {
		return this.list.get(i);
	}

	public void preencheBusca(List<Produto> lista) {
		this.list = lista;
	} 

}
