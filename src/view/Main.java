package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import controller.GerArquivo;
import controller.GerGasto;
import controller.GerUser;
import controller.Util;
import model.Gasto;

public class Main {

	public static void main(String[] args) {
		String user = menuLogin();

		if (user != null) {
			menuPrincipal(user);
		}
	}

	// Menu para o login do usuário
	private static String menuLogin() {
		String login;
		String senha;
		Scanner sc = new Scanner(System.in);
		System.out.print("1 - Login\n" + "2 - Registrar\n" + "0 - Sair\n> ");

		char menuLogin = 'ç';
		do {
			try {
				menuLogin = sc.nextLine().charAt(0);
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Opção inválida");
			}
		} while (menuLogin != '1' && menuLogin != '2' && menuLogin != '0');

		switch (menuLogin) {
		case '1':
			System.out.print("\nLogin: ");
			login = sc.nextLine();
			System.out.print("Senha: ");
			senha = sc.nextLine();
			System.out.println();

			if (GerUser.validaLogin(login, senha)) {
				System.out.println("Logado com sucesso");
				return login;
			} else {
				System.out.println("Login invalido");
				return menuLogin();
			}

		case '2':
			System.out.println("\nRegistrando");
			System.out.print("Login: ");
			login = sc.nextLine();
			System.out.print("Senha: ");
			senha = sc.nextLine();
			System.out.println();

			if (GerUser.registrar(login, senha)) {
				System.out.println("Registro efetuado com sucesso");
				return login;
			} else {
				if (login.equals("") || senha.equals("")) {
					System.out.println("Informações inválidas");
				} else {
					System.out.println("Usuário já existente");
				}
			}

			return menuLogin();
		case '0':
			System.out.println("Saindo...\n");
			return null;
		}

		return null;
	}

	// Menu com a funções principais do programa
	private static void menuPrincipal(String usuario) {
		
		Scanner sc = new Scanner(System.in);

		System.out.print("\n1 - Adicionar gasto\n" + "2 - Remover gasto\n" + "3 - Editar gasto\n" + "4 - Relatórios\n"
				+ "5 - Configurar renda mensal\n" + "0 - Logout\n> ");
		char menuPrincipal = (char) sc.nextLine().charAt(0);

		switch (menuPrincipal) {
		case '1':
			System.out.println("\nAdicionando gasto:");
			System.out.print("Valor: ");
			double valor = sc.nextDouble();
			sc.nextLine();
			System.out.print("Descrição: ");
			String descricao = sc.nextLine();
			System.out.println();

			GerGasto.escreveGasto(usuario, valor, descricao);

			menuPrincipal(usuario);
			break;
		case '2':
			System.out.println("\nRemovendo gasto:");
			ArrayList<Gasto> gastosPeriodo = selecionarPeriodo(usuario);
			mostraGastos(gastosPeriodo);
			System.out.print("Qual gasto deseja remover (0 - sair): ");
			int indice = sc.nextInt();
			sc.nextLine();

			if (indice > 0) {
				GerGasto.removeGasto(usuario, gastosPeriodo, indice - 1);
			}

			menuPrincipal(usuario);
			break;
		case '3':
			editarGasto(usuario);
			break;
		case '4':
			relatorio(usuario);
			break;
		case '5':
			System.out.println("\nConfigurando renda mensal:");
			double ganho = 0;
			do {
				System.out.print("Renda mensal: ");
				ganho = sc.nextDouble();
				sc.nextLine();
				if (ganho < 0) {
					System.out.println("Valor inválido. O valor não pode ser menor que 0!");
				}
			} while (ganho < 0);

			GerArquivo.atualizaRenda(usuario, ganho);
			System.out.println("Renda mensal atualizada");
			menuPrincipal(usuario);
			break;
		case '0':
			System.out.println("Desconectando...");
			main(null);
			break;
		}

	}

	// Menu de opções de dados processados
	private static void relatorio(String usuario) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n1 - Visualizar extrato\n" + "2 - Saldo disponivel\n" + "0 - Voltar\n> ");
		char menuExtrato = sc.nextLine().charAt(0);

		switch (menuExtrato) {

		case '1':
			ArrayList<Gasto> gastosPeriodo = selecionarPeriodo(usuario);
			mostraGastos(gastosPeriodo);
			System.out.println("\nTOTAL:\t" + GerGasto.somaGastos(gastosPeriodo));
			relatorio(usuario);
			break;
		case '2':
			try {
				double saldoDisponivel = GerUser.saldoDisponivel(usuario);
				double porcentagemGasta = GerUser.porcentagemGasta(usuario);
				System.out.printf("\nSaldo disponível: %.2f\n", saldoDisponivel);
				System.out.printf("Porcentagem gasta: %.2f%%\n", porcentagemGasta*100);
			} catch (Exception e) {
				System.out.println("\nRenda mensal não informada");
			}
			
			relatorio(usuario);
			break;
		case '0':
			menuPrincipal(usuario);
			break;
		default:
			System.out.println("Opção inválida\n");
			relatorio(usuario);
			break;
		}
	}

	private static void editarGasto(String usuario) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nEditando gasto:");
		ArrayList<Gasto> gastosPeriodo = selecionarPeriodo(usuario);
		mostraGastos(gastosPeriodo);
		System.out.print("Qual gasto deseja editar (0 - sair): ");
		int indice = sc.nextInt();
		sc.nextLine();
		
		Gasto editado = null;

		char opt = 'ç';

		while (opt != '0' && indice != 0) {
			editado = gastosPeriodo.get(indice - 1).clone();
			mostraGasto(editado);

			do {
				System.out.print("O que deseja alterar:\n1 - Valor\n2 - Descrição\n3 - Data (Ex.:31/12/2021)\n0 - Confirmar\n> ");
				opt = sc.nextLine().charAt(0);
				
				if (opt != '1' && opt != '2' && opt != '3' && opt != '0') {
					System.out.print("Opção inválida\n> ");
				}
			} while (opt != '1' && opt != '2' && opt != '3' && opt != '0');

			System.out.print("> ");
			switch (opt) {
			case '1':
				double valor = sc.nextDouble();
				sc.nextDouble();
				editado.setValor(valor);
				System.out.println("Valor alterado\n");
				break;
			case '2':
				String desc = sc.nextLine();
				editado.setDesc(desc);
				System.out.println("Descrição alterada\n");
				break;
			case '3':
				String data = sc.nextLine();
				while(!Util.validaDataComDia(data)) {
					System.out.print("Data inválida\n> ");
					data = sc.nextLine();
				}
				editado.setData(data);
				System.out.println("Data alterada\n");
				break;
			}
		}
		
		GerGasto.editaGasto(usuario, gastosPeriodo, opt, editado);

		menuPrincipal(usuario);
	}

	// Menu de seleção de periodo de gastos
	private static ArrayList<Gasto> selecionarPeriodo(String usuario) {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nSelecione um período:");
		char opt = 0;

		do {
			System.out
					.print("1 - Mês corrente\n" + "2 - Mês específico\n" + "3 - Período específico\n" + "0 - Voltar\n> ");
			opt = sc.nextLine().charAt(0);
		} while (opt != '1' && opt != '2' && opt != '3' && opt != '0');

		switch (opt) {
		case '1':
			return GerGasto.getGastosUsuarioMes(usuario, Util.getMesAtual());
		case '2':
			System.out.print("Selecione o mês e ano (Ex.: \"01-2021\"): ");
			String mes = "";
			do {
				mes = sc.nextLine();
				if (!Util.validaData(mes)) {
					System.out.println("Data inválida");
				}
			} while (!Util.validaData(mes));

			return GerGasto.getGastosUsuarioMes(usuario, mes);
		case '3':
			System.out.print("Procurar de (Ex.: \"31-12-2021\"): ");
			String inicio = "";
			do {
				inicio = sc.nextLine();
				if (!Util.validaData(inicio)) {
					System.out.println("Data inválida");
				}
			} while (!Util.validaData(inicio));

			System.out.print("Até (Ex.: \"31-12-2021\"): ");
			String fim = "";
			do {
				fim = sc.nextLine();
				if (!Util.validaData(fim)) {
					System.out.println("Data inválida");
				}
			} while (!Util.validaData(fim));

			return GerGasto.getGastoUsuarioPeriodo(usuario, inicio, fim);
		}
		return null;
	}

	// mostra de forma formatada os gastos da lista passada como parametro
	private static void mostraGastos(ArrayList<Gasto> gastos) {
		int i = 1;
		System.out.println("\nÍndice | Valor | Descrição | Data");
		for (Iterator iterator = gastos.iterator(); iterator.hasNext(); i++) {
			Gasto gasto = (Gasto) iterator.next();
			System.out.println(i + " | " + gasto);
		}
	}

	private static void mostraGasto(Gasto gasto) {
		System.out.println("\nValor: " + gasto.getValor() + "\nDescrição: " + gasto.getDescricao() + "\nData: "
				+ gasto.getDataStr() + "\n");
	}

}
