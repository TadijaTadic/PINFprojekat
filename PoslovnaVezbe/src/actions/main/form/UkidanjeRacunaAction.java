package actions.main.form;

import gui.standard.form.UkidanjeRacunaForm;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class UkidanjeRacunaAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public UkidanjeRacunaAction() {
		KeyStroke ctrlVKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlVKeyStroke);
		putValue(SHORT_DESCRIPTION, "Ukinuti racuni");
		putValue(NAME, "Ukinuti racuni");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		UkidanjeRacunaForm form = new UkidanjeRacunaForm();
		form.setVisible(true);
	}

}
