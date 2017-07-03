package br.univel.produto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeitorProdutoUrl {
	
	
	public List<Produto> lerProdutos(String strUrl) throws Exception{
		List<Produto> lista = new ArrayList<>();
		URL url = new URL(strUrl);
		URLConnection urlCon = url.openConnection();
		
		try(InputStream is = urlCon.getInputStream()){
			InputStreamReader irs = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(irs);
			
			String linha;
			
			while((linha = in.readLine()) != null){
				if(linha.matches("[0-9]+.*")){
					Produto p = lerProduto(linha);
					lista.add(p);
				}
			}
		}
		return lista;
	}
	
	private Pattern pattern = Pattern.compile("([0-9]+)(.*)US\\$ (.*)");

	private Produto lerProduto(String linha) {
		
		Matcher mat = pattern.matcher(linha);
		
		Produto produto = new Produto();
		if(mat.matches()){
			String strId = mat.group(1).trim();
			produto.setId(Long.parseLong(strId));
			
			String desc = mat.group(2).trim();
			produto.setDescricao(desc);
			
			String strValorOrg = mat.group(3).trim();
			String strValorSemPonto = strValorOrg.replaceAll("\\.", "");
			String strValorIngles =strValorSemPonto.replaceAll(",", ".");
			produto.setValorDolar(new BigDecimal(strValorIngles));
			//se for trocar ponto precisa colocar \\ pois e um caracter especial
			//produto.setValorDolar(new BigDecimal(strValor.replaceAll("\\.", ",")));
			
		} else {
			throw new RuntimeException("Linha invalida: " + linha);
		}
		return produto;
	}
	
	public static void main(String[] args) throws Exception {
		String url = "http://www.master10.com.py/lista-txt/download";
		LeitorProdutoUrl lpu = new LeitorProdutoUrl();
		List<Produto> lista = lpu.lerProdutos(url);
		lista.forEach((e) ->
		System.out.println(e.toString()));
	}

}
