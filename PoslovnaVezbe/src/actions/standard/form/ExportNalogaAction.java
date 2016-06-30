package actions.standard.form;

import gui.standard.form.MedjubankarskiNalogForm;
import gui.standard.form.RacuniPravnihLicaForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.xml.parsers.ParserConfigurationException;

import model.MedjubankarskiNalogTableModel;
import model.RacuniPravnihLicaTableModel;


public class ExportNalogaAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	private MedjubankarskiNalogForm form;
	
	public ExportNalogaAction(MedjubankarskiNalogForm form) {
		putValue(SHORT_DESCRIPTION, "Export naloga");
		putValue(NAME, "Export naloga");
		this.form = form;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		MedjubankarskiNalogTableModel tm =  (MedjubankarskiNalogTableModel) form.getTblGrid().getModel();
		try {
			tm.exportNaloga(form.getTblGrid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
