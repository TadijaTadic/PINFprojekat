package actions.standard.form;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.KlijentForm;
import gui.standard.form.NaseljenoMestoStandardForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class AddAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	//kada se napravi genericka forma, staviti tu klasu umesto JDialog
	private JDialog standardForm;
	
	public AddAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/add.gif")));
		putValue(SHORT_DESCRIPTION, "Dodavanje");
		this.standardForm=standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (standardForm instanceof DrzavaStandardForm) {
			DrzavaStandardForm dsf = (DrzavaStandardForm) standardForm;
			DrzavaStandardForm.addState.doAction(dsf);			
		}
		else if (standardForm instanceof NaseljenoMestoStandardForm) {
			NaseljenoMestoStandardForm nmsf = (NaseljenoMestoStandardForm) standardForm;
			NaseljenoMestoStandardForm.addState.doAction(nmsf);
		}
		else if (standardForm instanceof KlijentForm) {
			KlijentForm kf = (KlijentForm) standardForm;
			KlijentForm.addState.doAction(kf);
		}
		
	}
}
