package pattern;

import javax.swing.JDialog;

import gui.standard.form.AbstractForm;
import gui.standard.form.DrzavaForm;

public class Context {
	
	private State state;
	
	public Context(AbstractForm form) {
			state = AbstractForm.editState;
		/*else if (form instanceof NaseljenoMestoStandardForm)
			state = NaseljenoMestoStandardForm.editState;*/
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}

}

