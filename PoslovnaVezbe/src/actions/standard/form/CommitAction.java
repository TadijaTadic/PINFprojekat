package actions.standard.form;


import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import pattern.AddState;
import pattern.Context;
import pattern.EditState;
import pattern.SearchState;



public class CommitAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	
	public CommitAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/commit.gif")));
		putValue(SHORT_DESCRIPTION, "Commit");
		this.standardForm=standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (standardForm instanceof DrzavaStandardForm) {
			DrzavaStandardForm dsf = (DrzavaStandardForm) standardForm;
			Context context = dsf.getContext();
			if (context.getState() instanceof EditState) {
				if (dsf.getTblGrid().getSelectedRow() == -1)
					return;
				dsf.editRow();
			}
			else if (context.getState() instanceof AddState) {
				dsf.addRow();
			}
			else if (context.getState() instanceof SearchState) {
				dsf.search();
				DrzavaStandardForm.editState.doAction(dsf);
			}
		}
		else if (standardForm instanceof NaseljenoMestoStandardForm) {
			NaseljenoMestoStandardForm nmsf = (NaseljenoMestoStandardForm) standardForm;
			Context context = nmsf.getContext();
			if (context.getState() instanceof EditState) {
				if (nmsf.getTblGrid().getSelectedRow() == -1)
					return;
				nmsf.editRow();
			}
			else if (context.getState() instanceof AddState) {
				nmsf.addRow();
			}
			else if (context.getState() instanceof SearchState) {
				nmsf.search();
				NaseljenoMestoStandardForm.editState.doAction(nmsf);
			}
		}
	}
}

