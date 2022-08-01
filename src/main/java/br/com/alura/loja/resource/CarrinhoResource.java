package br.com.alura.loja.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

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
	
	@POST //o path aqui continuar� sendo /carrinhos s� que agora para o m�todo POST
	@Consumes(MediaType.APPLICATION_XML)//no caso eu vou ENVIAR XML para meu servidor
	public Response adicionaNoServidor(String conteudo) {
		//Aqui no Codigo � sempre o SERVIDOR, ent�o eu como SERVIDOR quero receber esses dados do usu�rio e cadastrar no meu banco de dados
		Carrinho carrinho = (Carrinho)new XStream().fromXML(conteudo);//pegando o conteudo que o usuario nos enviou e transformando em XML
		new CarrinhoDAO().adiciona(carrinho);//adicionando o conteudo do usuario que ele enviou dentro do banco de dados
		URI uri = URI.create("/carrinhos/" + carrinho.getId());//ex: /carrinhos/5
		return Response.created(uri).build();//.created() mostrando que foi criado com sucesso e mostrando a URL em que foi criada
		//assim o usuario pode acessar essa URL posteriormente, com o .build() vai retornar um "Response", e assim estamos devolvendo em STATUS CODE
		//no caso .build() devolve a URI Localiza��o onde esse recurso foi criado
	}
	
	@Path("{id}/produtos/{produtoId}")//localhost:8080/carrinhos/45/produtos/456
	@DELETE
	public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {//ex: id = 45, produtoId = 456
		Carrinho carrinho = new CarrinhoDAO().busca(id);//encontrei o carrinho com id 1
		carrinho.remove(produtoId);
		return Response.ok().build();//retornando o CODE 200 - Success
	}
	
	@Path("{id}/produtos/{produtoId}/quantidade")
	@PUT
	public Response alteraDadosDoProduto(String conteudo, @PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		Produto produto = (Produto) new XStream().fromXML(conteudo);
		//trocar a quantidade antiga, pela quantidade nova
		carrinho.troca(produto);
		return Response.ok().build();
		
	}
}
