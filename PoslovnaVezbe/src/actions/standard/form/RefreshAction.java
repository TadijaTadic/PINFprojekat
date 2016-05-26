package actions.standard.form;

import gui.standard.form.AbstractForm;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.DrzaveTableModel;
import model.NaseljenoMestoTableModel;

public class RefreshAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private AbstractForm form;

	public RefreshAction(JDialog sf) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/refresh.gif")));
		putValue(SHORT_DESCRIPTION, "Refresh");
		this.form = (AbstractForm) sf;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {	
		DefaultTableModel dtm = (DefaultTableModel) form.getTblGrid().getModel();
		try {
			if (dtm instanceof DrzaveTableModel) 
				((DrzaveTableModel) dtm).open();
			else if (dtm instanceof NaseljenoMestoTableModel)
				((NaseljenoMestoTableModel) dtm).open();
			//ovde dodavati refresh za ostale forme
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(form, e.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
