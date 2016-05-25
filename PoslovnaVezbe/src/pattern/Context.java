package pattern;

import javax.swing.JDialog;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

public class Context {
	
	private State state;
	
	public Context(JDialog form) {
		if (form instanceof DrzavaStandardForm)
			state = DrzavaStandardForm.editState;
		else if (form instanceof NaseljenoMestoStandardForm)
			state = NaseljenoMestoStandardForm.editState;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}

}
