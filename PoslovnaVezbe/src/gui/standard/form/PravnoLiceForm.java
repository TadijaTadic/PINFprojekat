package gui.standard.form;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.PravnoLiceTableModel;

public class PravnoLiceForm extends AbstractForm {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField idKlijenta = new JTextField(10);
	private JTextField pib = new JTextField(15);
	private JTextField adresa = new JTextField(20);
	private JTextField telefon = new JTextField(20);
	private JTextField email = new JTextField(20);
	private JTextField naziv = new JTextField(20);
	private JTextField webadresa = new JTextField(20);
	private JTextField fax = new JTextField(20);
	private JTextField ime = new JTextField(20);
	private JTextField prezime = new JTextField(20);
	private JTextField jmbg = new JTextField(20);
		
	public Object[] collectionOfFields = {  idKlijenta, pib, adresa, telefon, 
			email, naziv, webadresa, fax, ime, prezime, jmbg};
	
	
	
	public JTextField getIdKlijenta() {
		return idKlijenta;
	}

	public void setIdKlijenta(JTextField idKlijenta) {
		this.idKlijenta = idKlijenta;
	}

	public JTextField getPib() {
		return pib;
	}

	public void setPib(JTextField pib) {
		this.pib = pib;
	}

	public JTextField getAdresa() {
		return adresa;
	}

	public void setAdresa(JTextField adresa) {
		this.adresa = adresa;
	}

	public JTextField getTelefon() {
		return telefon;
	}

	public void setTelefon(JTextField telefon) {
		this.telefon = telefon;
	}

	public JTextField getEmail() {
		return email;
	}

	public void setEmail(JTextField email) {
		this.email = email;
	}

	public JTextField getNaziv() {
		return naziv;
	}

	public void setNaziv(JTextField naziv) {
		this.naziv = naziv;
	}

	public JTextField getWebadresa() {
		return webadresa;
	}

	public void setWebadresa(JTextField webadresa) {
		this.webadresa = webadresa;
	}

	public JTextField getFax() {
		return fax;
	}

	public void setFax(JTextField fax) {
		this.fax = fax;
	}

	public JTextField getIme() {
		return ime;
	}

	public void setIme(JTextField ime) {
		this.ime = ime;
	}

	public JTextField getPrezime() {
		return prezime;
	}

	public void setPrezime(JTextField prezime) {
		this.prezime = prezime;
	}

	public JTextField getJmbg() {
		return jmbg;
	}

	public void setJmbg(JTextField jmbg) {
		this.jmbg = jmbg;
	}

	public PravnoLiceForm() {
		super();
		setTitle("Pravna lica");
		JLabel lblIdKlijenta = new JLabel("Id Klijenta:");
		JLabel lblPib = new JLabel("PIB:");
		JLabel lblAdresa = new JLabel("Adresa:");
		JLabel lblTelefon = new JLabel("Telefon:");
		JLabel lblEmail = new JLabel("Email:");
		JLabel lblNaziv = new JLabel("Naziv:");
		JLabel lblWebadresa = new JLabel("Web Adresa:");
		JLabel lblFax = new JLabel("Fax:");
		JLabel lblIme = new JLabel("Ime:");
		JLabel lblPrezime = new JLabel("Prezime:");
		JLabel lblJMBG = new JLabel("JMBG Klijenta:");
		
		dataPanel.add(lblIdKlijenta);
		dataPanel.add(idKlijenta, "wrap");
		dataPanel.add(lblPib);
		dataPanel.add(pib, "wrap");
		dataPanel.add(lblAdresa);
		dataPanel.add(adresa, "wrap");		
		dataPanel.add(lblTelefon);
		dataPanel.add(telefon, "wrap");
		dataPanel.add(lblEmail);
		dataPanel.add(email, "wrap");
		dataPanel.add(lblNaziv);
		dataPanel.add(naziv, "wrap");
		dataPanel.add(lblWebadresa);
		dataPanel.add(webadresa, "wrap");
		dataPanel.add(lblFax);
		dataPanel.add(fax, "wrap");
		dataPanel.add(lblIme);
		dataPanel.add(ime, "wrap");
		dataPanel.add(lblPrezime);
		dataPanel.add(prezime, "wrap");
		dataPanel.add(lblJMBG);
		dataPanel.add(jmbg, "wrap");
		bottomPanel.add(dataPanel);
		
	}
	
	@Override
	protected void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		PravnoLiceTableModel tableModel = new PravnoLiceTableModel(new String[] {
				 "ID Klijenta", "PIB", "Adresa", "Telefon", "Email", "Naziv", "Web Adresa", "FAX", "Ime", "Prezime", "JMBG" }, 0);
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
		PravnoLiceTableModel tableModel =  (PravnoLiceTableModel) tblGrid.getModel();
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
			PravnoLiceTableModel fltm = (PravnoLiceTableModel) tblGrid.getModel();
			int index = fltm.insertRow(values, idKlijenta.getText().trim());
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
			PravnoLiceTableModel fltm = (PravnoLiceTableModel) tblGrid.getModel();
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
		PravnoLiceTableModel tableModel = (PravnoLiceTableModel) tblGrid.getModel();
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
			PravnoLiceTableModel fltm = (PravnoLiceTableModel) tblGrid.getModel();
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
		PravnoLiceTableModel fltm = (PravnoLiceTableModel) tblGrid.getModel();
		try {
			fltm.searchRow(values);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}

	}
	

}
