package pattern;

import javax.swing.JDialog;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.KlijentForm;
import gui.standard.form.NaseljenoMestoStandardForm;

public class AddState implements State {

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
			dsf.getBtnSearch().getAction().setEnabled(false);
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
			nmsf.getBtnSearch().getAction().setEnabled(false);
			nmsf.getTblGrid().clearSelection();
		}
		else if (sf instanceof KlijentForm) {
			KlijentForm kf = (KlijentForm) sf;
			kf.getContext().setState(this);
			kf.getTfIdKlijenta().setText("");
			kf.getTfAdresa().setText("");
			kf.getTfTelefon().setText("");
			kf.getTfEmail().setText("");
			kf.getTfIdKlijenta().requestFocus();
			kf.getBtnDelete().getAction().setEnabled(false);
			kf.getBtnFirst().getAction().setEnabled(false);
			kf.getBtnLast().getAction().setEnabled(false);
			kf.getBtnNext().getAction().setEnabled(false);
			kf.getBtnPickup().getAction().setEnabled(false);
			kf.getBtnNextForm().getAction().setEnabled(false);
			kf.getBtnPrevious().getAction().setEnabled(false);
			kf.getBtnRefresh().getAction().setEnabled(false);
			kf.getBtnSearch().getAction().setEnabled(false);
			kf.getTblGrid().clearSelection();
		}
	}

}
