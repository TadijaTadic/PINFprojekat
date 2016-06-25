package gui.standard.form;

import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.UkidanjeTableModel;

public class UkidanjeRacunaForm extends AbstractForm {
	private JTextField tfIdUkidanja = new JTextField(3);
	private JTextField tfIdRacuna = new JTextField(20);
	private JTextField tfDatumUkidanja = new JTextField(20);
	private JTextField tfNaRacun = new JTextField(20);
	
	
	public UkidanjeRacunaForm() {
		super();
		setTitle("Ukidanje racuna");
		JLabel lblIdUkidanja = new JLabel ("ID ukidanja:");
		JLabel lblIdRacuna = new JLabel("ID racuna:");
		JLabel lblDatumUkidanja = new JLabel("Datum ukidanja:");
		JLabel lblNaRacun = new JLabel("Prenos na racun");
		
		dataPanel.add(lblIdUkidanja);
		dataPanel.add(tfIdUkidanja,"wrap");
		dataPanel.add(lblIdRacuna);
		dataPanel.add(tfIdRacuna, "wrap");
		dataPanel.add(lblDatumUkidanja);
		dataPanel.add(tfDatumUkidanja, "wrap");
		dataPanel.add(lblNaRacun);
		dataPanel.add(tfNaRacun);
		bottomPanel.add(dataPanel);
		
		toolBar.setVisible(false);
	}
	@Override
	protected void initTable() {

		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		UkidanjeTableModel tableModel = new UkidanjeTableModel(new String[] {
				"ID ukidanja", "ID racuna","Datum ukidanja","Prenos na racun" }, 0);
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
			tfIdUkidanja.setText("");
			tfIdRacuna.setText("");
			tfDatumUkidanja.setText("");
			tfNaRacun.setText("");
			return;
		}
		UkidanjeTableModel tableModel =  (UkidanjeTableModel) tblGrid.getModel();
		String idukid = (String) tableModel.getValueAt(index, 0);
		String idrac = (String) tableModel.getValueAt(index, 1);
		String datum = (String) tableModel.getValueAt(index, 2);
		String naracun = (String) tableModel.getValueAt(index, 3);
		tfIdUkidanja.setText(idukid);
		tfIdRacuna.setText(idrac);
		tfDatumUkidanja.setText(datum);
		tfNaRacun.setText(naracun);
	}
	
	@Override
	public void addRow() {
		String idukid= tfIdUkidanja.getText().trim();
		String idrac = tfIdRacuna.getText().trim();
		String datum= tfDatumUkidanja.getText().trim();
		String naracun = tfNaRacun.getText().trim();
		try {
			UkidanjeTableModel utm = (UkidanjeTableModel) tblGrid.getModel();
			int index = utm.insertRow(idukid, idrac, datum, naracun);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void editRow() {
		String idukid= tfIdUkidanja.getText().trim();
		String idrac = tfIdRacuna.getText().trim();
		String datum= tfDatumUkidanja.getText().trim();
		String naracun = tfNaRacun.getText().trim();
		int index = tblGrid.getSelectedRow();
		String staraSifra = (String) tblGrid.getValueAt(index, 0); 
		try {
			UkidanjeTableModel utm = (UkidanjeTableModel) tblGrid.getModel();
			utm.updateRow(index, idukid, idrac, datum, naracun, staraSifra);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void search() {
		String idukid= tfIdUkidanja.getText().trim();
		String idrac = tfIdRacuna.getText().trim();
		String datum= tfDatumUkidanja.getText().trim();
		String naracun = tfNaRacun.getText().trim();
		UkidanjeTableModel utm = (UkidanjeTableModel) tblGrid.getModel();
		try {
			utm.searchRow(idukid, idrac, datum, naracun);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void removeRow() {
		int index = tblGrid.getSelectedRow();
		UkidanjeTableModel tableModel = (UkidanjeTableModel) tblGrid.getModel();
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
			UkidanjeTableModel utm = (UkidanjeTableModel) tblGrid.getModel();
			utm.deleteRow(index);
			if (tableModel.getRowCount() > 0)
				tblGrid.setRowSelectionInterval(newIndex, newIndex);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
}
