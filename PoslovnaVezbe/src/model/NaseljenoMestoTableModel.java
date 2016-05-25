package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import util.SortUtils;

import database.DBConnection;

public class NaseljenoMestoTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT nm_sifra, nm_naziv, naseljeno_mesto.dr_sifra, dr_naziv FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra";
	private String orderBy = " ORDER BY nm_sifra";
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
			String sifra = rset.getString("NM_SIFRA");
			String naziv = rset.getString("NM_NAZIV");
			String sifraDrzave = rset.getString("DR_SIFRA");
			String nazivDrzave = rset.getString("DR_NAZIV");
			addRow(new String[]{sifra, naziv, sifraDrzave, nazivDrzave});
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
	
	public void updateRow(int index, String nmsifra, String nmnaziv, String drsifra, String nmstaraSifra) throws SQLException {
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"UPDATE naseljeno_mesto SET nm_sifra=?, nm_naziv=?, dr_sifra=? WHERE nm_sifra=?");
		stmt.setString(1, nmsifra);
		stmt.setString(2, nmnaziv);
		stmt.setString(3, drsifra);
		stmt.setString(4, nmstaraSifra);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			setValueAt(nmsifra, index, 0);
			setValueAt(nmnaziv, index, 1);
			setValueAt(drsifra, index, 2);
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
	
	private int sortedInsert(String sifra, String naziv, String drsifra, String drnaziv) {
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
		insertRow(left, new String[] { sifra, naziv, drsifra, drnaziv });
		return left;
	}
	
	public int insertRow(String sifra, String naziv, String drsifra, String drnaziv) throws SQLException {
		int retVal = 0;
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"INSERT INTO naseljeno_mesto (nm_sifra, nm_naziv, dr_sifra) VALUES (? ,?, ?)");
		stmt.setString(1, sifra);
		stmt.setString(2, naziv);
		stmt.setString(3, drsifra);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		// Unos sloga u bazu
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i unos u TableModel
			retVal = sortedInsert(sifra, naziv, drsifra, drnaziv);
			fireTableDataChanged();
		}
		return retVal;
	}
	
	public void searchRow(String sifra, String naziv, String drsifra) throws SQLException {
		String sqlQuery = "SELECT * FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra WHERE nm_sifra LIKE '%"
				+ sifra
				+ "%' AND nm_naziv LIKE '%"
				+ naziv
				+ "%' AND naseljeno_mesto.dr_sifra LIKE '%" + drsifra + "%'";
		fillData(sqlQuery);
	}

	/**
	 * Inicijalno popunjavanje forme kada se otvori iz forme Drzave preko next mehanizma
	 * @param where
	 * @throws SQLException
	 */
	public void openAsChildForm(String sql) throws SQLException{
		//String sql = "SELECT * FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra WHERE naseljeno_mesto.dr_sifra LIKE '%"
			//	+ where + "%'"; // upotrebiti where parametar
		fillData(sql);
	}

}



