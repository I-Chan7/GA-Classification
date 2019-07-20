import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SelectDataSet extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1140673410129529031L;
	private JPanel panel;
	private JFrame frame;
	private JLabel lblFileName;
	private JComboBox<String> fileComboBox;
	private JButton btnConfirm;
	private static String fileName;
	static GUI UI;
	
	public SelectDataSet() {
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
		frame = new JFrame();
		frame.setSize(250,190);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setSize(200,100);
		panel.setLayout(null);
		Color color = new Color(220, 220, 220);
		panel.setBackground(color);	
	}
	
	public void addFields() {
		lblFileName = new JLabel("Select a dataset: ");
		lblFileName.setBounds(40, 20, 150, 20);
		panel.add(lblFileName);
		
		String[] fileNames = {"Iris", "Car Evaluation", "Australian Credit Approval", "Portuguese Bank"};		
		fileComboBox = new JComboBox<>(fileNames);
		fileComboBox.setBounds(40, 50, 150, 20);
		panel.add(fileComboBox);
	}
	
	public void addButtons() {
		btnConfirm = new JButton ("Confirm");
		btnConfirm.setBounds(50, 90, 100, 20);
		btnConfirm.addActionListener(new ConfirmHandler());
		panel.add (btnConfirm);
	}
	
	class ConfirmHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//handle file
			if(fileComboBox.getSelectedItem()=="Iris") {
				fileName = "Iris";
			}
			if(fileComboBox.getSelectedItem()=="Car Evaluation") {
				fileName = "car";
			}
			if(fileComboBox.getSelectedItem()=="Australian Credit Approval") {
				fileName = "australian";
			}
			if(fileComboBox.getSelectedItem()=="Portuguese Bank") {
				fileName = "bank";
			}
			UI = new GUI();
			frame.setVisible(false);
			frame.dispose();
		}
	}
	
	/**
	 * Retrieves fileName
	 * @return fileName
	 */
	public static String getFileName() {
		return fileName;
	}
}
