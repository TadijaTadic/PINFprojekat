package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import sun.awt.SunHints.Value;
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
		insertIzvod(values);
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
	
	public void insertIzvod(ArrayList<String> values) throws SQLException {
		//8,10,13
		String datum="";
		String racun="";
		String iznos="";
		/*System.out.print(values.get(8));
		String delims1 = "[ ]";
		String[] tokens1 = values.get(8).split(delims1);
		String da = tokens1[0].trim();*/
		Statement stmtb1 = DBConnection.getConnection().createStatement();
		ResultSet rsetb1 = stmtb1.executeQuery("SELECT * FROM racuni_pravnih_lica WHERE id_racuna='"+values.get(10)+"'");
		if (rsetb1.next()) {
			/*Statement stmt1 = DBConnection.getConnection().createStatement();
			String date="SELECT asi_datpri, asi_racpov, asi_iznos FROM ANALITIKA_IZVODA WHERE asi_racduz='"+values.get(13)+"' AND CONVERT(VARCHAR(25), asi_datpri, 126) LIKE '%"+da+"%'";
			ResultSet rset1 = stmt1.executeQuery(date);
			if(rset1.next()) {*/
				datum = values.get(8);
				racun = values.get(10);
				iznos = values.get(17);
				String delims = "[ ]";
				String[] tokens = datum.split(delims);
				System.out.print(tokens[0]);
				String lajk = tokens[0].trim();
				
				/*PreparedStatement stmt1 = DBConnection.getConnection().prepareStatement("SELECT asi_datpri, asi_racduz, asi_iznos "
						+ "FROM ANALITIKA_IZVODA WHERE asi_racduz=?");
				stmt1.setString(1, values.get(10));
				ResultSet rset1 = stmt1.executeQuery();*/
				
				String qwer="SELECT * FROM dnevno_stanje_racuna WHERE id_racuna='";
			    String y="' AND CONVERT(VARCHAR(25), dsr_datum, 126) LIKE '%"+lajk+"%'";
			    Statement stmt2 = DBConnection.getConnection().createStatement();
				ResultSet rset2 = stmt2.executeQuery(qwer+racun+y);
				if(rset2.next()) {
					float sst = rset2.getFloat(4);
					float kor = rset2.getFloat(5);
					float ter = rset2.getFloat(6);
					float izn = Float.parseFloat(iznos);
					float dec = ter + izn;
					float nst = sst + kor - dec;
					String decs = Float.toString(dec);
					String nsts = Float.toString(nst);
					String q = "' AND CONVERT(VARCHAR(25), dsr_datum, 126) LIKE '%"+lajk+"%'";
					PreparedStatement stmt3 = DBConnection.getConnection().prepareStatement("UPDATE dnevno_stanje_racuna SET"
							+ " dsr_nateret=?, dsr_novostanje=? WHERE id_racuna='"+racun+q);
					stmt3.setString(1, decs);
					stmt3.setString(2, nsts);
					stmt3.executeUpdate();
					stmt3.close();
				} else {
					int rbr = 0;
					float sst = 0;
					Statement stmt5 = DBConnection.getConnection().createStatement();
					ResultSet rset4 = stmt5.executeQuery("SELECT max(dsr_izvod) FROM dnevno_stanje_racuna");
					if(rset4.next()) {
						rbr = rset4.getInt(1);
					}
					Statement stmt4 = DBConnection.getConnection().createStatement();
					ResultSet rset3 = stmt4.executeQuery("SELECT dsr_novostanje FROM dnevno_stanje_racuna WHERE dsr_izvod=(SELECT "
							+ "max(dsr_izvod) FROM dnevno_stanje_racuna WHERE id_racuna='"+racun+"')");
					if(rset3.next()) {
						sst = rset3.getFloat(1);
					}
					rbr++;
					String rbrs = Integer.toString(rbr);
					float izn = Float.parseFloat(iznos);
					float nst = sst - izn;
					String ssts = Float.toString(sst);
					String nsts = Float.toString(nst);
					PreparedStatement stmt6 = DBConnection.getConnection().prepareStatement("INSERT INTO dnevno_stanje_racuna "
							+ "(dsr_izvod, id_racuna, dsr_datum, dsr_prethodno,dsr_ukorist, dsr_nateret, dsr_novostanje) "
							+ "VALUES(?, '"+racun+"', '"+datum+"', ?, '0', ?, ?)");
					stmt6.setString(1, rbrs);
					stmt6.setString(2, ssts);
					stmt6.setString(3, iznos);
					stmt6.setString(4, nsts);
					stmt6.executeUpdate();
					stmt6.close();
					stmt4.close();
					stmt5.close();
					rset3.close();
					rset4.close();
				}
				stmt2.close();
				rset2.close();
			//}
			stmtb1.close();
			rsetb1.close();
		}
		Statement stmtb2 = DBConnection.getConnection().createStatement();
		ResultSet rsetb2 = stmtb2.executeQuery("SELECT * FROM racuni_pravnih_lica WHERE id_racuna='"+values.get(13)+"'");
		if (rsetb2.next()) {
			/*String delims2 = "[ ]";
			String[] tokens2 = values.get(8).split(delims2);
			String daa = tokens2[0].trim();
			Statement stmt7 = DBConnection.getConnection().createStatement();
			String date="SELECT asi_datpri, asi_racpov, asi_iznos FROM ANALITIKA_IZVODA WHERE asi_racduz='"+values.get(13)+"' AND CONVERT(VARCHAR(25), asi_datpri, 126) LIKE '%"+daa+"%'";
			ResultSet rset5 = stmt7.executeQuery(date);
			if(rset5.next()) {*/
			datum = values.get(8);
			racun = values.get(13);
			iznos = values.get(17);
				String delims = "[ ]";
				String[] tokens = datum.split(delims);
				String lajk = tokens[0].trim();
				System.out.print(tokens[0]);
			    String qwer="SELECT * FROM dnevno_stanje_racuna WHERE id_racuna='";
			    String y="' AND CONVERT(VARCHAR(25), dsr_datum, 126) LIKE '%"+lajk+"%'";
			    Statement stmt8 = DBConnection.getConnection().createStatement();
				ResultSet rset6 = stmt8.executeQuery(qwer+racun+y);
				if(rset6.next()) {
					float sst = rset6.getFloat(4);
					float kor = rset6.getFloat(5);
					float ter = rset6.getFloat(6);
					float izn = Float.parseFloat(iznos);
					float dec = kor + izn;
					float nst = sst + dec - ter;
					String decs = Float.toString(dec);
					String nsts = Float.toString(nst);
					String q = "' AND CONVERT(VARCHAR(25), dsr_datum, 126) LIKE '%"+lajk+"%'";
					PreparedStatement stmt9 = DBConnection.getConnection().prepareStatement("UPDATE dnevno_stanje_racuna SET"
							+ " dsr_ukorist=?, dsr_novostanje=? WHERE id_racuna='"+racun+q);
					stmt9.setString(1, decs);
					stmt9.setString(2, nsts);
					stmt9.executeUpdate();
					stmt9.close();
				} else {
					int rbr = 0;
					float sst = 0;
					Statement stmt10 = DBConnection.getConnection().createStatement();
					ResultSet rset7 = stmt10.executeQuery("SELECT max(dsr_izvod) FROM dnevno_stanje_racuna");
					if(rset7.next()) {
						rbr = rset7.getInt(1);
					}
					Statement stmt11 = DBConnection.getConnection().createStatement();
					ResultSet rset8 = stmt11.executeQuery("SELECT dsr_novostanje FROM dnevno_stanje_racuna WHERE dsr_izvod=(SELECT "
							+ "max(dsr_izvod) FROM dnevno_stanje_racuna WHERE id_racuna='"+racun+"')");
					if(rset8.next()) {
						sst = rset8.getFloat(1);
					}
					rbr++;
					String rbrs = Integer.toString(rbr);
					float izn = Float.parseFloat(iznos);
					float nst = sst + izn;
					String ssts = Float.toString(sst);
					String nsts = Float.toString(nst);
					PreparedStatement stmt12 = DBConnection.getConnection().prepareStatement("INSERT INTO dnevno_stanje_racuna "
							+ "(dsr_izvod, id_racuna, dsr_datum, dsr_prethodno,dsr_ukorist, dsr_nateret, dsr_novostanje) "
							+ "VALUES(?, '"+racun+"', '"+datum+"', ?, ?, '0', ?)");
					stmt12.setString(1, rbrs);
					stmt12.setString(2, ssts);
					stmt12.setString(3, iznos);
					stmt12.setString(4, nsts);
					stmt12.executeUpdate();
					stmt12.close();
					stmt10.close();
					stmt11.close();
					rset7.close();
					rset8.close();
				}
				stmt8.close();
				rset6.close();
				
			stmtb2.close();
			rsetb2.close();
		}
		DBConnection.getConnection().commit();
	}

}
