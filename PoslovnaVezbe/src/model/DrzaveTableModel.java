package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import util.SortUtils;
import database.DBConnection;

public class DrzaveTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT dr_sifra, dr_naziv FROM drzava";
	private String orderBy = " ORDER BY dr_sifra";
	private String whereStmt = "";
	public DrzaveTableModel(Object[] colNames, int rowCount) {
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
			String sifra = rset.getString("DR_SIFRA");
			String naziv = rset.getString("DR_NAZIV");
			addRow(new Object[] { sifra, naziv });
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
				"DELETE FROM drzava WHERE dr_sifra=?");
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

	private int sortedInsert(String sifra, String naziv) {
		int left = 0;
		int right = getRowCount() - 1;
		int mid = (left + right) / 2;
		while (left <= right) {
			mid = (left + right) / 2;
			String aSifra = (String) getValueAt(mid, 0);
			if (SortUtils.getLatCyrCollator().compare(sifra, aSifra) > 0)
				left = mid + 1;
			else if (SortUtils.getLatCyrCollator().compare(sifra, aSifra) < 0)
				right = mid - 1;
			else
				// ako su jednaki: to ne moze da se desi ako tabela ima primarni
				// kljuc
				break;
		}
		insertRow(left, new String[] { sifra, naziv });
		return left;
	}
	
	public int insertRow(String sifra, String naziv) throws SQLException {
		int retVal = 0;
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"INSERT INTO drzava (dr_sifra, dr_naziv) VALUES (? ,?)");
		stmt.setString(1, sifra);
		stmt.setString(2, naziv);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		// Unos sloga u bazu
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i unos u TableModel
			retVal = sortedInsert(sifra, naziv);
			fireTableDataChanged();
		}
		return retVal;
	}

	public void updateRow(int index, String sifra, String naziv, String staraSifra) throws SQLException {
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"UPDATE drzava SET dr_sifra=?, dr_naziv=? WHERE dr_sifra=?");
		stmt.setString(1, sifra);
		stmt.setString(2, naziv);
		stmt.setString(3, staraSifra);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			setValueAt(sifra, index, 0);
			setValueAt(naziv, index, 1);
			fireTableDataChanged();
		}			
	}

	public void searchRow(String sifra, String naziv) throws SQLException {
		String sqlQuery = "SELECT * FROM drzava WHERE dr_sifra LIKE '%"
				+sifra+ "%' AND dr_naziv LIKE '%" +naziv+ "%'";
		fillData(sqlQuery);
	}

}