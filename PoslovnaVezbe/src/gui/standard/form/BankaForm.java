package gui.standard.form;

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

import actions.standard.form.SpisakAction;
import model.BankeTableModel;
import model.DrzaveTableModel;
import model.KursnaListaTableModel;
import model.NaseljenoMestoTableModel;
import util.Column;
import util.ColumnList;

public class BankaForm extends AbstractForm {
	
	private JTextField tfIdBanke = new JTextField(5);
	private JTextField tfSifraBanke = new JTextField(5);
	private JTextField tfPIB = new JTextField(20);
	private JTextField tfNazivBanke = new JTextField(20);
	private JTextField tfAdresaBanke = new JTextField(20);
	private JTextField tfEmailBanke = new JTextField(20);
	private JTextField tfWebSajtBanke = new JTextField(20);
	private JTextField tfTelefonBanke = new JTextField(20);
	private JTextField tfFaxBanke = new JTextField(20);
	private JCheckBox cbBanka = new JCheckBox();
	private JButton btnSpisak = new JButton("Spisak računa");
	public Object[] collectionOfFields = { tfIdBanke, tfSifraBanke, tfPIB, tfNazivBanke,
			tfAdresaBanke, tfEmailBanke, tfWebSajtBanke, tfTelefonBanke, tfFaxBanke, cbBanka};
	
	
	
	public BankaForm() {
		super();
		setTitle("Banke");
		JLabel lblIdBanke = new JLabel("Id banke:");
		JLabel lblSifraBanke = new JLabel("Sifra banke:");
		JLabel lblPIB = new JLabel("PIB:");
		JLabel lblNazivBanke = new JLabel("Naziv banke:");
		JLabel lblAdresaBanke = new JLabel("Adresa banke:");
		JLabel lblEmailBanke = new JLabel("Email banke:");
		JLabel lblWebSajtBanke = new JLabel("Sajt banke:");
		JLabel lblTelefonBanke = new JLabel("Telefon banke:");
		JLabel lblFaxBanke = new JLabel("Fax banke:");
		JLabel lblBanka = new JLabel("Banka:");
		
		dataPanel.add(lblIdBanke);
		dataPanel.add(tfIdBanke, "wrap");
		dataPanel.add(lblSifraBanke);
		dataPanel.add(tfSifraBanke, "wrap");
		dataPanel.add(lblPIB);
		dataPanel.add(tfPIB, "wrap");
		dataPanel.add(lblNazivBanke);
		dataPanel.add(tfNazivBanke, "wrap");
		dataPanel.add(lblAdresaBanke);
		dataPanel.add(tfAdresaBanke, "wrap");
		dataPanel.add(lblEmailBanke);
		dataPanel.add(tfEmailBanke, "wrap");
		dataPanel.add(lblWebSajtBanke);
		dataPanel.add(tfWebSajtBanke, "wrap");
		dataPanel.add(lblTelefonBanke);
		dataPanel.add(tfTelefonBanke, "wrap");
		dataPanel.add(lblFaxBanke);
		dataPanel.add(tfFaxBanke, "wrap");
		dataPanel.add(lblBanka);
		dataPanel.add(cbBanka, "wrap");
		dataPanel.add(btnSpisak);
		btnSpisak.setAction(new SpisakAction(this));
		bottomPanel.add(dataPanel);
	}

	@Override
	protected void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		BankeTableModel tableModel = new BankeTableModel(new String[] {
				"Id banke", "Sifra banke", "PIB", "Naziv banke", "Adresa banke", "Email banke", 
				"Sajt banke", "Telefon banke", "Fax banke", "Banka" }, 0);
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
		BankeTableModel tableModel =  (BankeTableModel) tblGrid.getModel();
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
			BankeTableModel btm = (BankeTableModel) tblGrid.getModel();
			int index = btm.insertRow(values, tfIdBanke.getText().trim());
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
			BankeTableModel btm = (BankeTableModel) tblGrid.getModel();
			btm.updateRow(index, staraSifra, values);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public  JTextField getTfIdBanke() {
		return tfIdBanke;
	}

	@Override
	public void removeRow() {
		int index = tblGrid.getSelectedRow();
		BankeTableModel tableModel = (BankeTableModel) tblGrid.getModel();
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
			BankeTableModel btm = (BankeTableModel) tblGrid.getModel();
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

		BankeTableModel btm = (BankeTableModel) tblGrid.getModel();
		try {
			btm.searchRow(values);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public void pickup() {
		 int index = tblGrid.getSelectedRow();
		 String idBanke = (String)tblGrid.getModel().getValueAt(index, 0);
		 list = new ColumnList();
		 list.add(new Column("ID_BANKE",idBanke));
		 this.setVisible(false);
	}
	
	public void nextForm() {
		int index = tblGrid.getSelectedRow();
		String idBanke = (String)tblGrid.getModel().getValueAt(index, 0);
		KursnaListaForm form = new KursnaListaForm();
		KursnaListaTableModel kltm = (KursnaListaTableModel) form.getTblGrid().getModel();
		try {
			kltm.openAsChildForm("SELECT id_kursne_liste, id_banke, kl_datum, kl_broj, kl_datpr FROM kursna_lista WHERE kursna_lista.id_banke LIKE '%"
					+idBanke+ "%'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.setVisible(true);
	}
}
