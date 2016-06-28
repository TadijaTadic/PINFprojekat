package actions.main.form;

import gui.standard.form.BankaForm;
import gui.standard.form.NalogZaUplatuForm;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class NalogZaUplatuAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public NalogZaUplatuAction() {
		KeyStroke ctrlBKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlBKeyStroke);
		putValue(SHORT_DESCRIPTION, "Nalozi za uplatu");
		putValue(NAME, "Nalozi za uplatu");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		NalogZaUplatuForm form = new NalogZaUplatuForm();
		form.setVisible(true);
	}

}
