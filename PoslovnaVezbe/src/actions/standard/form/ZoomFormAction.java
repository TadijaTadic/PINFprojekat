package actions.standard.form;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;


public class ZoomFormAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	
	public ZoomFormAction(JDialog sf) {
		putValue(SHORT_DESCRIPTION, "Zoom");
		putValue(NAME, "...");
		this.standardForm = sf;

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(standardForm instanceof NaseljenoMestoStandardForm) {
			((NaseljenoMestoStandardForm) standardForm).zoom();
		}
	}
	
}
