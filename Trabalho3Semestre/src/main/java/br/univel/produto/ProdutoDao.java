package br.univel.produto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.zxing.Result;

import br.univel.cliente.Cliente;
import br.univel.orcamento.ConexaoDB;

public class ProdutoDao {
	
	private static final String SQL_BUSCA_TODOS = "SELECT * FROM produto";
	
	
	public List<Produto> getTodos() {
		
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		List<Produto> lista = new ArrayList<>();
		try (PreparedStatement ps = con
					.prepareStatement(SQL_BUSCA_TODOS);
				ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				Produto d = new Produto();
				d.setId(rs.getLong(1));
				d.setDescricao(rs.getString(2));
				d.setValorDolar(rs.getBigDecimal(3));
				lista.add(d);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}

	public void insere(List<Produto> lista) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		String insertSql = ("insert into produto (id_produto, nome, valor) values (?, ?, ?)");
		try {
			PreparedStatement ps = con.prepareStatement(insertSql);
			for (Produto d : lista) {
				if(busca(d.getId())){
					ps.setLong(1, d.getId());
					ps.setString(2, d.getDescricao());
					ps.setBigDecimal(3, d.getValorDolar());
					ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean busca(Long id) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		String busca = ("SELECT id_produto FROM  produto WHERE id_produto = ?");
		
		try {
			PreparedStatement ps = con.prepareStatement(busca);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public List<Produto> buscaBanco(String palavra) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		List<Produto> achados = new ArrayList<>();
		
		String sql = "SELECT *FROM produto WHERE nome LIKE ?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			palavra += "%";
			ps.setString(1, palavra);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Produto d = new Produto();
				d.setId(rs.getLong(1));
				d.setDescricao(rs.getString(2));
				d.setValorDolar(rs.getBigDecimal(3));
				achados.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return achados;
	}

	public List<Produto> getProdutos(long id) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		List<Produto> achados = new ArrayList<>();
		
		String sql = "SELECT *FROM produto WHERE id_produto = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Produto p = new Produto();
				p.setId(rs.getLong(1));
				p.setDescricao(rs.getString(2));
				p.setValorDolar(rs.getBigDecimal(3));
				p.setQtd(rs.getBigDecimal(4));
				achados.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return achados;
	}

	
	
}
