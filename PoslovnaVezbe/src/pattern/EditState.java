package pattern;

import gui.standard.form.AbstractForm;
import gui.standard.form.DrzavaForm;

public class EditState implements State {

	@Override
	public void doAction(AbstractForm form) {
		if (form instanceof DrzavaForm) {
			DrzavaForm df = (DrzavaForm) form;
			df.getContext().setState(this);
			df.sync();
			df.getBtnDelete().getAction().setEnabled(true);
			df.getBtnFirst().getAction().setEnabled(true);
			df.getBtnLast().getAction().setEnabled(true);
			df.getBtnNext().getAction().setEnabled(true);
			df.getBtnPickup().getAction().setEnabled(true);
			df.getBtnNextForm().getAction().setEnabled(true);
			df.getBtnPrevious().getAction().setEnabled(true);
			df.getBtnRefresh().getAction().setEnabled(true);
			df.getBtnSearch().getAction().setEnabled(true);
			df.getBtnAdd().getAction().setEnabled(true);
		}
		/*
		else if (sf instanceof NaseljenoMestoStandardForm) {
			NaseljenoMestoStandardForm nmsf = (NaseljenoMestoStandardForm) sf;
			nmsf.getContext().setState(this);
			nmsf.sync();
			nmsf.getBtnDelete().getAction().setEnabled(true);
			nmsf.getBtnFirst().getAction().setEnabled(true);
			nmsf.getBtnLast().getAction().setEnabled(true);
			nmsf.getBtnNext().getAction().setEnabled(true);
			nmsf.getBtnNextForm().getAction().setEnabled(true);
			nmsf.getBtnPrevious().getAction().setEnabled(true);
			nmsf.getBtnRefresh().getAction().setEnabled(true);
			nmsf.getBtnSearch().getAction().setEnabled(true);
			nmsf.getBtnAdd().getAction().setEnabled(true);
		}
		*/
	}

}
