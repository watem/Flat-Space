package flatSpace.temporaryFrontend;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import flatSpace.Controller.StartMenuController;
import flatSpace.Controller.TO.TOGame;
import flatSpace.Controller.TO.TONationSettings;
import flatSpace.backendData.game.FlatSpace;

//TODO
public class GameSettingsView extends JDialog{
	JLabel gameNameLabel = new JLabel("Game Name:");
	JComboBox<String> startSys = new JComboBox<>(FlatSpace.startingSys);
	JLabel startSysLabel = new JLabel("Starting Sys");
	JTextField gameName = new JTextField(10);
	JButton update;
	JLabel error = new JLabel();
	StartMenu parent;
	
	
	public GameSettingsView(TOGame game) {
		setTitle("Flat-Space: "+game.getGameName());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		update = new JButton("update");
		update.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateGame(game);
			}
		});
		
	}
	public GameSettingsView(StartMenu parent) {
		this.parent = parent;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Create New Game:");
		update = new JButton("create");
		update.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newGame();
			}
		});
		refreshLayout();
		
		
		
		
	}
	
	public void refreshLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(error)
				.addComponent(update)
				.addGroup(layout.createSequentialGroup()
						.addComponent(gameNameLabel)
						.addComponent(gameName)
				)
				.addComponent(startSys)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(error)
				.addGroup(layout.createParallelGroup()
						.addComponent(gameNameLabel)
						.addComponent(gameName)
				)
				.addComponent(startSys)
				.addComponent(update)
		);
		pack();
	}
	
	public void newGame() {
		TOGame newGame = new TOGame().setGameName(gameName.getText());
		TONationSettings natSet = new TONationSettings();
		natSet.setHomeSys(startSys.getItemAt(startSys.getSelectedIndex()));
		newGame.setNatSet(natSet);
		StartMenuController.createNewGame(newGame);
		parent.loggedIn();
		dispose();
	}
	public void updateGame(TOGame game) {
		
	}

}
