package actions.main.form;

import gui.standard.form.ValuteForm;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class ValuteAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public ValuteAction() {
		KeyStroke ctrlVKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlVKeyStroke);
		putValue(SHORT_DESCRIPTION, "Valute");
		putValue(NAME, "Valute");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ValuteForm form = new ValuteForm();
		form.setVisible(true);
	}

}
