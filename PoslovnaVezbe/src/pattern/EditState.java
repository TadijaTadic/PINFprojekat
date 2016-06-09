package pattern;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.KlijentForm;
import gui.standard.form.NaseljenoMestoStandardForm;

import javax.swing.JDialog;

public class EditState implements State {

	@Override
	public void doAction(JDialog sf) {
		if (sf instanceof DrzavaStandardForm) {
			DrzavaStandardForm dsf = (DrzavaStandardForm) sf;
			dsf.getContext().setState(this);
			dsf.sync();
			dsf.getBtnDelete().getAction().setEnabled(true);
			dsf.getBtnFirst().getAction().setEnabled(true);
			dsf.getBtnLast().getAction().setEnabled(true);
			dsf.getBtnNext().getAction().setEnabled(true);
			dsf.getBtnPickup().getAction().setEnabled(true);
			dsf.getBtnNextForm().getAction().setEnabled(true);
			dsf.getBtnPrevious().getAction().setEnabled(true);
			dsf.getBtnRefresh().getAction().setEnabled(true);
			dsf.getBtnSearch().getAction().setEnabled(true);
			dsf.getBtnAdd().getAction().setEnabled(true);
		}
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
		else if (sf instanceof KlijentForm) {
			KlijentForm kf = (KlijentForm) sf;
			kf.getContext().setState(this);
			kf.sync();
			kf.getBtnDelete().getAction().setEnabled(true);
			kf.getBtnFirst().getAction().setEnabled(true);
			kf.getBtnLast().getAction().setEnabled(true);
			kf.getBtnNext().getAction().setEnabled(true);
			kf.getBtnNextForm().getAction().setEnabled(true);
			kf.getBtnPrevious().getAction().setEnabled(true);
			kf.getBtnRefresh().getAction().setEnabled(true);
			kf.getBtnSearch().getAction().setEnabled(true);
			kf.getBtnAdd().getAction().setEnabled(true);
		}
	}

}
