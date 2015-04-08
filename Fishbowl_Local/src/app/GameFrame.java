/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author haywoosd
 */
public class GameFrame extends javax.swing.JFrame {

    /**
     * Creates new form GameFrame
     */
    int t1PointsInt = 0;
    int t2PointsInt = 0;
    int activeTeam = 1;

    long lastNextPhrase = System.currentTimeMillis();

    TimerThread timerThread;

    public GameFrame() {
        initComponents();
        resumeBtn.setVisible(false);

        t1Name.setText(JOptionPane.showInputDialog(this, "What is the name for team 1?", "Naming Teams", JOptionPane.QUESTION_MESSAGE));
        t2Name.setText(JOptionPane.showInputDialog(this, "What is the name for team 2?", "Naming Teams", JOptionPane.QUESTION_MESSAGE));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        t1Name = new javax.swing.JLabel();
        t2Name = new javax.swing.JLabel();
        t1Points = new javax.swing.JLabel();
        t2Points = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        timer = new javax.swing.JLabel();
        editBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        phrase = new javax.swing.JTextArea();
        resumeBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setExtendedState(6);
        setName("Fishbowl!"); // NOI18N
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        t1Name.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        t1Name.setText("Team 1 Name");

        t2Name.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        t2Name.setText("Team 2 Name");

        t1Points.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        t1Points.setText("0 points");

        t2Points.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        t2Points.setText("0 points");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Current Phrase:");

        timer.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        timer.setForeground(new java.awt.Color(0, 0, 0));
        timer.setText("0:00:00");

        editBtn.setBackground(new java.awt.Color(255, 255, 255));
        editBtn.setForeground(new java.awt.Color(0, 0, 0));
        editBtn.setText("Edit");
        editBtn.setBorder(null);
        editBtn.setBorderPainted(false);
        editBtn.setFocusable(false);
        editBtn.setOpaque(false);
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(102, 102, 102));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setFont(new java.awt.Font("Dialog", 0, 100)); // NOI18N
        jScrollPane1.setOpaque(false);

        phrase.setBackground(new java.awt.Color(255, 255, 255));
        phrase.setColumns(20);
        phrase.setFont(new java.awt.Font("Dialog", 0, 100)); // NOI18N
        phrase.setForeground(new java.awt.Color(0, 0, 0));
        phrase.setLineWrap(true);
        phrase.setRows(5);
        phrase.setText("Test Phrase");
        phrase.setWrapStyleWord(true);
        phrase.setFocusable(false);
        phrase.setOpaque(false);
        jScrollPane1.setViewportView(phrase);

        resumeBtn.setBackground(new java.awt.Color(255, 255, 255));
        resumeBtn.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        resumeBtn.setText("Resume");
        resumeBtn.setBorder(null);
        resumeBtn.setOpaque(false);
        resumeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(t2Points)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resumeBtn)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(editBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(t2Name)
                                    .addComponent(t1Points)
                                    .addComponent(jLabel2))
                                .addGap(0, 808, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(t1Name)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(timer)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(t1Name)
                    .addComponent(timer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(t1Points)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(t2Name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(t2Points)
                        .addContainerGap())
                    .addComponent(editBtn, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resumeBtn, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed

        if (timerThread != null && !timerThread.paused) {

            pauseGame("Game is paused while editing. Press resume when ready to continue");

            EditDialog dialog = new EditDialog(this, true, t1Name.getText(), t2Name.getText(), t1PointsInt, t2PointsInt);

            dialog.setLocationRelativeTo(this);
            dialog.setTitle("Edit");

            dialog.setVisible(true);

            if (dialog.submitted) {
                t1Name.setText(dialog.t1NameString);
                t2Name.setText(dialog.t2NameString);

                t1PointsInt = dialog.t1PointsInt;
                t2PointsInt = dialog.t2PointsInt;

                updateScores();
            }

            new Thread() {

                @Override
                public void run() {

                    try {
                        main.latch.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    phrase.setText(main.phraseList.get(main.pos - 1));
                }
            }.start();

        } else {

            EditDialog dialog = new EditDialog(this, true, t1Name.getText(), t2Name.getText(), t1PointsInt, t2PointsInt);

            dialog.setLocationRelativeTo(this);
            dialog.setTitle("Edit");

            dialog.setVisible(true);

            if (dialog.submitted) {
                t1Name.setText(dialog.t1NameString);
                t2Name.setText(dialog.t2NameString);

                t1PointsInt = dialog.t1PointsInt;
                t2PointsInt = dialog.t2PointsInt;

                updateScores();
            }
        }
    }//GEN-LAST:event_editBtnActionPerformed

    private void resumeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resumeBtnActionPerformed

        if (timerThread != null) {
            timerThread.paused = false;
        }

        main.latch.countDown();
        main.latch = new CountDownLatch(1);

        resumeBtn.setVisible(false);

        phrase.setForeground(Color.black);

        requestFocus();
    }//GEN-LAST:event_resumeBtnActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (timerThread != null && !timerThread.paused && (System.currentTimeMillis() - lastNextPhrase) > 750) {

            lastNextPhrase = System.currentTimeMillis();

            if (activeTeam == 1) {
                t1PointsInt++;
            } else {
                t2PointsInt++;
            }

            updateScores();

            if (!nextPhrase()) {
                pauseGame("You have reached the end of the list. Press resume when ready to move to the next stage");

                Collections.shuffle(main.phraseList);
                main.pos = 0;

                new Thread() {

                    @Override
                    public void run() {

                        try {
                            main.latch.await();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        nextPhrase();

                    }
                }.start();
            }

        }
    }//GEN-LAST:event_formKeyPressed

    public void switchTeam() {

        if (activeTeam == 1) {
            switchTeam(2);
        } else {
            switchTeam(1);
        }

    }

    public void switchTeam(int team) {

        if (team == 1) {
            t2Name.setForeground(Color.LIGHT_GRAY);
            t2Points.setForeground(Color.LIGHT_GRAY);

            t1Name.setForeground(Color.BLACK);
            t1Points.setForeground(Color.BLACK);

            activeTeam = 1;
        } else if (team == 2) {
            t1Name.setForeground(Color.LIGHT_GRAY);
            t1Points.setForeground(Color.LIGHT_GRAY);

            t2Name.setForeground(Color.BLACK);
            t2Points.setForeground(Color.BLACK);

            activeTeam = 2;
        }
    }

    public void pauseGame(String message) {
        phrase.setForeground(Color.red);
        phrase.setText(message);

        resumeBtn.setVisible(true);

        if (timerThread != null) {
            timerThread.pauseTimer();
        }

    }

    public void updateScores() {

        t1Points.setText(String.valueOf(t1PointsInt) + " points");
        t2Points.setText(String.valueOf(t2PointsInt) + " points");

    }

    public boolean nextPhrase() {

        if (main.pos < main.phraseList.size()) {
            phrase.setText(main.phraseList.get(main.pos));
            main.pos++;

            return true;
        } else {
            main.pos = 0;
            
            return false;
        }
    }

    public void startTimer(int minute, int second, int milisecond) {
        timerThread = new TimerThread(minute, second, milisecond);
        timerThread.start();

    }

    public class TimerThread extends Thread {

        long timeDuration;
        long lastTime;
        long timeRemain;

        boolean paused = false;

        CountDownLatch timerLatch;

        TimerThread(int minute, int second, int milisecond) {
            timeDuration = minute * 1000 * 60 + second * 1000 + milisecond;

        }

        @Override
        public void run() {

            lastTime = System.currentTimeMillis();

            while (true) {

                if (!paused) {

                    timeRemain = timeDuration - (System.currentTimeMillis() - lastTime);

                    updateTime();

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                    try {
                        timerLatch.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    lastTime = System.currentTimeMillis() - (timeDuration - timeRemain);
                }

                if (timeRemain < 0) {

                    pauseGame("Time is up! Switch teams and press resume to continue");
                    switchTeam();

                    try {
                        timerLatch.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Random rnd = new Random();

                    String tempPhrase = main.phraseList.get(main.pos - 1);

                    int newPos = rnd.nextInt(main.phraseList.size() - (main.pos - 1));

                    main.phraseList.set(main.pos - 1, main.phraseList.get(newPos + main.pos - 1));
                    main.phraseList.set(newPos + main.pos - 1, tempPhrase);

                    phrase.setText(main.phraseList.get(main.pos - 1));

                    lastTime = System.currentTimeMillis();
                }
            }
        }

        public void pauseTimer() {

            paused = true;

            timerLatch = main.latch;
        }

        public void updateTime() {

            String minutes = String.valueOf(timeRemain / (1000 * 60));
            String seconds = String.valueOf(timeRemain % (1000 * 60) / 1000);
            String miliseconds = String.valueOf(timeRemain % (1000 * 60) % 1000 * 60 / 1000);

            if (seconds.length() < 2) {
                seconds = "0" + seconds;
            }

            if (miliseconds.length() < 2) {
                miliseconds = "0" + miliseconds;
            }

            if (minutes.equals("0")) {
                timer.setText(seconds + ":" + miliseconds);
            } else {
                timer.setText(minutes + ":" + seconds + ":" + miliseconds);
            }

        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editBtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea phrase;
    private javax.swing.JButton resumeBtn;
    public javax.swing.JLabel t1Name;
    private javax.swing.JLabel t1Points;
    public javax.swing.JLabel t2Name;
    private javax.swing.JLabel t2Points;
    private javax.swing.JLabel timer;
    // End of variables declaration//GEN-END:variables
}