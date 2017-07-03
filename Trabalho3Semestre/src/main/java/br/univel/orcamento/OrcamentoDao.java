package br.univel.orcamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.univel.cliente.Cliente;
import br.univel.cliente.ClienteDao;
import br.univel.produto.Produto;
import br.univel.produto.ProdutoDao;

public class OrcamentoDao {
	
	private ProdutoDao pd = new ProdutoDao();
	private ClienteDao cd = new ClienteDao();
	private static final String SQL_BUSCA_TODOS = "SELECT * FROM orcamento";

	public List<Orcamento> getTodos() {
		
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		List<Orcamento> lista = new ArrayList<>();
		try (PreparedStatement ps = con
					.prepareStatement(SQL_BUSCA_TODOS);
				ResultSet rs = ps.executeQuery()) {
			while(rs.next()) {
				Orcamento o = new Orcamento();					
				o.setId(rs.getLong(1));
				Cliente c = cd.achaCliente(rs.getLong(2));
				o.setCliente(c);
				o.setDescricao(rs.getString(3));
				o.setLista(pd.getProdutos(rs.getLong(1)));
				lista.add(o);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	
	private Cliente acharCliente(long id) {
		// TODO Auto-generated method stub
		return null;
	}


	public void insere(Orcamento o) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		String insertSql = ("insert into orcamento(id_orcamento, id_cliente, descricao) values (?, ?, ?)");
		try {
			PreparedStatement ps = con.prepareStatement(insertSql);
			ps.setLong(1, o.getId());
			ps.setLong(2, o.getCliente().getId());
			ps.setString(3, o.getDescricao());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();;
		}
		
	}
	
	
	public void exclui(Orcamento o) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		String deleteSql = ("DELETE FROM orcamento where id_orcamento = ?");
		try {
			PreparedStatement ps = con.prepareStatement(deleteSql);
			ps.setLong(1, o.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void AtualizaOrcamento(Orcamento o){
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		String updatetSql = ("UPDATE orcamento SET id_cliente = ?, descricao = ? WHERE id = ?");
		try {
			excluiProdOrc(o);
			insereProdOrc(o, o.getLista());
			PreparedStatement ps = con.prepareStatement(updatetSql);
			ps.setLong(1, o.getCliente().getId());
			ps.setString(2, o.getDescricao());
			ps.setLong(3, o.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void insereProdOrc(Orcamento o,List<Produto> lista){
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		String insertSql = ("insert into produto_orcamento(id_produto, id_orcamento, quantidade) values (?, ?, ?)");
		try {
			PreparedStatement ps = con.prepareStatement(insertSql);
			
			for (Produto produto : lista) {
				ps.setLong(1, produto.getId());
				ps.setLong(2, o.getId());
				ps.setBigDecimal(3, produto.getQtd());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void excluiProdOrc(Orcamento o) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		String deleteSql = ("DELETE FROM produto_orcamento where id_orcamento = ?");
		try {
			PreparedStatement ps = con.prepareStatement(deleteSql);
			ps.setLong(1, o.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Produto> SelectProdutoOrcamento(Orcamento o) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		List<Produto> lista = new ArrayList<>();
		List<Produto> aux = new ArrayList<>();
		aux = o.getLista();
		
		String buscaSql = ("SELECT produto_orcamento.id_produto, PRODUTO.nome, produto_orcamento.quantidade, produto.valor FROM PRODUTO_ORCAMENTO " +
		"INNER JOIN PRODUTO ON PRODUTO_ORCAMENTO.ID_PRODUTO = PRODUTO.ID_PRODUTO WHERE ID_ORCAMENTO = ?");
		try (PreparedStatement ps = con
					.prepareStatement(buscaSql);) {
			ps.setLong(1, o.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Produto p = new Produto();
				p.setId(rs.getLong(1));
				p.setDescricao(rs.getString(2));
				p.setQtd(rs.getBigDecimal(3));
				p.setValorDolar(rs.getBigDecimal(4));
				lista.add(p);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
}
