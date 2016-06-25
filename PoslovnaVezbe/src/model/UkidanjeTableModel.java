package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import util.SortUtils;
import database.DBConnection;

public class UkidanjeTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT id_ukidanja, id_racuna, uk_datukidanja, uk_naracun FROM ukidanje";
	private String orderBy = " ORDER BY id_ukidanja";
	private String whereStmt = "";
	public UkidanjeTableModel(Object[] colNames, int rowCount) {
		super(colNames, rowCount);
	}
	public void open() throws SQLException {
		fillData(basicQuery + whereStmt + orderBy);
	}
	
	private void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String idukid= rset.getString("ID_UKIDANJA");
			String idrac = rset.getString("ID_RACUNA");
			String datum = rset.getString("UK_DATUKIDANJA");
			String naracun = rset.getString("UK_NARACUN");
			
			
			
			
			addRow(new Object[] { idukid, idrac, datum, naracun });
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
				"DELETE FROM ukidanje WHERE id_ukidanja=?");
		String id = (String) getValueAt(index, 0);
		stmt.setString(1, id);
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
	
	private int sortedInsert(String idukid,String idrac,String datum,String naracun) {
		int left = 0;
		int right = getRowCount() - 1;
		int mid = (left + right) / 2;
		while (left <= right) {
			mid = (left + right) / 2;
			String aSifra = (String) getValueAt(mid, 0);
			if (SortUtils.getLatCyrCollator().compare(idukid, aSifra) > 0)
				left = mid + 1;
			else if (SortUtils.getLatCyrCollator().compare(idukid, aSifra) < 0)
				right = mid - 1;
			else
				// ako su jednaki: to ne moze da se desi ako tabela ima primarni
				// kljuc
				break;
		}
		//insertRow(left, new String[] { idrac, idban, valuta, idklijenta, racun, datum, vazi });
		insertRow(left, new String[] { idukid, idrac, datum, naracun });
		return left;
	}
	
	public int insertRow(String idukid,String idrac,String datum,String naracun) throws SQLException {
		int retVal = 0;
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"INSERT INTO ukidanje (id_ukidanja, id_racuna, uk_datukidanja, uk_naracun) VALUES (? , ?, ?, ?)");
		stmt.setString(1, idukid);
		stmt.setString(2, idrac);
		stmt.setString(3, datum);
		stmt.setString(4, naracun);
		
		
		
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		// Unos sloga u bazu
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i unos u TableModel
			retVal = sortedInsert(idukid, idrac, datum, naracun);
			fireTableDataChanged();
		}
		return retVal;
	}
	
	public void updateRow(int index, String idukid,String idrac,String datum,String naracun, String staraSifra) throws SQLException {
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"UPDATE ukidanje SET id_ukidanja=?, id_racuna=?, uk_datukidanja=?,uk_naracun=? WHERE id_ukidanja=?");
		stmt.setString(1, idukid);
		stmt.setString(2, idrac);
		stmt.setString(3, datum);
		stmt.setString(4, naracun);
		
		stmt.setString(5, staraSifra);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			setValueAt(idukid, index, 0);
			setValueAt(idrac, index, 1);
			setValueAt(datum, index, 2);
			setValueAt(naracun, index, 3);
			
			
			
			fireTableDataChanged();
		}			
	}
	
	public void searchRow(String idukid,String idrac,String datum,String naracun) throws SQLException {
		String sqlQuery = "SELECT * FROM ukidanje WHERE id_ukidanja LIKE '%"
				+idukid
				+ "%' AND id_racuna LIKE '%"
				+idrac
				+ "%' AND uk_datukidanja LIKE '%"
				+datum
				+"%' AND uk_naracun LIKE '%"
				+naracun
				+"%'";
				
				
		fillData(sqlQuery);
	}
}
