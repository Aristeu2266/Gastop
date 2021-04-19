package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Gasto;
import model.Usuario;

public class GerJSON {

	// extrai o json array de um arquivo
	public static JSONArray getJSON(String arquivo) {
		GerArquivo.validaDir(arquivo);

		JSONArray jSONArray = null;
		JSONParser parser = new JSONParser();

		try {
			jSONArray = (JSONArray) parser.parse(new FileReader(arquivo));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return jSONArray;
	}

	// transforma um json object em um json usuário
	private static Usuario JSONtoUsuario(JSONObject objeto) {
		String login = "";
		String senha = "";
		double renda = 0;
		for (Object chave : objeto.keySet()) {
			if (chave.equals("senha")) {
				senha = (String) objeto.get(chave);
			} else {
				if (chave.equals("login")) {
					login = (String) objeto.get(chave);
				} else {
					renda = (double) objeto.get(chave);
				}
			}
		}

		if (login.equals("") || senha.equals("")) {
			return null;
		}

		return new Usuario(login, senha, renda);
	}

	// transforma um json object em um json usuário
	private static Gasto JSONtoGasto(JSONObject objeto) {
		String user = "";
		double valor = 0;
		String desc = "";
		String data = "";
		for (Object chave : objeto.keySet()) {
			if (chave.equals("usuario")) {
				user = (String) objeto.get(chave);
			} else if (chave.equals("valor")) {
				valor = (double) objeto.get(chave);
			} else if (chave.equals("descricao")) {
				desc = (String) objeto.get(chave);
			} else {
				data = (String) objeto.get(chave);
			}

		}

		return new Gasto(user, valor, desc, data);
	}

	// transforma um json array em um array de usuario
	public static ArrayList<Usuario> usuarioJSONtoArrayList(JSONArray jSONArray) {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Iterator it = jSONArray.iterator();

		while (it.hasNext()) {
			Usuario usuario = JSONtoUsuario((JSONObject) it.next());

			if (usuario != null) {
				usuarios.add(usuario);
			}
		}

		return usuarios;
	}

	// transforma um json array em um array de gasto
	public static ArrayList<Gasto> gastosJSONtoArrayList(JSONArray jSONArray) {
		ArrayList<Gasto> gastos = new ArrayList<Gasto>();
		Iterator it = jSONArray.iterator();

		while (it.hasNext()) {
			gastos.add(JSONtoGasto((JSONObject) it.next()));
		}

		return gastos;
	}
}
