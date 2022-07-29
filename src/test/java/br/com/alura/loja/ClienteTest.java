package br.com.alura.loja;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import junit.framework.Assert;

public class ClienteTest {
	private HttpServer server;
	private WebTarget target;
	private Client client;

//Está class é para testar se eu acessar está URI = http://www.mocky.io/v2/52aaf5deee7ba8c70329fb7d, trás esse XML aqui
	//Então vamos criar um programa Cliente que vai se comunicar com o outro

	@Before //Antes de qualquer @Test, executa este método
	public void startaServidor() {
		server = Servidor.inicializaServidor();//está retornando para a váriavel "server" o servidor
		this.client = ClientBuilder.newClient();
		this.target = client.target("http://localhost:8080");
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
		WebTarget target = client.target("http://localhost:8090");//e isso nós devolve um WebTarget
		// .get(String.class) <- pedindo para retornar a informação dessa requisição em formato String
		String conteudo = target.path("/carrinhos/1").request().get(String.class);//fazendo requisição pra uma URI especifica que fica assim = http://www.mocky.io/v2/52aaf5deee7ba8c70329fb7d
		System.out.println(conteudo);
//		Assert.assertTrue(conteudo.contains("Rua Vergueiro 3185"));//confirmando se dentro de conteudo tem essa palavra que tem no XML
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		assertEquals("Rua Vergueiro, 3185, 8 andar", carrinho.getRua());
		
	}
	
	@Test
	public void testaQueSuportaNovosCarrinhos() {
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314, "Microfone", 37, 1));//Aqui está criando um Produto = Microfone
		carrinho.setRua("Rua Vergueiro 3185");
		carrinho.setCidade("Sao Paulo");
		String xml = carrinho.toXML();//Transformando o carrinho em XML
		javax.ws.rs.client.Entity<String> entity = javax.ws.rs.client.Entity.entity(xml, MediaType.APPLICATION_XML);//Entity representa o APPLICATION_XML
		Response response = target.path("/carrinhos").request().post(entity);//E aqui estamos fazendo o POST da entity que é aquele carrinho em formato XML, fazendo o POST no /carrinhos
		Assert.assertEquals(201, response.getStatus());//de acordo com a response.getStatus() irá vir ex: 200, 201, 300, 400 ou 500
		String location = response.getHeaderString("Location");//pegando a Location que pode ser ex: http://localhost:8080/carrinhos/5
		String conteudo = client.target(location).request().get(String.class);//vamos requisitar fazer um GET em ex: http://localhost:8080/carrinhos/5
		Assert.assertTrue(conteudo.contains("Microfone"));//dentro de conteudo tem todo nosso XML do carrinho, e vamos verificar se existe um Microfone dentro desse carrinho em formato XML
	}
	
}
