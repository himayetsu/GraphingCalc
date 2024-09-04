import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;


public class Graph extends JComponent {

	DecimalFormat df = new DecimalFormat("#.####");

	public JButton button;
	public JButton rando;
	public static JTextField textField;
	public JButton returnButton;
	public JButton filechooser;
	public static String equation;
	public double zoomLevel = 1.0;
	public boolean update;
	public double numberLineIncrement = 1.0;


	public Graph() {
		setLayout(null);
		button = new JButton("Update Equation");
		rando = new JButton("Randomize Equation");
		filechooser = new JButton ("Import an equation");
		returnButton = new JButton("Return to Main Menu");
		textField = new JTextField(20);
		textField.setOpaque(false);
		button.setBounds(220, 10, 150, 30);
		rando.setBounds(30, 700, 180, 30);
		returnButton.setBounds(10, 50, 180, 30);
		textField.setBounds(10, 10, 200, 30);
		filechooser.setBounds(200, 50, 170, 30);
		
		update = false;
		
		//Add actions to the buttons when they are pressed
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update = true;
				repaint();
				equation = textField.getText();
				System.out.println("Equation stored: " + equation);

			}
		});
		rando.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				equation = randomizer();
				System.out.println("Equation stored: " + equation);
				textField.setText(equation);
				textField.setOpaque(false);
				update = true;
				repaint();
			}
		});
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenu main = new MainMenu();
				Converter conv = new Converter();
				main.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				main.setVisible(true);
				SwingUtilities.windowForComponent(button).dispose();
			}
		});
		filechooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
		        int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						Scanner scanner = new Scanner(file);
						StringBuilder sb = new StringBuilder();
						while (scanner.hasNextLine()) {
							sb.append(scanner.nextLine());
						}
						equation = sb.toString();
						textField.setText(sb.toString());
						scanner.close();
					} catch (FileNotFoundException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int notches = e.getWheelRotation();
				if (notches < 0) {
					// Zoom in
					zoomLevel *= 1.1;
				} else {
					// Zoom out
					zoomLevel /= 1.1;
				}
				repaint();
				update = true;
			}
		});
		
		//add them to the screen
		add(returnButton);
		add(button);
		add(rando);
		add(textField);
		add(filechooser);	
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Gets the center X and Y pixels of the screen
		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;
		double visibleRange = 50 / zoomLevel;
		double increment = visibleRange / getWidth()*5; // Increment based on the number of iterations
		Graphics2D g2d = (Graphics2D) g;

		// Using center pixels and zoomlevel to calculate visible pixels
		double startX = -centerX + (centerX - (centerX / zoomLevel));
		double endX = centerX - (centerX - (centerX / zoomLevel));

		// Calculate number line increments based on zoom level
		if(zoomLevel>=1) {
			numberLineIncrement = 1/((((int)zoomLevel)/Math.pow(10, String.valueOf(zoomLevel).length()))*Math.pow(10, String.valueOf(zoomLevel).length())/2);
		}
		else {
			numberLineIncrement = 2*1/zoomLevel;
		}

		// Draw axes lines
		g2d.setStroke(new BasicStroke(1.0f));
		g2d.setColor(Color.GRAY);

		// X-axis number lines
		double startLineX = Math.ceil(startX / numberLineIncrement) * numberLineIncrement;
		for (double x = startLineX; x <= endX; x += numberLineIncrement) {
			int pixelX = (int) (((x * 20) * zoomLevel) + centerX);
			g2d.drawLine(pixelX, centerY - 3, pixelX, centerY + 3);
			String number = df.format(x);
			int stringWidth = g2d.getFontMetrics().stringWidth(number);
			g2d.drawString(number, pixelX - (stringWidth / 2), centerY + 15);
		}

		// Y-axis number lines
		double startY = -centerY + (centerY - (centerY / zoomLevel));
		double endY = centerY - (centerY - (centerY / zoomLevel));
		double startLineY = Math.ceil(startY / numberLineIncrement) * numberLineIncrement;
		for (double y = startLineY; y <= endY; y += numberLineIncrement) {
			int pixelY = (int) (((-y * 20) * zoomLevel) + centerY);
			g2d.drawLine(centerX - 3, pixelY, centerX + 3, pixelY);
			String number = df.format(y);
			int stringWidth = g2d.getFontMetrics().stringWidth(number);
			g2d.drawString(number, centerX + 5, pixelY + (stringWidth / 2));
		}
		
		g2d.setColor(Color.BLACK);
		g2d.drawLine(0, centerY + 1, getWidth(), centerY + 1);
		g2d.drawLine(centerX, 0, centerX, getHeight());
		
		if (update) {
			// Repeated code to update whenever an equation is updated
			startX = -centerX + (centerX - (centerX / zoomLevel));
			endX = centerX - (centerX - (centerX / zoomLevel));
			centerX = getWidth() / 2;
			centerY = getHeight() / 2;

			// Determine the appropriate increment for number lines based on zoom level
			if(zoomLevel>=1) numberLineIncrement = 1/((((int)zoomLevel)/Math.pow(10, String.valueOf(zoomLevel).length()))*Math.pow(10, String.valueOf(zoomLevel).length())/2);
			else numberLineIncrement = 2*1/zoomLevel;

			// Draw number lines on the axes
			g2d.setStroke(new BasicStroke(1.0f));
			g2d.setColor(Color.GRAY);

			// X-axis number lines
			startLineX = Math.ceil(startX / numberLineIncrement) * numberLineIncrement;
			for (double x = startLineX; x <= endX; x += numberLineIncrement) {
				int pixelX = (int) (((x * 20) * zoomLevel) + centerX);
				g2d.drawLine(pixelX, centerY - 3, pixelX, centerY + 3);
				String number = df.format(x);
				int stringWidth = g2d.getFontMetrics().stringWidth(number);
				g2d.drawString(number, pixelX - (stringWidth / 2), centerY + 15);
			}

			// Y-axis number lines
			startY = -centerY + (centerY - (centerY / zoomLevel));
			endY = centerY - (centerY - (centerY / zoomLevel));
			startLineY = Math.ceil(startY / numberLineIncrement) * numberLineIncrement;
			for (double y = startLineY; y <= endY; y += numberLineIncrement) {
				int pixelY = (int) (((-y * 20) * zoomLevel) + centerY);
				g2d.drawLine(centerX - 3, pixelY, centerX + 3, pixelY);
				String number = df.format(y);
				int stringWidth = g2d.getFontMetrics().stringWidth(number);
				g2d.drawString(number, centerX + 5, pixelY + (stringWidth / 2));
			}
			
			//Evaluating the expression (if it's not empty)
			if (equation != null) {
				double prevy = Integer.MAX_VALUE;
				double prevx = Integer.MAX_VALUE;

				// Graphing actual graph
				for (double x = startX; x <= endX; x += increment) {
					String orig = equation;
					String sub = orig.replaceAll("x", "*(" + df.format(x) + ")");
					double y = 0;

					// Evaluates a Y value for every X
					y = eval(sub);

					int pixelX = (int) (((x * 20) * zoomLevel) + centerX);
					int pixelY = (int) (((-y * 20) * zoomLevel) + centerY);

					if (Double.isNaN(y)) {
						continue; // If the Y value is undefined, skip the drawing of the point
					}
					else g.fillOval(pixelX, pixelY, 5, 5);

					g2d.setStroke(new BasicStroke(1.0f));
					g2d.drawLine((int) (prevx), (int) prevy, (int) pixelX + 2, (int) pixelY + 2);
					prevy = pixelY + 2;
					prevx = pixelX + 2;
				}
			}
			g2d.setColor(Color.BLACK);
			g2d.drawLine(0, centerY + 1, getWidth(), centerY + 1);
			g2d.drawLine(centerX, 0, centerX, getHeight());

			update = false; // After the graph is plotted, set update to false to not graph again

		}
	}




	//Function to randomize an expression
	public static String randomizer() {
		int ranfunc = (int)(Math.floor(Math.random()*7));
		if(ranfunc==0) {
			int posneg = Math.random()<=0.5?-1:1;
			int m = (int)(Math.floor(Math.random()*10));
			int b = (int)(Math.floor(Math.random()*20));

			return posneg*m+"x"+(posneg>=0?"+":"-")+b;
		}
		if(ranfunc==1) {
			int posneg = Math.random()<=0.5?-1:1;
			int a = (int)(Math.floor(Math.random()*10)+1);
			int b = (int)(Math.floor(Math.random()*10)+1);
			int c = (int)(Math.floor(Math.random()*20)+1);

			return posneg*a+"x^2"+(posneg>=0?"+":"-")+b+"x"+(posneg>=0?"+":"-")+c;
		}
		if(ranfunc==2) {
			int posneg = Math.random()<=0.5?-1:1;
			int a = (int)(Math.floor(Math.random()*10)+1);
			int b = (int)(Math.floor(Math.random()*10)+1);
			int c = (int)(Math.floor(Math.random()*20)+1);
			int d = (int)(Math.floor(Math.random()*20)+1);

			return posneg*a+"x^3"+(posneg>=0?"+":"-")+b+"x^2"+(posneg>=0?"+":"-")+c+"x"+(posneg>=0?"+":"-")+d;
		}
		if(ranfunc==3) {
			int posneg = Math.random()<=0.5?-1:1;
			int e = (int)(Math.floor(Math.random()*6));

			return posneg*e+"^1x";
		}
		if(ranfunc==4) {
			int posneg = Math.random()<=0.5?-1:1;
			int m = (int)(Math.floor(Math.random()*10));
			int b = (int)(Math.floor(Math.random()*20));

			return "|"+posneg*m+"x|"+(posneg>=0?"+":"-")+b;
		}
		if(ranfunc==5) {
			int posneg = Math.random()<=0.5?-1:1;
			int m = (int)(Math.floor(Math.random()*6));
			int b = (int)(Math.floor(Math.random()*20));

			return 1+"/("+posneg*m+"x"+(posneg>=0?"+":"-")+b+")";
		}
		if(ranfunc==6) {
			int posneg = Math.random()<=0.5?-1:1;
			int m = (int)(Math.floor(Math.random()*6));
			int b = (int)(Math.floor(Math.random()*20));

			return "sqrt("+posneg*m+"x"+(posneg>=0?"+":"-")+b+")";
		}
		return "";
	}

	
	// Function to evaluate the expression with X replaced with a number
	public static double eval(final String str) {
		try {
			return new Object() {
				int pos = -1;
				int ch;
				void nextChar() {
					ch = (++pos<str.length())?str.charAt(pos):-1;
				}
				boolean eat(int charToEat) {
					while (ch==' ') {
						nextChar();
					}
					if (ch==charToEat) {
						nextChar();
						return true;
					}
					return false;
				}

				double parse() {
					nextChar();
					double x = parseExpression();
					if (pos < str.length()) {
						throw new RuntimeException("Unexpected: " + (char) ch);
					}
					return x;
				}

				double parseExpression() {
					double x = parseTerm();
					for (; ; ) {
						if (eat('+')) {
							x += parseTerm(); // addition
						}
						else if (eat('-')) {
							x -= parseTerm(); // subtraction
						}
						else return x;
					}
				}

				double parseTerm() {
					double x = parseFactor();
					for (; ; ) {
						if (eat('*')) {
							x *= parseFactor(); // multiplication
						}
						else if (eat('/')) {
							x /= parseFactor(); // division
						}
						else return x;
					}
				}

				double parseFactor() {
					if (eat('+')) {
						return +parseFactor(); // unary plus
					}
					if (eat('-')) {
						return -parseFactor(); // unary minus
					}

					double x;
					int startPos = this.pos;
					if (eat('(')) { // parentheses
						x = parseExpression();
						if (!eat(')')) throw new RuntimeException("Missing ')'");
					} else if (eat('|')) { // absolute value
						x = parseExpression();
						if (!eat('|')) throw new RuntimeException("Missing '|'");
						x = Math.abs(x);
					} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
						while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
						x = Double.parseDouble(str.substring(startPos, this.pos));
					} else if (ch >= 'a' && ch <= 'z') { // functions
						while (ch >= 'a' && ch <= 'z') nextChar();
						String func = str.substring(startPos, this.pos);
						if (eat('(')) {
							x = parseExpression();
							if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
						} else {
							x = parseFactor();
						}
						if (func.equals("sqrt")) x = Math.sqrt(x);
						else if (func.equals("sin")) x = Math.sin((x));
						else if (func.equals("cos")) x = Math.cos((x));
						else if (func.equals("tan")) x = Math.tan((x));
						else throw new RuntimeException("Unknown function: " + func);
					} else {
						throw new RuntimeException("Unexpected: " + (char) ch);
					}

					if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

					return x;
				}
			}.parse();
			
		} catch (Exception e) { // If the function is not valid, replace equation with "0" and tell the user
			equation = "0";
			textField.setText(textField.getText()+ " IS INVALID");
		}
		return 0;
	}
}
