import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "register-name", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("register.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_name = req.getParameter("name");
        String user_email = req.getParameter("email");
        String user_password = req.getParameter("password");
        String user_username = req.getParameter("username");
        String user_surname = req.getParameter("surname");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JavaSimpleLoginRegister","breznar-test","BrkataSinica");

            PreparedStatement ps;
            ps=conn.prepareStatement("insert into loginregister.users(\"name\", \"surname\", \"username\", \"password\", \"email\") values(?, ?, ?, ?, ?)");

            ps.setString(1, user_name);
            ps.setString(2, user_surname);
            ps.setString(3, user_username);
            ps.setString(4, user_password);
            ps.setString(5, user_email);

            if(ps.executeUpdate() > 0)
            {
                System.out.println("You are sucessfully registered");
            }else{
                System.out.println("Failed in  registration");
            }
        } catch(Exception se) {
            se.printStackTrace();
        }
    }
}