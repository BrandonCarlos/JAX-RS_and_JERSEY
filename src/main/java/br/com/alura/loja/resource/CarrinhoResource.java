package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

//Todo Resource do JAX-RS vai ser anotado básicamente com o @Path() <- vamos dizer qual a URI que vou acessar no "Servidor"

@Path("carrinhos")// -> UriDoServidor/carrinhos <- veja a barra é implicita, então não precisamos colocar /
public class CarrinhoResource {

	@Path("{id}") //<- ex: localhost:8080/carrinhos/10 <- eu pego esse 10 e jogo lá pro "Long id", OBS: não precisamos colocar a barra ex: "/{id}"
	//precisamos dizer para o servidor que tipo de String estamos devolvendo se é String JSON, String XML, no caso vamos devolver String XML
	@GET
	@Produces(MediaType.APPLICATION_XML)//estamos produzindo XML, e precisamos falar que esse método vai ser acessado pelo método HTTP "GET"
	public String busca(@PathParam("id") Long id) {//Não vamos usar assim: ?id=10, pq perde o CACHE, vamos usar o @PathParam assim não perdemos CACHE e ainda passamos assim: localhost:8080/carrinhos/10
		System.out.println("Dentro do método");
		Carrinho carrinho = new CarrinhoDAO().busca(id);//e aqui passando o id pro meu método busca para pegar atráves do id que eu passei na URL
		return carrinho.toXML();//este método vai devolver para nós a String, o próprio XML solto
		
	}
	
}
