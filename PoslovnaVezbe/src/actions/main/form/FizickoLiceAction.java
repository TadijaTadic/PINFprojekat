package actions.main.form;

import gui.standard.form.FizickoLiceForm;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class FizickoLiceAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public FizickoLiceAction() {
		/*KeyStroke ctrlBKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK);
		putValue(ACCELERATOR_KEY,ctrlBKeyStroke);*/
		putValue(SHORT_DESCRIPTION, "Fizicko lice");
		putValue(NAME, "Fizicko lice");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		FizickoLiceForm form = new FizickoLiceForm();
		form.setVisible(true);
	}

}
