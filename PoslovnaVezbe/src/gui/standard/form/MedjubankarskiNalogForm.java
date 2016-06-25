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

import model.MedjubankarskiNalogTableModel;


public class MedjubankarskiNalogForm extends AbstractForm {

	private static final long serialVersionUID = 1L;
	
	private JTextField idNaloga = new JTextField(10);
	private JTextField sifraBanke = new JTextField(5);
	private JTextField banSifraBanke = new JTextField(5);
	private JTextField ukupanIznos = new JTextField(15);
	private JTextField datum = new JTextField(20);
	private JCheckBox rtgs = new JCheckBox();
	private JTextField status = new JTextField(2);
	public Object[] collectionOfFields = { idNaloga, sifraBanke, banSifraBanke, ukupanIznos,
			datum, rtgs, status };
	
	public MedjubankarskiNalogForm() {
		super();
		setTitle("Medjubankarski Nalozi");
		JLabel lblIdNaloga = new JLabel("ID Naloga:");
		JLabel lblSifraBanke = new JLabel("Sifra banke:");
		JLabel lblBanSifraBanke = new JLabel("Ban Sifra Banke:");
		JLabel lblUkupanIznos = new JLabel("Ukupan Iznos:");
		JLabel lblDatum = new JLabel("Datum:");
		JLabel lblRtgs = new JLabel("RTGS:");
		JLabel lblStatus = new JLabel("Status:");
		
		dataPanel.add(lblIdNaloga);
		dataPanel.add(idNaloga, "wrap");
		dataPanel.add(lblSifraBanke);
		dataPanel.add(sifraBanke, "wrap");
		dataPanel.add(lblBanSifraBanke);
		dataPanel.add(banSifraBanke, "wrap");
		dataPanel.add(lblUkupanIznos);
		dataPanel.add(ukupanIznos, "wrap");
		dataPanel.add(lblDatum);
		dataPanel.add(datum, "wrap");
		dataPanel.add(lblRtgs);
		dataPanel.add(rtgs, "wrap");
		dataPanel.add(lblStatus);
		dataPanel.add(status, "wrap");
		bottomPanel.add(dataPanel);
	}

	@Override
	protected void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		MedjubankarskiNalogTableModel tableModel = new MedjubankarskiNalogTableModel(new String[] {
				"ID Naloga", "Sifra banke", "Ban Sifra Banke", "Ukupan Iznos", "Datum", "RTGS", 
				"Status" }, 0);
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
		MedjubankarskiNalogTableModel tableModel =  (MedjubankarskiNalogTableModel) tblGrid.getModel();
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
			MedjubankarskiNalogTableModel btm = (MedjubankarskiNalogTableModel) tblGrid.getModel();
			int index = btm.insertRow(values, idNaloga.getText().trim());
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
			MedjubankarskiNalogTableModel btm = (MedjubankarskiNalogTableModel) tblGrid.getModel();
			btm.updateRow(index, staraSifra, values);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public JTextField getIdNaloga() {
		return idNaloga;
	}

	@Override
	public void removeRow() {
		int index = tblGrid.getSelectedRow();
		MedjubankarskiNalogTableModel tableModel = (MedjubankarskiNalogTableModel) tblGrid.getModel();
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
			MedjubankarskiNalogTableModel btm = (MedjubankarskiNalogTableModel) tblGrid.getModel();
			btm.deleteRow(index);
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

		MedjubankarskiNalogTableModel btm = (MedjubankarskiNalogTableModel) tblGrid.getModel();
		try {
			btm.searchRow(values);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}

	}
	
}
