package actions.main.form;

import gui.standard.form.BankaForm;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class BankeAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public BankeAction() {
		KeyStroke ctrlBKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlBKeyStroke);
		putValue(SHORT_DESCRIPTION, "Banke");
		putValue(NAME, "Banke");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		BankaForm form = new BankaForm();
		form.setVisible(true);
	}

}
