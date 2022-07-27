package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Test;

public class ClienteTest {//Est� class � para testar se eu acessar est� URI = http://www.mocky.io/v2/52aaf5deee7ba8c70329fb7d, tr�s esse XML aqui
	//Ent�o vamos criar um programa Cliente que vai se comunicar com o outro

	@Test //faremos o teste com JUnit
	public void testaQueAConexaoComOServidorFunciona() {
		//Aqui dentro vou criar uma conex�o Cliente para fazer as requisi��es para meu servidor, e vai ser um "Cliente HTTP"
		Client client = ClientBuilder.newClient();//pronto criamos um Cliente
		
		//Vamos definir a URI base(alvo) para fazermos v�rias requisi��es
		WebTarget target = client.target("http://www.mocky.io");//e isso n�s devolve um WebTarget
		// .get(String.class) <- pedindo para retornar a informa��o dessa requisi��o em formato String
		String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);//fazendo requisi��o pra uma URI especifica que fica assim = http://www.mocky.io/v2/52aaf5deee7ba8c70329fb7d
		System.out.println(conteudo);
		Assert.assertTrue(conteudo.contains("Rua Vergueiro 3185"));//confirmando se dentro de conteudo tem essa palavra que tem no XML
		
	}
	
}
