package br.univel.orcamento;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProdOrcamentoModel extends AbstractTableModel{
	
	private List<Orcamento> lista;

	public ProdOrcamentoModel(List<Orcamento> list) {
		this.lista = list;
	}

	@Override
	public int getRowCount() {
		return lista.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "ID";
		case 1:
			return "Descricao";
		}
		return "Coluna nao inserida";
	}

	@Override
	public Object getValueAt(int row, int col) {
		Orcamento o = lista.get(row);
		switch (col) {
		case 0:
			return o.getId();
		case 1:
			return o.getDescricao();
		}
		return new Exception("Nao rolou");
	}

	public Orcamento getOrcamento(int idx) {
		return lista.get(idx);
	}

}
