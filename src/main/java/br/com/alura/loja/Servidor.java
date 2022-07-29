package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Produto;

public class Servidor {
	
	public static void main(String[] args) throws IOException {
		System.out.println("Servidor rodando");//mostrando que o servidor iniciou e agora podemos fazer nossas requisições HTTP
		System.in.read();//vai ficar esperando eu dar ENTER
//		server.stop();//parando o servidor
		
//		ProdutoDAO produtoDao = new ProdutoDAO();
//		Produto produtoOne = new Produto(2l, "Caelum", 2015, 15);
//		
//		produtoDao.adiciona(produtoOne);
//		Produto produtoEncontradoPorId = produtoDao.buscaProdutoPorId(2l);
//		System.out.println(produtoEncontradoPorId.getNome());
//		double preco = produtoEncontradoPorId.getPreco();
//		int precoSemCasasDecimais = (int)preco;
//		System.out.println(precoSemCasasDecimais);
//		System.out.println(produtoEncontradoPorId.getQuantidade());
	
	}

		static HttpServer inicializaServidor() {//removemos o "private" para poder utilizar lá no meu método dentro da class ClientTest.java
		//Precisamos passar a configuração dos meus Recursos, falar quais são as minhas classes JAX-RS
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");//Tudo que tiver dentro de "br.com.alura.loja" e for de JAX-RS pode usar pro meu servidor
		URI uri = URI.create("http://localhost:8090");
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);//Servidor criado, agora podemos acessar ex: localhost:8080/carrinho
		return server;
	}
	
}
