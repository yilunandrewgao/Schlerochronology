import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.util.ArrayList;
import java.io.File;
import java.io.*;

public class ScleroGUI implements ActionListener {
	
	private JFrame frame;
	private JPanel panel;
    private JButton chooseFile;

	private ScleroGUI() {
		frame = new JFrame();
		panel = new JPanel();
        chooseFile = new JButton("Choose Data File");
        chooseFile.addActionListener(this);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);		
	}
	
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chooseFile) {
            String absolutePath = "";
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            int returnVal = chooser.showOpenDialog(mainPanel);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                absolutePath = chooser.getSelectedFile().getAbsolutePath();
                readFile(absolutePath);
            }
        }
	}
    
    private void readFile(String fileName) {
        ArrayList<double> data = new ArrayList<double>();
        BufferedReader reader=new BufferedReader(new FileReader(fileName));
    }

	public static void main (String[] args) {
		new ScleroGUI();
	}
}
