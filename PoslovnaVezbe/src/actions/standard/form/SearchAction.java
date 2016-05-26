package actions.standard.form;

import gui.standard.form.AbstractForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class SearchAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private AbstractForm form;

	public SearchAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/search.gif")));
		putValue(SHORT_DESCRIPTION, "Pretraga");
		this.form=(AbstractForm) standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AbstractForm.serachState.doAction(form);
	}
}
