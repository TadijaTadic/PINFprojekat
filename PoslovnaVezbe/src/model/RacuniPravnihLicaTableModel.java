package model;

import java.io.File;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.SortUtils;
import database.DBConnection;

public class RacuniPravnihLicaTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT * FROM racuni_pravnih_lica";
	private String orderBy = " ORDER BY id_racuna";
	private String whereStmt = "";
	public RacuniPravnihLicaTableModel(Object[] colNames, int rowCount) {
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
			String idrac = rset.getString("ID_RACUNA");
			String idban = rset.getString("ID_BANKE");
			String valuta = rset.getString("ID_VALUTE");
			String idklijenta = rset.getString("ID_KLIJENTA");
			String racun= rset.getString("BAR_RACUN");
			String datum = rset.getString("BAR_DATOTV");
			boolean vazi = rset.getBoolean("BAR_VAZI");
			
			
			addRow(new Object[] { idrac, idban, valuta, idklijenta, racun, datum, vazi });
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
				"DELETE FROM racuni_pravnih_lica WHERE id_racuna=?");
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
				"INSERT INTO racuni_pravnih_lica (" + preparedString1 +  ") VALUES (" + preparedString2 + ")");
		for (int i = 1; i <= values.size(); i++) {
			stmt2.setString(i, values.get(i-1));
		}
		int rowsAffected = stmt2.executeUpdate();
		stmt2.close();
		String brizv = null;
		Statement stmt4 = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt4.executeQuery("select dsr_izvod from DNEVNO_STANJE_RACUNA where dsr_izvod=(select max(dsr_izvod) from DNEVNO_STANJE_RACUNA)");
		if(rset.next()) {
			brizv = rset.getString(1);
		}
		int a = Integer.parseInt(brizv);
		a++;
		String rbr = Integer.toString(a);
		System.out.print(rbr);
		PreparedStatement stmt3 = DBConnection.getConnection().prepareStatement(
				"INSERT INTO dnevno_stanje_racuna (dsr_izvod, id_racuna, dsr_datum, dsr_prethodno, "
				+ "dsr_ukorist, dsr_nateret, dsr_novostanje) VALUES (?, ?, ?, '0', '0', '0', '0')");			
		stmt3.setString(1, rbr);
		stmt3.setString(2, id);
		stmt3.setString(3, values.get(5));
		int bl = stmt3.executeUpdate();
		System.out.print(bl);
		stmt3.close();
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i unos u TableModel
			retVal = sortedInsert(values, id);
			fireTableDataChanged();
		}
		
		return retVal;
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
				"UPDATE racuni_pravnih_lica SET " + preparedString + " WHERE ID_RACUNA=?");
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
		String sqlQuery = "SELECT * FROM racuni_pravnih_lica WHERE "+preparedString;
		fillData(sqlQuery);				
	}
	
	public void openAsChildForm(String sql) throws SQLException{
		fillData(sql);
	}

	public void exportIzvoda(JTable table) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.newDocument();
	    Element results = doc.createElement("Results");
	    doc.appendChild(results);
	    
	    int index = table.getSelectedRow();
	    String id_racuna = (String) table.getValueAt(index, 0);
	    ResultSet rs = DBConnection.getConnection().createStatement().executeQuery("SELECT * FROM DNEVNO_STANJE_RACUNA WHERE ID_RACUNA="+id_racuna);
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int colCount = rsmd.getColumnCount();

	    while (rs.next()) {
	      Element row = doc.createElement("Row");
	      results.appendChild(row);
	      for (int i = 1; i <= colCount; i++) {
	        String columnName = rsmd.getColumnName(i);
	        Object value = rs.getObject(i);
	        Element node = doc.createElement(columnName);
	        node.appendChild(doc.createTextNode(value.toString()));
	        row.appendChild(node);
	      }
	    }
	    
	    DOMSource domSource = new DOMSource(doc);
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	    
	    StreamResult result = new StreamResult(new File("IzvodKlijenta.xml"));
		transformer.transform(domSource, result);

		System.out.println("File saved!");		
	}
}
