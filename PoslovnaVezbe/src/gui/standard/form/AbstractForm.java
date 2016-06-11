package gui.standard.form;

import gui.main.form.MainFrame;

import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;

import pattern.AddState;
import pattern.Context;
import pattern.EditState;
import pattern.SearchState;
import pattern.State;
import util.ColumnList;
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

public class AbstractForm extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	protected JTable tblGrid = new JTable();
	private Context context = new Context(this);
	public static final State editState = new EditState();
	public static final State addState = new AddState();
	public static final State serachState = new SearchState();	
	protected ColumnList list;
	protected JPanel bottomPanel = new JPanel();
	protected JPanel dataPanel = new JPanel();
	
	public JPanel getBottomPanel() {
		return bottomPanel;
	}

	public JPanel getDataPanel() {
		return dataPanel;
	}

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


	public AbstractForm(){

		setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		//setTitle("Države");
		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);
		
		
		initToolbar();
		initTable();
		initGui();
		
	}
	
	protected void initTable(){
	
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
		
		bottomPanel.setLayout(new MigLayout("fillx"));		
		dataPanel.setLayout(new MigLayout("gapx 15px"));

		JPanel buttonsPanel = new JPanel();
		btnCommit = new JButton(new CommitAction(this));
		btnRollback = new JButton(new RollbackAction(this));

		buttonsPanel.setLayout(new MigLayout("wrap"));
		buttonsPanel.add(btnCommit);
		buttonsPanel.add(btnRollback);
		bottomPanel.add(buttonsPanel,"dock east");

		add(bottomPanel, "grow, wrap");
	}
	
	public void removeRow() {
		
	}
	 
	public void addRow() {
		
	}

	public void editRow() {
		
	}

	public void search() {
		
	}

	public ColumnList getList() {
		// TODO Auto-generated method stub
		return list;
	}

	public void pickup() {

	}

	public void nextForm() {
		
	}
	 
	 

}

