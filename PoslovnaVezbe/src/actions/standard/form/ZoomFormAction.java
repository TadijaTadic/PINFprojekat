package actions.standard.form;

import gui.standard.form.AbstractForm;
import gui.standard.form.FizickoLiceForm;
import gui.standard.form.KursnaListaForm;
import gui.standard.form.NalogZaUplatuForm;
import gui.standard.form.NaseljenoMestoForm;
import gui.standard.form.RacuniPravnihLicaForm;
import gui.standard.form.ValuteForm;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JDialog;


public class ZoomFormAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private AbstractForm form;

	
	public ZoomFormAction(AbstractForm form) {
		putValue(SHORT_DESCRIPTION, "Zoom");
		putValue(NAME, "...");
		this.form = form;

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(form instanceof RacuniPravnihLicaForm) {
			if (((RacuniPravnihLicaForm) form).getBtnZoom().isFocusOwner())
				try {
					((RacuniPravnihLicaForm) form).zoomKlijenta();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if (((RacuniPravnihLicaForm) form).getBtnZoomBanka().isFocusOwner())
				try {
					((RacuniPravnihLicaForm) form).zoomBanka();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if (((RacuniPravnihLicaForm) form).getBtnZoomValuta().isFocusOwner())
				try {
					((RacuniPravnihLicaForm) form).zoomValuta();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		if(form instanceof KursnaListaForm) {
			((KursnaListaForm) form).zoom();
		}
		if(form instanceof NaseljenoMestoForm) {
			((NaseljenoMestoForm) form).zoom();
		}
		if(form instanceof ValuteForm) {
			((ValuteForm) form).zoom();
		}
		if (form instanceof NalogZaUplatuForm)
			if (((NalogZaUplatuForm) form).getBtnZoomBrIzvoda().isFocusOwner())
				((NalogZaUplatuForm) form).zoomBrIzvodaDnevonogStanja();
			else if (((NalogZaUplatuForm) form).getBtnZoomSifraMesta().isFocusOwner())
					((NalogZaUplatuForm) form).zoomSifraMesta();
	}
	
}
