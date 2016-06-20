package actions.main.form;


import gui.standard.form.PravnoLiceForm;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;


public class PravnoLiceAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	
	public PravnoLiceAction() {
		//KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK);
		//putValue(ACCELERATOR_KEY,ctrlDKeyStroke);
		putValue(SHORT_DESCRIPTION, "Pravno Lice");
		putValue(NAME, "Pravno Lice");
	}
	//
	public void actionPerformed(ActionEvent arg0) {	
		PravnoLiceForm form = new PravnoLiceForm();
		form.setVisible(true);
	}
}


