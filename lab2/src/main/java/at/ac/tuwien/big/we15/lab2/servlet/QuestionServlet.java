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
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter(); out.println("<html><body>");
		out.println("Hello GET" ); out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//For Testing (not entering the method)
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter(); out.println("<html><body>");
		out.println("Hello POST" ); 
		out.println("</body></html>");

		List<Category> categories = (List<Category>) session.getAttribute("categories");
		int counter = (int) session.getAttribute("counter");
		
		String selectedValue=request.getParameter("question_selection");	
        if(!selectedValue.equals("")){
        	int price =0;
        	try{
        		int selectedQuestionId = Integer.parseInt(selectedValue);
        		for(Category cat : categories){
        			int index=1;
        			for(Question q : cat.getQuestions()){
        				if(q.getId() == selectedQuestionId){
        					//calculate price
        					switch(index){
	        					case 1: price = 100; break;
	        					case 2: price = 200; break;
	        					case 3: price = 500; break;
	        					case 4: price = 750; break;
	        					case 5: price = 1000; break;
        					}
        					q.setAnswered(true);
        					session.setAttribute("currentQuestion", q);
        					session.setAttribute("counter", ++counter);
        					session.setAttribute("price", price);
        					
        					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp"); 
        					dispatcher.forward(request, response);
        					return;
        				}
            			index++;
        			}
        		}
        	}catch(Exception e){
        		System.out.println("Question is not available: " + e.getMessage());
        	}
        }
	}
	

}
