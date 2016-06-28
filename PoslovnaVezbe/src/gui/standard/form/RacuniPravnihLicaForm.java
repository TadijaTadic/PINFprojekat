package gui.standard.form;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import database.DBConnection;
import util.Column;
import util.ColumnList;
import actions.standard.form.DnevnoStanjeAction;
import actions.standard.form.ZoomFormAction;
import model.DnevnoStanjeTableModel;
import model.RacuniPravnihLicaTableModel;

public class RacuniPravnihLicaForm extends AbstractForm {
	private JTextField tfIdRacuna = new JTextField(5);
	private JTextField tfIdBanke = new JTextField(5);
	private JTextField tfIdValute = new JTextField(20);
	private JTextField tfIdKlijenta = new JTextField(20);
	private JTextField tfBrRacun = new JTextField(20);
	private JTextField tfDatum = new JTextField(20);
	private JTextField tfOd = new JTextField(20);
	private JTextField tfDo = new JTextField(20);
	private JCheckBox cbVazi = new JCheckBox();
	private JButton btnZoom = new JButton("...");
	private JButton btnDnStanje = new JButton("Dnevno stanje");
	public Object[] collectionOfFields = { tfIdRacuna, tfIdBanke, tfIdValute,tfIdKlijenta,
			tfBrRacun, tfDatum, cbVazi };
	
	public RacuniPravnihLicaForm() {
		super();
		setTitle("Racuni");
		JLabel lblIdRacuna = new JLabel("Id racuna:");
		JLabel lblIdBanke = new JLabel("Id banke:");
		JLabel lblIdValute = new JLabel("Id valute:");
		JLabel lblIdKlijenta = new JLabel("Id klijenta:");
		JLabel lblBrRacun = new JLabel("Broj racuna:");
		JLabel lblDatum = new JLabel("Datum otvaranja racuna:");
		JLabel lblVazi = new JLabel("Da li vazi:");
		JLabel lblOd = new JLabel("Od:");
		JLabel lblDo = new JLabel("Do");
		
		dataPanel.add(lblIdRacuna);
		dataPanel.add(tfIdRacuna, "wrap");
		dataPanel.add(lblIdBanke);
		dataPanel.add(tfIdBanke, "wrap");
		dataPanel.add(lblIdValute);
		dataPanel.add(tfIdValute, "wrap");
		dataPanel.add(lblIdKlijenta);
		dataPanel.add(tfIdKlijenta);
		dataPanel.add(btnZoom, "wrap");
		btnZoom.setAction(new ZoomFormAction(this));
		dataPanel.add(lblBrRacun);
		dataPanel.add(tfBrRacun, "wrap");
		dataPanel.add(lblDatum);
		dataPanel.add(tfDatum, "wrap");
		dataPanel.add(lblVazi);
		dataPanel.add(cbVazi, "wrap");
		dataPanel.add(btnDnStanje, "wrap");
		btnDnStanje.setAction(new DnevnoStanjeAction(this));
		bottomPanel.add(dataPanel);
		
}
	
	@Override
	protected void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		RacuniPravnihLicaTableModel tableModel = new RacuniPravnihLicaTableModel(new String[] {
				"Id racuna", "Id banke", "Id valute", "ID klijenta", "Broj racuna", "Datum otvaranja racuna", 
				"Da li vazi:" }, 0);
		tblGrid.setModel(tableModel);

		try {
			tableModel.open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Dozvoljeno selektovanje redova
		tblGrid.setRowSelectionAllowed(true);
		// Ali ne i selektovanje kolona
		tblGrid.setColumnSelectionAllowed(false);

		// Dozvoljeno selektovanje samo jednog reda u jedinici vremena
		tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tblGrid.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting())
							return;
						sync();
					}
				});
		
	}
	
	@Override
	public void sync() {
		int index = tblGrid.getSelectedRow();
		if (index < 0) {
			for (Object field: collectionOfFields) {
				if (field instanceof JTextField)
					((JTextField) field).setText("");
				else if (field instanceof JCheckBox)
					((JCheckBox) field).setSelected(false);
			}
			
			return;
		}
		RacuniPravnihLicaTableModel tableModel =  (RacuniPravnihLicaTableModel) tblGrid.getModel();
		int size = tableModel.getColumnCount();
		for (int i=0; i<size; i++) {
			if (collectionOfFields[i] instanceof JTextField)
				((JTextField) collectionOfFields[i]).setText((String) tableModel.getValueAt(index, i));
			else if (collectionOfFields[i] instanceof JCheckBox) {
				if (tableModel.getValueAt(index,  i) instanceof Boolean) {
					boolean bool = (Boolean) tableModel.getValueAt(index, i);
					if (bool)
						((JCheckBox) collectionOfFields[i]).setSelected(true);
					else
						((JCheckBox) collectionOfFields[i]).setSelected(false);
				}
				else if (tableModel.getValueAt(index,  i) instanceof String) {
					String string = (String) tableModel.getValueAt(index, i);
					if (string.equalsIgnoreCase("true"))
						((JCheckBox) collectionOfFields[i]).setSelected(true);
					else
						((JCheckBox) collectionOfFields[i]).setSelected(false);
				}
				
			}
		}
	}
	
	
	@Override
	public void addRow() {
		ArrayList<String> values = new ArrayList<String>();
		for (Object field : collectionOfFields) {
			if (field instanceof JTextField)
				values.add(((JTextField) field).getText().trim());
			else if (field instanceof JCheckBox) 
				if (((JCheckBox) field).isSelected())
					values.add("true");
				else 
					values.add("false");
			
		}
		try {
			RacuniPravnihLicaTableModel rpltm = (RacuniPravnihLicaTableModel) tblGrid.getModel();
			int index = rpltm.insertRow(values, tfIdRacuna.getText().trim());
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	@Override
	public void editRow() {
		ArrayList<String> values = new ArrayList<String>();
		for (Object field : collectionOfFields) {
			if (field instanceof JTextField)
				values.add(((JTextField) field).getText().trim());
			else if (field instanceof JCheckBox) 
				if (((JCheckBox) field).isSelected())
					values.add("true");
				else 
					values.add("false");
			
		}
		int index = tblGrid.getSelectedRow();
		String staraSifra = (String) tblGrid.getValueAt(index, 0); 
		try {
			RacuniPravnihLicaTableModel rpltm = (RacuniPravnihLicaTableModel) tblGrid.getModel();
			rpltm.updateRow(index, staraSifra, values);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	@Override
	public void removeRow() {
		int index = tblGrid.getSelectedRow();
		RacuniPravnihLicaTableModel tableModel = (RacuniPravnihLicaTableModel) tblGrid.getModel();
		if (index == -1) // Ako nema selektovanog reda (tabela prazna)
			return; // izlazak
		if (JOptionPane.showConfirmDialog(this, "Da li ste sigurni?",
				"Pitanje", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		// kada obrisemo tekuci red, selektovacemo sledeci (newindex):
		int newIndex = index;
		// sem ako se obrise poslednji red, tada selektujemo prethodni
		if (index == tableModel.getRowCount() - 1)
			newIndex--;
		try {
			RacuniPravnihLicaTableModel rpltm = (RacuniPravnihLicaTableModel) tblGrid.getModel();
			rpltm.deleteRow(index);
			if (tableModel.getRowCount() > 0)
				tblGrid.setRowSelectionInterval(newIndex, newIndex);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void search() {
		ArrayList<String> values = new ArrayList<String>();
		for (Object field : collectionOfFields) {
			if (field instanceof JTextField)
				values.add(((JTextField) field).getText().trim());
			else if (field instanceof JCheckBox)
				if (((JCheckBox) field).isSelected())
					values.add("1");
				else
					values.add("0");
		}

		RacuniPravnihLicaTableModel rpltm = (RacuniPravnihLicaTableModel) tblGrid.getModel();
		try {
			rpltm.searchRow(values);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public JTextField getIdRacuna() {
		// TODO Auto-generated method stub
		return tfIdRacuna;
	}
	
	public void zoomKlijenta() throws SQLException {
		JPanel al = new JPanel(); 	
		JRadioButton box = new JRadioButton("Fizičko lice");
		JRadioButton box1 = new JRadioButton("Pravno lice");
		ButtonGroup group = new ButtonGroup();
		group.add(box);
		group.add(box1);
		al.add(box);
		al.add(box1);
		String res =" ";
		if (JOptionPane.showConfirmDialog(this, al, "Preuzmi klijenta", JOptionPane.YES_NO_OPTION) 
				== JOptionPane.YES_OPTION && box.isSelected()) {
			res = "fl";
		} else if (box1.isSelected()) {
			res = "pl";
		}
		if(res.equals("fl")) {
			FizickoLiceForm flf = new FizickoLiceForm();
			flf.setLocation(500,140);
			flf.setModal(true);
			flf.setVisible(true);
			tfIdKlijenta.setText((String)flf.getList().getValue("ID_KLIJENTA"));
		}else if(res.equals("pl")) {
			PravnoLiceForm plf = new PravnoLiceForm();
			plf.setLocation(500,140);
			plf.setModal(true);
			plf.setVisible(true);
			tfIdKlijenta.setText((String)plf.getList().getValue("ID_KLIJENTA"));
		}
	}
	
	public void dnevnoStanje() {
		/*int index = tblGrid.getSelectedRow();
		if (index != 0) {
			String idRacuna = (String) tblGrid.getValueAt(index, 0); 
			list = new ColumnList();
			list.add(new Column("ID_RACUNA",idRacuna));
		}
		DnevnoStanjeForm dsf = new DnevnoStanjeForm();
		dsf.setLocation(500,140);
		dsf.setModal(true);
		dsf.setVisible(true);*/
		int index = tblGrid.getSelectedRow();
		String idRacuna = (String)tblGrid.getModel().getValueAt(index, 0);
		//DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MI:SS");
		//get current date time with Date()
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		String datum = sqlDate.toString();
		System.out.print(datum);
	    //String naziv = (String)tblGrid.getModel().getValueAt(index, 1);
		DnevnoStanjeForm form = new DnevnoStanjeForm();
		DnevnoStanjeTableModel rpltm = (DnevnoStanjeTableModel) form.getTblGrid().getModel();
		try {
			rpltm.openAsChildForm("SELECT asi_duznik, asi_racduz, asi_poverilac, asi_racpov, "
					+ "asi_iznos FROM analitika_izvoda WHERE CONVERT(VARCHAR(25), asi_datpri, 126) LIKE '%"+datum+"%' AND (asi_racduz='"+idRacuna+"' "
					+ "OR asi_racpov='"+idRacuna+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.setVisible(true);
	}
		
		
		/*public void stanjeZaPeriod() {
			  System.out.println(getClass().getResource("/reports/StanjeZaPeriod.jasper"));
		      String idracuna = tfIdRacuna.getText();
		      Map<String,Object> params = new HashMap<String,Object>(1);
		      params.put("banka",banka);
			  JasperPrint jp = JasperFillManager.fillReport(
			  getClass().getResource("/reports/StanjeZaPeriod.jasper").openStream(),
			  params, DBConnection.getConnection());
			  JasperViewer.viewReport(jp, false);
			} catch (Exception ex) {
			  ex.printStackTrace();
	}*/

}
