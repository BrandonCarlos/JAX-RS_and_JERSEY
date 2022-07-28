package br.com.alura.loja;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;

public class ClienteTest {
	private HttpServer server;

//Está class é para testar se eu acessar está URI = http://www.mocky.io/v2/52aaf5deee7ba8c70329fb7d, trás esse XML aqui
	//Então vamos criar um programa Cliente que vai se comunicar com o outro

	@Before //Antes de qualquer @Test, executa este método
	public void startaServidor() {
		server = Servidor.inicializaServidor();//está retornando para a váriavel "server" o servidor
	}
	
	@After //Depois de qualquer @Test, executa este método
	public void mataServidor() {
		server.stop(); 		
	}
	
	@Test //faremos o teste com JUnit
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		//Aqui dentro vou criar uma conexão Cliente para fazer as requisições para meu servidor, e vai ser um "Cliente HTTP"
		Client client = ClientBuilder.newClient();//pronto criamos um Cliente
		
		//Vamos definir a URI base(alvo) para fazermos várias requisições
		WebTarget target = client.target("http://localhost:8085");//e isso nós devolve um WebTarget
		// .get(String.class) <- pedindo para retornar a informação dessa requisição em formato String
		String conteudo = target.path("/carrinhos/1").request().get(String.class);//fazendo requisição pra uma URI especifica que fica assim = http://www.mocky.io/v2/52aaf5deee7ba8c70329fb7d
		System.out.println(conteudo);
//		Assert.assertTrue(conteudo.contains("Rua Vergueiro 3185"));//confirmando se dentro de conteudo tem essa palavra que tem no XML
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		assertEquals("Rua Vergueiro, 3185, 8 andar", carrinho.getRua());
		
	}
	
}
