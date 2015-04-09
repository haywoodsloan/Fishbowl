/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author haywoosd
 */
@WebServlet(name = "GetEntryServlet", urlPatterns = {"/GetEntryServlet"})
public class GetEntryServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    static List<String> phraseList;

    static int pos = 0;
    static int t1Points = 0;
    static int t2Points = 0;
    static int activeTeam = 1;

    static long lastUpdateTime;
    static long timeRemain = 60000;

    static boolean paused = true;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean post)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if (post) {

                if (pos < phraseList.size()) {
                    out.println(phraseList.get(pos));

                    pos++;
                } else {
                    out.println("&&!!**$$");

                    pos = 0;
                    Collections.shuffle(phraseList);

                    paused = true;
                }

                out.println(t1Points);
                out.println(t2Points);
                out.println(activeTeam);

            } else {

                out.println(t1Points);
                out.println(t2Points);
                out.println(activeTeam);
                out.println(timeRemain);

            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        updateTime();

        if (timeRemain < 0) {
            pos--;
            activeTeam = activeTeam % 2 + 1;

            Random rnd = new Random();

            String tempPhrase = phraseList.get(pos);
            int tempPos = rnd.nextInt(phraseList.size() - pos);

            phraseList.set(pos, phraseList.get(tempPos + pos));
            phraseList.set(tempPos + pos, tempPhrase);

            timeRemain = 0;

            processRequest(request, response, false);

            timeRemain = 60000;

        } else {
            processRequest(request, response, false);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (phraseList == null) {
            phraseList = Files.readAllLines(Paths.get(System.getProperty("user.home") + "\\desktop\\Fishbowl Entries.txt"));
            Collections.shuffle(phraseList);
        }

        if (request.getParameter("increasePoints").equals("true")) {

            if (activeTeam == 1 && !paused) {
                t1Points++;
            } else if (!paused) {
                t2Points++;
            }

        } else {

            if (paused) {
                paused = false;
                lastUpdateTime = System.currentTimeMillis() - (60000 - timeRemain);

            } else {
                pos--;

            }
        }

        processRequest(request, response, true);
    }

    void updateTime() {
        if (!paused) {
            timeRemain = 60000 - (System.currentTimeMillis() - lastUpdateTime);

            if (timeRemain < 0) {
                paused = true;
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
