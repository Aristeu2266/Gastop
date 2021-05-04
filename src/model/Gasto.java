package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

public class Gasto {
	protected String user;
	protected String desc;
	protected double valor;
	protected String data;
	protected SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	// Metodos construtores
	public Gasto(String user, double valor, String desc) {
		this.user = user;
		this.valor = valor;
		this.desc = desc;
		this.data = sdf.format(new Date(System.currentTimeMillis()));
	}
	
	public Gasto(String user, double valor) {
		this.user = user;
		this.valor = valor;
		this.desc = "";
		this.data = sdf.format(new Date(System.currentTimeMillis()));
	}
	
	public Gasto(String user, double valor, String desc, String data) {
		this.user = user;
		this.valor = valor;
		this.desc = desc;
		this.data = data;
	}
	
	public String toString() {
		return valor + " - " + desc + " - " + data;
	}
	
	public boolean equals(Gasto oto) {
		return this.desc.equals(oto.getDescricao()) && this.valor == oto.getValor() && this.getData().equals(oto.getData());
	}
	
	public Gasto clone() {
		return new Gasto(this.user, this.valor, this.desc, this.data);
	}

	// getters
	public JSONObject getJSON() {
		JSONObject objeto = new JSONObject();
		objeto.put("usuario", user);
		objeto.put("descricao", desc);
		objeto.put("valor", valor);
		objeto.put("data", data);
		objeto.put("cartao", "");
		return objeto;
	}
	
	public String getUsuario() {
		return this.user;
	}

	public Date getData() {
		try {
			return sdf.parse(data);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public String getDataStr() {
		return data;
	}
	
	public double getValor() {
		return this.valor;
	}
	
	public String getDescricao() {
		return this.desc;
	}
	
	// setters
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
