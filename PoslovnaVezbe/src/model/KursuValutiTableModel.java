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

public class KursuValutiTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT kls_rbr, kurs_u_valuti.id_kursne_liste, OS.va_naziv, PR.va_naziv, kls_kupovni, kls_srednji, kls_prodajni "
			+ "FROM kurs_u_valuti JOIN  valute as OS on kurs_u_valuti.id_valute=OS.id_valute JOIN valute as PR on val_id_valute=PR.id_valute "
			+ "JOIN kursna_lista on kurs_u_valuti.id_kursne_liste=kursna_lista.id_kursne_liste";
	//SELECT kls_rbr, kurs_u_valuti.id_kursne_liste, OS.VA_NAZIV, PR.VA_NAZIV, kls_kupovni, kls_srednji, kls_prodajni 
	//FROM kurs_u_valuti JOIN valute as OS on kurs_u_valuti.id_valute=OS.id_valute JOIN valute as PR on val_id_valute=PR.id_valute 
	//JOIN kursna_lista on kurs_u_valuti.id_kursne_liste=kursna_lista.id_kursne_liste
	private String orderBy = " ORDER BY KLS_RBR";
	private String whereStmt = "";
	public KursuValutiTableModel(Object[] colNames, int rowCount) {
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
				String KLS_RBR = rset.getString("KLS_RBR");
				String ID_KURSNE_LISTE = rset.getString("ID_KURSNE_LISTE");
				String OS_VA_NAZIV = rset.getString(3);
				String PR_VA_NAZIV = rset.getString(4);
				String KLS_KUPOVNI = rset.getString("KLS_KUPOVNI");
				String KLS_SREDNJI = rset.getString("KLS_SREDNJI");
				String KLS_PRODAJNI = rset.getString("KLS_PRODAJNI");
				
				addRow(new Object[] { KLS_RBR, ID_KURSNE_LISTE, OS_VA_NAZIV, 
						PR_VA_NAZIV, KLS_KUPOVNI, KLS_SREDNJI, KLS_PRODAJNI });
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
				 if(i == 3) {
					  name="id_valute";
				  }
				  else if (i == 4){
					  name="val_id_valute";
				  }	
			  preparedString += name + "=?, " ;
			}
			String lastName = rsmd.getColumnName(columnCount);
			preparedString += lastName + "=?" ;
			stmt1.close();
			
			PreparedStatement stmt2 = DBConnection.getConnection().prepareStatement(
					"UPDATE kurs_u_valuti SET " + preparedString + " WHERE KLS_RBR=?");
			for (int i = 1; i <= values.size(); i++) {
				stmt2.setString(i, values.get(i-1));
				if(i==3) {
					String query= "SELECT id_valute FROM valute WHERE va_naziv=?";
					PreparedStatement prv1 = DBConnection.getConnection().prepareStatement(query);
					prv1.setString(1, values.get(2));
					ResultSet rset = prv1.executeQuery();
					if(rset.next()) {
						stmt2.setString(3, rset.getString(1));						
					}					
				} else if(i==4) {
					String query= "SELECT id_valute FROM valute WHERE va_naziv=?";
					PreparedStatement prv2 = DBConnection.getConnection().prepareStatement(query);
					prv2.setString(1, values.get(3));
					ResultSet rset = prv2.executeQuery();
					if(rset.next()) {
						stmt2.setString(4, rset.getString(1));
					}
				}
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
			  System.out.print(name);
			  if(i == 3) {
				  name="id_valute";
			  }
			  else if (i == 4){
				  name="val_id_valute";
			  }		 
			  preparedString1 += name + ", " ;
			  preparedString2 += "?, ";
			}
			String lastName = rsmd.getColumnName(columnCount);
			preparedString1 += lastName;
			preparedString2 += "?";
			
			stmt1.close();
			
			PreparedStatement stmt2 = DBConnection.getConnection().prepareStatement(
					"INSERT INTO kurs_u_valuti (" + preparedString1 +  ") VALUES (" + preparedString2 + ")");
			for (int i = 1; i <= values.size(); i++) {
				if(i==3) {
					System.out.print(values.get(2));
					String query= "SELECT id_valute FROM valute WHERE va_naziv=?";
					PreparedStatement prv1 = DBConnection.getConnection().prepareStatement(query);
					prv1.setString(1, values.get(2));
					ResultSet rset = prv1.executeQuery();
					if(rset.next()) {
						stmt2.setString(3, rset.getString(1));						
					}					
				} else if(i==4) {
					System.out.print(values.get(3));
					String query= "SELECT id_valute FROM valute WHERE va_naziv=?";
					PreparedStatement prv2 = DBConnection.getConnection().prepareStatement(query);
					prv2.setString(1, values.get(3));
					ResultSet rset = prv2.executeQuery();
					if(rset.next()) {
						stmt2.setString(4, rset.getString(1));						
					}									
				} else {
				stmt2.setString(i, values.get(i-1));
				}
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
					"DELETE FROM kurs_u_valuti WHERE kls_rbr=?");
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
			  if(i == 3) {
				  name="id_valute";
				  String query= "SELECT id_valute FROM valute WHERE va_naziv=?";
				  PreparedStatement prv1 = DBConnection.getConnection().prepareStatement(query);
				  prv1.setString(1, values.get(2));
				  ResultSet rset = prv1.executeQuery();
				  if(rset.next()) {
					  preparedString += name+" LIKE '%"+rset.getString(1)+"%' AND ";						
				  }
			  }else if (i == 4){
				  name="val_id_valute";
				  String query= "SELECT id_valute FROM valute WHERE va_naziv=?";
				  PreparedStatement prv1 = DBConnection.getConnection().prepareStatement(query);
				  prv1.setString(1, values.get(3));
				  ResultSet rset = prv1.executeQuery();
				  if(rset.next()) {
					  preparedString += name+" LIKE '%"+rset.getString(1)+"%' AND ";						
				  }
			  }else {
				  preparedString += name+" LIKE '%"+values.get(i-1)+"%' AND ";
			  }
			}
			preparedString += rsmd.getColumnName(columnCount)+" LIKE '%"+values.get(columnCount-1)+"%'";	
			String sqlQuery = "SELECT * FROM kurs_u_valuti WHERE "+preparedString;
			fillData(sqlQuery);				
		}
		
		public void openAsChildForm(String sql) throws SQLException{
			//String sql = "SELECT * FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra WHERE naseljeno_mesto.dr_sifra LIKE '%"
				//	+ where + "%'"; // upotrebiti where parametar
			fillData(sql);
		}


}