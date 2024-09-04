import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestYourself extends JFrame implements ActionListener {
    public JLabel[] questionLabels;
    public JRadioButton[][] optionButtons;
    public ButtonGroup[] optionGroups;
    public JButton submitButton;
    public boolean answered;

    public String[] correctAnswers = {"y = x^2+2", "Three", "-2", "-2", "y=0", "-4", "[1, -1]", "a"};
    public JPanel containerPanel;

    JLabel title;

    public TestYourself() {
        setTitle("Test Yourself");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 900);
        setLayout(new BorderLayout());
        submitButton = new JButton("Submit");
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        containerPanel = new JPanel();
        questionLabels = new JLabel[8];
        optionButtons = new JRadioButton[8][4];
        optionGroups = new ButtonGroup[8];

        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 20));

        
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        title = new JLabel("Test yourself");
        title.setFont(new Font("Verdana", Font.BOLD, 21));
        titlePanel.add(title);
        containerPanel.add(titlePanel);
        containerPanel.add(title);

        createQuestionAndOptions(0, "Which of these are an example of a Quadratic Equation?", "y = 1/(3x)", "y = x^3+5x^2+4x-6", "y = x^2+2", "x = y^2");
        createQuestionAndOptions(1, "How many ROOTS does a cubic equation have?", "One", "Two", "Three", "Four");
        createQuestionAndOptions(2, "For the equation y = 3x - 2, what is the y-intercept?", "2", "6", "5", "-2");
        createQuestionAndOptions(3, "What's the slope of the line y = -2x+5?", "-2", "5", "3", "-10");
        createQuestionAndOptions(4, "What is the asymptote of the function y = 1/x?", "y=0", "y=5", "y=1", "There is no asymptote");
        createQuestionAndOptions(5, "For the equation y = -1/2x-4, what is the y-intercept?", "-1/2", "-4", "There are more than one", "There is none");
        createQuestionAndOptions(6, "What is the range of the function y = sin(x)?", "[3.14, -3.14]", "[2, -2]", "[1, -1]", "[x, -x]");
        createQuestionAndOptionsWithIcon(7, "What does a graph with the equation of y = 3x^2 look like?", "a", "b", "c", "d");

        submitButton.addActionListener(this);

        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 20));

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 600));

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel submitPanel = new JPanel(new GridBagLayout());
        submitPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);

        submitPanel.add(submitButton, gbc);

        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainMenu main = new MainMenu();
                main.setExtendedState(JFrame.MAXIMIZED_BOTH);
                main.setVisible(true);
                dispose();
            }
        });

        JPanel returnButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        returnButtonPanel.setBorder(null);
        returnButtonPanel.add(returnButton);
        returnButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        mainPanel.add(returnButtonPanel, BorderLayout.NORTH);

        add(mainPanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        answered = false;
    }

    private void createQuestionAndOptions(int questionIndex, String question, String... options) {
        questionLabels[questionIndex] = new JLabel("Question " + (questionIndex + 1) + ": " + question);
        optionButtons[questionIndex] = new JRadioButton[4];
        optionGroups[questionIndex] = new ButtonGroup();

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        questionPanel.add(questionLabels[questionIndex]);
        for (int i = 0; i < 4; i++) {
            optionButtons[questionIndex][i] = new JRadioButton(options[i]);
            optionGroups[questionIndex].add(optionButtons[questionIndex][i]);
            questionPanel.add(optionButtons[questionIndex][i]);
        }

        containerPanel.add(questionPanel);
    }

    private void createQuestionAndOptionsWithIcon(int questionIndex, String question, String... options) {
        ImageIcon[] icons = new ImageIcon[4];
        ImageIcon[] selectedIcons = new ImageIcon[4];
        questionLabels[questionIndex] = new JLabel("Question " + (questionIndex + 1) + ": " + question);
        optionButtons[questionIndex] = new JRadioButton[4];
        optionGroups[questionIndex] = new ButtonGroup();

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        questionPanel.add(questionLabels[questionIndex]);

        for (int i = 0; i < 4; i++) {
            optionButtons[questionIndex][i] = new JRadioButton(options[i]);
            optionGroups[questionIndex].add(optionButtons[questionIndex][i]);
            questionPanel.add(optionButtons[questionIndex][i]);
            optionButtons[questionIndex][i].addActionListener(e -> {
                for (int j = 0; j < 4; j++) {
                    if (optionButtons[questionIndex][j].isSelected()) {
                        optionButtons[questionIndex][j].setIcon(selectedIcons[j]);
                    } else {
                        optionButtons[questionIndex][j].setIcon(icons[j]);
                    }
                }
            });
        }

        icons[0] = new ImageIcon("src/Screenshot 2023-06-17 215010.png");
        icons[1] = new ImageIcon("src/Screenshot 2023-06-17 215155.png");
        icons[2] = new ImageIcon("src/Screenshot 2023-06-17 215256.png");
        icons[3] = new ImageIcon("src/Screenshot 2023-06-17 215359.png");

        selectedIcons[0] = new ImageIcon("src/Screenshot 2023-06-17 215010_selected.png");
        selectedIcons[1] = new ImageIcon("src/Screenshot 2023-06-17 215155_selected.png");
        selectedIcons[2] = new ImageIcon("src/Screenshot 2023-06-17 215256_selected.png");
        selectedIcons[3] = new ImageIcon("src/Screenshot 2023-06-17 215359_selected.png");

        for (int i = 0; i < 4; i++) {
            optionButtons[questionIndex][i].setIcon(icons[i]);
        }

        containerPanel.add(questionPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            if (!answered) {
                int score = 0;
                for (int i = 0; i < 8; i++) {
                    String selectedOption = "";
                    for (int j = 0; j < 4; j++) {
                        if (optionButtons[i][j].isSelected()) {
                            selectedOption = optionButtons[i][j].getText();
                            break;
                        }
                    }
                    if (selectedOption.equals(correctAnswers[i])) {
                        score++;
                        questionLabels[i].setForeground(Color.GREEN.darker());
                    } else {
                        questionLabels[i].setForeground(Color.RED);
                    }
                }

                JOptionPane.showMessageDialog(this, "Your score is: " + score + " out of 8");

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 4; j++) {
                        optionButtons[i][j].setEnabled(false);
                    }
                }
                answered = true;
            }
        }
    }    
}
