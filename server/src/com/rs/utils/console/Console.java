package com.rs.utils.console;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.text.*;

import com.rs.Settings;
import com.rs.cores.CoresManager;

/**
 * @OriginalAuthor Anthony
 * @Editor King Fox
 */

@SuppressWarnings("serial")
public class Console extends JFrame implements Runnable
{

    private static Console instance;
   
    /**
     * @return creates the instance and the thread
     * makes the console visible
     */
    public static Console getInstance() {
        if(instance == null) {
            instance = new Console();
            new Thread(instance, "Console").start();
            instance.setVisible(true);
        }
        return instance;
    }
   
    public static JTextPane consolePane;
    public JScrollPane consoleScrollPane;
    public JTextField commandsField;

    private StyledDocument document;
    private StyleContext styleContext;
    
    public Console() {
        setTitle(""+Settings.SERVER_NAME+" Console");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        consoleScrollPane = new JScrollPane();
        consolePane = new JTextPane();
        commandsField = new JTextField();
        commandsField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                performCommand(evt);
            }
        });
        styleContext = new StyleContext();
        document = (StyledDocument) consolePane.getDocument();
        consolePane.setEditable(false);
        consoleScrollPane.setViewportView(consolePane);
        
        consolePane.setBackground(new Color(255, 255, 255)); // Set's the background color
        consolePane.setFont(new Font("SAN_SERIF", Font.PLAIN, 11)); // Set Font Type & Size
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(consoleScrollPane, GroupLayout.PREFERRED_SIZE, 650, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(commandsField, GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(consoleScrollPane, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(commandsField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        pack();
    }
   
    /**
     * Clears the console at intervals to keep it from getting lengthy
     */
    public static void cleanConsoleTask() {
        CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (consolePane.getText() != "") {
                	consolePane.setText("");
                    System.out.println("Console Cleared.");
                }
            }
        }, 30, 30, TimeUnit.MINUTES);
    }
    
    /**
     * @param evt
     * 		listens for and executes the command
     */
    public void performCommand(ActionEvent evt) {
        String command = commandsField.getText();
        if (command == null) {
            return;
        }
        if (!ConsoleCommands.processCommand(command)) {
            System.out.println("Please type a valid command.");
        }
        commandsField.setText(null);
    }   
   
    /**
     * @param colour of the text
     * @param message to be sent to the console
     */
    public void append(final String colour, final String message) {       
        try {
            document.insertString(document.getLength(), message + "\r\n", styleContext.getStyle(colour));
            consolePane.setCaretPosition(consolePane.getDocument().getLength());
        } catch (BadLocationException e) {
           
        }
    }
   
   
    @Override
    public void run() {
       
    }
   
}