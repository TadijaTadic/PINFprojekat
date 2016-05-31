package pattern;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import gui.standard.form.AbstractForm;
import gui.standard.form.BankaForm;
import gui.standard.form.DrzavaForm;

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
		
	}

}
