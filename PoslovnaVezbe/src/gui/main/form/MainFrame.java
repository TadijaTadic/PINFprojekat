package gui.main.form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import actions.main.form.BankeAction;
import actions.main.form.DrzaveAction;
import actions.main.form.FizickoLiceAction;
import actions.main.form.KursnaListaAction;
import actions.main.form.KursuValutiAction;
import actions.main.form.MedjubankarskiNalogAction;
import actions.main.form.NalogZaUplatuAction;
import actions.main.form.NaseljenoMestoAction;
import actions.main.form.PravnoLiceAction;
import actions.main.form.RacuniAction;
import actions.main.form.UkidanjeRacunaAction;
import actions.main.form.ValuteAction;
import database.DBConnection;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static MainFrame instance;
	private JMenuBar menuBar;


	public MainFrame(){

		setSize(new Dimension(800,600));
		setLocationRelativeTo(null);
		setTitle("Poslovna");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setUpMenu();
		



		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(MainFrame.getInstance(),
						"Da li ste sigurni?", "Pitanje",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					/*
					 * Zatvori konekciju
					 */
					DBConnection.close();
					System.exit(0);
				}
			}
		});
		
		setJMenuBar(menuBar);
		
		 
	}


	private void setUpMenu(){
		menuBar = new JMenuBar();

		JMenu orgSemaMenu = new JMenu("Organizaciona šema");
		orgSemaMenu.setMnemonic(KeyEvent.VK_O);
		
		JMenuItem drzaveMI = new JMenuItem(new DrzaveAction());
		orgSemaMenu.add(drzaveMI);
		JMenuItem mestoMI = new JMenuItem(new NaseljenoMestoAction());
		orgSemaMenu.add(mestoMI);
		
		orgSemaMenu.addSeparator();
		
		JMenuItem bankeMI = new JMenuItem(new BankeAction());
		orgSemaMenu.add(bankeMI);
		JMenuItem medjbanknalMI = new JMenuItem(new MedjubankarskiNalogAction());
		orgSemaMenu.add(medjbanknalMI);
		
		orgSemaMenu.addSeparator();
		
		JMenuItem racuniMI = new JMenuItem(new RacuniAction());
		orgSemaMenu.add(racuniMI);
		JMenuItem ukidanjeracunaMI = new JMenuItem(new UkidanjeRacunaAction());
		orgSemaMenu.add(ukidanjeracunaMI);
		
		orgSemaMenu.addSeparator();

		JMenuItem fizliceMI = new JMenuItem(new FizickoLiceAction());
		orgSemaMenu.add(fizliceMI);
		JMenuItem pravnoliceMI = new JMenuItem(new PravnoLiceAction());
		orgSemaMenu.add(pravnoliceMI);
		
		orgSemaMenu.addSeparator();
		
		JMenuItem kursnaListaMI = new JMenuItem(new KursnaListaAction());
		orgSemaMenu.add(kursnaListaMI);
		JMenuItem valuteMI = new JMenuItem(new ValuteAction());
		orgSemaMenu.add(valuteMI);
		JMenuItem uplateMI = new JMenuItem(new NalogZaUplatuAction());
		orgSemaMenu.add(uplateMI);
		JMenuItem kursuvalMI = new JMenuItem(new KursuValutiAction());
		orgSemaMenu.add(kursuvalMI);
		menuBar.add(orgSemaMenu);
		
	     this.setLayout(new BorderLayout());
	     
	     JPanel panelgore = new JPanel();
	     this.add(panelgore, BorderLayout.NORTH);
	     panelgore.setPreferredSize(new Dimension(200,200));
	     JPanel paneldole = new JPanel();
	     this.add(paneldole, BorderLayout.SOUTH);
	     paneldole.setPreferredSize(new Dimension(200,200));
	     JPanel panellevo = new JPanel();
	     this.add(panellevo, BorderLayout.WEST);
	     panellevo.setPreferredSize(new Dimension(200,200));
	     JPanel paneldesno = new JPanel();
	     this.add(paneldesno, BorderLayout.EAST);
	     paneldesno.setPreferredSize(new Dimension(200,200));
	     
	     
	     JPanel panelsredina = new JPanel();
	     this.add(panelsredina, BorderLayout.CENTER);
	     panelsredina.setPreferredSize(new Dimension(100,100));
	     panelsredina.setLayout(new GridLayout(4,3));

	     
	     JButton banka = new JButton(new BankeAction());
	     banka.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(banka);

	     JButton racun = new JButton(new RacuniAction());
	     racun.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(racun);
	     
	     JButton drzava = new JButton(new DrzaveAction());
	     drzava.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(drzava);
	     
	     JButton naseljenomesto = new JButton(new NaseljenoMestoAction());
	     naseljenomesto.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(naseljenomesto);
	     
	     
	     JButton fizlice = new JButton(new FizickoLiceAction());
	     fizlice.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(fizlice);
	     
	     JButton pravlice = new JButton(new PravnoLiceAction());
	     pravlice.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(pravlice);
	     
	     JButton kurslista = new JButton(new KursnaListaAction());
	     kurslista.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(kurslista);
	     
	     JButton valute = new JButton(new ValuteAction());
	     valute.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(valute);
	     
	     JButton nalozi = new JButton(new NalogZaUplatuAction());
	     nalozi.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(nalozi);
	     
	     JButton medj = new JButton(new MedjubankarskiNalogAction());
	     medj.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(medj);
	     
	     JButton racuni = new JButton(new RacuniAction());
	     racuni.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(racuni);
	     
	     JButton kursuval = new JButton(new KursuValutiAction());
	     kursuval.setPreferredSize(new Dimension(100,100));
	     panelsredina.add(kursuval);

	}



	
	
	
	public static MainFrame getInstance(){
		if (instance==null)
			instance=new MainFrame();
		return instance;

	}

}