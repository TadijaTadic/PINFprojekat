package pattern;

import gui.standard.form.AbstractForm;
import gui.standard.form.DrzavaForm;


public class AddState implements State {

	@Override
	public void doAction(AbstractForm form) {
		if (form instanceof DrzavaForm) {
			DrzavaForm df = (DrzavaForm) form;
			df.getContext().setState(this);
			df.getTfSifra().setText("");
			df.getTfNaziv().setText("");
			df.getTfSifra().requestFocus();
			df.getBtnDelete().getAction().setEnabled(false);
			df.getBtnFirst().getAction().setEnabled(false);
			df.getBtnLast().getAction().setEnabled(false);
			df.getBtnNext().getAction().setEnabled(false);
			df.getBtnPickup().getAction().setEnabled(false);
			df.getBtnNextForm().getAction().setEnabled(false);
			df.getBtnPrevious().getAction().setEnabled(false);
			df.getBtnRefresh().getAction().setEnabled(false);
			df.getBtnSearch().getAction().setEnabled(false);
			df.getTblGrid().clearSelection();
		}
		/*
		else if (form instanceof NaseljenoMestoStandardForm) {
			NaseljenoMestoStandardForm nmsf = (NaseljenoMestoStandardForm) form;
			nmsf.getContext().setState(this);
			nmsf.getTfSifra().setText("");
			nmsf.getTfNaziv().setText("");
			nmsf.getTfSifraDrzave().setText("");
			nmsf.getTfNazivDrzave().setText("");
			nmsf.getTfSifra().requestFocus();
			nmsf.getBtnDelete().getAction().setEnabled(false);
			nmsf.getBtnFirst().getAction().setEnabled(false);
			nmsf.getBtnLast().getAction().setEnabled(false);
			nmsf.getBtnNext().getAction().setEnabled(false);
			nmsf.getBtnPickup().getAction().setEnabled(false);
			nmsf.getBtnNextForm().getAction().setEnabled(false);
			nmsf.getBtnPrevious().getAction().setEnabled(false);
			nmsf.getBtnRefresh().getAction().setEnabled(false);
			nmsf.getBtnSearch().getAction().setEnabled(false);
			nmsf.getTblGrid().clearSelection();
		}*/
	}

}
