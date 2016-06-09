package gui.standard.form;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class DnevnoStanjeForm extends AbstractForm {
	
	private JTextField tfBrIzvoda = new JTextField(20);
	private JTextField tfIDRacuna = new JTextField(20);
	private JTextField tfDatPrometa = new JTextField(10);
	private JTextField tfPretStanje = new JTextField(20);
	private JTextField tfNovoStanje= new JTextField(20);
	private JTextField tfPromKor = new JTextField(20);
	private JTextField tfPromTer= new JTextField(20);
	
	public DnevnoStanjeForm() {
		super();
		setTitle("Dnevno stanje");
		JLabel lblBrIzvoda = new JLabel ("Broj izvoda:");
		JLabel lblIDRacuna = new JLabel("ID računa:");
		JLabel lblDatPrometa = new JLabel ("Datum računa:");
		JLabel lblPretStanje = new JLabel("Prethodno stanje:");
		JLabel lblNovoStanje = new JLabel ("Novo stanje:");
		JLabel lblPromKor = new JLabel("Promet u korist:");
		JLabel lblPromTer = new JLabel ("Promet na teret:");

		/*dataPanel.add(lblBrIzvoda);
		dataPanel.add(tfSifra,"wrap");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv);
		bottomPanel.add(dataPanel);*/
	}

}
