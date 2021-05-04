package model;

import org.json.simple.JSONObject;

public class Cartao {
	private String user;
	private String nome;
	
	public Cartao(String user, String nome) {
		this.user = user;
		this.nome = nome;
	}
	
	public JSONObject getJSON() {
		JSONObject objeto = new JSONObject();
		objeto.put("usuario", user);
		objeto.put("nome", nome);
		return objeto;
	}

	public String getUsuario() {
		return user;
	}

	public String getNome() {
		return nome;
	}
	
	public String toString() {
		return nome;
	}
	
}
