package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import util.SortUtils;
import database.DBConnection;

public class KlijentTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT id_klijenta, adresa, telefon, e_mail FROM Klijent";
	private String orderBy = " ORDER BY id_klijenta";
	private String whereStmt = "";
	public KlijentTableModel(Object[] colNames, int rowCount) {
		super(colNames, rowCount);
	}

	// Otvaranje upita
	public void open() throws SQLException {
		fillData(basicQuery + whereStmt + orderBy);
	}

	// Popunjavanje matrice podacima

	private void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String id = rset.getString("ID_KLIJENTA");
			String adresa = rset.getString("ADRESA");
			String telefon = rset.getString("TELEFON");
			String email = rset.getString("E_MAIL");
			addRow(new String[] { id, adresa, telefon, email });
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		// all cells false
		return false;
	}

	public void deleteRow(int index) throws SQLException {
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"DELETE FROM klijent WHERE id_klijenta=?");
		String sifra = (String) getValueAt(index, 0);
		stmt.setString(1, sifra);
		// Brisanje iz baze
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i brisanje iz TableModel-a
			removeRow(index);
			fireTableDataChanged();
		}
	}
	
	private int sortedInsert(String id, String adresa, String telefon, String email) {
		int left = 0;
		int right = getRowCount() - 1;
		int mid = (left + right) / 2;
		while (left <= right) {
			mid = (left + right) / 2;
			String aSifra = (String) getValueAt(mid, 0);
			if (SortUtils.getLatCyrCollator().compare(id, aSifra) > 0)
				left = mid + 1;
			else if (SortUtils.getLatCyrCollator().compare(id, aSifra) < 0)
				right = mid - 1;
			else
				// ako su jednaki: to ne moze da se desi ako tabela ima primarni
				// kljuc
				break;
		}
		insertRow(left, new String[] { id, adresa, telefon, email  });
		return left;
	}
	
	public int insertRow(String id, String adresa, String telefon, String email) throws SQLException {
		int retVal = 0;
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"INSERT INTO klijent (id_klijenta, adresa, telefon , e_mail) VALUES (? ,?, ?, ?)");
		stmt.setString(1, id);
		stmt.setString(2, adresa);
		stmt.setString(3, telefon);
		stmt.setString(4, email);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		// Unos sloga u bazu
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i unos u TableModel
			retVal = sortedInsert(id, adresa, telefon, email);
			fireTableDataChanged();
		}
		return retVal;
	}

	public void updateRow(int index, String id, String adresa, String telefon, String email, String oldId) throws SQLException {
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"UPDATE klijent SET id_klijenta=?, adresa=?, telefon=?, e_mail=? WHERE id_klijenta=?");
		stmt.setString(1, id);
		stmt.setString(2, adresa);
		stmt.setString(3, telefon);
		stmt.setString(4, email);
		stmt.setString(5, oldId);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			setValueAt(id, index, 0);
			setValueAt(adresa, index, 1);
			setValueAt(telefon, index, 2);
			setValueAt(email, index, 3);
			fireTableDataChanged();
		}			
	}
/*
	public void searchRow(String sifra, String naziv) throws SQLException {
		String sqlQuery = "SELECT * FROM drzava WHERE dr_sifra LIKE '%"
				+sifra+ "%' AND dr_naziv LIKE '%" +naziv+ "%'";
		fillData(sqlQuery);
	}
	*/
	

}
