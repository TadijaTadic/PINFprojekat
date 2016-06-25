package pattern;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import gui.standard.form.FizickoLiceForm;
import gui.standard.form.KlijentForm;
import gui.standard.form.KursnaListaForm;
import gui.standard.form.MedjubankarskiNalogForm;
import gui.standard.form.NaseljenoMestoForm;
import gui.standard.form.PravnoLiceForm;
import gui.standard.form.AbstractForm;
import gui.standard.form.BankaForm;
import gui.standard.form.DrzavaForm;
import gui.standard.form.KursuValutiForm;
import gui.standard.form.RacuniPravnihLicaForm;
import gui.standard.form.ValuteForm;

public class AddState implements State {

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
		form.getBtnSearch().getAction().setEnabled(false);
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
		if (form instanceof KursnaListaForm) {
			KursnaListaForm klf = (KursnaListaForm) form;
			klf.getContext().setState(this);
			klf.getIdKursneListe().setText("");
			klf.getIdBanke().setText("");
			klf.getKlDatum().setText("");
			klf.getKlBroj().setText("");
			klf.getKlDatPr().setText("");
			klf.getIdKursneListe().requestFocus();
		}
		if (form instanceof MedjubankarskiNalogForm) {
			MedjubankarskiNalogForm mnf = (MedjubankarskiNalogForm) form;
			mnf.getContext().setState(this);
			mnf.getIdNaloga().requestFocus();
			for (Object field : mnf.collectionOfFields) {
				if (field instanceof JTextField)
					((JTextField) field).setText("");
				else if (field instanceof JCheckBox)
					((JCheckBox) field).setSelected(false);
			}
		}
		if(form instanceof NaseljenoMestoForm) {
			NaseljenoMestoForm nmf = (NaseljenoMestoForm) form;
			nmf.getContext().setState(this);
			nmf.getNmSifra().setText("");
			nmf.getDrSifra().setText("");
			nmf.getNmNaziv().setText("");
			nmf.getNmPTT().setText("");
			nmf.getNmSifra().requestFocus();
		}
		if(form instanceof PravnoLiceForm) {
			PravnoLiceForm plf = (PravnoLiceForm) form;
			plf.getContext().setState(this);
			plf.getIdKlijenta().setText("");
			plf.getPib().setText("");
			plf.getAdresa().setText("");
			plf.getTelefon().setText("");
			plf.getEmail().setText("");
			plf.getNaziv().setText("");
			plf.getWebadresa().setText("");
			plf.getFax().setText("");
			plf.getIme().setText("");
			plf.getPrezime().setText("");
			plf.getJmbg().setText("");
			plf.getIdKlijenta().requestFocus();
		}
		if (form instanceof RacuniPravnihLicaForm) {
			RacuniPravnihLicaForm rplf = (RacuniPravnihLicaForm) form;
			rplf.getContext().setState(this);
			rplf.getIdRacuna().requestFocus();
			for (Object field : rplf.collectionOfFields) {
				if (field instanceof JTextField)
					((JTextField) field).setText("");
				else if (field instanceof JCheckBox)
					((JCheckBox) field).setSelected(false);
			}
		}
		if (form instanceof ValuteForm) {
			ValuteForm vf = (ValuteForm) form;
			vf.getContext().setState(this);
			vf.getTfIdValute().requestFocus();
			for (Object field : vf.collectionOfFields) {
				if (field instanceof JTextField)
					((JTextField) field).setText("");
				else if (field instanceof JCheckBox)
					((JCheckBox) field).setSelected(false);
			}
			
		}
	}

}
