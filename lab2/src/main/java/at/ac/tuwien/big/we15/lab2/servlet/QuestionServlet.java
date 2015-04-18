package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*ServletContext servletContext = getServletContext();
		JeopardyFactory factory = new ServletJeopardyFactory(servletContext);
		QuestionDataProvider provider = factory.createQuestionDataProvider();
		List<Category> categories = provider.getCategoryData();*/
		HttpSession session = request.getSession(true);
		List<Category> categories = (List<Category>) session.getAttribute("categories");
		
		String selectedValue=request.getParameter("question_selection");	
		//System.out.println(selectedValue);
        if(!selectedValue.equals("")){
        	try{
        		int selectedQuestionId = Integer.parseInt(selectedValue);
        		for(Category cat : categories){
        			for(Question q : cat.getQuestions()){
        				if(q.getId() == selectedQuestionId){
        					session.setAttribute("currentQuestion", q);
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
	}

}
