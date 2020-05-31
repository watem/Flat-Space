package flatSpace.temporaryFrontend;

import java.awt.HeadlessException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import flatSpace.Controller.InvalidInputException;
import flatSpace.Controller.StartMenuController;
import flatSpace.Controller.TO.TOGame;
import flatSpace.Controller.TO.TOProfile;
import flatSpace.application.FlatSpaceApplication;

public class StartMenu extends JFrame {
	JComboBox<TOProfile> selectUser = new JComboBox<>();
	JTextField pass = new JTextField(10);
	JLabel passLabel = new JLabel("pass:");
	JButton loginButton = new JButton("login");
	JButton newUserButton = new JButton("new user");
	JLabel user = new JLabel();
	JComboBox<TOGame> selectGame = new JComboBox<>();
	JTextField newGameName = new JTextField(10);
	JLabel newGameNameLabel = new JLabel("game name:");
	JButton newGameButton = new JButton("add game");
	JButton logoutButton = new JButton("logout");
	JButton startGame = new JButton("start game");
	JButton openSettings = new JButton("user settings");
	JLabel error = new JLabel();
	boolean quit = false;
	
	public StartMenu() throws HeadlessException {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Flat-Space");
		StartMenuController.createNewProfile("A", null);
		listeners();
		loggedIn();
		preLogin();
	}
	
	//select profile, select game

	public void preLogin() {
//		NewProfileDialog pd = new NewProfileDialog(this);
//		pd.pack();
//		pd.setVisible(true);
		LoginDialog	ld = new LoginDialog(this);
		ld.pack();
		ld.setVisible(true);
	}
	public void loggedIn() {
		this.getContentPane().removeAll();
		TOProfile profile = StartMenuController.getCurrentProfile();
		if (profile !=null) {
			user.setText(profile.getUsername());
		}
		selectGame.removeAllItems();
		for(TOGame g:StartMenuController.getProfileGames(profile)) {
			selectGame.addItem(g);
		}
		
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(error)
				.addComponent(user)
				.addComponent(selectGame)
				.addComponent(logoutButton)
				.addComponent(newGameButton)
				.addComponent(startGame)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(error)
				.addGroup(layout.createParallelGroup()
					.addComponent(user)
					.addComponent(selectGame)
					.addComponent(logoutButton)
					.addComponent(newGameButton)
					.addComponent(startGame)
				)
		);
		
		
		pack();
		
	}
	public void createNewGameScreen() {
		new GameSettingsView(this).setVisible(true);
	}
	public void openUserSettingsScreen() {
		new UserSettingsView(this).setVisible(true);
	}
	public void startGame() { //TODO
		try {
			StartMenuController.startGame(selectGame.getItemAt(selectGame.getSelectedIndex()));
			GameMenuView gmv = new GameMenuView(selectGame.getItemAt(selectGame.getSelectedIndex()));
			FlatSpaceApplication.setGameMenuView(gmv);
			gmv.setVisible(true);
			dispose();
		} catch(InvalidInputException e) {
			error.setText(e.getMessage());
			pack();
		}
	}
	
	private void listeners() {
		logoutButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				StartMenuController.logout();
				preLogin();
			}
		});
		newGameButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createNewGameScreen();
			}
		});
		startGame.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startGame();
			}
		});
	}
	
	public void quit() {
		this.dispose();
	}
}
