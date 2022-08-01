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
	
	@POST //o path aqui continuará sendo /carrinhos só que agora para o método POST
	@Consumes(MediaType.APPLICATION_XML)//no caso eu vou ENVIAR XML para meu servidor
	public Response adicionaNoServidor(String conteudo) {
		//Aqui no Codigo é sempre o SERVIDOR, então eu como SERVIDOR quero receber esses dados do usuário e cadastrar no meu banco de dados
		Carrinho carrinho = (Carrinho)new XStream().fromXML(conteudo);//pegando o conteudo que o usuario nos enviou e transformando em XML
		new CarrinhoDAO().adiciona(carrinho);//adicionando o conteudo do usuario que ele enviou dentro do banco de dados
		URI uri = URI.create("/carrinhos/" + carrinho.getId());//ex: /carrinhos/5
		return Response.created(uri).build();//.created() mostrando que foi criado com sucesso e mostrando a URL em que foi criada
		//assim o usuario pode acessar essa URL posteriormente, com o .build() vai retornar um "Response", e assim estamos devolvendo em STATUS CODE
		//no caso .build() devolve a URI Localização onde esse recurso foi criado
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
