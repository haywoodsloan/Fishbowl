/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author haywoosd
 */
@WebServlet(name = "addEntries", urlPatterns = {"/addEntries"})
public class AddEntriesServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param failed
     * @param numMissing
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean failed, int numMissing)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Fishbowl!</title>");
            out.println("<link rel=\"stylesheet\" href=\"index.css\">");
            out.println("</head>");
            out.println("<body>");

            if (failed) {
                out.println("<header>Warning: less than 5 entries have been submitted!<br>Missing " + numMissing + " entries! Submit them below:</header>");
                out.println("<form name=\"entryForm\" action=\"addEntries\" method=\"POST\" autocomplete=\"off\">");

                for (int i = 1; i < numMissing + 1; i++) {
                    out.println("<input type=\"text\" name=\"entry" + i + "\" value=\"\" size=\"20\" /><br>");
                }

                out.println("<input type=\"hidden\" name =\"numEntries\" value =\"" + numMissing + "\"/>");
                out.println("<input type=\"submit\" value=\"Submit\" />");

            } else {
                out.println("<h1>All entries have been submitted!</h1>");
            }

            out.println("</body>");
            out.println("</html>");
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
        processRequest(request, response, false, 0);
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

        boolean failed = false;
        int numMissing = 0;
        int numEntries = Integer.parseInt(request.getParameter("numEntries"));

        File entriesFile = new File(System.getProperty("user.home") + "\\desktop\\Fishbowl Entries.txt");

        synchronized (AddEntriesServlet.class) {

            try (FileWriter entryWriter = new FileWriter(entriesFile, true)) {
                String tempEntry;

                for (int i = 1; i < numEntries + 1; i++) {

                    tempEntry = request.getParameter("entry" + i).trim();

                    if (!tempEntry.equals("")) {
                        System.out.println("Adding entry: " + tempEntry + " to the Fishbowl file");

                        entryWriter.write(tempEntry);
                        entryWriter.write(System.getProperty("line.separator"));

                    } else {
                        failed = true;
                        numMissing++;

                    }
                }
            }
        }
        processRequest(request, response, failed, numMissing);
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
