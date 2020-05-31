package flatSpace.temporaryFrontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import flatSpace.Controller.TimeController;

public class TimeControlPanel extends JPanel {
	private static List<TimeControlPanel> panels;
	
	private static final int HEIGHT = 30;
	private static final int WIDTH = 20;
	
	JButton speedUp = new JButton("^");
	JButton speedDown = new JButton("v");
	JLabel speedVal = new JLabel();
	JButton pause = new JButton("||");
	
	public TimeControlPanel() {
		getPanels().add(this);
		speedVal.setHorizontalAlignment(SwingConstants.CENTER);
		listeners();
		updateSpeedVal();
		setupGridBadLayout();
	}
	public TimeControlPanel(JFrame owner) {
		this();
	}
	public TimeControlPanel(JDialog owner) {
		this();
	}
	
	public void remove() {
		getPanels().remove(this);
	}
	
	private void updateSpeedVal() {
		if (TimeController.isPaused()) {
			speedVal.setText("||");
		} else {
			speedVal.setText(Integer.toString(TimeController.getSpeed()));
		}
	}
	
	private void update() {
		for(TimeControlPanel p:getPanels()) {
			if(p==null) {
				remove(p);
			} else {
				p.updateSpeedVal();
			}
		}
	}
	public void setSpeed(int i) {
		TimeController.setSpeed(i);
		update();
	}
	public void incrementSpeed() {
		setSpeed(TimeController.getSpeed()+1);
	}
	public void decrementSpeed() {
		setSpeed(TimeController.getSpeed()-1);
	}

	private static List<TimeControlPanel> getPanels() {
		if (panels==null) {
			panels = new ArrayList<>();
		}
		return panels;
	}
	private void togglePause() {
		TimeController.togglePause();
		update();
	}
	
	@Override
	public void setVisible(boolean aFlag) {
		if (aFlag) {
			if (!getPanels().contains(this)) {
				getPanels().add(this);
			}
			updateSpeedVal();
		} else {
			getPanels().remove(this);
		}
		super.setVisible(aFlag);
	}


	private void listeners() {
		speedUp.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				incrementSpeed();
			}
		});
		speedDown.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				decrementSpeed();
			}
		});
		pause.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				togglePause();
			}
		});
	}
	private void setupGroupLayout() {
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(pause)
				.addComponent(speedVal)
				.addGroup(layout.createParallelGroup()
						.addComponent(speedUp)
						.addComponent(speedDown)
				)
		);
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(pause)
				.addComponent(speedVal)
				.addGroup(layout.createSequentialGroup()
						.addComponent(speedUp)
						.addComponent(speedDown)
				)
		);
		Dimension small = new Dimension(WIDTH, HEIGHT/2);
		Dimension normal = new Dimension(WIDTH, HEIGHT);
		speedUp.setMinimumSize(small);
		speedDown.setMinimumSize(small);
		pause.setMinimumSize(normal);
		speedVal.setMinimumSize(normal);
		speedUp.setMaximumSize(small);
		speedDown.setMaximumSize(small);
		pause.setMaximumSize(normal);
		speedVal.setMaximumSize(normal);
//		pack();
	}
	
	private void setupGridBadLayout() {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 2;
		c.weightx = 1;
		layout.setConstraints(pause, c);
		add(pause);
		layout.setConstraints(speedVal, c);
		add(speedVal);
		c.gridheight = 1;
		layout.setConstraints(speedUp, c);
		add(speedUp);
		c.weightx = 0;
		c.gridy = 1;
		layout.setConstraints(speedDown, c);
		add(speedDown);
		
	}

}
