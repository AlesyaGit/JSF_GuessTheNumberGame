import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/hint")
public class HintServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer targetNumber = (Integer) session.getAttribute("targetNumber");


        if (targetNumber != null) {

            String hintMessage = "";

            if (targetNumber % 2 == 0) {
                hintMessage = "The number is even.";
            } else {
                hintMessage = "The number is odd.";
            }

            if (targetNumber % 5 == 0) {
                hintMessage += " The number is divisible by 5.";
            }
            
            response.getWriter().write(hintMessage);
        } else {
            response.getWriter().write("No game in progress.");
        }
    }
}



