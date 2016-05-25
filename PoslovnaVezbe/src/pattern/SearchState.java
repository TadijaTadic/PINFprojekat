package pattern;

import javax.swing.JDialog;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

public class SearchState implements State {

	@Override
	public void doAction(JDialog sf) {
		if (sf instanceof DrzavaStandardForm) {
			DrzavaStandardForm dsf = (DrzavaStandardForm) sf;
			dsf.getContext().setState(this);
			dsf.getTfSifra().setText("");
			dsf.getTfNaziv().setText("");
			dsf.getTfSifra().requestFocus();
			dsf.getBtnDelete().getAction().setEnabled(false);
			dsf.getBtnFirst().getAction().setEnabled(false);
			dsf.getBtnLast().getAction().setEnabled(false);
			dsf.getBtnNext().getAction().setEnabled(false);
			dsf.getBtnPickup().getAction().setEnabled(false);
			dsf.getBtnNextForm().getAction().setEnabled(false);
			dsf.getBtnPrevious().getAction().setEnabled(false);
			dsf.getBtnRefresh().getAction().setEnabled(false);
			dsf.getBtnAdd().getAction().setEnabled(false);
			dsf.getTblGrid().clearSelection();
		}
		else if (sf instanceof NaseljenoMestoStandardForm) {
			NaseljenoMestoStandardForm nmsf = (NaseljenoMestoStandardForm) sf;
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
			nmsf.getBtnAdd().getAction().setEnabled(false);
			nmsf.getTblGrid().clearSelection();
		}
	}

}
