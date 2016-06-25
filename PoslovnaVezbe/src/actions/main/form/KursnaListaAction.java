package actions.main.form;

import gui.standard.form.KursnaListaForm;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;


public class KursnaListaAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	
	public KursnaListaAction() {
		//KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK);
		//putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Kursna Lista");
		putValue(NAME, "Kursna Lista");
	}
	//
	public void actionPerformed(ActionEvent arg0) {	
		KursnaListaForm form = new KursnaListaForm();
		form.setVisible(true);
	}
}


