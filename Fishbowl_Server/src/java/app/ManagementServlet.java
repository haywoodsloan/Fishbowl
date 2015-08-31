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

@WebServlet(name = "manage", urlPatterns = {"/manage"})
public class ManagementServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if ("127.0.0.1".equals(request.getRemoteAddr()) || "0:0:0:0:0:0:0:1".equals(request.getRemoteAddr())) {

                InputStream inputFile = ManagementServlet.class.getResourceAsStream("manageHTML.html");
                Scanner scannedInput = new Scanner(inputFile);

                while (scannedInput.hasNext()) {
                    out.println(scannedInput.nextLine());
                }

                if (GetEntryServlet.phraseList != null) {
                    out.println("<form name=\"modList\" action=\"manage\" method=\"POST\">");
                    out.println("What is the last phrase completed: <select name=\"selectedPhrase\" class=\"scoreInput\">");

                    for (int i = 0; i < GetEntryServlet.phraseList.size(); i++) {
                        out.println("<option>" + GetEntryServlet.phraseList.get(i) + "</option>");
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (null != action) {
            switch (action) {
                case "restartGame":
                    System.out.println("Restarting the game");
                    
                    GetEntryServlet.t1Points = 0;
                    GetEntryServlet.t2Points = 0;
                    
                    GetEntryServlet.paused = true;
                    GetEntryServlet.timeRemain = 60000;
                    
                    GetEntryServlet.activeTeam = 1;
                    
                    GetEntryServlet.phraseList = null;
                    GetEntryServlet.pos = 0;
                    
                    AddEntriesServlet.entryList.clear();
                    break;
                case "Pause":
                    System.out.println("Pausing game");
                    GetEntryServlet.paused = true;
                    GetEntryServlet.pos--;
                    break;
                case "updateTimer":
                    System.out.println("Updating timer");
                    GetEntryServlet.timeRemain = 1000 * Integer.parseInt(request.getParameter("secondsLeft"));
                    break;
                case "t1ScoreUpdate":
                    System.out.println("Updating team 1's score");
                    GetEntryServlet.t1Points = Integer.parseInt(request.getParameter("t1Score"));
                    break;
                case "t2ScoreUpdate":
                    System.out.println("Updating team 2's score");
                    GetEntryServlet.t1Points = Integer.parseInt(request.getParameter("t2Score"));
                    break;
                case "teamUpdate":
                    System.out.println("Changing Teams");
                    if ("t1".equals(request.getParameter("teamGroup"))) {
                        GetEntryServlet.activeTeam = 1;
                    } else {
                        GetEntryServlet.activeTeam = 2;
                    }
                    break;
                case "clearEntries":
                    System.out.println("Clearing all entries");
                    AddEntriesServlet.entryList.clear();
                    break;
                case "reloadList":
                    System.out.println("Reloading the phrase list");
                    GetEntryServlet.loadList();
                    GetEntryServlet.pos = 0;
                    break;
                case "modList":
                    System.out.println("Modifying the phrase list");
                    for (int i = 0; i < GetEntryServlet.phraseList.size(); i++) {

                        if (GetEntryServlet.phraseList.get(i).equals(request.getParameter("selectedPhrase"))) {

                            Random rnd = new Random();
                            String tempPhrase;
                            int tempPos;

                            GetEntryServlet.pos = i + 1;
                            GetEntryServlet.randomize = false;

                            for (int i2 = i + 1; i2 < GetEntryServlet.phraseList.size(); i2++) {
                                tempPhrase = GetEntryServlet.phraseList.get(i2);
                                tempPos = rnd.nextInt(GetEntryServlet.phraseList.size() - i2) + i2;

                                GetEntryServlet.phraseList.set(i2, GetEntryServlet.phraseList.get(tempPos));
                                GetEntryServlet.phraseList.set(tempPos, tempPhrase);
                            }

                            break;
                        }
                    }
                    break;
            }
        }

        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
