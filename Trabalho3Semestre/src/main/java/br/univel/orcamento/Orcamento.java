package br.univel.orcamento;

import java.util.List;

import br.univel.cliente.Cliente;
import br.univel.produto.Produto;

public class Orcamento {
	
	private Long id;
	private String descricao;
	private List<Produto> lista;
	private Cliente cliente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<Produto> getLista() {
		return lista;
	}
	public void setLista(List<Produto> lista) {
		this.lista = lista;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
