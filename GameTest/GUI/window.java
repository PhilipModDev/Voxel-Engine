package com.dawnfall.engine.GameTest.GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class window extends Frame {
    public static WindowValue windowValue = WindowValue.DEFAULT;
    public window(){

        setTitle("Debugger.");
        setSize(500,320);
        setVisible(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0x134D50));
        setResizable(false);
        setIconImage(loadWindowIcon().getImage());
        setLocation(500,200);
        setLayout(new FlowLayout());

        Button block = new Button("Load Block Scene");
        block.setForeground(Color.WHITE);
        block.setBackground(new Color(0xFF9800));
        block.setFont(new Font("text",Font.BOLD|Font.ITALIC,32));
        add(block);

        Button terrain = new Button("Load Terrain Scene");
        terrain.setForeground(Color.WHITE);
        terrain.setBackground(new Color(0x42864B));
        terrain.setFont(new Font("text",Font.BOLD|Font.ITALIC,32));
        add(terrain);

        Register register = new Register();
        addWindowListener(register);
        terrain.addActionListener(register);
        block.addActionListener(register);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
    private ImageIcon loadWindowIcon(){
        ImageIcon imageIcon;
        return imageIcon = new ImageIcon("star-fall-logo.png");
    }
    class Register extends WindowAdapter implements ActionListener {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
            super.windowClosed(e);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
             if (e.getActionCommand().equals("Load Block Scene")){
                 windowValue = WindowValue.BLOCK;
                 setVisible(false);
             }else if(e.getActionCommand().equals("Load Terrain Scene")){
                 windowValue = WindowValue.TERRAIN;
                 setVisible(false);
             }
        }
    }
}
