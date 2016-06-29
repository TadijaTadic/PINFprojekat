package actions.standard.form;

import gui.standard.form.AbstractForm;
import gui.standard.form.BankaForm;
import gui.standard.form.RacuniPravnihLicaForm;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import database.DBConnection;


public class StanjeZaPeriodAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private RacuniPravnihLicaForm form;

	
	public StanjeZaPeriodAction(RacuniPravnihLicaForm form) {
		putValue(SHORT_DESCRIPTION, "Izvod za period");
		putValue(NAME, "Izvod za period");
		this.form = form;

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		form.stanjeZaPeriod();	
	}
	
}