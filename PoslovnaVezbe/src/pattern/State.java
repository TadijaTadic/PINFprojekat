package pattern;

import javax.swing.JDialog;

import gui.standard.form.DrzavaStandardForm;

public interface State {
	
	public void doAction(JDialog sf);
	
}
