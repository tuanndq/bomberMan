package uet.CodeToanBug.bomberMan;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import uet.CodeToanBug.bomberMan.gui.Frame;

public class MainMenu extends javax.swing.JFrame {

    public static int index = 0;
    /** Creates new form NewJFrame */
    public MainMenu() throws IOException {
        initComponents();
        super.setTitle("Bomberman");
        super.setResizable(false);
        super.setVisible(true);
        super.setLocationRelativeTo(null);
    }

//    private class PlayGameListener implements ActionListener {
//
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            // Hide this window
//            setVisible(false);
//
//            // Create the game's window and display it
//            //new Frame(MainMenu.this).setVisible(true);
//        }
//
//    }



    private void initComponents() throws IOException {

        onePlayerButton = new JButton();
        highScoreButton = new JButton();
        exitGameButton = new JButton();
        bombermanLabel = new JLabel();
        twoPlayersButton = new JButton();
        authorLabel = new JLabel();
        background = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        getContentPane().setLayout(null);

        onePlayerButton.setText("1 PLAYER");
        getContentPane().add(onePlayerButton);
        onePlayerButton.setBounds(120, 120, 152, 40);
        onePlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onePlayerButtonActionPerformed(evt);
//                index = 1;
            }
        });

        twoPlayersButton.setText("2 PLAYERS");
        getContentPane().add(twoPlayersButton);
        twoPlayersButton.setBounds(120, 180, 152, 36);
        twoPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                twoPlayersButtonActionPerformed(evt);
//                index = 2;
            }
        });

        highScoreButton.setText("HIGH SCORES");
        getContentPane().add(highScoreButton);
        highScoreButton.setBounds(120, 240, 152, 39);
        highScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                highScoreButtonActionPerformed(evt);
            }
        });

        exitGameButton.setText("EXIT GAME");
        getContentPane().add(exitGameButton);
        exitGameButton.setBounds(120, 306, 152, 35);
        exitGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitGameButtonActionPerformed(evt);
            }
        });

        authorLabel.setText("Copyright © 2020 by CodeToanBug");
        authorLabel.setForeground(new Color(255, 255, 255));
        getContentPane().add(authorLabel);
        authorLabel.setBounds(10, 360, 172, 14);

        background.setIcon(new ImageIcon("C:\\Users\\DELL\\Downloads\\rsz_super-bomberman-r-cover.jpg")); // NOI18N
        background.setText("jLabel2");
        getContentPane().add(background);
        background.setBounds(0, 0, 400, 385);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(120, 120, 120)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(onePlayerButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(highScoreButton, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                                        .addComponent(exitGameButton,  GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(twoPlayersButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(58, 58, 58)
                                                .addComponent(bombermanLabel, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(authorLabel)))
                                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(bombermanLabel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(onePlayerButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(twoPlayersButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(highScoreButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(exitGameButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addComponent(authorLabel)
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void onePlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {
        index = 1;
        this.setVisible(false);
        this.setFocusable(false);
        new Frame(1);
    }

    private void twoPlayersButtonActionPerformed(java.awt.event.ActionEvent evt) {
        index = 2;
        this.setVisible(false);
        this.setFocusable(false);
        new Frame(2);
    }

    private void highScoreButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void exitGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify
    private javax.swing.JLabel bombermanLabel;
    private javax.swing.JButton exitGameButton;
    private javax.swing.JButton highScoreButton;
    private javax.swing.JLabel authorLabel;
    private javax.swing.JButton onePlayerButton;
    private javax.swing.JButton twoPlayersButton;
    private javax.swing.JLabel background;
    // End of variables declaration

}