package flatSpace.temporaryFrontend;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import flatSpace.Controller.TO.Coordinates;
import flatSpace.Controller.TO.TOBody;
import flatSpace.Controller.TO.TOSystem;

public class FlatView extends JDialog {
	private JButton zoomInButton = new JButton("+");
	private JButton zoomOutButton = new JButton("-");
	private JButton moveUp = new JButton("^");
	private JButton moveDown = new JButton("v");
	private JButton moveLeft = new JButton("<");
	private JButton moveRight = new JButton(">");
	private double baseMoveDis = 20;

	private double zoomLevel = 3E-6;
	private JLabel zoomLevelLabel = new JLabel(Double.toString(zoomLevel));
	
	private FlatMap map = new FlatMap(this);
	TimeControlPanel tcp;

	
	
	public FlatView(GameMenuView owner) {
		super(owner, false);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Flat-Space: 2D-Space");
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
				map.setZoomLevel(zoomLevel);
			}
		});
		zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				zoomLevel /= 2;
				zoomLevelLabel.setText(Double.toString(zoomLevel));
				map.setZoomLevel(zoomLevel);
			}
		});
//		systemSelection.addActionListener(new java.awt.event.ActionListener() {
//			@Override
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				TOSystem system = systemSelection.getItemAt(systemSelection.getSelectedIndex());
//				selectedSystem = system;
//				setTitle("Flat-Space - " + system);
//				map.setSystem(system);
//				updateSystem();
//			}
//		});
		moveUp.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Coordinates cur = map.getCurrentCoords();
				Coordinates next = new Coordinates(cur.getX(), cur.getY() + baseMoveDis * inverseZoom());
				map.setFocusedCoords(next);
				map.setFocus("coords");
			}
		});
		moveDown.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Coordinates cur = map.getCurrentCoords();
				Coordinates next = new Coordinates(cur.getX(), cur.getY() - baseMoveDis * inverseZoom());
				map.setFocusedCoords(next);
				map.setFocus("coords");
			}
		});
		moveLeft.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Coordinates cur = map.getCurrentCoords();
				Coordinates next = new Coordinates(cur.getX() - baseMoveDis * inverseZoom(), cur.getY());
				map.setFocusedCoords(next);
				map.setFocus("coords");
			}
		});
		moveRight.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Coordinates cur = map.getCurrentCoords();
				Coordinates next = new Coordinates(cur.getX() + baseMoveDis * inverseZoom(), cur.getY());
				map.setFocusedCoords(next);
				map.setFocus("coords");
			}
		});
//		bodySelection.addActionListener(new java.awt.event.ActionListener() {
//			@Override
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				TOBody body = bodySelection.getItemAt(bodySelection.getSelectedIndex());
//				map.setFocusedBody(body);
//				map.setFocus("body");
//				refresh();
//			}
//		});
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
//				removeThis(); hide this dialog
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
//						.addComponent(systemSelection)
//						.addComponent(bodySelection)
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
						.addComponent(map)
				)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
//						.addComponent(systemSelection)
//						.addComponent(bodySelection)
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
						.addComponent(map)
				)
		);
//		layout.linkSize(SwingConstants.VERTICAL,
//				new java.awt.Component[] { tcp,systemSelection, bodySelection });
		map.setMinimumSize(new Dimension(200, 200));
		pack();
		tcp.setMaximumSize(tcp.getSize());
		tcp.setMinimumSize(tcp.getSize());
		Dimension newSize = new Dimension(getSize().width*2, getSize().height*2);
		this.setSize(newSize);
	}

	public void update() {
		tcp = new TimeControlPanel(this);
		setupLayout();
	}
	public void refresh() {
		map.refresh();
	}
	private double inverseZoom() {
		return 1 / zoomLevel;
	}
}

