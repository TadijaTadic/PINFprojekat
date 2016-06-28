package gui.standard.form;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
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
import model.NaseljenoMestoTableModel;


public class NaseljenoMestoForm extends AbstractForm {
	

	private static final long serialVersionUID = 1L;
	
		private JTextField nmSifra = new JTextField(5);
		private JTextField drSifra = new JTextField(20);
		private JTextField nmNaziv = new JTextField(20);
		private JTextField nmPTT = new JTextField(20);
		private JButton btnZoom = new JButton("...");

		
		
		public JTextField getNmSifra() {
			return nmSifra;
		}




		public void setNmSifra(JTextField nmSifra) {
			this.nmSifra = nmSifra;
		}




		public JTextField getDrSifra() {
			return drSifra;
		}




		public void setDrSifra(JTextField drSifra) {
			this.drSifra = drSifra;
		}




		public JTextField getNmNaziv() {
			return nmNaziv;
		}




		public void setNmNaziv(JTextField nmNaziv) {
			this.nmNaziv = nmNaziv;
		}




		public JTextField getNmPTT() {
			return nmPTT;
		}




		public void setNmPTT(JTextField nmPTT) {
			this.nmPTT = nmPTT;
		}




		public NaseljenoMestoForm() {
			super();
			setTitle("Naseljeno Mesto");
			JLabel lblNmSifra = new JLabel ("Sifra Nas. Mesta:");
			JLabel lblDrSifra = new JLabel("Sifra Drzave:");
			JLabel lblNmNaziv = new JLabel("Naziv Nas. Mesta:");
			JLabel lblNmPTT = new JLabel("PTT Oznaka:");

			dataPanel.add(lblNmSifra);
			dataPanel.add(nmSifra,"wrap");
			dataPanel.add(lblDrSifra);
			dataPanel.add(drSifra);
			dataPanel.add(btnZoom, "wrap");
			btnZoom.setAction(new ZoomFormAction(this));
			dataPanel.add(lblNmNaziv);
			dataPanel.add(nmNaziv, "wrap");
			dataPanel.add(lblNmPTT);
			dataPanel.add(nmPTT,"wrap");
			bottomPanel.add(dataPanel);
		}

		


		@Override
		protected void initTable() {

			JScrollPane scrollPane = new JScrollPane(tblGrid);
			add(scrollPane, "grow, wrap");
			NaseljenoMestoTableModel tableModel = new NaseljenoMestoTableModel(new String[] {
					"Sifra Nas. Mesta", "Sifra Drzave", "Naziv Nas. Mesta", "PTT Oznaka" }, 0);
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
				nmSifra.setText("");
				drSifra.setText("");
				nmNaziv.setText("");
				nmPTT.setText("");
				return;
			}
			NaseljenoMestoTableModel tableModel =  (NaseljenoMestoTableModel) tblGrid.getModel();
			String nm_Sifra = (String) tableModel.getValueAt(index, 0);
			String dr_Sifra = (String) tableModel.getValueAt(index, 1);
			String nm_Naziv = (String) tableModel.getValueAt(index, 2);
			String nm_PTT = (String) tableModel.getValueAt(index, 3);
			nmSifra.setText(nm_Sifra);
			drSifra.setText(dr_Sifra);
			nmNaziv.setText(nm_Naziv);
			nmPTT.setText(nm_PTT);
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
				NaseljenoMestoTableModel kltm = (NaseljenoMestoTableModel) tblGrid.getModel();
				kltm.deleteRow(index);
				if (tableModel.getRowCount() > 0)
					tblGrid.setRowSelectionInterval(newIndex, newIndex);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void addRow() {
			String nm_sifra = nmSifra.getText().trim();
			String dr_sifra = drSifra.getText().trim();
			String nm_naziv = nmNaziv.getText().trim();
			String nm_ptt = nmPTT.getText().trim();
			try {
				NaseljenoMestoTableModel dtm = (NaseljenoMestoTableModel) tblGrid.getModel();
				int index = dtm.insertRow(nm_sifra, dr_sifra, nm_naziv, nm_ptt);
				tblGrid.setRowSelectionInterval(index, index);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void editRow() {
			String nm_sifra = nmSifra.getText().trim();
			String dr_sifra = drSifra.getText().trim();
			String nm_naziv = nmNaziv.getText().trim();
			String nm_ptt = nmPTT.getText().trim();
			
			int index = tblGrid.getSelectedRow();
			String oldId = (String) tblGrid.getValueAt(index, 0); 
			try {
				NaseljenoMestoTableModel dtm = (NaseljenoMestoTableModel) tblGrid.getModel();
				dtm.updateRow(index, nm_sifra, dr_sifra, nm_naziv, nm_ptt, oldId);
				tblGrid.setRowSelectionInterval(index, index);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void search() {
			Object[] collectionOfFields = { nmSifra, drSifra, nmNaziv, nmPTT };
			ArrayList<String> values = new ArrayList<String>();
			for (Object field : collectionOfFields) {
					values.add(((JTextField) field).getText().trim());
			}
			NaseljenoMestoTableModel kltm = (NaseljenoMestoTableModel) tblGrid.getModel();
			try {
				kltm.searchRow(values);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void zoom() {
			DrzavaForm df = new DrzavaForm();
			df.setLocation(500,140);
			df.setModal(true);
			df.setVisible(true);
			
			drSifra.setText((String)df.getList().getValue("DR_SIFRA"));
		}

		public void pickup() {
			 int index = tblGrid.getSelectedRow();
			 String sifraMesta = (String)tblGrid.getModel().getValueAt(index, 0);
			 list = new ColumnList();
			 list.add(new Column("NM_SIFRA", sifraMesta));
			 this.setVisible(false);
		}
		
		
	

}
