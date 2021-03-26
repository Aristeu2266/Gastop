package controller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;

import model.Gasto;
import model.Usuario;

public class GerArquivo {
	
	// escreve um usuario em um arquivo json
	public static void escreveUsuario(Usuario usuario) {
		JSONArray conteudo = GerJSON.getJSON("dados\\usuarios.json");
		
		try (FileWriter arquivo = new FileWriter("dados\\usuarios.json")) {
			conteudo.add(usuario.getJSON());
			arquivo.write(conteudo.toJSONString());
		} catch (IOException e) {
            e.printStackTrace();
        }
	}

	// escreve gastos no txt do usuário
	public static void escreveGasto(String usuario, Gasto gasto) {
		JSONArray conteudo = GerJSON.getJSON("dados\\" + usuario + ".txt");
		
		try (FileWriter arquivo = new FileWriter("dados\\" + usuario + ".txt")) {
			conteudo.add(gasto.getJSON());
			arquivo.write(conteudo.toJSONString());
		} catch (IOException e) {
            e.printStackTrace();
        }
	}

	// edita um gasto no arquivo txt de um usuário
	public static void sobreescreveGastos(String usuario, ArrayList<Gasto> gastos) {
		resetaArquivo("dados\\" + usuario + ".txt");
		for (Gasto gasto : gastos) {
			escreveGasto(usuario, gasto);
		}
	}
	
	// edita um gasto no arquivo txt de um usuário
	public static void sobreescreveUsuarios(ArrayList<Usuario> usuarios) {
		resetaArquivo("dados\\usuarios.json");
		for (Usuario usuario: usuarios) {
			escreveUsuario(usuario);
		}
	}
	
	//atualiza a renda do usuário no arquivo
	public static void atualizaRenda(String usuario, double renda) {
		JSONArray conteudo = GerJSON.getJSON("dados\\usuarios.json");
		ArrayList<Usuario> usuarios = GerJSON.usuarioJSONtoArrayList(conteudo);
		
		// Para ao encontrar o usuário a ser atualizado
		for (Usuario user : usuarios) {
			if (user.getLogin().equals(usuario)) {
				user.setRenda(renda);
				break;
			}
		}
		
		sobreescreveUsuarios(usuarios);
	}

	// valida se o diretorio é existente
	public static void validaDir(String dirArquivo) {
		String dir = dirArquivo.split("\\\\")[0];
		String arq = dirArquivo.split("\\\\")[1];

		File diretorio = new File(dir);
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}

		File arquivo = new File(dir + "\\" + arq);

		if(!arquivo.exists()) {
			try {
				FileWriter fw = new FileWriter(arquivo.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("[]");
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}

	//apaga o conteudo do arquivo deixando com um json array vazio
	private static void resetaArquivo(String dirArquivo) {
		validaDir(dirArquivo);

		File arquivo = new File(dirArquivo);
		try {
			FileWriter fw = new FileWriter(arquivo.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("[]");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
