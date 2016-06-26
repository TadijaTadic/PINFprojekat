package gui.standard.form;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import actions.standard.form.ZoomFormAction;
import util.Column;
import util.ColumnList;
import util.Lookup;
import model.BankeTableModel;
import model.FizickoLiceTableModel;
import model.RacuniPravnihLicaTableModel;

public class FizickoLiceForm extends AbstractForm {
	
	private JTextField tfIdKlijenta = new JTextField(10);
	private JTextField tfJMBG = new JTextField(15);
	private JTextField tfIme = new JTextField(20);
	private JTextField tfPrezime = new JTextField(20);
	private JTextField tfAdresa = new JTextField(20);
	private JTextField tfEmail = new JTextField(20);
	private JTextField tfTelefon = new JTextField(20);
	private JButton btnZoom = new JButton("...");
	public Object[] collectionOfFields = {  tfIdKlijenta, tfJMBG, tfAdresa, tfTelefon, tfEmail, tfPrezime, tfIme};
	
	public FizickoLiceForm() {
		super();
		setTitle("Fizička lica");
		JLabel lblIdKlijenta = new JLabel("Id klijenta:");
		JLabel lblJMBG = new JLabel("JMBG:");
		JLabel lblIme = new JLabel("Ime:");
		JLabel lblPrezime = new JLabel("Prezime:");
		JLabel lblAdresa = new JLabel("Adresa:");
		JLabel lblEmail = new JLabel("Email:");
		JLabel lblTelefon = new JLabel("Telefon:");
		
		
		dataPanel.add(lblIdKlijenta);
		dataPanel.add(tfIdKlijenta);
		dataPanel.add(btnZoom ,"wrap");
		//btnZoom.setAction(new ZoomFormAction(this));
		dataPanel.add(lblJMBG);
		dataPanel.add(tfJMBG, "wrap");
		dataPanel.add(lblAdresa);
		dataPanel.add(tfAdresa, "wrap");
		//tfAdresa.setEditable(false);
		dataPanel.add(lblEmail);
		dataPanel.add(tfEmail, "wrap");
		//tfEmail.setEditable(false);
		dataPanel.add(lblTelefon);
		dataPanel.add(tfTelefon, "wrap");
		//tfTelefon.setEditable(false);		
		dataPanel.add(lblPrezime);
		dataPanel.add(tfPrezime, "wrap");			
		dataPanel.add(lblIme);
		dataPanel.add(tfIme);
		bottomPanel.add(dataPanel);
		
		/*tfIdKlijenta.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String sifraDrzave = tfIdKlijenta.getText().trim();
				try {
					ArrayList<String> list = Lookup.getKlijent(sifraDrzave);
					tfAdresa.setText(list.get(0));
					tfEmail.setText(list.get(1));
					tfTelefon.setText(list.get(2));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});*/
	}
	
	@Override
	protected void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		FizickoLiceTableModel tableModel = new FizickoLiceTableModel(new String[] {
				"Id Klijenta", "JMBG", "Adresa", "Telefon", "Email" , "Prezime", "Ime" }, 0);
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
					((JTextField) field).setText("");
			}			
			return;
		}
		FizickoLiceTableModel tableModel =  (FizickoLiceTableModel) tblGrid.getModel();
		int size = tableModel.getColumnCount();
		for (int i=0; i<size; i++) {
			((JTextField) collectionOfFields[i]).setText((String) tableModel.getValueAt(index, i));
		}
	}
	
	@Override
	public void addRow() {
		ArrayList<String> values = new ArrayList<String>();
		for (Object field : collectionOfFields) {
				values.add(((JTextField) field).getText().trim());		
		}
		try {
			FizickoLiceTableModel fltm = (FizickoLiceTableModel) tblGrid.getModel();
			int index = fltm.insertRow(values, tfIdKlijenta.getText().trim());
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void editRow() {
		ArrayList<String> values = new ArrayList<String>();
		for (Object field : collectionOfFields) 
			values.add(((JTextField) field).getText().trim());
		int index = tblGrid.getSelectedRow();
		String staraSifra = (String) tblGrid.getValueAt(index, 0); 
		try {
			FizickoLiceTableModel fltm = (FizickoLiceTableModel) tblGrid.getModel();
			fltm.updateRow(index, staraSifra, values);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void removeRow() {
		int index = tblGrid.getSelectedRow();
		FizickoLiceTableModel tableModel = (FizickoLiceTableModel) tblGrid.getModel();
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
			FizickoLiceTableModel fltm = (FizickoLiceTableModel) tblGrid.getModel();
			fltm.deleteRow(index);
			if (tableModel.getRowCount() > 0)
				tblGrid.setRowSelectionInterval(newIndex, newIndex);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void search() {
		ArrayList<String> values = new ArrayList<String>();
		for (Object field : collectionOfFields) {
			values.add(((JTextField) field).getText().trim());
		}
		FizickoLiceTableModel fltm = (FizickoLiceTableModel) tblGrid.getModel();
		try {
			fltm.searchRow(values);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public JTextField getTfJMBG() {
		return tfJMBG;
	}
	
	/*public void zoom() {
		KlijentForm kf = new KlijentForm();
		kf.setLocation(500,140);
		kf.setModal(true);
		kf.setVisible(true);
		tfIdKlijenta.setText((String)kf.getList().getValue("ID_KLIJENTA"));
		tfAdresa.setText((String)kf.getList().getValue("ADRESA"));
		tfEmail.setText((String)kf.getList().getValue("E_MAIL"));
		tfTelefon.setText((String)kf.getList().getValue("TELEFON"));
	}*/
	
	public void pickup() {
		 int index = tblGrid.getSelectedRow();
		 String idKlijenta = (String)tblGrid.getModel().getValueAt(index, 0);
		 list = new ColumnList();
		 list.add(new Column("ID_KLIJENTA",idKlijenta));
		 this.setVisible(false);
	}
	
	public void nextForm() {
		int index = tblGrid.getSelectedRow();
		String idKlijenta = (String)tblGrid.getModel().getValueAt(index, 0);
	    //String naziv = (String)tblGrid.getModel().getValueAt(index, 1);
		RacuniPravnihLicaForm form = new RacuniPravnihLicaForm();
		RacuniPravnihLicaTableModel rpltm = (RacuniPravnihLicaTableModel) form.getTblGrid().getModel();
		try {
			rpltm.openAsChildForm("SELECT * FROM racuni_pravnih_lica WHERE id_klijenta LIKE '%"+idKlijenta+"%'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.setVisible(true);
	}

}
