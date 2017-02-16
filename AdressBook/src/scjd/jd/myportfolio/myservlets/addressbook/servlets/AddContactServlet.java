package scjd.jd.myportfolio.myservlets.addressbook.servlets;

import scjd.jd.myportfolio.myservlets.addressbook.objects.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by Andrey on 05.03.2016.
 */
public class AddContactServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Проверка авторизации
        String sendUrl = "/authorization";
        HttpSession session = req.getSession(true);
        if(session == null){
            resp.sendRedirect(sendUrl);
        }else{
            String loggedIn = (String) session.getAttribute("loggedIn");
            if(loggedIn == null || !loggedIn.equals("true")){
                resp.sendRedirect(sendUrl);
            }
        }
        ////



        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\" />");
        out.println("<title>");
        out.println("Add Contact");
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");


        out.println("<h3>Здесь вы можете добавить контакт в адресную книгу</h3>");

        out.println("<br>");
        out.println("<p>");
        out.println("<form method=\"post\" action=\"Contact\">");
        out.println("<input type=\"hidden\" name=\"Status\" value=\"add\">");
        out.println("<br> Фамилия: <input type=\"text\" name=\"LastName\">");
        out.println("<br> Имя: <input type=\"text\" name=\"FirstName\">");
        out.println("<br> Телефон: <input type=\"text\" name=\"Phone\">");
        out.println("<br> Електронная почта: <input type=\"text\" name=\"Email\">");
        out.println("<br> <input type=\"submit\" value=\"Добавить\">");
        out.println("<input type=\"reset\" value=\"Сбросить\">");
        out.println("</form>");
        out.println("</p>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Проверка авторизации
        String sendUrl = "/authorization";
        HttpSession session = req.getSession(true);
        if(session == null){
            resp.sendRedirect(sendUrl);
        }else{
            String loggedIn = (String) session.getAttribute("loggedIn");
            if(loggedIn == null || !loggedIn.equals("true")){
                resp.sendRedirect(sendUrl);
            }
        }
        ////

        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        int index = Integer.parseInt(req.getParameter("index"));

        Enumeration<String> en1 = req.getParameterNames();
        Contact contact = new Contact();

        while(en1.hasMoreElements()){
            String str = en1.nextElement();
            switch (str){
                case "FirstName":
                    contact.setFirstName(req.getParameter(str));
                    break;
                case "LastName":
                    contact.setLastName(req.getParameter(str));
                    break;
                case "Phone":
                    contact.setPhone(req.getParameter(str));
                    break;
                case "Email":
                    contact.setEmail(req.getParameter(str));
                    break;
                default:
                    break;
            }
        }

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\" />");
        out.println("<title>");
        out.println("Edit Contact");
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");


        out.println("<h3>Редактирование контакта</h3>");

        out.println("<br>");
        out.println("<p>");
        out.println("<form method=\"post\" action=\"Contact\">");
        out.println("<input type=\"hidden\" name=\"Status\" value=\"edit\">");
        out.println("<input type=\"hidden\" name=\"index\" value=\"" + index + "\">");
        out.println("<br> Фамилия: <input type=\"text\" name=\"LastName\" value=" + contact.getLastName() + ">");
        out.println("<br> Имя: <input type=\"text\" name=\"FirstName\" value=" + contact.getFirstName() + ">");
        out.println("<br> Телефон: <input type=\"text\" name=\"Phone\" value=" + contact.getPhone() + ">");
        out.println("<br> Електронная почта: <input type=\"text\" name=\"Email\" value=" + contact.getEmail() + ">");
        out.println("<br> <input type=\"submit\" value=\"Добавить\">");
        out.println("<input type=\"reset\" value=\"Сбросить\">");
        out.println("</form>");
        out.println("</p>");

        out.println("</body>");
        out.println("</html>");
    }
}
