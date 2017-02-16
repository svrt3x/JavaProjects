package scjd.jd.myportfolio.myservlets.addressbook.servlets;

import scjd.jd.myportfolio.myservlets.addressbook.objects.AllClients;
import scjd.jd.myportfolio.myservlets.addressbook.objects.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by Andrey on 12.05.2016.
 */
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Проверка авторизации
        String sendUrl = "/myPortfolio/myServlets/AddressBook";
        HttpSession session = req.getSession(true);
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


        out.println("<h3>Регистрация</h3>");

        out.println("<br>");
        out.println("<p>");
        out.println("<form method=\"post\" action=\"register\">");
        out.println("<br> Имя пользователя: <input type=\"text\" name=\"userName\">");
        out.println("<br> Пароль: <input type=\"password\" name=\"password\">");
        out.println("<br> Подтверждение пароля: <input type=\"password\" name=\"passwordDouble\">");
        out.println("<br> Имя: <input type=\"text\" name=\"firstName\">");
        out.println("<br> Фамилия: <input type=\"text\" name=\"lastName\">");
        out.println("<br> Електронная почта: <input type=\"text\" name=\"email\">");
        out.println("<br> <input type=\"submit\" value=\"Зарегистрироваться\">");
        out.println("<input type=\"reset\" value=\"Сбросить\">");
        out.println("</form>");
        out.println("</p>");


        out.println("<br>");


        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName;
        String password;
        String passwordDouble;

        String fileNameClients = "D:\\MyDirectory\\Clients.dat";
        String sendUrlOk = "/myPortfolio/myServlets/AddressBook";
        String sendUrlBack = "/myPortfolio/myServlets/AddressBook/authorization";

        //проверка совпадения пришедших паролей между собой (в далнейшем JavaScript)

        //проверка корректности userName(в далнейшем JavaScript)

        //проверка БД на наличие уже зарегестрированого пользователя с таким userName
        userName = req.getParameter("userName");
        if (userName != null && !AllClients.isUserNameInDB(fileNameClients, userName)) {
            //непосредственно регистрация
            Client clientNew = new Client();
            Enumeration<String> enumeration = req.getParameterNames();

            //Принимаем параметры из формы в экземпляр клиента
            while (enumeration.hasMoreElements()) {
                String str = enumeration.nextElement();
                switch (str) {
                    case "userName":
                        clientNew.setUserName(req.getParameter(str));
                        //debugging
                        System.out.println("Username newClient  " + clientNew.getUserName());
                        break;
                    case "password":
                        clientNew.setPassword(req.getParameter(str));
                        //debugging
                        System.out.println("Password newClient  " + clientNew.getPassword());
                        break;
                    case "firsName":
                        clientNew.setFirstName(req.getParameter(str));
                        //debugging
                        System.out.println("FirstName newClient  " + clientNew.getFirstName());
                        break;
                    case "lastName":
                        clientNew.setLastName(req.getParameter(str));
                        //debugging
                        System.out.println("LastName newClient  " + clientNew.getLastName());
                        break;
                    case "email":
                        clientNew.setEmail(req.getParameter(str));
                        //debugging
                        System.out.println("Email newClient  " + clientNew.getEmail());
                        break;

                    case "passwordDouble":
                        break;
                }
            }

            //Запись в БД нового клиента
            AllClients.addClient(fileNameClients, clientNew);

            //Логин клиента
            HttpSession session = req.getSession(true);
            session.setAttribute("loggedIn", "true");
            session.setAttribute("userName", clientNew.getUserName());

            //Отладка
           /* System.out.println("Register->AddressBook");
            System.out.println(session.getAttribute("loggedIn"));*/

            resp.sendRedirect(sendUrlOk);

        } else {
            //отладка
           /* System.out.println("Back again");*/

            resp.sendRedirect(sendUrlBack);

        }

    }
}
