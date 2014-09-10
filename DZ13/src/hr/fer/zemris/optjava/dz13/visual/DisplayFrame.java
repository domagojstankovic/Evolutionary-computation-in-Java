package hr.fer.zemris.optjava.dz13.visual;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hr.fer.zemris.optjava.dz13.model.MapPosition;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DisplayFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	protected static final int SLEEP_TIME = 100;

	private AntTrailComponent panel;
	private JPanel buttonPanel;
	private JButton next;
	private JButton reset;
	private JButton auto;
	private Timer timer;
	private boolean isAuto = false;
	
	public DisplayFrame(MapPosition position) throws HeadlessException {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocation(200, 50);
		setSize(700, 750);

		buttonPanel = new JPanel();

		next = new JButton(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.next();
			}
		});
		next.setText("Next");

		reset = new JButton(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.reset();
			}
		});
		reset.setText("Reset");

		auto = new JButton(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				isAuto = true;
				if (!timer.isRunning()) {
					timer.start();
				}
			}
		});
		auto.setText("Auto");
		
		timer = new Timer(SLEEP_TIME, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				auto();
			}
		});

		buttonPanel.add(reset);
		buttonPanel.add(next);
		buttonPanel.add(auto);

		add(buttonPanel, BorderLayout.SOUTH);

		panel = new AntTrailComponent(position);
		add(panel, BorderLayout.CENTER);
	}

	protected void auto() {
		if (isAuto) {
			if (!panel.next()) {
				isAuto = false;
				timer.stop();
			}
		}
		
	}

}
