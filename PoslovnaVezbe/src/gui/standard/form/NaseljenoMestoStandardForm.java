/*package gui.standard.form;

import gui.main.form.MainFrame;

import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import pattern.AddState;
import pattern.Context;
import pattern.EditState;
import pattern.SearchState;
import pattern.State;
import util.Lookup;
import database.DBConnection;
import net.miginfocom.swing.MigLayout;
import model.DrzaveTableModel;
import model.NaseljenoMestoTableModel;
import actions.standard.form.AddAction;
import actions.standard.form.CommitAction;
import actions.standard.form.DeleteAction;
import actions.standard.form.FirstAction;
import actions.standard.form.HelpAction;
import actions.standard.form.LastAction;
import actions.standard.form.NextAction;
import actions.standard.form.NextFormAction;
import actions.standard.form.PickupAction;
import actions.standard.form.PreviousAction;
import actions.standard.form.RefreshAction;
import actions.standard.form.RollbackAction;
import actions.standard.form.SearchAction;
import actions.standard.form.ZoomFormAction;

public class NaseljenoMestoStandardForm extends JDialog{
	private static final long serialVersionUID = 1L;

	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JTable tblGrid = new JTable();
	private JTextField tfSifra = new JTextField(5);
	private JTextField tfNaziv = new JTextField(20);

	private JTextField tfNazivDrzave = new JTextField(20);
	private JTextField tfSifraDrzave = new JTextField(5);
	
	private Context context = new Context(this);
	public static final State editState = new EditState();
	public static final State addState = new AddState();
	public static final State serachState = new SearchState();	

	private JButton btnZoom = new JButton("...");

	public NaseljenoMestoStandardForm(){

		setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		setTitle("Naseljena mesta");
		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);
		
		initToolbar();
		initTable();
		initGui();

	}
	
	private void initGui(){
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new MigLayout());

		JPanel buttonsPanel = new JPanel();
		btnCommit = new JButton(new CommitAction(this));
		btnRollback = new JButton(new RollbackAction(this));
		btnZoom.setAction(new ZoomFormAction(this));

		JLabel lblSifra = new JLabel ("Šifra mesta:");
		JLabel lblNaziv = new JLabel("Naziv mesta:");
		JLabel lblSifraDrzave = new JLabel ("Šifra države:");

		dataPanel.add(lblSifra);
		dataPanel.add(tfSifra,"wrap, gapx 15px");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv,"wrap,gapx 15px, span 3");
		dataPanel.add(lblSifraDrzave);
		dataPanel.add(tfSifraDrzave, "gapx 15px");
		dataPanel.add(btnZoom);

		dataPanel.add(tfNazivDrzave,"pushx");
		tfNazivDrzave.setEditable(false);
		bottomPanel.add(dataPanel);

		buttonsPanel.setLayout(new MigLayout("wrap"));
		buttonsPanel.add(btnCommit);
		buttonsPanel.add(btnRollback);
		bottomPanel.add(buttonsPanel,"dock east");

		add(bottomPanel, "grow, wrap");
		
		tfSifraDrzave.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String sifraDrzave = tfSifraDrzave.getText().trim();
				try {
					tfNazivDrzave.setText(Lookup.getDrzava(sifraDrzave));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	
	}
	
	private void initToolbar(){
		toolBar = new JToolBar();
		btnSearch = new JButton(new SearchAction(this));
		toolBar.add(btnSearch);


		btnRefresh = new JButton(new RefreshAction(this));
		toolBar.add(btnRefresh);

		btnPickup = new JButton(new PickupAction(this));
		toolBar.add(btnPickup);


		btnHelp = new JButton(new HelpAction());
		toolBar.add(btnHelp);

		toolBar.addSeparator();

		btnFirst = new JButton(new FirstAction(this));
		toolBar.add(btnFirst);

		btnPrevious = new JButton(new PreviousAction(this));
		toolBar.add(btnPrevious);

		btnNext = new JButton(new NextAction(this));
		toolBar.add(btnNext);

		btnLast = new JButton(new LastAction(this));
		toolBar.add(btnLast);

		toolBar.addSeparator();


		btnAdd = new JButton(new AddAction(this));
		toolBar.add(btnAdd);

		btnDelete = new JButton(new DeleteAction(this));
		toolBar.add(btnDelete);

		toolBar.addSeparator();

		btnNextForm = new JButton(new NextFormAction(this));
		toolBar.add(btnNextForm);
		
		btnPickup.setEnabled(false);
		add(toolBar, "dock north");
	}


	private void initTable(){
	
		JScrollPane scrollPane = new JScrollPane(tblGrid);      
		add(scrollPane, "grow, wrap");
		NaseljenoMestoTableModel tableModel = new NaseljenoMestoTableModel(new String[] {"Šifra",   "Naziv", "Šifra države", "Naziv države"}, 0);
		tblGrid.setModel(tableModel);

		try {
			tableModel.open();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		//Dozvoljeno selektovanje redova
		tblGrid.setRowSelectionAllowed(true);
		//Ali ne i selektovanje kolona 
		tblGrid.setColumnSelectionAllowed(false);

		//Dozvoljeno selektovanje samo jednog reda u jedinici vremena 
		tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tblGrid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					return;
				sync();
			}
		});

	}
	
	public void goLast() {
		int rowCount = tblGrid.getModel().getRowCount();
		if (rowCount > 0)
			tblGrid.setRowSelectionInterval(rowCount - 1, rowCount - 1);
	}
	
	public void goFirst() {
		int rowCount = tblGrid.getModel().getRowCount();
		if (rowCount > 0 )
			tblGrid.setRowSelectionInterval(0, 0);
	}
	
	public void goNext() {
		int rowCount = tblGrid.getModel().getRowCount();
		int selectedRowIndex = tblGrid.getSelectedRow();
		if (rowCount > 0 && selectedRowIndex > -1 && selectedRowIndex < rowCount - 1)
			tblGrid.setRowSelectionInterval(selectedRowIndex + 1, selectedRowIndex + 1);
	}
	
	public void goPrevious() {
		int rowCount = tblGrid.getModel().getRowCount();
		int selectedRowIndex = tblGrid.getSelectedRow();
		if (rowCount > 0 && selectedRowIndex > -1 && selectedRowIndex > 0)
			tblGrid.setRowSelectionInterval(selectedRowIndex - 1, selectedRowIndex - 1);
	}

	public void sync() {
		int index = tblGrid.getSelectedRow();
		if (index < 0) {
			tfSifra.setText("");
			tfNaziv.setText("");
			tfSifraDrzave.setText("");
			tfNazivDrzave.setText("");
			return;
		}
		String sifra = (String)tblGrid.getModel().getValueAt(index, 0);
		String naziv = (String)tblGrid.getModel().getValueAt(index, 1);
		String sifraDrzave = (String)tblGrid.getModel().getValueAt(index, 2);
		String nazivDrzave = (String)tblGrid.getModel().getValueAt(index, 3);
		tfSifra.setText(sifra);
		tfNaziv.setText(naziv);
		tfSifraDrzave.setText(sifraDrzave);
		tfNazivDrzave.setText(nazivDrzave);
	}

	public void editRow() {
		String nmsifra = tfSifra.getText().trim();
		String nmnaziv = tfNaziv.getText().trim();
		String drsifra = tfSifraDrzave.getText().trim();
		int index = tblGrid.getSelectedRow();
		String staraSifra = (String) tblGrid.getValueAt(index, 0); 
		try {
			NaseljenoMestoTableModel nmtm = (NaseljenoMestoTableModel) tblGrid.getModel();
			nmtm.updateRow(index, nmsifra, nmnaziv, drsifra, staraSifra);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void removeRow() {
		if (JOptionPane.showConfirmDialog(this, "Da li ste sigurni?",
				"Pitanje", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		int index = tblGrid.getSelectedRow();
		NaseljenoMestoTableModel tableModel = (NaseljenoMestoTableModel) tblGrid.getModel();
		if (index == -1) // Ako nema selektovanog reda (tabela prazna)
			return; // izlazak
		// kada obrisemo tekuci red, selektovacemo sledeci (newindex):
		int newIndex = index;
		// sem ako se obrise poslednji red, tada selektujemo prethodni
		if (index == tableModel.getRowCount() - 1)
			newIndex--;
		try {
			NaseljenoMestoTableModel nmtm = (NaseljenoMestoTableModel) tblGrid.getModel();
			nmtm.deleteRow(index);
			if (tableModel.getRowCount() > 0)
				tblGrid.setRowSelectionInterval(newIndex, newIndex);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void addRow() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		String drsifra = tfSifraDrzave.getText().trim();
		String drnaziv = tfNazivDrzave.getText().trim();
		try {
			NaseljenoMestoTableModel nmtm = (NaseljenoMestoTableModel) tblGrid.getModel();
			int index = nmtm.insertRow(sifra, naziv, drsifra, drnaziv);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void search() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		String drsifra = tfSifraDrzave.getText().trim();
		NaseljenoMestoTableModel nmtm = (NaseljenoMestoTableModel) tblGrid.getModel();
		try {
			nmtm.searchRow(sifra, naziv, drsifra);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void transferValues(String sifra, String naziv) {
		tfSifraDrzave.setText(sifra);
		tfNazivDrzave.setText(naziv);
	}

	public JTable getTblGrid() {
		// TODO Auto-generated method stub
		return tblGrid;
	}

	public Context getContext() {
		// TODO Auto-generated method stub
		return context;
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	public JButton getBtnCommit() {
		return btnCommit;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnFirst() {
		return btnFirst;
	}

	public JButton getBtnLast() {
		return btnLast;
	}

	public JButton getBtnHelp() {
		return btnHelp;
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public JButton getBtnNextForm() {
		return btnNextForm;
	}

	public JButton getBtnPickup() {
		return btnPickup;
	}

	public JButton getBtnRefresh() {
		return btnRefresh;
	}

	public JButton getBtnRollback() {
		return btnRollback;
	}

	public JButton getBtnSearch() {
		return btnSearch;
	}

	public JButton getBtnPrevious() {
		return btnPrevious;
	}

	public JButton getBtnZoom() {
		return btnZoom;
	}

	public JTextField getTfSifra() {
		return tfSifra;
	}

	public JTextField getTfNaziv() {
		return tfNaziv;
	}

	public JTextField getTfNazivDrzave() {
		return tfNazivDrzave;
	}

	public JTextField getTfSifraDrzave() {
		return tfSifraDrzave;
	}

	public void zoom() {
		DrzavaStandardForm dsf = new DrzavaStandardForm();
		dsf.setLocation(500,140);
		dsf.setModal(true);
		dsf.setVisible(true);
		tfSifraDrzave.setText((String)dsf.getList().getValue("DR_SIFRA"));
		tfNazivDrzave.setText((String)dsf.getList().getValue("DR_NAZIV"));
	}	
	
}

*/