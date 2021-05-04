package controller;

import java.util.ArrayList;
import java.util.Iterator;

import model.Cartao;

public class GerCartao {

	// armazena os cartoes em um arquivo
	public static void escreveCartao(String usuario, String nome) {
		GerArquivo.escreveCartao(new Cartao(usuario, nome));
	}
	
	public static boolean cartaoExiste(String usuario, String nome) {
		ArrayList<Cartao> cartoesUsuario = getCartoesUsuario(usuario);
		
		Iterator<Cartao> it = cartoesUsuario.iterator();

		while (it.hasNext()) {
			Cartao cartao = it.next();
			if (cartao.getNome().equals(nome)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static ArrayList<Cartao> getCartoesArmazenados() {
		return GerJSON.cartoesJSONtoArrayList(GerJSON.getJSON("dados\\cartoes.json"));
	}
	
	public static ArrayList<Cartao> getCartoesUsuario(String usuario){
		ArrayList<Cartao> cartoesUsuario = new ArrayList<Cartao>();
		ArrayList<Cartao> cartoes = GerJSON.cartoesJSONtoArrayList(GerJSON.getJSON("dados\\cartoes.txt"));
		Iterator<Cartao> it = cartoes.iterator();

		while (it.hasNext()) {
			Cartao cartao = it.next();
			if (cartao.getUsuario().equals(usuario)) {
				cartoesUsuario.add(cartao);
			}
		}
		
		return cartoesUsuario;
	}
	
	public static boolean existeCartao(String usuario, String nome) {
		ArrayList<Cartao> cartoesUsuario = getCartoesUsuario(usuario);
		Iterator<Cartao> it = cartoesUsuario.iterator();
		
		while(it.hasNext()) {
			Cartao cartao = it.next();
			if(cartao.getNome().equals(nome)) {
				return true;
			}
		}
		
		return false;
	}
}
