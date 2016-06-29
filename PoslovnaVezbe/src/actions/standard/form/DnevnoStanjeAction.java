package actions.standard.form;

import gui.standard.form.AbstractForm;
import gui.standard.form.DnevnoStanjeForm;
import gui.standard.form.RacuniPravnihLicaForm;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;



public class DnevnoStanjeAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private RacuniPravnihLicaForm form;

	
	public DnevnoStanjeAction(RacuniPravnihLicaForm form) {
		putValue(SHORT_DESCRIPTION, "Dnevno stanje");
		putValue(NAME, "Dnevno stanje");
		this.form = form;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		form.dnevnoStanje();
	}
	
}