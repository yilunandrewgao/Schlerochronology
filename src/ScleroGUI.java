import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ScleroGUI implements ActionListener {
	
	private JFrame frame;
	private JPanel panel;

	private ScleroGUI() {
		frame = new JFrame();
		panel = new JPanel();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);		
	}
	
	public void actionPerformed(ActionEvent e) {

	}

	public static void main (String[] args) {
		new ScleroGUI();
	}
}
