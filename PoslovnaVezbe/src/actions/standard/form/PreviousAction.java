package actions.standard.form;

import gui.standard.form.AbstractForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class PreviousAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private AbstractForm form;

	public PreviousAction(AbstractForm form) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/prev.gif")));
		putValue(SHORT_DESCRIPTION, "Prethodni");
		this.form=form;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		form.goPrevious();
	}
}
