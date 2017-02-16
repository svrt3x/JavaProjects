package scjd.jd.myportfolio.myservlets.addressbook.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Andrey on 10.10.2016.
 */
public class KillSessionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Выход из сессии
        HttpSession session = req.getSession(true);
        String sendUrl = "/myPortfolio/myServlets/AddressBook";
        try {
            //debugging
            System.out.println("Enter to killSession Servlet");

            String logout = req.getParameter("logout");

            //debugging
            System.out.println("reading of parameter logout = " + req.getParameter("logout"));

            //PrintWriter out = resp.getWriter();
            //out.println(logout);
            //Thread.sleep(1000);
            if (logout.equals("true")) {
                //debugging
                System.out.println("Enter to if for killing session" );

                session.invalidate();
                //debugging
                System.out.println("Session Invalidate");
                resp.sendRedirect(sendUrl);
                return;
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
