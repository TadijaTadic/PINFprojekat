package gui.standard.form;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.DnevnoStanjeTableModel;
import model.FizickoLiceTableModel;
import actions.standard.form.ZoomFormAction;

public class DnevnoStanjeForm extends AbstractForm {
	
	private JTextField tfBrIzvoda = new JTextField(20);
	private JTextField tfIDRacuna = new JTextField(20);
	private JTextField tfDatPrometa = new JTextField(10);
	private JTextField tfPretStanje = new JTextField(20);
	private JTextField tfNovoStanje= new JTextField(20);
	private JTextField tfPromKor = new JTextField(20);
	private JTextField tfPromTer= new JTextField(20);
	private JButton btnZoom = new JButton("...");
	public Object[] collectionOfFields = {  tfBrIzvoda, tfIDRacuna, tfDatPrometa, tfPretStanje, 
			tfPromKor, tfPromTer, tfNovoStanje};
	
	public DnevnoStanjeForm() {
		super();
		setTitle("Dnevno stanje");
		JLabel lblBrIzvoda = new JLabel ("Broj izvoda:");
		JLabel lblIDRacuna = new JLabel("ID računa:");
		JLabel lblDatPrometa = new JLabel ("Datum računa:");
		JLabel lblPretStanje = new JLabel("Prethodno stanje:");
		JLabel lblNovoStanje = new JLabel ("Novo stanje:");
		JLabel lblPromKor = new JLabel("Promet u korist:");
		JLabel lblPromTer = new JLabel ("Promet na teret:");

		dataPanel.add(lblBrIzvoda);
		dataPanel.add(tfBrIzvoda,"wrap");
		dataPanel.add(lblIDRacuna);
		dataPanel.add(tfIDRacuna);
		dataPanel.add(btnZoom,"wrap");
		btnZoom.setAction(new ZoomFormAction(this));
		dataPanel.add(lblDatPrometa);
		dataPanel.add(tfDatPrometa,"wrap");
		dataPanel.add(lblPretStanje);
		dataPanel.add(tfPretStanje,"wrap");
		dataPanel.add(lblPromKor);
		dataPanel.add(tfPromKor,"wrap");
		dataPanel.add(lblPromTer);
		dataPanel.add(tfPromTer,"wrap");
		dataPanel.add(lblNovoStanje);
		dataPanel.add(tfNovoStanje,"wrap");		
		
		bottomPanel.add(dataPanel);
	}
	
	@Override
	protected void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		DnevnoStanjeTableModel tableModel = new DnevnoStanjeTableModel(new String[] {
				 "Broj izvoda", "ID računa", "Datum prometa","Prethodno stanje",
				 "Promet u korist", "Promet na teret", "Novo stanje" }, 0);
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

}
