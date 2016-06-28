package gui.standard.form;

import gui.main.form.MainFrame;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import actions.standard.form.ZoomFormAction;
import model.BankeTableModel;
import model.DrzaveTableModel;
import model.KursnaListaTableModel;
import model.NalogZaUplatuTableModel;
import model.NaseljenoMestoTableModel;
import util.Column;
import util.ColumnList;
import util.DateLabelFormatter;

public class NalogZaUplatuForm extends AbstractForm {
	
	private static final long serialVersionUID = 1L;
	private JTextField tfBrStavke = new JTextField(5);
	private JTextField tfBrIzvoda = new JTextField(5);
	private JTextField tfOznakaVrste = new JTextField(3);
	private JTextField tfSifraMesta = new JTextField(10);
	private JTextField tfIdValute = new JTextField(5);
	private JTextField tfNalogodavac = new JTextField(20);
	private JTextField tfSvrhaPlacanja = new JTextField(20);
	private JTextField tfPoverilac = new JTextField(20);
	private UtilDateModel model = new UtilDateModel();
	private UtilDateModel model1 = new UtilDateModel();
	private Properties p = new Properties();
	private JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	private JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
	private JDatePickerImpl datumPrijema = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	private JDatePickerImpl datumValute = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
	private JTextField tfRacunDuznika = new JTextField(20);
	private JTextField tfModelZaduzenja = new JTextField(3);
	private JTextField tfPozivNaBrojZaduzenja = new JTextField(20);
	private JTextField tfModelOdobrenja = new JTextField(3);
	private JTextField tfPozivNaBrojOdobrenja = new JTextField(20);
	private JTextField tfRacunPoverioca = new JTextField(20);
	private JCheckBox cbHitno = new JCheckBox();
	private JTextField tfIznos = new JTextField(20);
	private JButton btnZoomBrIzvoda = new JButton("...");
	private JButton btnZoomSifraMesta = new JButton("...");
	
	public Object[] collectionOfFields = { tfBrStavke, tfBrIzvoda,
			tfOznakaVrste, tfSifraMesta, tfIdValute, tfNalogodavac,
			tfSvrhaPlacanja, tfPoverilac, datumPrijema, datumValute,
			tfRacunDuznika, tfModelZaduzenja, tfPozivNaBrojZaduzenja,
			tfRacunPoverioca, tfModelOdobrenja, tfPozivNaBrojOdobrenja,
			cbHitno, tfIznos };
	
	
	
	public NalogZaUplatuForm() {
		super();
		setTitle("Nalozi za uplatu");
		setSize(1200, 600);
		setLocationRelativeTo(MainFrame.getInstance());
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		setTitle("Banke");
		JLabel lblBrStavke = new JLabel("Broj stavke:");
		JLabel lblBrIzvoda = new JLabel("Broj izvoda:");
		JLabel lblOznakaVrste = new JLabel("Oznaka vrste:");
		JLabel lblSifraMesta = new JLabel("Sifra mesta:");
		JLabel lblIdValute = new JLabel("Id valute:");
		JLabel lblNalogodavac = new JLabel("Nalogodavac:");
		JLabel lblSvrhaPlacanja = new JLabel("Svrha placanja");
		JLabel lblPoverilac = new JLabel("Poverilac:");
		JLabel lbldatumPrijema = new JLabel("Datum prijema:");
		JLabel lbldatumValute = new JLabel("Datum valute:");
		JLabel lblRacunDuznika = new JLabel("Racun duznika:");
		JLabel lblModelZaduzenja = new JLabel("Model zaduzenja:");
		JLabel lblPozivNaBrojZaduzenja= new JLabel("Poziv na broj zaduzenja:");
		JLabel lblRacunPoverioca = new JLabel("Racun poverioca:");
		JLabel lblModelOdobrenja = new JLabel("Model odobrenja:");
		JLabel lblPozivNaBrojOdobrenja = new JLabel("Poziv na broj odobrenja:");
		JLabel lblHitno = new JLabel("Hitno?");
		JLabel lblIznos = new JLabel("Iznos");
		
		dataPanel.add(lblBrStavke);
		dataPanel.add(tfBrStavke);
		dataPanel.add(lblRacunDuznika);
		dataPanel.add(tfRacunDuznika, "wrap");
		dataPanel.add(lblBrIzvoda);
		dataPanel.add(tfBrIzvoda);
		dataPanel.add(lblModelZaduzenja);
		dataPanel.add(tfModelZaduzenja, "wrap");
		dataPanel.add(lblOznakaVrste);
		dataPanel.add(tfOznakaVrste);
		dataPanel.add(lblPozivNaBrojZaduzenja);
		dataPanel.add(tfPozivNaBrojZaduzenja, "wrap");
		dataPanel.add(lblSifraMesta);
		dataPanel.add(tfSifraMesta);
		dataPanel.add(btnZoomSifraMesta, "wrap");
		btnZoomSifraMesta.setAction(new ZoomFormAction(this));
		dataPanel.add(lblIdValute);
		dataPanel.add(tfIdValute, "wrap");
		dataPanel.add(lblNalogodavac);
		dataPanel.add(tfNalogodavac, "wrap");
		dataPanel.add(lblSvrhaPlacanja);
		dataPanel.add(tfSvrhaPlacanja, "wrap");
		dataPanel.add(lblPoverilac);
		dataPanel.add(tfPoverilac, "wrap");
		dataPanel.add(lbldatumPrijema);
		dataPanel.add(datumPrijema, "wrap");
		dataPanel.add(lbldatumValute);
		dataPanel.add(datumValute, "wrap");
		dataPanel.add(lblRacunPoverioca);
		dataPanel.add(tfRacunPoverioca, "wrap");
		dataPanel.add(lblModelOdobrenja);
		dataPanel.add(tfModelOdobrenja, "wrap");
		dataPanel.add(lblPozivNaBrojOdobrenja);
		dataPanel.add(tfPozivNaBrojOdobrenja, "wrap");
		dataPanel.add(lblHitno);
		dataPanel.add(cbHitno, "wrap");
		dataPanel.add(lblIznos);
		dataPanel.add(tfIznos);
		bottomPanel.add(dataPanel);
	}

	@Override
	protected void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		NalogZaUplatuTableModel tableModel = new NalogZaUplatuTableModel(new String[] {
				"Broj stavke", "Broj izvoda", "Oznaka vrste", "Sifra mesta", "Id valute", "Nalogodavac", 
				"Svrha placanja", "Poverilac", "Datum prijema", "Datum valute","Racun duznika", "Model zaduzenja", "Poziv na broj zaduzenja", "Racun poverioca", "Model odobrenja", "Poziv na broj odobrenja",  "Hitno?", "Iznos" }, 0);
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
		NalogZaUplatuTableModel tableModel =  (NalogZaUplatuTableModel) tblGrid.getModel();
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
			else if (collectionOfFields[i] instanceof JDatePickerImpl) {
				if (tableModel.getValueAt(index,  i) instanceof String) {
					String date = (String) tableModel.getValueAt(index,  i);
					((JDatePickerImpl) collectionOfFields[i]).getJFormattedTextField().setText(date);
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
			else if (field instanceof JDatePickerImpl)
				values.add(((JDatePickerImpl) field).getJFormattedTextField().getText());
			
		}
		try {
			NalogZaUplatuTableModel nzutm = (NalogZaUplatuTableModel) tblGrid.getModel();
			int index = nzutm.insertRow(values, tfBrStavke.getText().trim());
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
			else if (field instanceof JDatePickerImpl)
					values.add(((JDatePickerImpl) field).getJFormattedTextField().getText());
		}
		
		int index = tblGrid.getSelectedRow();
		String staraSifra = (String) tblGrid.getValueAt(index, 0); 
		try {
			NalogZaUplatuTableModel nzutm = (NalogZaUplatuTableModel) tblGrid.getModel();
			nzutm.updateRow(index, staraSifra, values);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void removeRow() {
		int index = tblGrid.getSelectedRow();
		NalogZaUplatuTableModel tableModel = (NalogZaUplatuTableModel) tblGrid.getModel();
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
			NalogZaUplatuTableModel nzutm = (NalogZaUplatuTableModel) tblGrid.getModel();
			nzutm.deleteRow(index);
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
			else if (field instanceof JDatePickerImpl)
				values.add(((JDatePickerImpl) field).getJFormattedTextField().getText());
		}

		NalogZaUplatuTableModel nzutm = (NalogZaUplatuTableModel) tblGrid.getModel();
		try {
			nzutm.searchRow(values);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public void zoomBrIzvodaDnevonogStanja() {
		DnevnoStanjeForm dsf = new DnevnoStanjeForm();
		dsf.setLocationRelativeTo(MainFrame.getInstance());
		dsf.setModal(true);
		dsf.setVisible(true);
	}
	
	public void zoomSifraMesta() {
		NaseljenoMestoForm nmsf = new NaseljenoMestoForm();
		nmsf.setLocationRelativeTo(MainFrame.getInstance());
		nmsf.setModal(true);
		nmsf.setVisible(true);
		if (nmsf.getList() != null)
			tfSifraMesta.setText((String)nmsf.getList().getValue("NM_SIFRA"));
	}

	public void pickup() {
		
	}
	
	public void nextForm() {
		
	}

	public JComponent getTfBrStavke() {
		return tfBrStavke;
	}

	public JButton getBtnZoomBrIzvoda() {
		return btnZoomBrIzvoda;
	}

	public JButton getBtnZoomSifraMesta() {
		return btnZoomSifraMesta;
	}
	
}
