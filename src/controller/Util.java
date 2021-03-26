package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

	// Metodo validador do formato da data
	public static boolean validaData(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");

		try {
			sdf.parse(data);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	// Metodo validador do formato da data
	public static boolean validaDataComDia(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try {
			sdf.parse(data);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	// Metodo que formata String para data
	public static Date stringToDate(String dataStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-yyyy");
		Date data = null;

		try {
			data = sdf.parse(dataStr);
		} catch (ParseException e) {
			try {
				data = sdf2.parse(dataStr);
			} catch (ParseException e1) {
				return null;
			}
		}

		return data;
	}

	// Verifica se a data está entre o limite inferior e superior
	public static boolean checaPeriodo(Date data, Date i, Date f) {
		if (data != null && i != null && f != null) {
			return (data.after(i) || data.equals(i)) && (data.before(f) || data.equals(f));
		}
		return false;
	}

	// Retorna o mês atual formatado
	public static String getMesAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
		Date data = new Date(System.currentTimeMillis());
		return sdf.format(data);
	}
	
	// Retorna o dia seguinte a data passada como parâmetro
	public static Date getProxDia(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}
}
