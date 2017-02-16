package scjd.jd.myportfolio.myservlets.addressbook;

import scjd.jd.myportfolio.myservlets.addressbook.objects.AllContacts;
import scjd.jd.myportfolio.myservlets.addressbook.objects.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Andrey on 22.02.2016.
 */
public class StartAddressBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Проверка авторизации
        String sendUrl = "/myPortfolio/myServlets/AddressBook/authorization";
        HttpSession session = req.getSession();

        //Отладка
        System.out.println("Logged In " + session.getAttribute("loggedIn"));
        System.out.println("UserName " + session.getAttribute("userName"));

        if (session == null) {
            resp.sendRedirect(sendUrl);
        }
        if(session != null) {
                String loggedIn = (String) session.getAttribute("loggedIn");
                if (loggedIn == null || !loggedIn.equals("true")) {
                    session.setAttribute("loggedIn", "false");
                    resp.sendRedirect(sendUrl);
                }
        }
        ////
        if(session.getAttribute("loggedIn").equals("true")){
            String userName = (String) session.getAttribute("userName");
            String fileName = "D:\\MyDirectory\\" + userName + ".dat";


            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            File file = new File(fileName);
            int index = 0;

            out.println("<html>");
            out.println("<head>");
            //css
            out.println("<link href=\"css/style.css\" rel=\"stylesheet\">\n");
            out.println("<title>");
            out.println("Address Book");
            out.println("</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h2>Адресная книга.</h2><br>");
            out.println("<p align=\"right\">");
            out.println("<form method=\"post\" action=\"AddressBook/killsession\">");
            out.println("<input type=\"hidden\" name=\"logout\" value=\"true\">");
            out.println("<input type=\"submit\" value=\"Выход\">");
            out.println("</form>");
            out.println("</p>");

            out.println("<form method=\"get\" action=\"AddressBook/addContact\">");
            out.println("<input type=\"submit\" value=\"Добавить\">");
            out.println("</form>");

            /*out.println("<form method=\"get\" action=\"AddressBook/addContact\">");
            out.println("<input type=\"submit\" value=\"Редактировать\">");
            out.println("</form>");*/


            if (!file.exists()) {
                out.println("В вашей адресной книге нет контактов<br>");
            } else {
                AllContacts contacts;
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
                    contacts = (AllContacts) ois.readObject();
                    if (contacts.getContacts().isEmpty()) {
                        out.println("В вашей адресной книге нет контактов<br>");
                    } else {
                        out.println("<form method=\"post\" action=\"AddressBook\">");
                        out.println("<input type=\"submit\" value=\"Удалить\">");
                        out.println("<br>");
                        for (Contact contact : contacts.getContacts()) {
                            out.println("<input type=\"checkbox\" name=\"deleteIndex\" value=" + index + ">");
                            out.println("<a href = AddressBook/Contact?index=" + index + ">");
                            out.println(contact.getLastName() + " " + contact.getFirstName() + "<br>");
                            out.println("</a>");
                            index++;
                        }
                        out.println("</form>");
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }


            out.println("</body>");
            out.println("</html>");

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Проверка авторизации
        String sendUrl = "/authorization";
        HttpSession session = req.getSession(true);
        if (session == null) {
            resp.sendRedirect(sendUrl);
        } else {
            String loggedIn = session.getAttribute("loggedIn").toString();
            if (loggedIn == null || !loggedIn.equals("true")) {
                resp.sendRedirect(sendUrl);
            }
        }

        ////

        String userName = (String) session.getAttribute("userName");
        String fileName = "D:\\MyDirectory\\" + userName + ".dat";

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        //чтение из файла с записью контактов в коллекцию
        AllContacts contacts = AllContacts.readContacts(fileName);
        ArrayList<Contact> newContactsList = contacts.getContacts();


        //Html-отображение
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println("Address Book");
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");

        //удаление контактов
        if (req.getParameterValues("deleteIndex") == null) {
            out.println("Контакты не выбраны<br>");
        } else {
            String[] values = req.getParameterValues("deleteIndex");
            int j = 0;
            for (String str : values) {
                int i = Integer.parseInt(str);
                i = i - j;
                newContactsList.remove(i);
                j++;
            }
            out.println("Выбранные контакты удалены<br>");

        }

        //запись обновленой коллекции в файл
        contacts.setContacts(newContactsList);
        AllContacts.writeContacts(fileName, contacts);

        //Автоматический редирект на главную страницу
        try {
            Thread.sleep(2000);
            resp.sendRedirect("/myPortfolio/myServlets/AddressBook");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        out.println("<a href=AddressBook>");
        out.println("Назад<br>");
        out.println("</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
