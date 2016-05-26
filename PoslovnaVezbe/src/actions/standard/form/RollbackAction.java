package actions.standard.form;

import gui.standard.form.AbstractForm;
import gui.standard.form.DrzavaForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import pattern.AddState;
import pattern.SearchState;
import pattern.State;

public class RollbackAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private AbstractForm form;

	public RollbackAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/remove.gif")));
		putValue(SHORT_DESCRIPTION, "Poništi");
		this.form=(AbstractForm) standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		State state = form.getContext().getState();
		if (state instanceof AddState) {
			DrzavaForm.editState.doAction(form);
		}
		if (state instanceof SearchState)
			DrzavaForm.editState.doAction(form);
			
	}
}
