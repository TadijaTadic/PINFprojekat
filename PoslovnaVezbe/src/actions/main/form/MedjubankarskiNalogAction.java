package actions.main.form;

import gui.standard.form.MedjubankarskiNalogForm;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;


public class MedjubankarskiNalogAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	
	public MedjubankarskiNalogAction() {
		//KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK);
		//putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Medjubankarski Nalog");
		putValue(NAME, "Medjubankarski Nalog");
	}
	//
	public void actionPerformed(ActionEvent arg0) {	
		MedjubankarskiNalogForm form = new MedjubankarskiNalogForm();
		form.setVisible(true);
	}
}


