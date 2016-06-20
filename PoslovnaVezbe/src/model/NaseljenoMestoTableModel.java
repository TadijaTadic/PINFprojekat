package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import util.SortUtils;

import database.DBConnection;

public class NaseljenoMestoTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT * FROM naseljeno_mesto";
	private String orderBy = " ORDER BY NM_SIFRA";
	private String whereStmt = "";

	public NaseljenoMestoTableModel(Object[] colNames, int rowCount) {
		super(colNames, rowCount);
	}

	//Otvaranje upita
	public void open() throws SQLException {
	    fillData(basicQuery + whereStmt + orderBy);
	}

	private void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String nmSifra = rset.getString("NM_SIFRA");
			String drSifra = rset.getString("DR_SIFRA");
			String nmNaziv = rset.getString("NM_NAZIV");
			String nmPTT = rset.getString("NM_PTTOZNAKA");
			addRow(new String[]{nmSifra, drSifra, nmNaziv, nmPTT});
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
	
	public void updateRow(int index, String nmsifra, String drsifra, String nmNaziv, String nmPTT, String nmstaraSifra) throws SQLException {
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"UPDATE naseljeno_mesto SET nm_sifra=?, dr_sifra=?, nm_naziv=?, nm_pttoznaka=? WHERE nm_sifra=?");
		stmt.setString(1, nmsifra);
		stmt.setString(2, drsifra);
		stmt.setString(3, nmNaziv);
		stmt.setString(4, nmPTT);
		stmt.setString(5, nmstaraSifra);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			setValueAt(nmsifra, index, 0);
			setValueAt(drsifra, index, 1);
			setValueAt(nmNaziv, index, 2);
			setValueAt(nmPTT, index, 3);
			fireTableDataChanged();
		}			
	}
	
	public void deleteRow(int index) throws SQLException {
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"DELETE FROM naseljeno_mesto WHERE nm_sifra=?");
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
	
	private int sortedInsert(String nmSifra, String drSifra, String nmNaziv, String nmPTT) {
		int left = 0;
		int right = getRowCount() - 1;
		int mid = (left + right) / 2;
		while (left <= right) {
			mid = (left + right) / 2;
			String aSifra = (String) getValueAt(mid, 0);
			if (SortUtils.getLatCyrCollator().compare(nmSifra, aSifra) > 0)
				left = mid + 1;
			else if (SortUtils.getLatCyrCollator().compare(nmSifra, aSifra) < 0)
				right = mid - 1;
			else
				// ako su jednaki: to ne moze da se desi ako tabela ima primarni
				// kljuc
				break;
		}
		insertRow(left, new String[] { nmSifra, drSifra, nmNaziv, nmPTT });
		return left;
	}
	
	public int insertRow(String nmSifra, String drSifra, String nmNaziv, String nmPTT) throws SQLException {
		int retVal = 0;
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"INSERT INTO naseljeno_mesto (nm_sifra, dr_sifra, nm_nazic, nm_pttoznaka) VALUES (? ,?, ?, ?)");
		stmt.setString(1, nmSifra);
		stmt.setString(2, drSifra);
		stmt.setString(3, nmNaziv);
		stmt.setString(4, nmPTT);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		// Unos sloga u bazu
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i unos u TableModel
			retVal = sortedInsert(nmSifra, drSifra, nmNaziv, nmPTT);
			fireTableDataChanged();
		}
		return retVal;
	}
	
	public void searchRow(ArrayList<String> values) throws SQLException {
		Statement stmt1 = DBConnection.getConnection().createStatement();
		ResultSet rs = stmt1.executeQuery(basicQuery);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String preparedString = "";
		for (int i = 1; i <= columnCount-1; i++ ) {
		  String name = rsmd.getColumnName(i);
		  preparedString += name+" LIKE '%"+values.get(i-1)+"%' AND ";
		}
		preparedString += rsmd.getColumnName(columnCount)+" LIKE '%"+values.get(columnCount-1)+"%'";	
		String sqlQuery = "SELECT * FROM naseljeno_mesto WHERE "+preparedString;
		fillData(sqlQuery);				
	}

	public void openAsChildForm(String sql) throws SQLException{
		//String sql = "SELECT * FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra WHERE naseljeno_mesto.dr_sifra LIKE '%"
			//	+ where + "%'"; // upotrebiti where parametar
		fillData(sql);
	}

}



