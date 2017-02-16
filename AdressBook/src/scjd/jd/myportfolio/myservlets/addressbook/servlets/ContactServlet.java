package scjd.jd.myportfolio.myservlets.addressbook.servlets;

import scjd.jd.myportfolio.myservlets.addressbook.objects.AllContacts;
import scjd.jd.myportfolio.myservlets.addressbook.objects.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by Andrey on 22.02.2016.
 */
public class ContactServlet extends HttpServlet {
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

        String userName = (String) session.getAttribute("userName");
        String fileName = "D:\\MyDirectory\\" + userName + ".dat";

        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        int index;
        Contact contact;

        AllContacts allContacts;
        allContacts = AllContacts.readContacts(fileName);

        index = Integer.parseInt(req.getParameter("index"));
        contact = allContacts.getContacts().get(index);

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\" />");
        out.println("<title>");
        out.println(contact.getLastName() + " " + contact.getFirstName());
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");

        //управляющие кнопки
        out.println("<p>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<form method=\"get\" action=\"/myPortfolio/myServlets/AddressBook\">");
        out.println("<input type=\"submit\" value=\"Контакты\">");
        out.println("</form>");
        out.println("</td>");
        out.println("<td>");
        out.println("<form method=\"post\" action=\"addContact\">");
        out.println("<input type=\"hidden\" name=\"index\" value=" + index + ">");
        out.println("<input type=\"hidden\" name=\"LastName\" value=" + contact.getLastName() + ">");
        out.println("<input type=\"hidden\" name=\"FirstName\" value=" + contact.getFirstName() + ">");
        out.println("<input type=\"hidden\" name=\"Phone\" value=" + contact.getPhone() + ">");
        out.println("<input type=\"hidden\" name=\"Email\" value=" + contact.getEmail() + ">");
        out.println("<input type=\"submit\" value=\"Редактировать\">");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</p>");


        out.println("<p>");
        out.println("<br>Фамилия: " + contact.getLastName());
        out.println("<br>Имя: " + contact.getFirstName());
        out.println("<br>Телефон: " + contact.getPhone());
        out.println("<br>Электронная почта: " + contact.getEmail());
        out.println("<br>");
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

        String userName = (String) session.getAttribute("userName");
        String fileName = "D:\\MyDirectory\\" + userName + ".dat";


        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();


        AllContacts allContacts;
        //Чтение уже существующих контактов из файла
        allContacts = AllContacts.readContacts(fileName);

        //промежуточная переменная для хранения списка контактов (ArrayList)
        ArrayList<Contact> listOfContacts;
        listOfContacts = allContacts.getContacts();

        Contact contact = new Contact();
        Enumeration<String> en1 = req.getParameterNames();

        //Блок, принимающий параметры из формы в поля объекта (контакт) (Создание контакта)
        String status = "add";
        if (status.compareTo(req.getParameter("Status")) == 0) {
            while (en1.hasMoreElements()) {
                String str = en1.nextElement();
                switch (str) {
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
                    case "Status":
                        break;
                }
            }
            //Добавление нового контакта в коллекцию
            listOfContacts.add(contact);
        }




        //Блок, принимающий параметры из формы в поля объекта (контакт) (Редактирование контакта)
        status = "edit";
        if(status.compareTo(req.getParameter("Status")) == 0) {
            while (en1.hasMoreElements()) {
                String str = en1.nextElement();
                switch (str) {
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
                    case "index":
                        break;
                    case "Status":
                        break;
                }
            }

            int index = Integer.parseInt(req.getParameter("index"));
            listOfContacts.remove(index);
            listOfContacts.add(index, contact);
        }


        //Запись обновленного файла
        int index = listOfContacts.indexOf(contact);
        allContacts.setContacts(listOfContacts);
        AllContacts.writeContacts(fileName, allContacts);


        //html - отображение сервлета
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\" />");
        out.println("<title>");
        out.println(contact.getLastName() + " " + contact.getFirstName());
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<p>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<form method=\"get\" action=\"/myPortfolio/myServlets/AddressBook\">");
        out.println("<input type=\"submit\" value=\"Контакты\">");
        out.println("</form>");
        out.println("</td>");
        out.println("<td>");
        out.println("<form method=\"post\" action=\"addContact\">");
        out.println("<input type=\"hidden\" name=\"index\" value=" + index + ">");
        out.println("<input type=\"hidden\" name=\"LastName\" value=" + contact.getLastName() + ">");
        out.println("<input type=\"hidden\" name=\"FirstName\" value=" + contact.getFirstName() + ">");
        out.println("<input type=\"hidden\" name=\"Phone\" value=" + contact.getPhone() + ">");
        out.println("<input type=\"hidden\" name=\"Email\" value=" + contact.getEmail() + ">");
        out.println("<input type=\"submit\" value=\"Редактировать\">");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</p>");

        //Вывод нового контакта на экран
        out.println("<p>");
        out.println("<br>Фамилия: " + contact.getLastName());
        out.println("<br>Имя: " + contact.getFirstName());
        out.println("<br>Телефон: " + contact.getPhone());
        out.println("<br>Электронная почта: " + contact.getEmail());

        out.println("<br>");


        out.println("</p>");

        out.println("</body>");
        out.println("</html>");
    }
}
