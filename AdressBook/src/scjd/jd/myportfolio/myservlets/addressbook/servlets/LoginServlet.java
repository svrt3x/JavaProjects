package scjd.jd.myportfolio.myservlets.addressbook.servlets;

import scjd.jd.myportfolio.myservlets.addressbook.objects.AllClients;
import scjd.jd.myportfolio.myservlets.addressbook.objects.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Andrey on 20.04.2016.
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Проверка авторизации
        String sendUrl = "/myPortfolio/myServlets/AddressBook";

        HttpSession session = req.getSession();

        if (session != null) {
            String loggedIn = (String) session.getAttribute("loggedIn");
            if (loggedIn.equals("true")) {
                resp.sendRedirect(sendUrl);
            }
        }
        ////



        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\" />");
        out.println("<title>");
        out.println("Login");
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");


        out.println("<h3>Войдите по учетной записи или зарегистрируйтесь</h3>");

        out.println("<br>");
        out.println("<p>");
        out.println("<form method=\"post\" action=\"authorization\">");
        out.println("<br> Логин: <input type=\"text\" name=\"userName\">");
        out.println("<br> Пароль: <input type=\"password\" name=\"password\">");
        out.println("<br> <input type=\"submit\" value=\"Войти\">");
        out.println("<input type=\"reset\" value=\"Сбросить\">");
        out.println("</form>");
        out.println("</p>");

        out.println("<br>");
        out.println("<p>");
        out.println("<form method=\"get\" action=\"register\">");
        out.println("<br> <input type=\"submit\" value=\"Зарегистрироваться\">");
        out.println("</form>");
        out.println("</p>");

        out.println("</body>");
        out.println("</html>");


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileNameClients = "D:\\MyDirectory\\Clients.dat";
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String userName = req.getParameter("userName");
        if (userName != null && !userName.equals("")) {
            if(new File(fileNameClients).exists()){
                AllClients allClients = AllClients.readClients(fileNameClients);
                HashMap<String, Client> clients = allClients.getClients();

                for (Iterator<HashMap.Entry<String, Client>> iterator = clients.entrySet().iterator(); iterator.hasNext(); ) {
                    HashMap.Entry<String, Client> pair = iterator.next();
                    String key = pair.getKey();

                    //debugging
                    System.out.println("key of hash map in doPost LoginServlet is " + key);

                    if (userName.equals(key)) {
                        Client client = pair.getValue();
                        //debugging
                        System.out.println("UserName == key");
                        System.out.println("password in DB is " + client.getPassword());
                        System.out.println("password in form is " + req.getParameter("password"));

                        String password = req.getParameter("password"); // считывание пароля, пришедшего из формы
                        if (password.equals(client.getPassword())) {// Сравнение с паролем из файла (БД)
                            //debugging
                            System.out.println("password in DB ==  password in form");

                            HttpSession session = req.getSession(true);
                            session.setAttribute("loggedIn", "true");
                            session.setAttribute("userName", userName);
                            resp.sendRedirect("/myPortfolio/myServlets/AddressBook");
                        }

                    }
                }
            }else{
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\" />");
                out.println("<title>");
                out.println("Login");
                out.println("</title>");
                out.println("</head>");
                out.println("<body>");


                out.println("<h3>Войдите по учетной записи или зарегистрируйтесь</h3>");
                out.println("<br><h2>Такой учетной записи не существует<h2>");
                out.println("<br>");
                out.println("<p>");
                out.println("<form method=\"post\" action=\"authorization\">");
                out.println("<br> Логин: <input type=\"text\" name=\"userName\">");
                out.println("<br> Пароль: <input type=\"password\" name=\"password\">");
                out.println("<br> <input type=\"submit\" value=\"Войти\">");
                out.println("<input type=\"reset\" value=\"Сбросить\">");
                out.println("</form>");
                out.println("</p>");

                out.println("<br>");
                out.println("<p>");
                out.println("<form method=\"get\" action=\"register\">");
                out.println("<br> <input type=\"submit\" value=\"Зарегистрироваться\">");
                out.println("</form>");
                out.println("</p>");

                out.println("</body>");
                out.println("</html>");
            }


        } else {
            out.println("<h3>Войдите по учетной записи или зарегистрируйтесь</h3>");
            out.println("<br><h2>Такой учетной записи не существует<h2>");
            //resp.sendRedirect("/myPortfolio/myServlets/AddressBook/authorization");
        }
    }
}
