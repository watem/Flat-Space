package flatSpace.temporaryFrontend;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import flatSpace.Controller.StartMenuController;
import flatSpace.Controller.TO.TOProfile;

public class LoginDialog extends JDialog {
	String username = "Username:", pass = "Password:";
	JComboBox<TOProfile> users = new JComboBox<>();
	JTextField userField = new JTextField(10);
	JPasswordField passField = new JPasswordField(10);
	JPasswordField passField2 = new JPasswordField(10);
	String givenUsername, givenPassword;
	StartMenu parent;
	private JOptionPane loginPane, signupPane;
	
	TOProfile user;
	private String btnString1 = "Login";
    private String btnString2 = "Signup";
    private String btnString3 = "Quit";
    private String btn2String1 = "Enter";
    private String btn2String2 = "Cancel";
    
    
    public LoginDialog(StartMenu parent) {
    	super(parent, "Login", true);
		this.parent = parent;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		Object[] loginArray = {users,pass,passField};
		Object[] loginOptions = {btnString1, btnString2, btnString3};
		Object[] signupArray = {username, userField,pass,passField2};
		Object[] signupOptions = {btn2String1, btn2String2};
		loginPane = new JOptionPane(loginArray, JOptionPane.PLAIN_MESSAGE, 
				JOptionPane.OK_CANCEL_OPTION, null, loginOptions, loginOptions[0]);
		signupPane = new JOptionPane(signupArray, JOptionPane.PLAIN_MESSAGE, 
				JOptionPane.OK_CANCEL_OPTION, null, signupOptions, signupOptions[0]);
		
		//Register an event handler that reacts to option pane state changes.
		loginPane.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();
				 
		        if (isVisible()
		         && (e.getSource() == loginPane)
		         && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
		             JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
		            Object value = loginPane.getValue();
		 
		            if (value == JOptionPane.UNINITIALIZED_VALUE) {
		                //ignore reset
		                return;
		            }
		 
		            //Reset the JOptionPane's value.
		            loginPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
		            login(value);
		        }
			}
		});
		signupPane.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();
				 
		        if (isVisible()
		         && (e.getSource() == signupPane)
		         && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
		             JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
		            Object value = signupPane.getValue();
		 
		            if (value == JOptionPane.UNINITIALIZED_VALUE) {
		                //ignore reset
		                return;
		            }
		 
		            //Reset the JOptionPane's value.
		            signupPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
		            signup(value);
		        }	
			}
		});
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we) {
		        System.exit(0);
		    }
		});
		toLogin();
    }

    private void login(Object value) {
    	if (btnString1.equals(value)) {
            if (StartMenuController.login(users.getItemAt(users.getSelectedIndex()), passField.getPassword())) {
    			parent.loggedIn();
                clearAndHide();
            } else {
                //text was invalid
                userField.selectAll();
                JOptionPane.showMessageDialog(
                		LoginDialog.this,
                                "Sorry, \"" + userField.getText() + "\" "
                                + "is already taken.\n"
                                + "or there is something wrong \n"
                                + "with your password.",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                passField.setText(null);
                userField.requestFocusInWindow();
            }
        } else if (btnString2.equals(value)){ //signup button
        	toSignup();
        } else { //quit
        	System.exit(0);
        }
    }
    private void signup(Object value) {
    	if (btn2String1.equals(value)) {
        	user = StartMenuController.createNewProfile(userField.getText(), passField2.getPassword());
            if (user!=null) {
                //we're done; clear and dismiss the dialog
            	StartMenuController.login(user, passField2.getPassword());
    			parent.loggedIn();
                clearAndHide();
            } else {
                //text was invalid
                userField.selectAll();
                JOptionPane.showMessageDialog(
                		LoginDialog.this,
                                "Sorry, \"" + userField.getText() + "\" "
                                + "is already taken.\n"
                                + "or there is something wrong \n"
                                + "with your password.",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                passField2.setText(null);
                userField.requestFocusInWindow();
            }
        } else {
        	toLogin();
        }
    }
    
    /** This method clears the dialog and hides it. */
    private void clearAndHide() {
        userField.setText(null);
        passField.setText(null);
        passField2.setText(null);
        setVisible(false);
    	dispose();
    }
    
    private void clearPanes() {
    	userField.setText(null);
    	passField.setText(null);
    	passField2.setText(null);
    	try {
    		this.remove(loginPane);
    	} catch(NullPointerException e){
    		System.out.println("temp");
    	}
    	try {
    		this.remove(signupPane);
    	} catch(NullPointerException e){
    		System.out.println("temp");
    	}
    }
    private void toSignup() {
    	clearPanes();
    	this.add(signupPane);
    	this.pack();
    }
    public void toLogin() {
    	users.removeAllItems();
		for(TOProfile p:StartMenuController.getAllProfiles()) {
			users.addItem(p);
		}
    	clearPanes();
    	this.add(loginPane);
    	this.pack();
    	userField.requestFocus();
    }
}
