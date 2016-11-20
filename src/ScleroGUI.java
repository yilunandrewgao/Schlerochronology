import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.util.ArrayList;
import java.io.File;
import java.io.*;
import java.util.Scanner;

public class ScleroGUI implements ActionListener {
	
	private JFrame frame;
	private JPanel cards, filePanel, optionsPanel;
    private JButton chooseFileButton, normDataButton, getStatsButton, timelineButton;
    private ArrayList<Double> data;
    
    final static String FILEPANEL = "Card with FileChooser";
    final static String OPTIONSPANEL = "Card with Buttons";
    

	private ScleroGUI() {
		frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
		filePanel = new JPanel();
        chooseFileButton = new JButton("Choose Data File");
        chooseFileButton.addActionListener(this);
        filePanel.add(chooseFileButton);
        
        // Construct data options
        optionsPanel = new JPanel();
        normDataButton = new JButton("Normalize Data");
        normDataButton.addActionListener(this);
        getStatsButton = new JButton("Get Statistics");
        getStatsButton.addActionListener(this);
        timelineButton = new JButton("Build Timeline");
        timelineButton.addActionListener(this);
        optionsPanel.add(normDataButton);
        optionsPanel.add(getStatsButton);
        optionsPanel.add(timelineButton);
        
        cards = new JPanel(new CardLayout());
        cards.add(filePanel, FILEPANEL);
        cards.add(optionsPanel, OPTIONSPANEL);
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, FILEPANEL);
        frame.add(cards);
		frame.pack();
		frame.setVisible(true);		
	}
	
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chooseFileButton) {
            String absolutePath = "";
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(cards);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                absolutePath = chooser.getSelectedFile().getAbsolutePath();
                readFile(absolutePath);
            }
        }
	}
    
    private void readFile(String fileName) {
        data = new ArrayList<Double>();
        File file = new File(fileName);
        try {
            Scanner fileReader = new Scanner(file);
            fileReader.useDelimiter(",");
            double num;
            while (fileReader.hasNext()) {
                num = fileReader.nextDouble();
                data.add(num);
            }
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, OPTIONSPANEL);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

	public static void main (String[] args) {
		new ScleroGUI();
	}
}
