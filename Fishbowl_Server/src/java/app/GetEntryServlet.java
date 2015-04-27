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

    static List<String> phraseList;

    static int pos = 0;
    static int t1Points = 0;
    static int t2Points = 0;
    static int activeTeam = 1;

    static long lastUpdateTime;
    static long timeRemain = 60000;

    static boolean paused = true;
    static boolean randomize = false;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean post)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if (post) {

                if (!paused) {

                    if (pos < phraseList.size()) {
                        out.println(phraseList.get(pos));

                        pos++;
                    } else {
                        out.println("&&!!**$$");

                        pos = 0;

                        randomize = true;
                        paused = true;
                    }
                }

                out.println(t1Points);
                out.println(t2Points);
                out.println(activeTeam);
                out.println(paused);

            } else {

                out.println(t1Points);
                out.println(t2Points);
                out.println(activeTeam);
                out.println(timeRemain);
                out.println(paused);

            }

        }
    }

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (phraseList == null) {
            loadList();
        }

        if (request.getParameter("decreasePoints") != null) {

            if (pos > 1 && !paused) {

                if (activeTeam == 1 && !paused) {
                    t1Points--;
                } else if (!paused) {
                    t2Points--;
                }

                pos -= 2;

            } else if (paused && timeRemain < 60000) {
                
                if (activeTeam == 1 && timeRemain < 60000) {
                    t1Points--;
                } else if (timeRemain < 60000) {
                    t2Points--;
                }

                paused = false;
                randomize = false;
                
                lastUpdateTime = System.currentTimeMillis() - (60000 - timeRemain);

                pos = phraseList.size() - 1;
                
            } else if (!paused){
                pos--;
            }

        } else if (request.getParameter("increasePoints").equals("true")) {

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

        if (randomize) {
            Collections.shuffle(phraseList);
            
            randomize = false;
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
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public static void loadList() throws IOException {
        phraseList = Files.readAllLines(Paths.get(System.getProperty("user.home") + "\\desktop\\Fishbowl Entries.txt"));
        Collections.shuffle(phraseList);
    }

}
