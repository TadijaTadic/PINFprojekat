/*package gui.standard.form;

import gui.main.form.MainFrame;
import util.Column;

import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.AbstractAction;
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

import pattern.AddState;
import pattern.Context;
import pattern.EditState;
import pattern.SearchState;
import pattern.State;
import util.ColumnList;
import database.DBConnection;
import model.DrzaveTableModel;
import model.NaseljenoMestoTableModel;
import net.miginfocom.swing.MigLayout;
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

public class DrzavaStandardForm extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JTextField tfSifra = new JTextField(5);
	private JTextField tfNaziv = new JTextField(20);
	private JTable tblGrid = new JTable();
	private Context context = new Context(this);
	public static final State editState = new EditState();
	public static final State addState = new AddState();
	public static final State serachState = new SearchState();	
	private ColumnList list;
	
	public JTable getTblGrid() {
		return tblGrid;
	}
	
	public JButton getBtnSearch() {
		return btnSearch;
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

	public JButton getBtnPrevious() {
		return btnPrevious;
	}

	public Context getContext() {
		return context;
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

	public DrzavaStandardForm(){

		setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		setTitle("Države");
		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);
		
		
		initToolbar();
		initTable();
		initGui();
		
	}
	
	private void initTable(){
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
			return;
		}
		DrzaveTableModel tableModel =  (DrzaveTableModel) tblGrid.getModel();
		String sifra = (String) tableModel.getValueAt(index, 0);
		String naziv = (String) tableModel.getValueAt(index, 1);
		tfSifra.setText(sifra);
		tfNaziv.setText(naziv);
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

		add(toolBar, "dock north");
	}
	
	private void initGui(){
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new MigLayout("gapx 15px"));

		JPanel buttonsPanel = new JPanel();
		btnCommit = new JButton(new CommitAction(this));
		btnRollback = new JButton(new RollbackAction(this));


		JLabel lblSifra = new JLabel ("Šifra države:");
		JLabel lblNaziv = new JLabel("Naziv države:");

		dataPanel.add(lblSifra);
		dataPanel.add(tfSifra,"wrap");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv);
		bottomPanel.add(dataPanel);


		buttonsPanel.setLayout(new MigLayout("wrap"));
		buttonsPanel.add(btnCommit);
		buttonsPanel.add(btnRollback);
		bottomPanel.add(buttonsPanel,"dock east");

		add(bottomPanel, "grow, wrap");
	}
	
	 public void removeRow() {
		if (JOptionPane.showConfirmDialog(this, "Da li ste sigurni?",
				"Pitanje", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		int index = tblGrid.getSelectedRow();
		DrzaveTableModel tableModel = (DrzaveTableModel) tblGrid.getModel();
		if (index == -1) // Ako nema selektovanog reda (tabela prazna)
			return; // izlazak
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

	public ColumnList getList() {
		// TODO Auto-generated method stub
		return list;
	}

	public void pickup() {
		 int index = tblGrid.getSelectedRow();
		 String sifra = (String)tblGrid.getModel().getValueAt(index, 0);
	     String naziv = (String)tblGrid.getModel().getValueAt(index, 1);
		 list = new ColumnList();
		 list.add(new Column("DR_SIFRA",sifra));
		 list.add(new Column("DR_NAZIV",naziv));
		 this.setVisible(false);
	}

	public void nextForm() {
		int index = tblGrid.getSelectedRow();
		String sifra = (String)tblGrid.getModel().getValueAt(index, 0);
	    //String naziv = (String)tblGrid.getModel().getValueAt(index, 1);
		NaseljenoMestoStandardForm form = new NaseljenoMestoStandardForm();
		NaseljenoMestoTableModel nmtm = (NaseljenoMestoTableModel) form.getTblGrid().getModel();
		try {
			nmtm.openAsChildForm("SELECT * FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra WHERE naseljeno_mesto.dr_sifra LIKE '%"
					+sifra+ "%'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.setVisible(true);
	}
	 
	 

}
*/