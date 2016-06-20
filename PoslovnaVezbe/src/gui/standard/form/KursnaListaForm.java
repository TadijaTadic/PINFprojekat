package gui.standard.form;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.KursnaListaTableModel;


public class KursnaListaForm extends AbstractForm {
	

	private static final long serialVersionUID = 1L;
	
		private JTextField idKursneListe = new JTextField(5);
		private JTextField idBanke = new JTextField(20);
		private JTextField klDatum = new JTextField(20);
		private JTextField klBroj = new JTextField(20);
		private JTextField klDatPr = new JTextField(20);

		
		public JTextField getIdKursneListe() {
			return idKursneListe;
		}



		public void setIdKursneListe(JTextField idKursneListe) {
			this.idKursneListe = idKursneListe;
		}



		public JTextField getIdBanke() {
			return idBanke;
		}



		public void setIdBanke(JTextField idBanke) {
			this.idBanke = idBanke;
		}



		public JTextField getKlDatum() {
			return klDatum;
		}



		public void setKlDatum(JTextField klDatum) {
			this.klDatum = klDatum;
		}



		public JTextField getKlBroj() {
			return klBroj;
		}



		public void setKlBroj(JTextField klBroj) {
			this.klBroj = klBroj;
		}



		public JTextField getKlDatPr() {
			return klDatPr;
		}



		public void setKlDatPr(JTextField klDatPr) {
			this.klDatPr = klDatPr;
		}
		
		public KursnaListaForm() {
			super();
			setTitle("Kursne Liste");
			JLabel lblIDKursneListe = new JLabel ("ID Kursne Liste:");
			JLabel lblIDBanke = new JLabel("ID Banke:");
			JLabel lblDatum = new JLabel("Datum:");
			JLabel lblBroj = new JLabel("Broj:");
			JLabel lblDatPr = new JLabel("Primenjuje se od:");

			dataPanel.add(lblIDKursneListe);
			dataPanel.add(idKursneListe,"wrap");
			dataPanel.add(lblIDBanke);
			dataPanel.add(idBanke,"wrap");
			dataPanel.add(lblDatum);
			dataPanel.add(klDatum, "wrap");
			dataPanel.add(lblBroj);
			dataPanel.add(klBroj,"wrap");
			dataPanel.add(lblDatPr);
			dataPanel.add(klDatPr,"wrap");
			bottomPanel.add(dataPanel);
		}

		


		@Override
		protected void initTable() {

			JScrollPane scrollPane = new JScrollPane(tblGrid);
			add(scrollPane, "grow, wrap");
			KursnaListaTableModel tableModel = new KursnaListaTableModel(new String[] {
					"ID Kursne Liste", "ID Banke", "Datum", "Broj", "Primenjuje se od" }, 0);
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
				idKursneListe.setText("");
				idBanke.setText("");
				klDatum.setText("");
				klBroj.setText("");
				klDatPr.setText("");
				return;
			}
			KursnaListaTableModel tableModel =  (KursnaListaTableModel) tblGrid.getModel();
			String id_liste = (String) tableModel.getValueAt(index, 0);
			String id_banke = (String) tableModel.getValueAt(index, 1);
			String datum = (String) tableModel.getValueAt(index, 2);
			String broj = (String) tableModel.getValueAt(index, 3);
			String datpr = (String) tableModel.getValueAt(index, 4);
			idKursneListe.setText(id_liste);
			idBanke.setText(id_banke);
			klDatum.setText(datum);
			klBroj.setText(broj);
			klDatPr.setText(datpr);
		}
		
		public void removeRow() {
			if (JOptionPane.showConfirmDialog(this, "Da li ste sigurni?",
					"Pitanje", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			int index = tblGrid.getSelectedRow();
			KursnaListaTableModel tableModel = (KursnaListaTableModel) tblGrid.getModel();
			if (index == -1) // Ako nema selektovanog reda (tabela prazna)
				return; // izlazak
			// kada obrisemo tekuci red, selektovacemo sledeci (newindex):
			int newIndex = index;
			// sem ako se obrise poslednji red, tada selektujemo prethodni
			if (index == tableModel.getRowCount() - 1)
				newIndex--;
			try {
				KursnaListaTableModel kltm = (KursnaListaTableModel) tblGrid.getModel();
				kltm.deleteRow(index);
				if (tableModel.getRowCount() > 0)
					tblGrid.setRowSelectionInterval(newIndex, newIndex);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void addRow() {
			String id_liste = idKursneListe.getText().trim();
			String id_banke = idBanke.getText().trim();
			String datum = klDatum.getText().trim();
			String broj = klBroj.getText().trim();
			String datpr = klDatPr.getText().trim();
			try {
				KursnaListaTableModel dtm = (KursnaListaTableModel) tblGrid.getModel();
				int index = dtm.insertRow(id_liste, id_banke, datum, broj, datpr);
				tblGrid.setRowSelectionInterval(index, index);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void editRow() {
			String id_liste = idKursneListe.getText().trim();
			String id_banke = idBanke.getText().trim();
			String datum = klDatum.getText().trim();
			String broj = klBroj.getText().trim();
			String datpr = klDatPr.getText().trim();
			int index = tblGrid.getSelectedRow();
			String oldId = (String) tblGrid.getValueAt(index, 0); 
			try {
				KursnaListaTableModel dtm = (KursnaListaTableModel) tblGrid.getModel();
				dtm.updateRow(index, id_liste, id_banke, datum, broj, datpr, oldId);
				tblGrid.setRowSelectionInterval(index, index);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void search() {
			Object[] collectionOfFields = { idKursneListe, idBanke, klDatum, klBroj, klDatPr};
			ArrayList<String> values = new ArrayList<String>();
			for (Object field : collectionOfFields) {
					values.add(((JTextField) field).getText().trim());
			}
			KursnaListaTableModel kltm = (KursnaListaTableModel) tblGrid.getModel();
			try {
				kltm.searchRow(values);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Greska",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	

}
