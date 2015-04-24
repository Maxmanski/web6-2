package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.Question;

/**
 * Servlet implementation class QuestionServlet
 */
@WebServlet("/QuestionServlet")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//For Testing (not entering the method)
		HttpSession session = request.getSession(true);

		List<Category> categories = (List<Category>) session.getAttribute("categories");
		int counter = (int) session.getAttribute("counter");
		
		String selectedValue = request.getParameter("question_selection");
		
        if(selectedValue != null && !selectedValue.equals("")){
        	try{
        		int selectedQuestionId = Integer.parseInt(selectedValue);
        		
        		// search for chosen question
        		// & if found: redirect to question.jsp
        		for(Category cat : categories){
        			for(Question q : cat.getQuestions()){
        				if(q.getId() == selectedQuestionId){
        					q.setAnswered(true);
        					session.setAttribute("categories", categories);
        					session.setAttribute("currentAnswers", q.getAllAnswers());
        					session.setAttribute("currentQuestion", q);
        					session.setAttribute("counter", ++counter);
        					session.setAttribute("price", q.getValue()*10);
        					session.setAttribute("timeleftvalue", q.getValue());

        					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp"); 
        					dispatcher.forward(request, response);
        					return;
        				}
        			}
        		}
        	}catch(Exception e){
        		System.out.println("Question is not available: " + e.getMessage());
        	}
        }
        
        // if selected question couldn't be found or stuff: redirect to jeopardy.jsp
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp"); 
		dispatcher.forward(request, response);
		return;
	}
	

}
