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

public class NalogZaUplatuTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	private String collumnNames = "ASI_BROJSTAVKE, DSR_IZVOD, VPL_OZNAKA, NM_SIFRA, "
			+ "ID_VALUTE, ASI_DUZNIK, ASI_SVRHA, ASI_POVERILAC, ASI_DATPRI, ASI_DATVAL,"
			+ "ASI_RACDUZ, ASI_MODZAD, ASI_PBZAD, "
			+ "ASI_RACPOV,"
			+ " ASI_MODODOB, ASI_PBODO, ASI_HITNO, ASI_IZNOS";
	private String basicQuery = "SELECT "+collumnNames+" FROM ANALITIKA_IZVODA";
	private String orderBy = " ORDER BY ASI_BROJSTAVKE";
	private String whereStmt = "";

	public NalogZaUplatuTableModel(String[] strings, int i) {
		super(strings, i);
	}

	public void open() throws SQLException{
		fillData(basicQuery + whereStmt + orderBy);
	}

	// Popunjavanje matrice podacima

	private void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String ASI_BROJSTAVKE = rset.getString("ASI_BROJSTAVKE");
			String DSR_IZVOD = rset.getString("DSR_IZVOD");
			String VPL_OZNAKA = rset.getString("VPL_OZNAKA");
			String NM_SIFRA = rset.getString("NM_SIFRA");
			String ID_VALUTE = rset.getString("ID_VALUTE");
			String ASI_DUZNIK = rset.getString("ASI_DUZNIK");
			String ASI_SVRHA = rset.getString("ASI_SVRHA");
			String ASI_POVERILAC = rset.getString("ASI_POVERILAC");
			String ASI_DATPRI = rset.getString("ASI_DATPRI");
			String ASI_DATVAL = rset.getString("ASI_DATVAL");
			String ASI_RACDUZ = rset.getString("ASI_RACDUZ");
			String ASI_MODZAD = rset.getString("ASI_MODZAD");
			String ASI_PBZAD = rset.getString("ASI_PBZAD");
			String ASI_RACPOV = rset.getString("ASI_RACPOV");
			String ASI_MODODOB = rset.getString("ASI_MODODOB");
			String ASI_PBODO = rset.getString("ASI_PBODO");
			boolean ASI_HITNO = rset.getBoolean("ASI_HITNO");			
			String ASI_IZNOS = rset.getString("ASI_IZNOS");
			
			addRow(new Object[] { ASI_BROJSTAVKE, DSR_IZVOD, VPL_OZNAKA, NM_SIFRA, ID_VALUTE, ASI_DUZNIK,
					ASI_SVRHA, ASI_POVERILAC, ASI_DATPRI, ASI_DATVAL,
					ASI_RACDUZ, ASI_MODZAD, ASI_PBZAD,
					ASI_RACPOV,  ASI_MODODOB, 
					ASI_PBODO, ASI_HITNO, ASI_IZNOS});
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
				"UPDATE ANALITIKA_IZVODA SET " + preparedString + " WHERE ASI_BROJSTAVKE=?");
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
				"INSERT INTO ANALITIKA_IZVODA (" + preparedString1 +  ") VALUES (" + preparedString2 + ")");
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
				"DELETE FROM ANALITIKA_IZVODA WHERE ASI_BROJSTAVKE=?");
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
		String sqlQuery = "SELECT "+collumnNames+" FROM ANALITIKA_IZVODA WHERE "+preparedString;
		fillData(sqlQuery);		
	}

}
