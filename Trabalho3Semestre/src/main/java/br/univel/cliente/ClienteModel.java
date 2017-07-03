package br.univel.cliente;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ClienteModel extends AbstractTableModel{
	
	private List<Cliente> listaCliente;

	
	public ClienteModel(List<Cliente> list) {
		this.listaCliente = list;
	}
	
	@Override
	public int getRowCount() {
		return listaCliente.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Cliente c = listaCliente.get(row);
		switch (column) {
		case 0:
			return c.getId();
		case 1:
			return c.getNome();
		case 2:
			return c.getIdade();
		case 3:
			return c.getCpf();
		}
		return new Exception("Coluna nao tratada");
	}

	public Cliente pegaCliente(int row) {
		return this.listaCliente.get(row);
	}

}
