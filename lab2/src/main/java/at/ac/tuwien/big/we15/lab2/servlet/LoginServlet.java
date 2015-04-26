package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we15.lab2.api.Avatar;
import at.ac.tuwien.big.we15.lab2.api.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    };
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true); 
		session.setAttribute("counter", 0);

		if(request.getParameter("username") == ""){
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp"); 
			dispatcher.forward(request, response);
		}
		
		User user = new User();
		user.setName(request.getParameter("username"));
		user.setScore(0);
		user.setAvatar(Avatar.getRandomAvatar());
		session.setAttribute("user", user);
		
		User opponent = new User();
		opponent.setAvatar(Avatar.getOpponent(user.getAvatar()));
		opponent.setName(opponent.getAvatar().getName());
		opponent.setScore(0);
		session.setAttribute("opponent", opponent);
		
		session.setAttribute("currentQuestion", null);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/GameServlet"); 
		dispatcher.forward(request, response);
	}

}
