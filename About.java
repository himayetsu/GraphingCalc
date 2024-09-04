import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class About extends JFrame implements ActionListener {
	JLabel title;
    JLabel title2;
    JTextArea textArea;

    public About() {
        setTitle("TI-84");

        title = new JLabel("About");
        title.setFont(new Font("Verdana", Font.BOLD, 21));
        title.setBounds(640, 100, 300, 60);

        title2 = new JLabel("About the Program");
        title2.setFont(new Font("Verdana", Font.BOLD, 17));
        title2.setBounds(350, 180, 300, 60);

        textArea = new JTextArea();
        textArea.setText("This is a program that our group (Henry + Leen) made that graphs any type of function. It is able to graph seven parent functions, as well as two trigonometric functions and their phase shifts.\n"
        		+ "- Linear (mx+b)\n"
        		+ "- Quadratic (ax^2+bx+c)\n"
        		+ "- Cubic (ax^3+bx^2+cx+d)\n"
        		+ "- Rational (1/ax)\n"
        		+ "- Exponential (e^x)\n"
        		+ "- Square Root (sqrt(x))\n"
        		+ "- Absolute Value (|x|)\n"
        		+ "- Sine (sin(x))\n"
        		+ "- Cosine (cos(x))\n"
        		+ "- Tangent (tah(x))\n"
        		+ "\n"
        		+ "The calculator also provides you with the option of generating random equations and their graph, if needed. There is an evaluative portion on the side that allows you to test your skills, in the form of a multiple choice quiz.");
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBounds(350, 240, 600, 500);
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.addActionListener(this);

        JPanel returnButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        returnButtonPanel.add(returnButton);
        returnButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0)); // Add top padding

        JPanel mainPanel = new JPanel(null);
        mainPanel.add(returnButtonPanel);
        mainPanel.add(title);
        mainPanel.add(title2);
        mainPanel.add(textArea);

        // Set the bounds of the components
        returnButtonPanel.setBounds(10, 10, 500, 100);
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 900);
        setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		MainMenu main = new MainMenu();
        main.setExtendedState(JFrame.MAXIMIZED_BOTH);
        main.setVisible(true);
        dispose();
	}
}
