package gui.standard.form;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.DBConnection;
import net.miginfocom.swing.MigLayout;
import model.DnevnoStanjeTableModel;
import model.FizickoLiceTableModel;
import model.KursuValutiTableModel;
import model.RacuniPravnihLicaTableModel;
import actions.standard.form.CommitAction;
import actions.standard.form.RollbackAction;
import actions.standard.form.ZoomFormAction;

public class DnevnoStanjeForm extends AbstractForm {
	
	private JTextField tfDuznik = new JTextField(20);
	private JTextField tfRacDuznika = new JTextField(20);
	private JTextField tfPrimalac = new JTextField(20);
	private JTextField tfRacPrimaoca = new JTextField(20);
	private JTextField tfIznos= new JTextField(20);
	private JTextField tfStaro= new JTextField(7);
	private JTextField tfUkorist= new JTextField(7);
	private JTextField tfNateret= new JTextField(7);
	private JTextField tfNovo= new JTextField(7);
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
		JLabel lblStaro = new JLabel ("Staro stanje:");
		JLabel lblUkorist = new JLabel ("U korist:");
		JLabel lblNateret = new JLabel ("Na teret:");
		JLabel lblNovo = new JLabel ("Novo stanje:");

		dataPanel.add(lblDuznik);
		dataPanel.add(tfDuznik,"wrap");
		dataPanel.add(lblRacDuznika);
		dataPanel.add(tfRacDuznika,"wrap");
		dataPanel.add(lblPrimalac);
		dataPanel.add(tfPrimalac,"wrap");
		dataPanel.add(lblRacPrimaoca);
		dataPanel.add(tfRacPrimaoca,"wrap");
		dataPanel.add(lblIznos);
		dataPanel.add(tfIznos,"wrap");
		dataPanel.add(lblStaro);
		dataPanel.add(tfStaro);
		dataPanel.add(lblUkorist);
		dataPanel.add(tfUkorist);
		dataPanel.add(lblNateret);
		dataPanel.add(tfNateret);
		dataPanel.add(lblNovo);
		dataPanel.add(tfNovo);
		
		toolBar.setVisible(false);
		this.getBtnCommit().setVisible(false);
		this.getBtnRollback().setVisible(false);
		
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
/*@Override
	private void initGui(){		
		
		bottomPanel.setLayout(new MigLayout("fillx"));		
		dataPanel.setLayout(new MigLayout("gapx 15px"));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new MigLayout("wrap"));
		bottomPanel.add(buttonsPanel,"dock east");

		add(bottomPanel, "grow, wrap");
	}*/
	
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
	
	public void fillBal(String stt) throws SQLException {
		String staro = "";
		String ukorist = "";
		String nateret = "";
		String novo = "";
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(stt);
		if(rset.next()) {
			staro = rset.getString(1);
			ukorist = rset.getString(2);
			nateret = rset.getString(3);
			novo = rset.getString(4);
		}
		tfStaro.setText(staro);
		tfUkorist.setText(ukorist);
		tfNateret.setText(nateret);
		tfNovo.setText(novo);
		rset.close();
		stmt.close();
	}

}
