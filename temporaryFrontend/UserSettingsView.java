package flatSpace.temporaryFrontend;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

//TODO
public class UserSettingsView extends JFrame{
	JLabel userLabel = new JLabel("username:");
	JLabel passLabel = new JLabel("pass:");
	JTextField userField = new JTextField();
	JTextField passField = new JTextField();
	JButton create = new JButton("create");
	JLabel error = new JLabel();
	StartMenu parent;

	public UserSettingsView(StartMenu parent) {
		this.parent = parent;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Create New User:");
		
		userField.setText("	");
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(userLabel)
						.addComponent(passLabel)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(userField)
						.addComponent(passField)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(create)
						.addComponent(error)
				)
				
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(userLabel)
						.addComponent(userField)
						.addComponent(create)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(passLabel)
						.addComponent(passField)
						.addComponent(error)
				)
		);
		pack();
		userField.setText("");
	}
}
