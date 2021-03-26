package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

public class Gasto {
	private String desc;
	private double valor;
	private String data;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	// Metodos construtores
	public Gasto(double valor, String desc) {
		this.valor = valor;
		this.desc = desc;
		this.data = sdf.format(new Date(System.currentTimeMillis()));
	}
	
	public Gasto(double valor) {
		this.valor = valor;
		this.desc = "";
		this.data = sdf.format(new Date(System.currentTimeMillis()));
	}
	
	public Gasto(double valor, String desc, String data) {
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
		return new Gasto(this.valor, this.desc, this.data);
	}

	// getters
	public JSONObject getJSON() {
		JSONObject objeto = new JSONObject();
		objeto.put("descricao", desc);
		objeto.put("valor", valor);
		objeto.put("data", data);
		return objeto;
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
