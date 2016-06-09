package actions.standard.form;

import gui.standard.form.AbstractForm;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import pattern.AddState;
import pattern.SearchState;
import pattern.State;

public class RollbackAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private AbstractForm form;

	public RollbackAction(AbstractForm form) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/remove.gif")));
		putValue(SHORT_DESCRIPTION, "Poništi");
		this.form=form;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		State state = form.getContext().getState();
		if (state instanceof AddState) {
			AbstractForm.editState.doAction(form);
		}
		if (state instanceof SearchState)
			AbstractForm.editState.doAction(form);
	}
}
