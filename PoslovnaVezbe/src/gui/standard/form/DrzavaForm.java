package gui.standard.form;

import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.DrzaveTableModel;

public class DrzavaForm extends AbstractForm {

	private JTextField tfSifra = new JTextField(5);
	private JTextField tfNaziv = new JTextField(20);
	
	public DrzavaForm() {
		super();
		setTitle("Države");
		JLabel lblSifra = new JLabel ("Šifra države:");
		JLabel lblNaziv = new JLabel("Naziv države:");

		dataPanel.add(lblSifra);
		dataPanel.add(tfSifra,"wrap");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv);
		bottomPanel.add(dataPanel);
	}

	@Override
	protected void initTable() {

		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		DrzaveTableModel tableModel = new DrzaveTableModel(new String[] {
				"Šifra", "Naziv" }, 0);
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
			tfSifra.setText("");
			tfNaziv.setText("");
			return;
		}
		DrzaveTableModel tableModel =  (DrzaveTableModel) tblGrid.getModel();
		String sifra = (String) tableModel.getValueAt(index, 0);
		String naziv = (String) tableModel.getValueAt(index, 1);
		tfSifra.setText(sifra);
		tfNaziv.setText(naziv);
	}

	@Override
	public void addRow() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		try {
			DrzaveTableModel dtm = (DrzaveTableModel) tblGrid.getModel();
			int index = dtm.insertRow(sifra, naziv);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void editRow() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		int index = tblGrid.getSelectedRow();
		String staraSifra = (String) tblGrid.getValueAt(index, 0); 
		try {
			DrzaveTableModel dtm = (DrzaveTableModel) tblGrid.getModel();
			dtm.updateRow(index, sifra, naziv, staraSifra);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void search() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		DrzaveTableModel dtm = (DrzaveTableModel) tblGrid.getModel();
		try {
			dtm.searchRow(sifra, naziv);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void removeRow() {
		int index = tblGrid.getSelectedRow();
		DrzaveTableModel tableModel = (DrzaveTableModel) tblGrid.getModel();
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
			DrzaveTableModel dtm = (DrzaveTableModel) tblGrid.getModel();
			dtm.deleteRow(index);
			if (tableModel.getRowCount() > 0)
				tblGrid.setRowSelectionInterval(newIndex, newIndex);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public JTextField getTfSifra() {
		return tfSifra;
	}

	public void setTfSifra(JTextField tfSifra) {
		this.tfSifra = tfSifra;
	}

	public JTextField getTfNaziv() {
		return tfNaziv;
	}

	public void setTfNaziv(JTextField tfNaziv) {
		this.tfNaziv = tfNaziv;
	}
		
}
