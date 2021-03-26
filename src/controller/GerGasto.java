package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import model.Gasto;
import model.Usuario;

public class GerGasto {

	// armazena os gastos em um arquivo
	public static void escreveGasto(String usuario, double valor, String desc) {
		GerArquivo.escreveGasto(usuario, new Gasto(valor, desc));
	}

	//retorna um arraylist dos gastos de um usuário
	public static ArrayList<Gasto> getGastosUsuario(String usuario) {
		return GerJSON.gastosJSONtoArrayList(GerJSON.getJSON("dados\\" + usuario + ".txt"));
	}

	//retorna um arraylist dos gastos de um usuário em um determinado periodo
	public static ArrayList<Gasto> getGastoUsuarioPeriodo(String usuario, String i, String f) {
		ArrayList<Gasto> gastos = getGastosUsuario(usuario);
		ArrayList<Gasto> gastoPeriodo = new ArrayList<Gasto>();

		Date inicio = Util.stringToDate(i);
		Date fim = Util.getProxDia(Util.stringToDate(f));

		for (Gasto gasto : gastos) {
			if (Util.checaPeriodo(gasto.getData(), inicio, fim)) {
				gastoPeriodo.add(gasto);
			}
		}

		return gastoPeriodo;
	}

	// retorna um arraylist dos gastos de um usuário em um determinado mês
	public static ArrayList<Gasto> getGastosUsuarioMes(String usuario, String mes) {
		ArrayList<Gasto> gastos = getGastosUsuario(usuario);
		ArrayList<Gasto> gastoPeriodo = new ArrayList<Gasto>();

		Date data = Util.stringToDate(mes);

		for (Gasto gasto : gastos) {
			if (gasto.getData().getMonth() == Util.stringToDate(mes).getMonth()) {
				gastoPeriodo.add(gasto);
			}
		}

		return gastoPeriodo;
	}
	
	// retorna 
	public static Gasto getGastoUsuario(String usuario, ArrayList<Gasto> gastosPeriodo, int i) {
		ArrayList<Gasto> gastosUsuario = getGastosUsuario(usuario);
		
		for (Gasto gasto : gastosUsuario) {
			if (gasto.equals(gastosPeriodo.get(i))) {
				return gasto;
			}
		}
		
		return null;
	}

	// remove um gasto do arquivo correspondente a um usuário e retorna se apagou ou não
	public static boolean removeGasto(String usuario, ArrayList<Gasto> gastosPeriodo, int i) {
		ArrayList<Gasto> gastosUsuario = getGastosUsuario(usuario);
		
		for (Gasto gasto : gastosUsuario) {
			if (gasto.equals(gastosPeriodo.get(i))) {
				gastosUsuario.remove(gasto);
				GerArquivo.sobreescreveGastos(usuario, gastosUsuario);
				return true;
			}
		}
		
		return false;
	}

	// edita um gasto do arquivo correspondente a um usuário e retorna o gasto a ser editado
	public static void editaGasto(String usuario, ArrayList<Gasto> gastosPeriodo, int aEditar, Gasto editado) {
		ArrayList<Gasto> gastosUsuario = getGastosUsuario(usuario);

		// incrementa i até chegar no gasto a ser editado dentro da lista de gastos de um usuário específico
		for (int i = 0; i < gastosUsuario.size() && !gastosUsuario.get(i).equals(gastosPeriodo.get(aEditar)); i++) {
			if (gastosUsuario.get(i).equals(gastosPeriodo.get(aEditar))) {
				gastosUsuario.remove(i);
				gastosUsuario.add(i, editado);
			}
		}
		
		GerArquivo.sobreescreveGastos(usuario, gastosUsuario);
	}

	// faz a soma de todos os gastos de um usuárip
	public static double somaGastos(ArrayList<Gasto> gastos) {
		Double soma = 0.0;

		for (Gasto gasto : gastos) {
			soma += gasto.getValor();
		}

		return soma;
	}

}
