package pattern;

import gui.standard.form.KlijentForm;
import javax.swing.JDialog;
import gui.standard.form.AbstractForm;
import gui.standard.form.BankaForm;
import gui.standard.form.DrzavaForm;

public class EditState implements State {

	@Override
	public void doAction(AbstractForm form) {
		form.sync();
		form.getBtnDelete().getAction().setEnabled(true);
		form.getBtnFirst().getAction().setEnabled(true);
		form.getBtnLast().getAction().setEnabled(true);
		form.getBtnNext().getAction().setEnabled(true);
		form.getBtnPickup().getAction().setEnabled(true);
		form.getBtnNextForm().getAction().setEnabled(true);
		form.getBtnPrevious().getAction().setEnabled(true);
		form.getBtnRefresh().getAction().setEnabled(true);
		form.getBtnSearch().getAction().setEnabled(true);
		form.getBtnAdd().getAction().setEnabled(true);
		
		if (form instanceof DrzavaForm) {
			DrzavaForm df = (DrzavaForm) form;
			df.getContext().setState(this);
			
		}
		
		if (form instanceof BankaForm) {
			BankaForm bf = (BankaForm) form;
			bf.getContext().setState(this);
		}

		if (form instanceof KlijentForm) {
			KlijentForm kf = (KlijentForm) form;
			kf.getContext().setState(this);
		}
	}

}
