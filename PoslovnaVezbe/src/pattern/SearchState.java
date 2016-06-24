package pattern;

import javax.swing.JCheckBox;
import javax.swing.JTextField;




import gui.standard.form.FizickoLiceForm;
import gui.standard.form.KlijentForm;
import gui.standard.form.AbstractForm;
import gui.standard.form.BankaForm;
import gui.standard.form.DrzavaForm;
import gui.standard.form.KursuValutiForm;

public class SearchState implements State {

	@Override
	public void doAction(AbstractForm form) {
		form.getBtnDelete().getAction().setEnabled(false);
		form.getBtnFirst().getAction().setEnabled(false);
		form.getBtnLast().getAction().setEnabled(false);
		form.getBtnNext().getAction().setEnabled(false);
		form.getBtnPickup().getAction().setEnabled(false);
		form.getBtnNextForm().getAction().setEnabled(false);
		form.getBtnPrevious().getAction().setEnabled(false);
		form.getBtnRefresh().getAction().setEnabled(false);
		form.getBtnAdd().getAction().setEnabled(false);
		form.getTblGrid().clearSelection();
		
		if (form instanceof DrzavaForm) {
			DrzavaForm df = (DrzavaForm) form;
			df.getContext().setState(this);
			df.getTfSifra().setText("");
			df.getTfNaziv().setText("");
			df.getTfSifra().requestFocus();
		}
		
		if (form instanceof BankaForm) {
			BankaForm bf = (BankaForm) form;
			bf.getContext().setState(this);
			bf.getTfIdBanke().requestFocus();
			for (Object field : bf.collectionOfFields) {
				if (field instanceof JTextField)
					((JTextField) field).setText("");
				else if (field instanceof JCheckBox)
					((JCheckBox) field).setSelected(false);
			}
		}
		
		if (form instanceof KlijentForm) {
			KlijentForm kf = (KlijentForm) form;
			kf.getContext().setState(this);
			kf.getTfIdKlijenta().setText("");
			kf.getTfAdresa().setText("");
			kf.getTfTelefon().setText("");
			kf.getTfEmail().setText("");
			kf.getTfIdKlijenta().requestFocus();
		}
		
		if (form instanceof FizickoLiceForm) {
			FizickoLiceForm flf = (FizickoLiceForm) form;
			flf.getContext().setState(this);
			flf.getTfJMBG().requestFocus();
			for (Object field : flf.collectionOfFields) 
				((JTextField) field).setText("");
		}
		
		if (form instanceof KursuValutiForm) {
			KursuValutiForm kvf = (KursuValutiForm) form;
			kvf.getContext().setState(this);
			kvf.gettfRedniBroj().requestFocus();
			for (Object field : kvf.collectionOfFields) 
				((JTextField) field).setText("");
		}
	}

}
