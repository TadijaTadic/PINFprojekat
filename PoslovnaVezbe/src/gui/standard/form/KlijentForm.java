package gui.standard.form;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import util.Column;
import util.ColumnList;
import model.DrzaveTableModel;
import model.FizickoLiceTableModel;
import model.KlijentTableModel;

public class KlijentForm extends AbstractForm {
	
	private JTextField tfIdKlijenta = new JTextField(10);
	private JTextField tfAdresa = new JTextField(20);
	private JTextField tfTelefon = new JTextField(20);
	private JTextField tfEmail = new JTextField(20);
	
	public JTextField getTfIdKlijenta() {
		return tfIdKlijenta;
	}

	public void setTfIdKlijenta(JTextField tfIdKlijenta) {
		this.tfIdKlijenta = tfIdKlijenta;
	}

	public JTextField getTfAdresa() {
		return tfAdresa;
	}

	public void setTfAdresa(JTextField tfAdresa) {
		this.tfAdresa = tfAdresa;
	}

	public JTextField getTfTelefon() {
		return tfTelefon;
	}

	public void setTfTelefon(JTextField tfTelefon) {
		this.tfTelefon = tfTelefon;
	}

	public JTextField getTfEmail() {
		return tfEmail;
	}

	public void setTfEmail(JTextField tfEmail) {
		this.tfEmail = tfEmail;
	}

	public KlijentForm() {
		super();
		setTitle("Klijent");
		JLabel lblIdKlijenta = new JLabel ("ID:");
		JLabel lblIAdresa = new JLabel("Adresa:");
		JLabel lblTelefon = new JLabel ("Telefon:");
		JLabel lblEmail = new JLabel("E-mail:");

		dataPanel.add(lblIdKlijenta);
		dataPanel.add(tfIdKlijenta,"wrap");
		dataPanel.add(lblIAdresa);
		dataPanel.add(tfAdresa,"wrap");
		dataPanel.add(lblTelefon);
		dataPanel.add(tfTelefon,"wrap");
		dataPanel.add(lblEmail);
		dataPanel.add(tfEmail);
		bottomPanel.add(dataPanel);
	}
	
	@Override
	protected void initTable() {

		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		KlijentTableModel tableModel = new KlijentTableModel(new String[] {
				"ID", "Adresa", "Telefon", "E-mail" }, 0);
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
	
	public void sync() {
		int index = tblGrid.getSelectedRow();
		if (index < 0) {
			tfIdKlijenta.setText("");
			tfAdresa.setText("");
			tfTelefon.setText("");
			tfEmail.setText("");
			return;
		}
		KlijentTableModel tableModel =  (KlijentTableModel) tblGrid.getModel();
		String id = (String) tableModel.getValueAt(index, 0);
		String adresa = (String) tableModel.getValueAt(index, 1);
		String telefon = (String) tableModel.getValueAt(index, 2);
		String email = (String) tableModel.getValueAt(index, 3);
		tfIdKlijenta.setText(id);
		tfAdresa.setText(adresa);
		tfTelefon.setText(telefon);
		tfEmail.setText(email);
	}
	
	@Override
	public void removeRow() {
		if (JOptionPane.showConfirmDialog(this, "Da li ste sigurni?",
				"Pitanje", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		int index = tblGrid.getSelectedRow();
		KlijentTableModel tableModel = (KlijentTableModel) tblGrid.getModel();
		if (index == -1) // Ako nema selektovanog reda (tabela prazna)
			return; // izlazak
		// kada obrisemo tekuci red, selektovacemo sledeci (newindex):
		int newIndex = index;
		// sem ako se obrise poslednji red, tada selektujemo prethodni
		if (index == tableModel.getRowCount() - 1)
			newIndex--;
		try {
			KlijentTableModel ktm = (KlijentTableModel) tblGrid.getModel();
			ktm.deleteRow(index);
			if (tableModel.getRowCount() > 0)
				tblGrid.setRowSelectionInterval(newIndex, newIndex);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	@Override
	public void addRow() {
		String id = tfIdKlijenta.getText().trim();
		String adresa = tfAdresa.getText().trim();
		String telefon = tfTelefon.getText().trim();
		String email = tfEmail.getText().trim();
		try {
			KlijentTableModel dtm = (KlijentTableModel) tblGrid.getModel();
			int index = dtm.insertRow(id, adresa, telefon, email);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	@Override
	public void editRow() {
		String id = tfIdKlijenta.getText().trim();
		String adresa = tfAdresa.getText().trim();
		String telefon = tfTelefon.getText().trim();
		String email = tfEmail.getText().trim();
		int index = tblGrid.getSelectedRow();
		String oldId = (String) tblGrid.getValueAt(index, 0); 
		try {
			KlijentTableModel dtm = (KlijentTableModel) tblGrid.getModel();
			dtm.updateRow(index, id, adresa, telefon, email, oldId);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	@Override
	public void search() {
		/*String id = tfIdKlijenta.getText().trim();
		String adresa = tfAdresa.getText().trim();
		String telefon = tfTelefon.getText().trim();
		String email = tfEmail.getText().trim();*/
		Object[] collectionOfFields = { tfIdKlijenta, tfAdresa, tfTelefon, tfEmail};
		ArrayList<String> values = new ArrayList<String>();
		for (Object field : collectionOfFields) {
				values.add(((JTextField) field).getText().trim());
		}
		KlijentTableModel ktm = (KlijentTableModel) tblGrid.getModel();
		try {
			ktm.searchRow(values);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void pickup() {
		 int index = tblGrid.getSelectedRow();
		 String idKlijenta = (String)tblGrid.getModel().getValueAt(index, 0);
		 String adresa = (String)tblGrid.getModel().getValueAt(index, 1);
		 String telefon = (String)tblGrid.getModel().getValueAt(index, 2);
	     String email = (String)tblGrid.getModel().getValueAt(index, 3);;
		 list = new ColumnList();
		 list.add(new Column("ID_KLIJENTA",idKlijenta));
		 list.add(new Column("ADRESA",adresa));
		 list.add(new Column("TELEFON",telefon));
		 list.add(new Column("E_MAIL",email));
		 this.setVisible(false);
	}
	
	public void nextForm() {
		int index = tblGrid.getSelectedRow();
		String idKlijenta = (String)tblGrid.getModel().getValueAt(index, 0);
	    //String naziv = (String)tblGrid.getModel().getValueAt(index, 1);
		FizickoLiceForm form = new FizickoLiceForm();
		FizickoLiceTableModel fltm = (FizickoLiceTableModel) form.getTblGrid().getModel();
		try {
			fltm.openAsChildForm("SELECT jmbg, ime, prezime, fizicko_lice.id_klijenta, fizicko_lice.adresa, fizicko_lice.e_mail, fizicko_lice.telefon FROM fizicko_lice "
					+ "JOIN klijent on fizicko_lice.id_klijenta=klijent.id_klijenta WHERE fizicko_lice.id_klijenta LIKE '%"
					+idKlijenta+ "%'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.setVisible(true);
	}
}
