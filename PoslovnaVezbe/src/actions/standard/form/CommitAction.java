package actions.standard.form;

import gui.standard.form.AbstractForm;


import gui.standard.form.NalogZaUplatuForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import pattern.AddState;
import pattern.Context;
import pattern.EditState;
import pattern.SearchState;

public class CommitAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private AbstractForm form;
	
	public CommitAction(AbstractForm form) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/commit.gif")));
		putValue(SHORT_DESCRIPTION, "Commit");
		this.form=form;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Context context = form.getContext();
		if (context.getState() instanceof EditState) {
			if (form.getTblGrid().getSelectedRow() == -1)
				return;
			form.editRow();
		} else if (context.getState() instanceof AddState) {
			form.addRow();
		} else if (context.getState() instanceof SearchState) {
			form.search();
			AbstractForm.editState.doAction(form);
		}
		
	}
}

