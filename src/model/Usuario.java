package model;

import org.json.simple.JSONObject;

public class Usuario {
	private String login;
	private String senha;
	private double renda;

	// Metodos construtores
	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	public Usuario(String login, String senha, double renda) {
		this.login = login;
		this.senha = senha;
		this.renda = renda;
	}

	// Metodos para validas senha
	public boolean logar(String senha){
		return this.senha.equals(senha);
	}

	// Geters
	public String toString() {
		return this.login;
	}
	
	public JSONObject getJSON() {
		JSONObject objeto = new JSONObject();
		objeto.put("senha", senha);
		objeto.put("login", login);
		objeto.put("renda", renda);
		return objeto;
	}

	public String getLogin() {
		return this.login;
	}
	
	public double getRenda() {
		return this.renda;
	}
	
	// Seters
	public void setRenda(double renda) {
		this.renda = renda;
	}
	
}
