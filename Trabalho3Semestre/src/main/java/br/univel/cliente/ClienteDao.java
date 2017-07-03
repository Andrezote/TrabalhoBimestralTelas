package br.univel.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.univel.orcamento.ConexaoDB;
import br.univel.produto.Produto;


public class ClienteDao {
	
	private static final String SQL_BUSCA_TODOS = "SELECT * FROM cliente";

	public List<Cliente> getTodos() {
		
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		List<Cliente> lista = new ArrayList<>();
		try (PreparedStatement ps = con
					.prepareStatement(SQL_BUSCA_TODOS);
				ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				Cliente c = new Cliente();
				c.setId(rs.getLong(1));
				c.setNome(rs.getString(2));
				c.setIdade(rs.getInt(3));
				c.setCpf(rs.getString(4));
				lista.add(c);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public void insere(Cliente c) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		String insertSql = ("insert into cliente (id, nome, idade, cpf) values (?, ?, ?, ?)");
		try {
			PreparedStatement ps = con.prepareStatement(insertSql);
			ps.setLong(1, c.getId());
			ps.setString(2, c.getNome());
			ps.setInt(3, c.getIdade());
			ps.setString(4, c.getCpf());
			ps.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Cheque id duplicados");;
		}
		
	}
	
	public void atualiza(Long id, Cliente c) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		
		String updatetSql = ("UPDATE cliente SET nome = ?, idade = ?, cpf = ? WHERE id = ?");
		try {
			PreparedStatement ps = con.prepareStatement(updatetSql);
			ps.setString(1, c.getNome());
			ps.setInt(2, c.getIdade());
			ps.setString(3, c.getCpf());
			ps.setLong(4, c.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void exclui(Long id) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM cliente WHERE id =").append(id);
		
		try {
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Cliente> buscaBanco(String nome) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		List<Cliente> achados = new ArrayList<>();
		
		String sql = "SELECT *FROM cliente WHERE nome LIKE ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			nome += "%";
			ps.setString(1, nome);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Cliente c = new Cliente();
				c.setId(rs.getLong(1));
				c.setNome(rs.getString(2));
				c.setIdade(rs.getInt(3));
				c.setCpf(rs.getString(4));
				achados.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return achados;
	}

	public Cliente achaCliente(long id) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		Cliente c = new Cliente();
		String sql = "SELECT *FROM cliente WHERE id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				c.setId(rs.getLong(1));
				c.setNome(rs.getString(2));
				c.setIdade(rs.getInt(3));
				c.setCpf(rs.getString(4));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return c;
	}

}
