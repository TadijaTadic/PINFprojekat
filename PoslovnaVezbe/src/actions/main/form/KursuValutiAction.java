package actions.main.form;

import gui.standard.form.FizickoLiceForm;
import gui.standard.form.KursuValutiForm;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class KursuValutiAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public KursuValutiAction() {
		/*KeyStroke ctrlBKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlBKeyStroke);*/
		putValue(SHORT_DESCRIPTION, "Kurs u valuti");
		putValue(NAME, "Kurs u valuti");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		KursuValutiForm form = new KursuValutiForm();
		form.setVisible(true);
	}

}
