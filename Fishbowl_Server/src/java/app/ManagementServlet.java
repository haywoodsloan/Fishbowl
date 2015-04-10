/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author haywoosd
 */
@WebServlet(name = "manage", urlPatterns = {"/manage"})
public class ManagementServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if ("127.0.0.1".equals(request.getRemoteAddr()) || "0:0:0:0:0:0:0:1".equals(request.getRemoteAddr())) {

                InputStream inputFile = ManagementServlet.class.getResourceAsStream("manageHTML.html");
                Scanner scannedInput = new Scanner(inputFile);

                while (scannedInput.hasNext()) {
                    out.println(scannedInput.nextLine());
                }

                if (GetEntryServlet.phraseList != null) {
                    out.println("<form name=\"modList\" action=\"manage\" method=\"POST\">");
                    out.println("What is the last phrase completed: <select name=\"selectedPhrase\" class=\"scoreInput\">");

                    for (String phrase : GetEntryServlet.phraseList) {
                        out.println("<option>" + phrase + "</option>");
                    }

                    out.println("</select>");
                    out.println("<input type=\"hidden\" value=\"modList\" name=\"action\"/>");
                    out.println("<input type=\"submit\" value=\"Update\" />");
                    out.println("</form>");
                }

                out.println("</body>");
                out.println("</html>");

            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Manage Game</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>You do not have permission to access this page</h1>");
                out.println("</body>");
                out.println("</html>");
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
        processRequest(request, response);
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

        String action = request.getParameter("action");

        if ("Pause".equals(action)) {

            System.out.println("Pausing game");
            GetEntryServlet.paused = true;
            GetEntryServlet.pos--;

        } else if ("updateTimer".equals(action)) {

            System.out.println("Updating timer");
            GetEntryServlet.timeRemain = 1000 * Integer.parseInt(request.getParameter("secondsLeft"));

        } else if ("t1ScoreUpdate".equals(action)) {

            System.out.println("Updating team 1's score");
            GetEntryServlet.t1Points = Integer.parseInt(request.getParameter("t1Score"));

        } else if ("t2ScoreUpdate".equals(action)) {

            System.out.println("Updating team 2's score");
            GetEntryServlet.t1Points = Integer.parseInt(request.getParameter("t2Score"));

        } else if ("teamUpdate".equals(action)) {

            System.out.println("Changing Teams");

            if ("t1".equals(request.getParameter("teamGroup"))) {
                GetEntryServlet.activeTeam = 1;
            } else {
                GetEntryServlet.activeTeam = 2;
            }

        } else if ("modList".equals(action)) {

            System.out.println("Modifying the phrase list");

            for (int i = 0; i < GetEntryServlet.phraseList.size(); i++) {

                if (GetEntryServlet.phraseList.get(i).equals(request.getParameter("selectedPhrase"))) {

                    Random rnd = new Random();
                    String tempPhrase;
                    int tempPos;

                    for (int i2 = i + 1; i2 < GetEntryServlet.phraseList.size(); i2++) {
                        tempPhrase = GetEntryServlet.phraseList.get(i2);
                        tempPos = rnd.nextInt(GetEntryServlet.phraseList.size() - i2) + i2;

                        GetEntryServlet.phraseList.set(i2, GetEntryServlet.phraseList.get(tempPos));
                        GetEntryServlet.phraseList.set(tempPos, tempPhrase);
                    }

                    break;
                }
            }
        }

        processRequest(request, response);
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
