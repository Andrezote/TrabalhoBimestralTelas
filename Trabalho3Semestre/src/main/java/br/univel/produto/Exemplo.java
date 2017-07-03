package br.univel.produto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Exemplo {

	public static void main(String[] args) throws Exception {
		
		URL url = new URL("http://www.master10.com.py/lista-txt/download");
		URLConnection urlCon = url.openConnection();
		
		try(InputStream is = urlCon.getInputStream()){
			InputStreamReader irs = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(irs);
			
			String linha;
			
			while((linha = in.readLine()) != null){
				System.out.println(linha);
			}
		} 
	}

}
