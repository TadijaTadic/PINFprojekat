package actions.standard.form;

import gui.standard.form.RacuniPravnihLicaForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.xml.parsers.ParserConfigurationException;

import model.RacuniPravnihLicaTableModel;


public class ExportIzvodaAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	private RacuniPravnihLicaForm form;
	
	public ExportIzvodaAction(RacuniPravnihLicaForm form) {
		putValue(SHORT_DESCRIPTION, "Export izvoda");
		putValue(NAME, "Export izvoda");
		this.form = form;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		RacuniPravnihLicaTableModel tm =  (RacuniPravnihLicaTableModel) form.getTblGrid().getModel();
		try {
			tm.exportIzvoda(form.getTblGrid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
