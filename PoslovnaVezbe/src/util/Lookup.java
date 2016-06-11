package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection;


public class Lookup {
	
	public static String getDrzava (String sifraDrzave) throws SQLException {
		String naziv = "";
		if (sifraDrzave == "") return naziv;
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement("SELECT DR_NAZIV FROM DRZAVA WHERE DR_SIFRA = ?");
		stmt.setString(1, sifraDrzave);
		ResultSet rset = stmt.executeQuery();
		while (rset.next()) {	
			naziv = rset.getString("DR_NAZIV");
		}
		rset.close();
		stmt.close();	
		return naziv;
	}
	
	public static ArrayList<String> getKlijent (String idKlijenta) throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		String adresa = "", email = "", telefon = "";
		if (idKlijenta == "") {
			list.add(adresa);
			list.add(email);
			list.add(telefon);
			return list;
		}
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement("SELECT ADRESA, E_MAIL, TELEFON FROM KLIJENT WHERE ID_KLIJENTA = ?");
		stmt.setString(1, idKlijenta);
		ResultSet rset = stmt.executeQuery();
		while (rset.next()) {	
			adresa = rset.getString("ADRESA");
			list.add(adresa);
			email = rset.getString("E_MAIL");
			list.add(email);
			telefon = rset.getString("TELEFON");
			list.add(telefon);
		}
		rset.close();
		stmt.close();	
		return list;
	}
}





