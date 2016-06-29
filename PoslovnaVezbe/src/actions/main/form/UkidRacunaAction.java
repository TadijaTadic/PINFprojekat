package actions.main.form;

import gui.standard.form.AbstractForm;
import gui.standard.form.RacuniPravnihLicaForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class UkidRacunaAction extends AbstractAction {

private static final long serialVersionUID = 1L;
	
	private AbstractForm form;
    
    
    public UkidRacunaAction(AbstractForm form) {
		
		putValue(NAME, "Ukini racun");
		this.form = form;

	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		((RacuniPravnihLicaForm) form).UkiniRacun();
	}

}
