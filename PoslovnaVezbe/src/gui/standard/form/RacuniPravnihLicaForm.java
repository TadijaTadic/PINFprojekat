package gui.standard.form;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.RacuniPravnihLicaTableModel;

public class RacuniPravnihLicaForm extends AbstractForm {
	private JTextField tfIdRacuna = new JTextField(5);
	private JTextField tfIdBanke = new JTextField(5);
	private JTextField tfIdValute = new JTextField(20);
	private JTextField tfIdKlijenta = new JTextField(20);
	private JTextField tfBrRacun = new JTextField(20);
	private JTextField tfDatum = new JTextField(20);
	private JCheckBox cbVazi = new JCheckBox();
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
		
		dataPanel.add(lblIdRacuna);
		dataPanel.add(tfIdRacuna, "wrap");
		dataPanel.add(lblIdBanke);
		dataPanel.add(tfIdBanke, "wrap");
		dataPanel.add(lblIdValute);
		dataPanel.add(tfIdValute, "wrap");
		dataPanel.add(lblIdKlijenta);
		dataPanel.add(tfIdKlijenta, "wrap");
		dataPanel.add(lblBrRacun);
		dataPanel.add(tfBrRacun, "wrap");
		dataPanel.add(lblDatum);
		dataPanel.add(tfDatum, "wrap");
		dataPanel.add(lblVazi);
		dataPanel.add(cbVazi, "wrap");
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
}
