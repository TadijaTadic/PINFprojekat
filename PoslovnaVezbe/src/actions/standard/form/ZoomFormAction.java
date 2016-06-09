package actions.standard.form;

import gui.standard.form.AbstractForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;


public class ZoomFormAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private AbstractForm form;

	
	public ZoomFormAction(AbstractForm form) {
		putValue(SHORT_DESCRIPTION, "Zoom");
		putValue(NAME, "...");
		this.form = form;

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		/*if(form instanceof NaseljenoMestoStandardForm) {
			((NaseljenoMestoStandardForm) form).zoom();
		}*/
	}
	
}
