package pattern;

import gui.standard.form.KlijentForm;
import gui.standard.form.KursnaListaForm;
import gui.standard.form.MedjubankarskiNalogForm;
import gui.standard.form.NalogZaUplatuForm;
import gui.standard.form.NaseljenoMestoForm;
import gui.standard.form.PravnoLiceForm;
import gui.standard.form.RacuniPravnihLicaForm;
import gui.standard.form.ValuteForm;

import javax.swing.JDialog;

import gui.standard.form.AbstractForm;
import gui.standard.form.BankaForm;
import gui.standard.form.DrzavaForm;
import gui.standard.form.FizickoLiceForm;
import gui.standard.form.KursuValutiForm;

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
		
		if (form instanceof FizickoLiceForm) {
			FizickoLiceForm flf = (FizickoLiceForm) form;
			flf.getContext().setState(this);
		}
		if (form instanceof KursuValutiForm) {
			KursuValutiForm kvf = (KursuValutiForm) form;
			kvf.getContext().setState(this);
		}		
		if (form instanceof KursnaListaForm) {
			KursnaListaForm klf = (KursnaListaForm) form;
			klf.getContext().setState(this);
		}
		if (form instanceof MedjubankarskiNalogForm) {
			MedjubankarskiNalogForm mnf = (MedjubankarskiNalogForm) form;
			mnf.getContext().setState(this);
		}
		if (form instanceof NaseljenoMestoForm) {
			NaseljenoMestoForm nmf = (NaseljenoMestoForm) form;
			nmf.getContext().setState(this);
		}
		if (form instanceof PravnoLiceForm) {
			PravnoLiceForm plf = (PravnoLiceForm) form;
			plf.getContext().setState(this);
		}
		if (form instanceof RacuniPravnihLicaForm) {
			RacuniPravnihLicaForm rplf = (RacuniPravnihLicaForm) form;
			rplf.getContext().setState(this);
		}
		if (form instanceof ValuteForm) {
			ValuteForm vf = (ValuteForm) form;
			vf.getContext().setState(this);
		}
		if (form instanceof NalogZaUplatuForm) {
			NalogZaUplatuForm nzuf = (NalogZaUplatuForm) form;
			nzuf.getContext().setState(this);
		}
	}

}
