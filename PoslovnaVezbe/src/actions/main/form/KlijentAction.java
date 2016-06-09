package actions.main.form;

import gui.standard.form.KlijentForm;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;


public class KlijentAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	
	public KlijentAction() {
		/*KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlDKeyStroke);*/
		putValue(SHORT_DESCRIPTION, "Klijent");
		putValue(NAME, "Klijent");
	}
	//
	@Override
	public void actionPerformed(ActionEvent arg0) {	
		KlijentForm form = new KlijentForm();
		form.setVisible(true);
	}
}
