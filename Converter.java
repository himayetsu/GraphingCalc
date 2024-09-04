import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Converter extends JFrame {
	public Converter() {
        setTitle("TI-84");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Graph canvas = new Graph();
        
        getContentPane().add(canvas);
    }
}

