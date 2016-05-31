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

public class BankeTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT * FROM banka";
	private String orderBy = " ORDER BY ID_BANKE";
	private String whereStmt = "";
	public BankeTableModel(Object[] colNames, int rowCount) {
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
				String ID_BANKE = rset.getString("ID_BANKE");
				String SIFRA_BANKE = rset.getString("SIFRA_BANKE");
				String PR_PIB = rset.getString("PR_PIB");
				String PR_NAZIV = rset.getString("PR_NAZIV");
				String PR_ADRESA = rset.getString("PR_ADRESA");
				String PR_EMAIL = rset.getString("PR_EMAIL");
				String PR_WEB = rset.getString("PR_WEB");
				String PR_TELEFON = rset.getString("PR_TELEFON");
				String PR_FAX = rset.getString("PR_FAX");
				boolean PR_BANKA = rset.getBoolean("PR_BANKA");
				
				addRow(new Object[] { ID_BANKE, SIFRA_BANKE, PR_PIB, PR_NAZIV, PR_ADRESA,
						PR_EMAIL, PR_WEB, PR_TELEFON, PR_FAX, PR_BANKA });
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
		
		public void updateRow(int index, String staraSifra, ArrayList<String> values) throws SQLException {
			Statement stmt1 = DBConnection.getConnection().createStatement();
			ResultSet rs = stmt1.executeQuery(basicQuery);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String preparedString = "";
			for (int i = 1; i <= columnCount-1; i++ ) {
			  String name = rsmd.getColumnName(i);
			  preparedString += name + "=?, " ;
			}
			String lastName = rsmd.getColumnName(columnCount);
			preparedString += lastName + "=?" ;
			stmt1.close();
			
			PreparedStatement stmt2 = DBConnection.getConnection().prepareStatement(
					"UPDATE banka SET " + preparedString + " WHERE ID_BANKE=?");
			for (int i = 1; i <= values.size(); i++) {
				stmt2.setString(i, values.get(i-1));
			}
			stmt2.setString(values.size() + 1, staraSifra);
			
			int rowsAffected = stmt2.executeUpdate();
			stmt2.close();
			DBConnection.getConnection().commit();
			if (rowsAffected > 0) {
				for (int i = 0; i <= values.size()-1; i++) {
					setValueAt(values.get(i), index, i);
				}
				fireTableDataChanged();
			}			
		}
		
		private int sortedInsert(ArrayList<String> values, String id) {
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
			insertRow(left, values.toArray());
			return left;
		}
		
		public int insertRow(ArrayList<String> values, String id) throws SQLException {
			int retVal = 0;
			Statement stmt1 = DBConnection.getConnection().createStatement();
			ResultSet rs = stmt1.executeQuery(basicQuery);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String preparedString1 = "";
			String preparedString2 = "";
			for (int i = 1; i <= columnCount-1; i++ ) {
			  String name = rsmd.getColumnName(i);
			  preparedString1 += name + ", " ;
			  preparedString2 += "?, ";
			}
			String lastName = rsmd.getColumnName(columnCount);
			preparedString1 += lastName;
			preparedString2 += "?";
			
			stmt1.close();
			
			PreparedStatement stmt2 = DBConnection.getConnection().prepareStatement(
					"INSERT INTO banka (" + preparedString1 +  ") VALUES (" + preparedString2 + ")");
			for (int i = 1; i <= values.size(); i++) {
				stmt2.setString(i, values.get(i-1));
			}
			int rowsAffected = stmt2.executeUpdate();
			stmt2.close();
			DBConnection.getConnection().commit();
			if (rowsAffected > 0) {
				// i unos u TableModel
				retVal = sortedInsert(values, id);
				fireTableDataChanged();
			}
			
			return retVal;
		}

		public void deleteRow(int index) throws SQLException {
			PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
					"DELETE FROM banka WHERE id_banke=?");
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
			String sqlQuery = "SELECT * FROM banka WHERE "+preparedString;
			fillData(sqlQuery);				
		}

}
