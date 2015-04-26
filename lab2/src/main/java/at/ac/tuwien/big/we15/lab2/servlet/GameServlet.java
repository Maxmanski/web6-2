package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.JeopardyFactory;
import at.ac.tuwien.big.we15.lab2.api.Question;
import at.ac.tuwien.big.we15.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.User;
import at.ac.tuwien.big.we15.lab2.api.impl.ServletJeopardyFactory;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleCategory;
/**
 * Servlet implementation class GameServlet
 */
@WebServlet("/GameServlet")
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameServlet() {
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
		ServletContext servletContext = getServletContext();
		JeopardyFactory factory = new ServletJeopardyFactory(servletContext);
		QuestionDataProvider provider = factory.createQuestionDataProvider();
		List<Category> categories = provider.getCategoryData(), tmp = new ArrayList<Category>();
		HttpSession session = request.getSession(true);
		
		if(session.getAttribute("user") == null){
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		
		if(request.getParameter("htmlFormName").equals("newGame")){
			session.setAttribute("counter", 0);
			session.setAttribute("currentQuestion", null);
			session.setAttribute("currentAiQuestion", null);
			User user =(User) session.getAttribute("user");
			User opponent = (User) session.getAttribute("opponent");
			user.setScore(0);
			opponent.setScore(0);
			session.setAttribute("user", user);
			session.setAttribute("opponent", opponent);
		}
		
		for(Category c: categories){
			// valueQuestionMap: KEY: value of the question; VALUE: list of questions with the according value (KEY)
			// freshMap: KEY: value of the questions; VALUE: TRUE, if none of the questions with the specified value (KEY) has been answered yet
			// 													FALSE otherwise
			HashMap<Integer, List<Question>> valueQuestionMap = new HashMap<Integer, List<Question>>();
			HashMap<Integer, Boolean> freshMap = new HashMap<Integer, Boolean>();
			
			// filling up the maps
			for(Question q: c.getQuestions()){
				if(!valueQuestionMap.containsKey(q.getValue())){
					valueQuestionMap.put(q.getValue(), new ArrayList<Question>());
					freshMap.put(q.getValue(), true);
				}
				valueQuestionMap.get(q.getValue()).add(q);
				freshMap.put(q.getValue(), freshMap.get(q.getValue()) && !q.isAnswered());
			}
			
			// choosing a question per category and value randomly
			// if a question with this category and value already has been answered: set the
			// chosen question answered, too
			Category tmpCategory = new SimpleCategory(c.getName(), new ArrayList<Question>());
			for(Integer val: valueQuestionMap.keySet()){
				List<Question> questionList = new ArrayList<Question>(valueQuestionMap.get(val));
				Collections.shuffle(questionList);
				Question q = questionList.get((int)(Math.random() * questionList.size()));
				
				if(!freshMap.get(q.getValue())){
					q.setAnswered(false);
				}
				
				tmpCategory.addQuestion(q);
			}
			List<Question> sortedQuestions = new ArrayList<Question>(), tmpQuestions = tmpCategory.getQuestions();
			
			// sorting the question list (asc.)
			while(!tmpQuestions.isEmpty()){
				Question min = tmpQuestions.get(0);
				for(Question q: tmpQuestions){
					if(q.getValue() < min.getValue()){
						min = q;
					}
				}
				sortedQuestions.add(min);
				tmpQuestions.remove(min);
			}
			
			tmpCategory.setQuestions(sortedQuestions);
			tmp.add(tmpCategory);
		}
		
		// set the session's categories value to the determined stuff
		categories = tmp;
		session.setAttribute("categories", categories);
		tmp = null;
		
		RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/jeopardy.jsp"); 
		dispatcher.forward(request, response);
	}

}
