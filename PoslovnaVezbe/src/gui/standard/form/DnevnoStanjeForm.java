package gui.standard.form;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.DnevnoStanjeTableModel;
import model.FizickoLiceTableModel;
import model.KursuValutiTableModel;
import model.RacuniPravnihLicaTableModel;
import actions.standard.form.ZoomFormAction;

public class DnevnoStanjeForm extends AbstractForm {
	
	private JTextField tfDuznik = new JTextField(20);
	private JTextField tfRacDuznika = new JTextField(20);
	private JTextField tfPrimalac = new JTextField(20);
	private JTextField tfRacPrimaoca = new JTextField(20);
	private JTextField tfIznos= new JTextField(20);
	private JButton btnZoom = new JButton("...");
	public Object[] collectionOfFields = {  tfDuznik, tfRacDuznika, tfPrimalac, tfRacPrimaoca, tfIznos};
	
	public DnevnoStanjeForm() {
		super();
		setTitle("Dnevno stanje");
		JLabel lblDuznik = new JLabel ("Dužnik:");
		JLabel lblRacDuznika = new JLabel("Račun dužnika:");
		JLabel lblPrimalac = new JLabel ("Primalac:");
		JLabel lblRacPrimaoca = new JLabel("Račun primaoca:");
		JLabel lblIznos = new JLabel ("Iznos:");

		dataPanel.add(lblDuznik);
		dataPanel.add(tfDuznik,"wrap");
		dataPanel.add(lblRacDuznika);
		dataPanel.add(tfRacDuznika,"wrap");
		dataPanel.add(lblPrimalac);
		dataPanel.add(tfPrimalac,"wrap");
		dataPanel.add(lblRacPrimaoca);
		dataPanel.add(tfRacPrimaoca,"wrap");
		dataPanel.add(lblIznos);
		dataPanel.add(tfIznos);
		
		
		bottomPanel.add(dataPanel);
	}
	
	@Override
	protected void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		DnevnoStanjeTableModel tableModel = new DnevnoStanjeTableModel(new String[] {
				 "Dužnik", "Račun dužnika", "Primalac","Račun primaoca", 
				 "Iznos" }, 0);
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
		DnevnoStanjeTableModel tableModel =  (DnevnoStanjeTableModel) tblGrid.getModel();
		int size = tableModel.getColumnCount();
		for (int i=0; i<size; i++) {
			((JTextField) collectionOfFields[i]).setText((String) tableModel.getValueAt(index, i));
		}
	}

}
