package flatSpace.temporaryFrontend;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import flatSpace.Controller.StartMenuController;
import flatSpace.Controller.TimeController;
import flatSpace.Controller.TO.TOGame;

//TODO
public class GameMenuView extends JFrame{
	JTextField gameName = new JTextField();
	JButton update;
	JLabel error = new JLabel();
	StartMenu parent;
	JMenuBar menuBar = new JMenuBar();
	JMenu mapsMenu = new JMenu("Maps");
	JMenuItem systemMap = new JMenuItem("System");
	JMenuItem flatMap = new JMenuItem("2D Space");
	List<SystemView> systemViews;
	JButton speedUp = new JButton("<");
	JButton speedDown = new JButton(">");
	JLabel speedVal = new JLabel(Integer.toString(TimeController.getSpeed()));
	JButton pause = new JButton("||");
	TimeControlPanel tcp = new TimeControlPanel(this);
	private FlatView flatView;
	
	
	public GameMenuView(TOGame game) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		menuBar.add(mapsMenu);
		mapsMenu.add(systemMap);
		mapsMenu.add(flatMap);
		refreshLayout();
		menuListeners();
	}
	
	public void refreshLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(error)
						.addComponent(tcp)
				)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(error)
				.addComponent(tcp)
		);
		pack();
		
		tcp.setMaximumSize(tcp.getSize());
		tcp.setMinimumSize(tcp.getSize());
	}
	
//	public void newGame() {
//		TOGame newGame = new TOGame().setGameName(gameName.getText());
//		StartMenuController.createNewGame(newGame);
//		parent.loggedIn();
//		dispose();
//	}
//	public void updateGame(TOGame game) {
//		
//	}
	
	public void menuListeners() {
		systemMap.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				systemView();
			}
		});
		flatMap.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				flatView();
			}
		});
	}

	public List<SystemView> getSystemViews() {
		if (systemViews==null) {
			systemViews = new ArrayList<>();
		}
		return systemViews;
	}
	
	private void systemView() {
		SystemView s = new SystemView(this);
		s.setVisible(true);
		getSystemViews().add(s);
	}
	private void flatView() {
		if (flatView==null) {
			flatView = new FlatView(this);
		}
		flatView.setVisible(true);
	}

	public FlatView getFlatView() {
		return flatView;
	}
}
