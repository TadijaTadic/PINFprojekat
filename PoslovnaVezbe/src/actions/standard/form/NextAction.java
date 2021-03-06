package actions.standard.form;

import gui.standard.form.AbstractForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class NextAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private AbstractForm form;

	public NextAction(AbstractForm form) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/next.gif")));
		putValue(SHORT_DESCRIPTION, "Sledeci");
		this.form=form;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		form.goNext();
	}
}
