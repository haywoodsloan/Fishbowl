package app;

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

            if (failed) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Fishbowl!</title>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("<link rel=\"stylesheet\" href=\"entries.css\">");
                out.println("</head>");
                out.println("<body>");

                out.println("<header>Warning: less than 5 entries have been submitted!</header>");
                out.println("<header>Missing " + numMissing + " entries! Submit them below:</header>");

                out.println("<form name=\"entryForm\" action=\"addEntries\" method=\"POST\" autocomplete=\"off\">");

                for (int i = 1; i < numMissing + 1; i++) {
                    out.println("<input type=\"text\" name=\"entry" + i + "\" value=\"\" class=\"typeField\" /><br>");
                }

                out.println("<input type=\"hidden\" name =\"numEntries\" value =\"" + numMissing + "\"/>");
                out.println("<input type=\"submit\" value=\"Submit\" class=\"submitButton\"/>");

            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Fishbowl!</title>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("<link rel=\"stylesheet\" href=\"addEntries.css\">");
                out.println("</head>");
                out.println("<body>");

                out.println("<h1>All your entries have been submitted!</h1>");
                out.println("<form method = \"GET\" name=\"playGame\" action=\"play.html\">");
                out.println("<h1>If everyone's entries have been submitted you may now play!</h1>");
                out.println("<input type=\"submit\" value=\"Play Game\" />");
                out.println("</form>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, Boolean.parseBoolean(request.getParameter("failed")), Integer.parseInt(request.getParameter("numMissing")));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        synchronized (AddEntriesServlet.class) {
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
            
            response.sendRedirect("/addEntries?failed=" + failed + "&numMissing=" + numMissing);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
