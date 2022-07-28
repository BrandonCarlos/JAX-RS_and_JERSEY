package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

//Todo Resource do JAX-RS vai ser anotado b�sicamente com o @Path() <- vamos dizer qual a URI que vou acessar no "Servidor"

@Path("carrinhos")// -> UriDoServidor/carrinhos <- veja a barra � implicita, ent�o n�o precisamos colocar /
public class CarrinhoResource {

	@Path("{id}") //<- ex: localhost:8080/carrinhos/10 <- eu pego esse 10 e jogo l� pro "Long id", OBS: n�o precisamos colocar a barra ex: "/{id}"
	//precisamos dizer para o servidor que tipo de String estamos devolvendo se � String JSON, String XML, no caso vamos devolver String XML
	@GET
	@Produces(MediaType.APPLICATION_XML)//estamos produzindo XML, e precisamos falar que esse m�todo vai ser acessado pelo m�todo HTTP "GET"
	public String busca(@PathParam("id") Long id) {//N�o vamos usar assim: ?id=10, pq perde o CACHE, vamos usar o @PathParam assim n�o perdemos CACHE e ainda passamos assim: localhost:8080/carrinhos/10
		System.out.println("Dentro do m�todo");
		Carrinho carrinho = new CarrinhoDAO().busca(id);//e aqui passando o id pro meu m�todo busca para pegar atr�ves do id que eu passei na URL
		return carrinho.toXML();//este m�todo vai devolver para n�s a String, o pr�prio XML solto
		
	}
	
}
