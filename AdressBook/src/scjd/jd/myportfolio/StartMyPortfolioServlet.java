package scjd.jd.myportfolio;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Andrey on 22.02.2016.
 */
public class StartMyPortfolioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();

        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>");
        pw.println("My Portfolio");
        pw.println("</title>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h1>Portfolio</h1> <br>");
        pw.println("<a href = myPortfolio/myServlets/AddressBook>");
        pw.println("Address Book");
        pw.println("</a>");
        pw.println("</body>");
        pw.println("</html>");
    }
}
