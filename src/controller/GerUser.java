package controller;

import java.util.ArrayList;
import java.util.Iterator;

import model.Cartao;
import model.Usuario;

public class GerUser {

	// valida se o login do usuário é valido
	public static boolean validaLogin(String login, String senha) {
		ArrayList<Usuario> logins = getUsuariosArmazenados();
		Iterator<Usuario> it = logins.iterator();
		
		while (it.hasNext()) {
			Usuario aux = it.next();
			if (aux.getLogin().equals(login)) {
				return aux.logar(senha);
			}
		}
		return false;
	}

	// faz o registro do usuário no sistema
	public static boolean registrar(String login, String senha) {
		if (!login.equals("") && !senha.equals("") && !existeUsuario(login)) {
			Usuario usuario = new Usuario(login, senha);
			GerArquivo.escreveUsuario(usuario);
			return true;
		}else {
			return false;
		}
	}

	// retorna o objeto usuario apartir do login
	public static Usuario getUsuario(String login) {
		ArrayList<Usuario> logins = getUsuariosArmazenados();
		Iterator<Usuario> it = logins.iterator();
		
		while (it.hasNext()) {
			Usuario aux = it.next();
			if(aux.getLogin().equals(login)) {
				return aux;
			}
		}
		
		return null;
	}

	// verifica se o usuario já existe
	private static boolean existeUsuario(String login) {
		ArrayList<Usuario> logins = getUsuariosArmazenados();
		Iterator<Usuario> it = logins.iterator();
		
		while (it.hasNext()) {
			if(it.next().getLogin().equals(login)) {
				return true;
			}
		}
		
		return false;
	}

	// retorna um arraylist de todos os usuários já registrados
	private static ArrayList<Usuario> getUsuariosArmazenados(){
		return GerJSON.usuarioJSONtoArrayList(GerJSON.getJSON("dados\\usuarios.json"));
	}

	// retorna o saldo disponivel de um usuário
	public static double saldoDisponivel(String usuario) {
		Usuario user = getUsuario(usuario);

		return user.getRenda() - GerGasto.somaGastos(GerGasto.getGastosUsuarioMes(usuario, Util.getMesAtual()));
	}

	// retorna a porcentagem gasta da renda do usuário
	public static double porcentagemGasta(String usuario) throws Exception{
		Usuario user = getUsuario(usuario);

		if (user.getRenda() != 0) {
			return GerGasto.somaGastos(GerGasto.getGastosUsuarioMes(usuario, Util.getMesAtual())) / user.getRenda();
		} else {
			throw new Exception("Renda não informada");
		}
	}
	
	public static boolean usuarioTemCartao(String usuario) {
		ArrayList<Cartao> cartoesUsuario = GerCartao.getCartoesUsuario(usuario);
		return cartoesUsuario.size() > 0;
	}
	
}
