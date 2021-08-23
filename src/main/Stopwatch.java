package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Stopwatch {

    JFrame frame = new JFrame("Stopwatch");
    JButton startButton = new JButton("Start");
    JButton pauseButton = new JButton("Pause");
    JTextField time = new JTextField();
    boolean pause = false;

    int h = 0, m = 0, s = 0;

    public static void main(String[] args) {
        Thread thread = new Thread(){
            public void run(){
                new Stopwatch();
            }
        };
        thread.start();
    }

    Stopwatch() {
        
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception ex){
            
        }
        
        frame.setLayout(null);
        frame.setBounds(200, 100, 395, 280);
        frame.getContentPane().setBackground(Color.black);
        frame.setDefaultCloseOperation(3);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon(getClass().getResource("stop.png")).getImage());

        time.setHorizontalAlignment(JTextField.CENTER);
        time.setBackground(Color.black);
        time.setForeground(Color.green);
        time.setFont(new Font("Arial", Font.BOLD, 55));
        time.setBounds(80, 40, 225, 80);
        time.setEditable(false);
        time.setText("00:00:00");
        time.setBorder(BorderFactory.createBevelBorder(1));

        startButton.setBounds(80, 150, 110, 60);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (startButton.getText().equals("Start")) {
                    pause = false;
                    startButton.setText("Reset");
                    pauseButton.setEnabled(true);
                    resume();
                } else {
                    pause = true;
                    h = 0;
                    m = 0;
                    s = 0;
                    time.setHorizontalAlignment(JTextField.CENTER);
                    time.setText("00:00:00");
                    startButton.setText("Start");
                    pauseButton.setEnabled(false);
                    pauseButton.setText("Pause");
                    try{
                        Thread.sleep(1000);
                    }catch(Exception ex){
                        
                    }
                }
            }
        });

        pauseButton.setBounds(195, 150, 110, 60);
        pauseButton.setFont(new Font("Arial", Font.BOLD, 20));
        pauseButton.setFocusable(false);
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pauseButton.getText().equals("Pause")) {
                    pause = true;
                    time.setHorizontalAlignment(JTextField.CENTER);
                    time.setText(time.getText());
                    pauseButton.setText("Resume");
                    try{
                        Thread.sleep(1000);
                    }catch(Exception ex){
                        
                    }
                } else {
                    pause = false;
                    pauseButton.setText("Pause");
                    resume();
                }
            }
        });

        frame.add(startButton);
        frame.add(pauseButton);
        frame.add(time);
        frame.setVisible(true);
    }

    void resume() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!pause) {
                    s++;
                    if (s > 59) {
                        s = 0;
                        m++;
                    }
                    if (m > 59) {
                        m = 0;
                        h++;
                    }
                    time.setText(parseTime(h) + ":" + parseTime(m) + ":" + parseTime(s));
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        };
        thread.start();
    }
    
    String parseTime(int t){
        return String.format("%02d", t);
    }
}
