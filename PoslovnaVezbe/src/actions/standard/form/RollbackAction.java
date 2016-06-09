
package actions.standard.form;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.KlijentForm;
import gui.standard.form.NaseljenoMestoStandardForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import pattern.AddState;
import pattern.SearchState;
import pattern.State;

public class RollbackAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	public RollbackAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/remove.gif")));
		putValue(SHORT_DESCRIPTION, "Poništi");
		this.standardForm=standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (standardForm instanceof DrzavaStandardForm) {
			DrzavaStandardForm dsf = (DrzavaStandardForm) standardForm;
			State state = dsf.getContext().getState();
			if (state instanceof AddState) {
				DrzavaStandardForm.editState.doAction(dsf);
			}
			if (state instanceof SearchState)
				DrzavaStandardForm.editState.doAction(dsf);
		}
		else if (standardForm instanceof NaseljenoMestoStandardForm) {
			NaseljenoMestoStandardForm nmsf = (NaseljenoMestoStandardForm) standardForm;
			State state = nmsf.getContext().getState();
			if (state instanceof AddState) {
				NaseljenoMestoStandardForm.editState.doAction(nmsf);
			}
			if (state instanceof SearchState)
				NaseljenoMestoStandardForm.editState.doAction(nmsf);
		}
		
		else if (standardForm instanceof KlijentForm) {
			KlijentForm kf = (KlijentForm) standardForm;
			State state = kf.getContext().getState();
			if (state instanceof AddState) {
				KlijentForm.editState.doAction(kf);
			}
			if (state instanceof SearchState)
				KlijentForm.editState.doAction(kf);
		}
				
	}
}
