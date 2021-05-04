package model;

import org.json.simple.JSONObject;

public class GastoCartao extends Gasto{
	
	private Cartao cartao;

	public GastoCartao(String user, double valor, String desc, String data, Cartao cartao) {
		super(user, valor, desc, data);
		this.cartao = cartao;
	}

	public GastoCartao(String user, double valor, String desc, Cartao cartao) {
		super(user, valor, desc);
		this.cartao = cartao;
	}

	public GastoCartao(String user, double valor, Cartao cartao) {
		super(user, valor);
		this.cartao = cartao;
	}
	
	public JSONObject getJSON() {
		JSONObject objeto = new JSONObject();
		objeto.put("usuario", user);
		objeto.put("descricao", desc);
		objeto.put("valor", valor);
		objeto.put("data", data);
		objeto.put("cartao", this.cartao);
		return objeto;
	}

}
