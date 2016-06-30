package gui.standard.form;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import util.Column;
import util.ColumnList;
import actions.standard.form.ZoomFormAction;
import model.ValuteTableModel;

public class ValuteForm extends AbstractForm {
	private JTextField tfIdValute = new JTextField(5);
	private JTextField tfSifraDrzave = new JTextField(5);
	private JTextField tfSifraValute = new JTextField(5);
	private JTextField tfNaziv = new JTextField(20);
	private JCheckBox cbDomicilna = new JCheckBox();
	private JButton btnZoom = new JButton("...");
	public Object[] collectionOfFields = { tfIdValute, tfSifraDrzave, tfSifraValute, tfNaziv,
			 cbDomicilna};
	
	public ValuteForm() {
		super();
		setTitle("Valute");
		JLabel lblIdValute = new JLabel("Id valute:");
		JLabel lblSifraDrzave = new JLabel("Sifra drzave:");
		JLabel lblSifraValute = new JLabel("Sifra valute:");
		JLabel lblNaziv= new JLabel("Naziv valute:");
		JLabel lblDomicilna = new JLabel("Domicilna:");
		
		dataPanel.add(lblIdValute);
		dataPanel.add(tfIdValute, "wrap");
		dataPanel.add(lblSifraDrzave);
		dataPanel.add(tfSifraDrzave);
		dataPanel.add(btnZoom, "wrap");
		btnZoom.setAction(new ZoomFormAction(this));
		dataPanel.add(lblSifraValute);
		dataPanel.add(tfSifraValute, "wrap");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv, "wrap");
		dataPanel.add(lblDomicilna);
		dataPanel.add(cbDomicilna, "wrap");
		bottomPanel.add(dataPanel);
}
	@Override
	protected void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		ValuteTableModel tableModel = new ValuteTableModel(new String[] {
				"Id valute", "Sifra drzave", "Sifra valute", "Naziv valute", "Domicilna" }, 0);
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
		ValuteTableModel tableModel =  (ValuteTableModel) tblGrid.getModel();
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
			ValuteTableModel vtm = (ValuteTableModel) tblGrid.getModel();
			int index = vtm.insertRow(values, tfIdValute.getText().trim());
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
			ValuteTableModel vtm = (ValuteTableModel) tblGrid.getModel();
			vtm.updateRow(index, staraSifra, values);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void removeRow() {
		int index = tblGrid.getSelectedRow();
		ValuteTableModel tableModel = (ValuteTableModel) tblGrid.getModel();
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
			ValuteTableModel vtm = (ValuteTableModel) tblGrid.getModel();
			vtm.deleteRow(index);
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

		ValuteTableModel vtm = (ValuteTableModel) tblGrid.getModel();
		try {
			vtm.searchRow(values);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}

	}
	public JTextField getTfIdValute() {
		// TODO Auto-generated method stub
		return tfIdValute;
	}
	
	public void zoom() {
		DrzavaForm df = new DrzavaForm();
		df.setLocation(500,140);
		df.setModal(true);
		df.setVisible(true);
		
		tfSifraDrzave.setText((String)df.getList().getValue("DR_SIFRA"));
	}
	
	public void pickup() {
		 int index = tblGrid.getSelectedRow();
		 String idValute = (String)tblGrid.getModel().getValueAt(index, 0);
		 list = new ColumnList();
		 list.add(new Column("ID_VALUTE",idValute));
		 this.setVisible(false);
	}
}
