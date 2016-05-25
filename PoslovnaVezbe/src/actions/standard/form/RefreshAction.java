package actions.standard.form;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.DrzaveTableModel;
import model.NaseljenoMestoTableModel;

public class RefreshAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	public RefreshAction(JDialog sf) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/refresh.gif")));
		putValue(SHORT_DESCRIPTION, "Refresh");
		if (sf instanceof DrzavaStandardForm)
			this.standardForm = (DrzavaStandardForm) sf;
		else if (sf instanceof NaseljenoMestoStandardForm)
			this.standardForm = (NaseljenoMestoStandardForm) sf;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (standardForm instanceof DrzavaStandardForm) {
			DrzavaStandardForm dsf = (DrzavaStandardForm) standardForm;
			DrzaveTableModel dtm = (DrzaveTableModel) dsf.getTblGrid().getModel();
			try {
				dtm.open();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(dsf, e.getMessage(), "Greska",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (standardForm instanceof NaseljenoMestoStandardForm) {
			NaseljenoMestoStandardForm nmsf = (NaseljenoMestoStandardForm) standardForm;
			NaseljenoMestoTableModel nmtm = (NaseljenoMestoTableModel) nmsf.getTblGrid().getModel();
			try {
				nmtm.open();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(nmsf, e.getMessage(), "Greska",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
