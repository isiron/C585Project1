/*
 * Name: Ivan Suarez
 * Date: 2/11/2018
 * Class name: CalculatorName.java
 * Purpose: Building the gui, including the key listeners and action listeners.
 */
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CalculatorFrame extends JFrame {
    private JPanel displayPanel;
    private JPanel buttonPanel;
    private JLabel workingLabel;
    private JTextField textField;
    private JButton plusButton;
    private JButton minusButton;
    private JButton multiplyButton;
    private JButton divideButton;
    private JButton modButton;
    private JButton clearButton;
    private JButton ceButton;
    private JButton equalButton;

    //Menu stuff
    private JMenuBar menuBar;
    private JMenu app;
    private JMenu help;
    private JMenuItem exit;
    private JMenuItem about;

    private static final int FRAME_WIDTH = 250;
    private static final int FRAME_HEIGHT = 200;
    private static final int FIELD_LENGTH = 10;

    private CalculatorModel calcModel;
    private ActionListener mouseListener;
    private KeyListener keyListener;

    /**
     * The gui of the application
     */
    public CalculatorFrame(){
        calcModel = new CalculatorModel(this);
        buildMenu();
        buildButtons();
        buildPanel();
        buildFrame();
    }

    /**
     * Creates a mouse listener for all buttons in this class
     */
    private void addClickListeners(){
        mouseListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcModel.handleAction(e.getActionCommand());
                displayPanel.requestFocus();
            }
        };

        plusButton.addActionListener(mouseListener);
        minusButton.addActionListener(mouseListener);
        multiplyButton.addActionListener(mouseListener);
        divideButton.addActionListener(mouseListener);
        modButton.addActionListener(mouseListener);
        clearButton.addActionListener(mouseListener);
        ceButton.addActionListener(mouseListener);
        equalButton.addActionListener(mouseListener);
        exit.addActionListener(mouseListener);
        about.addActionListener(mouseListener);
    }

    /**
     * Creates a key listener and attaches them to all panels in this class
     */
    private void addKeyListeners(){
        keyListener = new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                //if backspace is hit (key code 8) then interpret that as a C for clearing
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    calcModel.handleAction("Back");
                else if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    calcModel.handleAction("=");
                else calcModel.handleAction("" + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        displayPanel.addKeyListener(keyListener);
        buttonPanel.addKeyListener(keyListener);
    }

    /**
     * builds the menu to add to the frame later
     */
    private void buildMenu(){
        menuBar = new JMenuBar();
        app = new JMenu("App");
        help = new JMenu("Help");
        exit = new JMenuItem("Exit");
        about = new JMenuItem("About");
        app.add(exit);
        help.add(about);
        menuBar.add(app);
        menuBar.add(help);
    }

    /**
     * instantiates all buttons and sets them to not focusable
     */
    private  void buildButtons(){
        plusButton = new JButton("+");
        minusButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("/");
        modButton = new JButton("%");
        clearButton = new JButton("C");
        ceButton = new JButton("CE");
        equalButton = new JButton("=");

        //the focus should not stick to the buttons
        plusButton.setFocusPainted(false);
        minusButton.setFocusPainted(false);
        multiplyButton.setFocusPainted(false);
        divideButton.setFocusPainted(false);
        modButton.setFocusPainted(false);
        clearButton.setFocusPainted(false);
        ceButton.setFocusPainted(false);
        equalButton.setFocusPainted(false);
    }

    /**
     * Adds all elements to panels
     */
    private void buildPanel(){
        textField = new JTextField(FIELD_LENGTH);
        displayPanel = new JPanel();
        buttonPanel = new JPanel();
        workingLabel = new JLabel("");

        //the keylistener will handle input, don't edit textfield directly
        textField.setEditable(false);
        textField.setText("0");
        //buttonPanel.setLayout(new GroupLayout());
        addClickListeners();
        addKeyListeners();

        displayPanel.add(menuBar);
        displayPanel.add(workingLabel, BorderLayout.NORTH);
        displayPanel.add(textField, BorderLayout.NORTH);
        buttonPanel.add(plusButton);
        buttonPanel.add(minusButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(modButton);
        buttonPanel.add(ceButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(equalButton);
        //displayPanel.add(new JButton("test"), BorderLayout.CENTER);
    }

    /**
     * Adds panels to frame and changes frame to appropriate settings
     */
    private void buildFrame(){
        setLayout(new BorderLayout());
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuBar);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        add(displayPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        displayPanel.requestFocus();
    }

    public String getTextField()
    {
        return textField.getText();
    }

    public String getWorkingLabel()
    {
        return workingLabel.getText();
    }

    public void setTextField(String text){
        textField.setText(text);
    }

    public void setWorkingLabel(String text){
        workingLabel.setText(text);
    }
}
