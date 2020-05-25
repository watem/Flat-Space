package FlatSpace.temporaryFrontend;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import FlatSpace.Controller.GameController;
import FlatSpace.Controller.SpatialController;
import FlatSpace.Controller.TimeController;
import FlatSpace.Controller.TO.TOSystem;



public class SystemView extends JFrame{
	private JButton pauseButton = new JButton("||");
	private JButton play1Button = new JButton(">");
	private JButton play2Button = new JButton(">>");
	private JButton play3Button = new JButton(">>>");
	private JButton zoomInButton = new JButton("+");
	private JButton zoomOutButton = new JButton("-");
	private double zoomLevel = 3;
	private JLabel zoomLevelLabel = new JLabel(Double.toString(zoomLevel));
	private JComboBox<TOSystem> systemSelection = new JComboBox<>();
	private SystemMap systemMap;
//	private int speed = 0;
	
	public void update() {
		systemSelection.removeAllItems();
		for(TOSystem name:GameController.getSystems()) {
			systemSelection.addItem(name);
		}
		TOSystem system = systemSelection.getItemAt(systemSelection.getSelectedIndex());
		
		setTitle("Flat-Space - "+system);
		
		
		systemMap = new SystemMap(system, SpatialController.getAllBodies(system.getId()));
		
		pauseButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				TimeController.setSpeed(0);
			}
		});
		play1Button.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				TimeController.setSpeed(1);
			}
		});
		play2Button.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				TimeController.setSpeed(2);
			}
		});
		play3Button.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				TimeController.setSpeed(3);
			}
		});
		zoomInButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				zoomLevel*=2;
				zoomLevelLabel.setText(Double.toString(zoomLevel));
				systemMap.setZoomLevel(zoomLevel);
			}
		});
		zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				zoomLevel/=2;
				zoomLevelLabel.setText(Double.toString(zoomLevel));
				systemMap.setZoomLevel(zoomLevel);
			}
		});
		systemSelection.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("selecetion");
				TOSystem system = systemSelection.getItemAt(systemSelection.getSelectedIndex());
				setTitle("Flat-Space - "+system);
				systemMap.setSystem(system);
			}
		});
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addComponent(systemSelection)
					.addComponent(pauseButton)
					.addComponent(play1Button)
					.addComponent(play2Button)
					.addComponent(play3Button)
				)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(zoomInButton)
								.addComponent(zoomLevelLabel)
								.addComponent(zoomOutButton)
						)
						.addComponent(systemMap)
				)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(systemSelection)
						.addComponent(pauseButton)
						.addComponent(play1Button)
						.addComponent(play2Button)
						.addComponent(play3Button)
				)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(zoomInButton)
								.addComponent(zoomLevelLabel)
								.addComponent(zoomOutButton)
						)
						.addComponent(systemMap)
				)
				
		);
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {pauseButton, systemSelection});
		systemMap.setMinimumSize(new Dimension(200, 200));
		pack();
		this.setMinimumSize(getSize());
	}
	
	public SystemView() throws HeadlessException {
		super();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Flat-Space");
		update();
		refresh();
	}

	public void refresh() {
		systemMap.refresh();
	}
	
	
	
}
