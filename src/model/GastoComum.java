package model;

public class GastoComum extends Gasto{

	public GastoComum(String user, double valor, String desc, String data) {
		super(user, valor, desc, data);
	}

	public GastoComum(String user, double valor, String desc) {
		super(user, valor, desc);
	}

	public GastoComum(String user, double valor) {
		super(user, valor);
	}
	
}
