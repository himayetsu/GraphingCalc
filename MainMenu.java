
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame implements ActionListener {

	JButton graphing; 
	JButton about;
	JButton quiz;
	JLabel title;



	MainMenu(){

		setTitle("TI-84");

		title = new JLabel("Graphing Calculator");
		title.setFont(new Font("Verdana", Font.BOLD, 21));
		title.setBounds(550, 300, 300, 60);

		graphing = new JButton();
		graphing.setBounds(300,400,210,40);
		graphing.addActionListener(this);
		graphing.setText("Enter Graphing Calculator");
		graphing.setFont(new Font("Verdana", Font.PLAIN, 13));
		graphing.setFocusable(false);

		quiz = new JButton();
		quiz.setBounds(550,400,210,40);
		quiz.addActionListener(this);
		quiz.setText("Test Yourself");
		quiz.setFont(new Font("Verdana", Font.PLAIN, 13));
		quiz.setFocusable(false);

		about = new JButton();
		about.setBounds(800,400,200,40);
		about.addActionListener(this);
		about.setText("About");
		about.setFont(new Font("Verdana", Font.PLAIN, 15));
		about.setFocusable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1600,900);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setVisible(true);
		
		this.add(graphing);
		this.add(about);
		this.add(quiz);
		this.add(title);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==graphing) {
			Converter calc = new Converter();
			calc.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			calc.setVisible(true);
			dispose();
		}
		if(e.getSource()==about) {
			About abt = new About();
			abt.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			abt.setVisible(true);
			dispose();
		}
		if(e.getSource()==quiz) {
			TestYourself test = new TestYourself();
			test.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			test.setVisible(true);
			dispose();
		}

	}

}
