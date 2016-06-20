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

public class KursnaListaTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT ID_KURSNE_LISTE, ID_BANKE, KL_DATUM, KL_BROJ, KL_DATPR FROM KURSNA_LISTA";
	private String orderBy = " ORDER BY ID_KURSNE_LISTE";
	private String whereStmt = "";
	public KursnaListaTableModel(Object[] colNames, int rowCount) {
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
			String id_liste = rset.getString("ID_KURSNE_LISTE");
			String id_banke = rset.getString("ID_BANKE");
			String datum = rset.getString("KL_DATUM");
			String broj = rset.getString("KL_BROJ");
			String datpr = rset.getString("KL_DATPR");
			addRow(new String[] { id_liste, id_banke, datum, broj, datpr });
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
				"DELETE FROM KURSNA_LISTA WHERE ID_KURSNE_LISTE=?");
		String id_liste = (String) getValueAt(index, 0);
		stmt.setString(1, id_liste);
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

	private int sortedInsert(String id_liste, String id_banke, String datum, String broj, String datpr) {
		int left = 0;
		int right = getRowCount() - 1;
		int mid = (left + right) / 2;
		while (left <= right) {
			mid = (left + right) / 2;
			String aid_liste = (String) getValueAt(mid, 0);
			if (SortUtils.getLatCyrCollator().compare(aid_liste, aid_liste) > 0)
				left = mid + 1;
			else if (SortUtils.getLatCyrCollator().compare(id_liste, id_liste) < 0)
				right = mid - 1;
			else
				// ako su jednaki: to ne moze da se desi ako tabela ima primarni
				// kljuc
				break;
		}
		insertRow(left, new String[] { id_liste, id_banke, datum, broj, datpr });
		return left;
	}
	
	public int insertRow(String id_liste, String id_banke, String datum, String broj, String datpr) throws SQLException {
		int retVal = 0;
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"INSERT INTO KURSNA_LISTA (ID_KURSNE_LISTE, ID_BANKE, KL_DATUM, KL_BROJ, KL_DATPR) VALUES (? ,? ,? ,? ,?)");
		stmt.setString(1, id_liste);
		stmt.setString(2, id_banke);
		stmt.setString(3, datum);
		stmt.setString(4, broj);
		stmt.setString(5, datpr);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		// Unos sloga u bazu
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i unos u TableModel
			retVal = sortedInsert(id_liste, id_banke, datum, broj, datpr);
			fireTableDataChanged();
		}
		return retVal;
	}

	public void updateRow(int index, String id_liste, String id_banke, String datum, String broj, String datpr, String stari_id_liste) throws SQLException {
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"UPDATE KURSNA_LISTA SET ID_KURSNE_LISTE=?, ID_BANKE=?, KL_DATUM=?, KL_BROJ=?, KL_DATPR=?");
		stmt.setString(1, id_liste);
		stmt.setString(2, id_banke);
		stmt.setString(3, datum);
		stmt.setString(4, broj);
		stmt.setString(5, datpr);
		stmt.setString(6, stari_id_liste);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			setValueAt(id_liste, index, 0);
			setValueAt(id_banke, index, 1);
			setValueAt(datum, index, 2);
			setValueAt(broj, index, 3);
			setValueAt(datpr, index, 4);
			fireTableDataChanged();
		}			
	}

	/*
	public void searchRow(String id_liste, String id_banke, String datum, String broj, String datpr) throws SQLException {

		String sqlQuery = "SELECT * FROM KURSNA_LISTA WHERE ID_KURSNE_LISTE LIKE '%"
				+id_liste+ "%' AND ID_BANKE LIKE '%" +id_banke+  "%' AND KL_DATUM LIKE '%" +datum+  "%' AND KL_BROJ LIKE '%" +broj+"%' AND KL_DATPR LIKE '%" +datpr+ "%'";
		fillData(sqlQuery);
	}
		 */
	
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
		String sqlQuery = "SELECT * FROM KURSNA_LISTA WHERE "+preparedString;
		fillData(sqlQuery);				
	}
	
	
}
