package app;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "addEntries", urlPatterns = {"/addEntries"})
public class AddEntriesServlet extends HttpServlet {

    public static ArrayList<String> entryList = new ArrayList<>();

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, false, 0);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean failed = false;
        int numMissing = 0;
        int numEntries = Integer.parseInt(request.getParameter("numEntries"));

        for (int i = 1; i < numEntries + 1; i++) {

            String tempEntry = request.getParameter("entry" + i).trim();

            if (!tempEntry.equals("")) {
                System.out.println("Adding entry: " + tempEntry + " to the Fishbowl file");

                entryList.add(tempEntry);

            } else {
                failed = true;
                numMissing++;

            }
        }
        processRequest(request, response, failed, numMissing);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
