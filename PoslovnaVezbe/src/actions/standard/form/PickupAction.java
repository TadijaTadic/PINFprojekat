package actions.standard.form;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;
import util.Column;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import util.ColumnList;

public class PickupAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	
	public PickupAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/zoom-pickup.gif")));
		putValue(SHORT_DESCRIPTION, "Zoom pickup");
		this.standardForm = standardForm;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (standardForm instanceof DrzavaStandardForm) {
			((DrzavaStandardForm) standardForm).pickup();				
		}
	}
	
}
