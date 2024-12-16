import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/guess")
public class GuessNumberServlet extends HttpServlet {
    private static final int MAX_ATTEMPTS = 6;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer targetNumber = (Integer) session.getAttribute("targetNumber");
        Integer attempts = (Integer) session.getAttribute("attempts");
        List<Integer> usedNumbers = (List<Integer>) session.getAttribute("usedNumbers");

        if (targetNumber == null || attempts == null) {
            targetNumber = new Random().nextInt(100) + 1;
            attempts = 0;
            usedNumbers = new ArrayList<>();
            session.setAttribute("targetNumber", targetNumber);
            session.setAttribute("usedNumbers", usedNumbers);
        }

        String userGuessStr = request.getParameter("guess");
        String message = "";
        boolean gameOver = false;
        boolean gameWon = false;

        try {
            int userGuess = Integer.parseInt(userGuessStr);
            attempts++;
            usedNumbers.add(userGuess);

            
            String hintMessage = "";
            if (userGuess == targetNumber) {
                message = "Congratulations! You guessed the number.";
                gameOver = true;
                gameWon = true;  
            } else if (userGuess < targetNumber) {
                message = "Your guess is too small.";
            } else {
                message = "Your guess is too large.";
            }

            
            if (targetNumber % 5 == 0) {
                hintMessage = "The number is divisible by 5.";
            } else if (targetNumber % 2 == 0) {
                hintMessage = "The number is even.";
            } else {
                hintMessage = "The number is odd.";
            }

            
            session.setAttribute("hintMessage", hintMessage);

            if (attempts >= MAX_ATTEMPTS && !gameOver) {
                message = "You have exceeded the maximum attempts. The correct number was " + targetNumber + ".";
                gameOver = true;
            }

            session.setAttribute("attempts", attempts);
            session.setAttribute("usedNumbers", usedNumbers);
            session.setAttribute("gameOver", gameOver);
            session.setAttribute("gameWon", gameWon);  

            if (gameOver) {
                session.removeAttribute("targetNumber");
                session.removeAttribute("attempts");
                session.removeAttribute("usedNumbers");
            }
        } catch (NumberFormatException e) {
            message = "Invalid input. Please enter a number.";
        }

        request.setAttribute("message", message);
        request.setAttribute("gameOver", gameOver);
        request.setAttribute("gameWon", gameWon);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

