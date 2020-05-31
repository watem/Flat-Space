package flatSpace.temporaryFrontend;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import flatSpace.Controller.GameController;
import flatSpace.Controller.SpatialController;
import flatSpace.Controller.TimeController;
import flatSpace.Controller.TO.Coordinates;
import flatSpace.Controller.TO.TOBody;
import flatSpace.Controller.TO.TOSystem;


public class SystemView extends JDialog {
	private JButton zoomInButton = new JButton("+");
	private JButton zoomOutButton = new JButton("-");
	private JButton moveUp = new JButton("^");
	private JButton moveDown = new JButton("v");
	private JButton moveLeft = new JButton("<");
	private JButton moveRight = new JButton(">");
	private double baseMoveDis = 20;

	private double zoomLevel = 3E-6;
	private JLabel zoomLevelLabel = new JLabel(Double.toString(zoomLevel));
	private JComboBox<TOSystem> systemSelection = new JComboBox<>();
	private JComboBox<TOBody> bodySelection = new JComboBox<>();
	private TOSystem selectedSystem;
	private SystemMap systemMap;
//	private int speed = 0;
	private GameMenuView gmv;
	TimeControlPanel tcp;

	public void update() {
		systemSelection.removeAllItems();
		for (TOSystem name : GameController.getSystems()) {
			systemSelection.addItem(name);
		}
		selectedSystem = systemSelection.getItemAt(systemSelection.getSelectedIndex());

		setTitle("Flat-Space - " + selectedSystem);

		systemMap = new SystemMap(selectedSystem, SpatialController.getAllBodies(selectedSystem.getId()));
		systemMap.setZoomLevel(zoomLevel);

		updateSystem();
		
		tcp = new TimeControlPanel(this);

		setupLayout();
	}

	public SystemView(GameMenuView owner) throws HeadlessException {
		super(owner, false);
		gmv = owner;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Flat-Space");
		update();
		listeners();
		refresh();
	}

	private void listeners() {
		zoomInButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				zoomLevel *= 2;
				zoomLevelLabel.setText(Double.toString(zoomLevel));
				systemMap.setZoomLevel(zoomLevel);
			}
		});
		zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				zoomLevel /= 2;
				zoomLevelLabel.setText(Double.toString(zoomLevel));
				systemMap.setZoomLevel(zoomLevel);
			}
		});
		systemSelection.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				TOSystem system = systemSelection.getItemAt(systemSelection.getSelectedIndex());
				selectedSystem = system;
				setTitle("Flat-Space - " + system);
				systemMap.setSystem(system);
				updateSystem();
			}
		});
		moveUp.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Coordinates cur = systemMap.getCurrentCoords();
				Coordinates next = new Coordinates(cur.getX(), cur.getY() + baseMoveDis * inverseZoom());
				systemMap.setFocusedCoords(next);
				systemMap.setFocus("coords");
			}
		});
		moveDown.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Coordinates cur = systemMap.getCurrentCoords();
				Coordinates next = new Coordinates(cur.getX(), cur.getY() - baseMoveDis * inverseZoom());
				systemMap.setFocusedCoords(next);
				systemMap.setFocus("coords");
			}
		});
		moveLeft.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Coordinates cur = systemMap.getCurrentCoords();
				Coordinates next = new Coordinates(cur.getX() - baseMoveDis * inverseZoom(), cur.getY());
				systemMap.setFocusedCoords(next);
				systemMap.setFocus("coords");
			}
		});
		moveRight.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Coordinates cur = systemMap.getCurrentCoords();
				Coordinates next = new Coordinates(cur.getX() + baseMoveDis * inverseZoom(), cur.getY());
				systemMap.setFocusedCoords(next);
				systemMap.setFocus("coords");
			}
		});
		bodySelection.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				TOBody body = bodySelection.getItemAt(bodySelection.getSelectedIndex());
				systemMap.setFocusedBody(body);
				systemMap.setFocus("body");
				refresh();
			}
		});
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				removeThis();
			}
		});
	}
	
	private void setupLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(systemSelection)
						.addComponent(bodySelection)
						.addComponent(tcp)
				)
				.addGroup(layout.createSequentialGroup()
						.addComponent(moveLeft)
						.addGroup(layout.createParallelGroup()
								.addComponent(zoomInButton)
								.addComponent(zoomLevelLabel)
								.addComponent(zoomOutButton)
								.addComponent(moveUp)
								.addComponent(moveDown)
						)
						.addComponent(moveRight)
						.addComponent(systemMap)
				)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(systemSelection)
						.addComponent(bodySelection)
						.addComponent(tcp)
				)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(moveUp)
								.addGroup(layout.createParallelGroup()
										.addComponent(moveLeft)
										.addComponent(moveRight)
								)
								.addComponent(moveDown)
								.addComponent(zoomInButton)
								.addComponent(zoomLevelLabel)
								.addComponent(zoomOutButton)
						)
						.addComponent(systemMap)
				)
		);
		layout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { tcp,systemSelection, bodySelection });
		systemMap.setMinimumSize(new Dimension(200, 200));
		pack();
		tcp.setMaximumSize(tcp.getSize());
		tcp.setMinimumSize(tcp.getSize());
		Dimension newSize = new Dimension(getSize().width*2, getSize().height*2);
		this.setSize(newSize);
	}

	public void refresh() {
		systemMap.refresh();
	}

	private void removeThis() {
		gmv.getSystemViews().remove(this);
		dispose();
	}

	private double inverseZoom() {
		return 1 / zoomLevel;
	}

	private void updateSystem() {
		bodySelection.removeAllItems();
		for (TOBody b : SpatialController.getAllBodies(selectedSystem.getId())) {
			bodySelection.addItem(b);
		}
		systemMap.setFocus("coords");
	}

}
