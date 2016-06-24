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
import util.Lookup;
import model.BankeTableModel;
import model.FizickoLiceTableModel;
import model.KursuValutiTableModel;

public class KursuValutiForm extends AbstractForm {
	
	private JTextField tfRedniBroj = new JTextField(10);
	private JTextField tfIDListe = new JTextField(5);
	private JTextField tfOsnovna = new JTextField(5);
	private JTextField tfPrema = new JTextField(5);
	private JTextField tfKupovni = new JTextField(10);
	private JTextField tfSrednji = new JTextField(10);
	private JTextField tfProdajni = new JTextField(10);
	private JButton btnZoom = new JButton("...");
	private JButton btnZoom1 = new JButton("...");
	private JButton btnZoom2 = new JButton("...");
	public Object[] collectionOfFields = {  tfRedniBroj, tfIDListe, tfOsnovna, tfPrema, 
			tfKupovni, tfSrednji, tfProdajni};
	
	public KursuValutiForm() {
		super();
		setTitle("Kursna lista");
		JLabel lblRedniBroj = new JLabel("Redni broj:");
		JLabel lblIDListe = new JLabel("ID kursne liste:");
		JLabel lblOsnovna = new JLabel("Osnovna valuta:");
		JLabel lblPrema = new JLabel("Prema valuti:");
		JLabel lblKupovni = new JLabel("Kupovni kurs:");
		JLabel lblSrednji = new JLabel("Srednji kurs:");
		JLabel lblProdajni = new JLabel("Prodajni kurs:");
		
		
		dataPanel.add(lblRedniBroj);
		dataPanel.add(tfRedniBroj, "wrap");
		dataPanel.add(lblIDListe);
		dataPanel.add(tfIDListe);
		dataPanel.add(btnZoom ,"wrap");
		btnZoom.setAction(new ZoomFormAction(this));
		dataPanel.add(lblOsnovna);
		dataPanel.add(tfOsnovna);
		tfOsnovna.setEditable(false);
		dataPanel.add(btnZoom1 ,"wrap");
		btnZoom1.setAction(new ZoomFormAction(this));
		dataPanel.add(lblPrema);
		dataPanel.add(tfPrema);
		tfPrema.setEditable(false);
		dataPanel.add(btnZoom2 ,"wrap");
		btnZoom2.setAction(new ZoomFormAction(this));
		dataPanel.add(lblKupovni);
		dataPanel.add(tfKupovni, "wrap");
		dataPanel.add(lblSrednji);
		dataPanel.add(tfSrednji, "wrap");
		dataPanel.add(lblProdajni);
		dataPanel.add(tfProdajni);
		bottomPanel.add(dataPanel);
	}
	
	@Override
	protected void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		KursuValutiTableModel tableModel = new KursuValutiTableModel(new String[] {
				 "Redni broj", "ID kursne liste", "Osnovna","Prema",
				 "Kupovni", "Srednji", "Prodajni" }, 0);
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
			for (Object field: collectionOfFields) {
					((JTextField) field).setText("");
			}			
			return;
		}
		KursuValutiTableModel tableModel =  (KursuValutiTableModel) tblGrid.getModel();
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
			KursuValutiTableModel kvtm = (KursuValutiTableModel) tblGrid.getModel();
			int index = kvtm.insertRow(values, tfRedniBroj.getText().trim());
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public JTextField gettfRedniBroj() {
		return tfRedniBroj;
	}
	
	@Override
	public void editRow() {
		ArrayList<String> values = new ArrayList<String>();
		for (Object field : collectionOfFields) 
			values.add(((JTextField) field).getText().trim());
		int index = tblGrid.getSelectedRow();
		String staraSifra = (String) tblGrid.getValueAt(index, 0); 
		try {
			KursuValutiTableModel kvtm = (KursuValutiTableModel) tblGrid.getModel();
			kvtm.updateRow(index, staraSifra, values);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void removeRow() {
		int index = tblGrid.getSelectedRow();
		KursuValutiTableModel tableModel = (KursuValutiTableModel) tblGrid.getModel();
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
			KursuValutiTableModel kvtm = (KursuValutiTableModel) tblGrid.getModel();
			kvtm.deleteRow(index);
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
		KursuValutiTableModel kvtm = (KursuValutiTableModel) tblGrid.getModel();
		try {
			kvtm.searchRow(values);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}

	}
		
}