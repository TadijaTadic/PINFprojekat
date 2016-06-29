package actions.standard.form;

import gui.standard.form.AbstractForm;
import gui.standard.form.BankaForm;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import database.DBConnection;


public class SpisakAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private AbstractForm form;

	
	public SpisakAction(AbstractForm form) {
		putValue(SHORT_DESCRIPTION, "Računi banke");
		putValue(NAME, "Računi banke");
		this.form = form;

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (form instanceof BankaForm) {
			try {
			      System.out.println(getClass().getResource("/reports/SpisakRacuna.jasper"));
			      String banka = ((BankaForm) form).getTfIdBanke().getText();
			      Map<String,Object> params = new HashMap<String,Object>(1);
			      params.put("banka",banka);
				  JasperPrint jp = JasperFillManager.fillReport(
				  getClass().getResource("/reports/SpisakRacuna.jasper").openStream(),
				  params, DBConnection.getConnection());
				  JasperViewer.viewReport(jp, false);
				} catch (Exception ex) {
				  ex.printStackTrace();
			}
		}			
	}
	
}