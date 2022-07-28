package br.com.alura.loja.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDAO {
	private static Map<Long, Produto> banco = new HashMap<>();
	private static AtomicLong contador = new AtomicLong(1);
	
	static {//este static -> assim que dermos RUN AS JAVA APPLICATION este "STATIC" vai executar na hora e antes mesmo do m�todo MAIN
		banco.put(1l, new Produto(1l, "Minha loja", 2014, 5));
		banco.put(1l, new Produto(1l, "Alura", 2012, 10));
	}
	
	public void adiciona(Produto produto) {
		long id = contador.incrementAndGet();//incrementando autom�ticamente de 1 em 1 
		produto.setId(id);
		banco.put(id, produto);
	}
	
	public Produto buscaProdutoPorId(Long id) {
		return banco.get(id);//enviando o ID, ir� buscar dentro do MAP se tem esse ID ai retorna o VALOR que � o Produto inteiro
	}
	
	public Produto removeProdutoPorId(Long id) {
		return banco.remove(id);//removendo do MAP atr�ves do ID fornecido pelo usu�rio
	}
}
