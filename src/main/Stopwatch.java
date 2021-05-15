package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.text.*;
import javax.swing.JTextField;

public class Stopwatch {

    DecimalFormat df = new DecimalFormat("##");
    JFrame frame = new JFrame();
    JButton startButton = new JButton("Start");
    JButton pauseButton = new JButton("Pause");
    JTextField time = new JTextField();
    boolean pause=false;

    int h = 0, m = 0, s = 0;
    
    public static void main(String[] args) {
        new Stopwatch();
    }

    Stopwatch() {
        frame.setLayout(null);
        frame.setBounds(200, 100, 430, 280);
        frame.getContentPane().setBackground(Color.black);
        frame.setDefaultCloseOperation(3);
        frame.setResizable(false);

        time.setHorizontalAlignment(JTextField.CENTER);
        time.setBackground(Color.black);
        time.setForeground(Color.green);
        time.setFont(new Font("Arial", Font.BOLD, 55));
        time.setBounds(105, 40, 225, 80);
        time.setEditable(false);
        time.setText("00:00:00");

        startButton.setBounds(105, 160, 110, 60);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (startButton.getText().equals("Start")) {
                    pause=false;
                    startButton.setText("Reset");
                    pauseButton.setEnabled(true);
                    Thread thread = new Thread() {

                        @Override
                        public void run() {
                            while (true) {
                                if(pause){
                                    break;
                                }
                                s++;
                                if (s > 59) {
                                    s = 0;
                                    m++;
                                }
                                if (m > 59) {
                                    m = 0;
                                    h++;
                                }
                                String hour=String.format("%02d", h);
                                String minute=String.format("%02d", m);
                                String second=String.format("%02d", s);
                                time.setText(hour + ":" + minute + ":" + second);
                                try {
                                    Thread.sleep(1000);
                                } catch (Exception ex) {
                                }
                            }
                        }
                    };
                    thread.start();
                } else {
                    pause=true;
                    h=0;
                    m=0;
                    s=0;
                    time.setText("00:00:00");
                    startButton.setText("Start");
                    pauseButton.setEnabled(false);
                }
            }
        });

        pauseButton.setBounds(220, 160, 110, 60);
        pauseButton.setFont(new Font("Arial", Font.BOLD, 20));
        pauseButton.setFocusable(false);
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(pauseButton.getText().equals("Pause")){
                    pause=true;
                    time.setText(time.getText());
                    pauseButton.setText("Resume");
                }else{
                    pause=false;
                    pauseButton.setText("Pause");
                    Thread thread=new Thread(){
                        @Override
                        public void run(){
                        int newhour=h,newminute=m,newsecond=s;       
                            while(true){
                                if(pause){
                                    break;
                                }
                                newsecond++;
                                if (newsecond > 59) {
                                    newsecond = 0;
                                    newminute++;
                                }
                                if (newminute > 59) {
                                    newminute = 0;
                                    newhour++;
                                }
                                String hour=String.format("%02d", newhour);
                                String minute=String.format("%02d", newminute);
                                String second=String.format("%02d", newsecond);
                                time.setText(hour+":"+minute+":"+second);
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
            }
        });

        frame.add(startButton);
        frame.add(pauseButton);
        frame.add(time);
        frame.setVisible(true);
    }
}
