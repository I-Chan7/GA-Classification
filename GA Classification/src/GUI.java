import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * 
 * @author: Ivan Chan
 * @version 1.0
 * 
 * Builds a GUI for GA
 */

public class GUI extends JFrame{

	private static final long serialVersionUID = -7870307308130448572L;
	private JPanel panel;
	private JFrame frame;
	private JLabel lblFileName, lblGenerationCount, lblPopulationSize, lblMutationRate, lblTournamentSize, lblUniformRate, lblMaximumRule, lblDataSet;
	private JTextField txtGenerationCount, txtPopulationSize, txtMutationRate, txtTournamentSize, txtUniformRate, txtMaximumRule;
	private static JTextArea textArea;
	private JScrollPane scrollPane;
	private JButton btnConfirm, btnDefault, btnStop;
	private static int generationCount;
	private static int populationSize;
	private static int tournamentSize;
	private static int maximumRule;
	private static double mutationRate;
	private static double uniformRate;
	String fileName = SelectDataSet.getFileName();
	GA.performAlgorithm runGA;


	public GUI() {
		
		createForm();
		addFields();
		addButtons();
		
		frame.add(panel); 
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}
	
	/**
	 * Creates GUI interface
	 */
	public void createForm() {
		frame = new JFrame("GA Classifier");
		frame.setSize(900,350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setSize(350,500);
		panel.setLayout(null);
		Color color = new Color(220, 220, 220);
		panel.setBackground(color);	
	}
	
	/**
	 * Adds labels and text fields to GUI
	 */
	public void addFields() {
		lblFileName = new JLabel("Dataset:");
		lblFileName.setBounds(20, 20, 150, 20);
		panel.add(lblFileName);
		
		lblGenerationCount = new JLabel("Generation Count: ");
		lblGenerationCount.setBounds(20, 50, 150, 20);
		panel.add(lblGenerationCount);
		
		lblPopulationSize = new JLabel("Population Size: ");
		lblPopulationSize.setBounds(20, 80, 150, 20);
		panel.add(lblPopulationSize);
		
		lblTournamentSize = new JLabel("Tournament Size");
		lblTournamentSize.setBounds(20, 110, 150, 20);
		panel.add(lblTournamentSize);
		
		lblMutationRate = new JLabel("Mutation Rate: ");
		lblMutationRate.setBounds(20, 140, 150, 20);
		panel.add(lblMutationRate);
			
		lblUniformRate = new JLabel("Uniform Rate: ");
		lblUniformRate.setBounds(20, 170, 150, 20);
		panel.add(lblUniformRate);
		
		lblMaximumRule = new JLabel("Maximum Rules: ");
		lblMaximumRule.setBounds(20, 200, 150, 20);
		panel.add(lblMaximumRule);
		
		lblDataSet = new JLabel(" "+fileName);
		lblDataSet.setBounds(150, 20, 150, 20);
		panel.add(lblDataSet);
		
		txtGenerationCount = new JTextField("100");
		txtGenerationCount.setBounds(150, 50, 150, 20);
		panel.add(txtGenerationCount);
		
		txtPopulationSize = new JTextField("50");
		txtPopulationSize.setBounds(150, 80, 150, 20);
		panel.add(txtPopulationSize);
			
		txtTournamentSize = new JTextField("3");
		txtTournamentSize.setBounds(150, 110, 150, 20);
		panel.add(txtTournamentSize);
		
		txtMutationRate = new JTextField("0.05");
		txtMutationRate.setBounds(150, 140, 150, 20);
		panel.add(txtMutationRate);
				
		txtUniformRate = new JTextField("0.5");
		txtUniformRate.setBounds(150, 170, 150, 20);
		panel.add(txtUniformRate);
		
		txtMaximumRule = new JTextField("10");
		txtMaximumRule.setBounds(150, 200, 150, 20);
		panel.add(txtMaximumRule);
		
		textArea = new JTextArea();		
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(350, 20, 500, 220);
		panel.add(scrollPane);
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea), true);
		System.setOut(printStream);
		System.setErr(printStream);

	}
	
	/**
	 * Adds buttons to GUI
	 */
	public void addButtons() {
		btnConfirm = new JButton ("Confirm");
		btnConfirm.setBounds(40, 250, 100, 20);
		btnConfirm.addActionListener(new ConfirmHandler());
		panel.add (btnConfirm);
		
		btnDefault = new JButton ("Default");
		btnDefault.setBounds(180, 250, 100, 20);
		btnDefault.addActionListener(new DefaultHandler());
		panel.add (btnDefault);
		
		btnStop = new JButton("Stop");
		btnStop.setBounds(550, 250, 100, 20);
		btnStop.addActionListener(new StopHandler());
		panel.add(btnStop);
		

	}
	
	//checks the parameters and executes algorithm
	class ConfirmHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int checked = 0;
				
			//handle generation count
			try{
				generationCount= Integer.parseInt(txtGenerationCount.getText()); 
				if(generationCount<=0){
					JOptionPane.showMessageDialog(frame, "Generation count must be 1 or higher.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					txtGenerationCount.setText("");		
					checked--;
				}
				checked++;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(frame, "Generation count missing or invalid.", "ERROR", JOptionPane.ERROR_MESSAGE);
				txtGenerationCount.setText(""); 
				checked--;
					// An error box will display if an integer is not entered
			} 
			//handle population size
			try{
				populationSize= Integer.parseInt(txtPopulationSize.getText()); 
				if(populationSize<=1){
					JOptionPane.showMessageDialog(frame, "Population size must be 2 or higher.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					txtPopulationSize.setText("");
					checked--;
				}
				checked++;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(frame, "Population size missing or invalid.", "ERROR", JOptionPane.ERROR_MESSAGE);
				txtPopulationSize.setText(""); 
				checked--;
					// An error box will display if an integer is not entered 
			} 
			//handle tournament size
			try{
				tournamentSize= Integer.parseInt(txtTournamentSize.getText()); 
				if(tournamentSize<=1){
					JOptionPane.showMessageDialog(frame, "Tournament size must be 2 or higher.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					txtTournamentSize.setText("");
					checked--;
				}
				if(tournamentSize > populationSize) {
					JOptionPane.showMessageDialog(frame, "Tournament size cannot be larger than population size.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					txtTournamentSize.setText("");
					checked--;
				}
				checked++;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(frame, "Tournament size missing or invalid.", "ERROR", JOptionPane.ERROR_MESSAGE);
				txtTournamentSize.setText(""); 
				checked--;
					// An error box will display if an integer is not entered 
			} 
			//handle mutation rate
			try{
				mutationRate= Double.parseDouble(txtMutationRate.getText()); 
				if(mutationRate<=0 || mutationRate > 1){
					JOptionPane.showMessageDialog(frame, "Mutation rate must be between 0 and 1.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					txtMutationRate.setText("");
					checked--;
				}
				checked++;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(frame, "Mutation rate missing or invalid.", "ERROR", JOptionPane.ERROR_MESSAGE);
				txtMutationRate.setText(""); 
				checked--;
					// An error box will display if a double is not entered 
			} 
			//handle uniform rate
			try{
				uniformRate= Double.parseDouble(txtUniformRate.getText()); 
				if(uniformRate<=0 || uniformRate > 1){
					JOptionPane.showMessageDialog(frame, "Uniform rate must be between 0 and 1.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					txtUniformRate.setText("");
					checked--;
				}
				checked++;
			}catch(NumberFormatException e){
				// An error box will display if a double is not entered
				JOptionPane.showMessageDialog(frame, "Uniform rate missing or invalid.", "ERROR", JOptionPane.ERROR_MESSAGE);
				txtUniformRate.setText("");
				checked--;					
			} 	
			//handle maximum rule
			try{
				maximumRule= Integer.parseInt(txtMaximumRule.getText()); 
				if(maximumRule<=0){
					JOptionPane.showMessageDialog(frame, "Maximum rule must be 1 or higher.", 
							"ERROR", JOptionPane.ERROR_MESSAGE);
					txtMaximumRule.setText("");		
					checked--;
				}
				checked++;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(frame, "Maximum rule missing or invalid.", "ERROR", JOptionPane.ERROR_MESSAGE);
				txtMaximumRule.setText(""); 
				checked--;
				// An error box will display if an integer is not entered
					
			} 
			//perform algorithm if all parameters are valid
			if(checked == 6) {	
				//perform calculations in new thread
				runGA = new GA.performAlgorithm();
				runGA.start();		    			    
			}			
		}
	}
	
	//revert all parameters to default values
	class DefaultHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			txtGenerationCount.setText("100");
			txtPopulationSize.setText("50");
			txtTournamentSize.setText("3");
			txtMutationRate.setText("0.05");
			txtUniformRate.setText("0.5");
			txtMaximumRule.setText("10");
		}
	}
	
	class StopHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			GA.stop = true;
		}
	}
	
	//redirects output to textArea
	public class CustomOutputStream extends OutputStream {
	    private JTextArea textArea;

	    public CustomOutputStream(JTextArea textArea) {
	        this.textArea = textArea;
	        
	    }
	    public void write(int b) throws IOException {
	        textArea.append(String.valueOf((char)b));
	        textArea.setCaretPosition(textArea.getDocument().getLength());	      
	        textArea.setLineWrap(true);
	        
	    }
	}
	

	
	/**
	 * Retrieves generationCount
	 * @return generationCount
	 */
	public static int getGenerationCount() {
		return generationCount;
	}
	
	/**
	 * Retrieves populationSize
	 * @return populationSize
	 */
	public static int getPopulationSize() {
		return populationSize;
	}
	
	/**
	 * Retrieves tournamentSize
	 * @return tournamentSize
	 */
	public static int getTournamentSize() {
		return tournamentSize;
	}
	
	/**
	 * Retrieves mutationRate
	 * @return mutationRate
	 */
	public static double getMutationRate() {
		return mutationRate;
	}
	
	/**
	 * Retrieves uniformRate
	 * @return uniformRate
	 */
	public static double getUniformRate() {
		return uniformRate;
	}
	
	/**
	 * Retrieves maximumRule
	 * @return maximumRule
	 */
	public static int getMaximumRule() {
		return maximumRule;
	}

}