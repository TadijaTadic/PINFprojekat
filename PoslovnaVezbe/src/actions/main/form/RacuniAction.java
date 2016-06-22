package actions.main.form;

import gui.standard.form.RacuniPravnihLicaForm;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class RacuniAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public RacuniAction() {
		KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlRKeyStroke);
		putValue(SHORT_DESCRIPTION, "Racuni");
		putValue(NAME, "Racuni");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		RacuniPravnihLicaForm form = new RacuniPravnihLicaForm();
		form.setVisible(true);
	}

}
