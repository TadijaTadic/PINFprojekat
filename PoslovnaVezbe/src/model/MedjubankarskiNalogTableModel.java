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

public class MedjubankarskiNalogTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT * FROM medjubankarski_nalog";
	private String orderBy = " ORDER BY ID_NALOGA";
	private String whereStmt = "";
	public MedjubankarskiNalogTableModel(Object[] colNames, int rowCount) {
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
				String id_naloga = rset.getString("ID_NALOGA");
				String sifra_banke = rset.getString("SIFRA_BANKE");
				String ban_sifra_banke = rset.getString("BAN_SIFRA_BANKE");
				String ukupan_iznos = rset.getString("UKUPAN_IZNOS");
				String datum = rset.getString("DATUM");
				boolean rtgs = rset.getBoolean("RTGS_");
				String status = rset.getString("STATUS");
				
				addRow(new Object[] { id_naloga, sifra_banke, ban_sifra_banke, ukupan_iznos, datum,
						rtgs, status });
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
					"UPDATE medjubankarski_nalog SET " + preparedString + " WHERE ID_NALOGA=?");
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
					"INSERT INTO medjubankarski_nalog (" + preparedString1 +  ") VALUES (" + preparedString2 + ")");
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
					"DELETE FROM medjubankarski_nalog WHERE id_naloga=?");
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
			String sqlQuery = "SELECT * FROM medjubankarski_nalog WHERE "+preparedString;
			fillData(sqlQuery);				
		}

}
