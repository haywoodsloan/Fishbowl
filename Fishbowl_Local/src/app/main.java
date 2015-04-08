/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author haywoosd
 */
public class main {

    static CountDownLatch latch = new CountDownLatch(1);

    static List<String> phraseList;
    static int pos = 0;

    public static void main(String[] args) throws IOException, InterruptedException, BrokenBarrierException {

        phraseList = Files.readAllLines(Paths.get(System.getProperty("user.home") + "\\desktop\\Fishbowl Entries.txt"));

        GameFrame frame = new GameFrame();

        frame.setVisible(true);

        Collections.shuffle(phraseList);

        frame.pauseGame("The game is paused while waiting to start. Press resume to start.");
        frame.switchTeam(1);

        latch.await();

        frame.nextPhrase();
        frame.startTimer(1, 0, 0);

    }

}
