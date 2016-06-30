package model;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

		public void exportNaloga(JTable table) throws Exception {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.newDocument();
		    Element results = doc.createElement("Results");
		    doc.appendChild(results);
		    
		    ResultSet rs = DBConnection.getConnection().createStatement().executeQuery("SELECT * FROM MEDJUBANKARSKI_NALOG");
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
		    
		    StreamResult result = new StreamResult(new File("IzvodNaloga.xml"));
			transformer.transform(domSource, result);

			System.out.println("File saved!");		
		}

}
