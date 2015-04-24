package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we15.lab2.api.AIPlayer;
import at.ac.tuwien.big.we15.lab2.api.Answer;
import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.GameEvaluation;
import at.ac.tuwien.big.we15.lab2.api.Question;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleAIPlayer;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleAnswer;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleCategory;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleGameEvaluation;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleQuestion;

/**
 * Servlet implementation class EvaluationServlet
 */
@WebServlet("/EvaluationServlet")
public class EvaluationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluationServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		HttpSession session = request.getSession(true); 
		int counter = (int) session.getAttribute("counter");
		
		if(request.getParameterValues("answers") != null){
			for(String answer: request.getParameterValues("answers")){
				// iterating over the specified answers
				// (strings from "1" to "n")
			}
		}
		
		// START OF AI AND EVALUATION TEST
		// TODO REMOVE
		Answer a = new SimpleAnswer("Me Not Good At Answers", null);
		Answer b = new SimpleAnswer("Me Good At Answers", null);
		List<Answer> goodAnswers = new ArrayList<Answer>(), badAnswers = new ArrayList<Answer>();
		goodAnswers.add(b);
		badAnswers.add(a);
		Category c = new SimpleCategory("SSD");
		Question q = new SimpleQuestion(0, "Question", 100, badAnswers, goodAnswers, c);
		a.setQuestion(q);
		b.setQuestion(q);
		
		AIPlayer ai = new SimpleAIPlayer();
		GameEvaluation eval = new SimpleGameEvaluation(q);
		
		for(int i=0; i<10; i++){
			List<Answer> aiAnswers = ai.answer(q);
			if(eval.evaluate(aiAnswers)){
				System.out.println("AI CORRECT");
			}else{
				System.out.println("AI WRONG");
			}
			System.out.println(aiAnswers);
		}
		// END OF AI & EVALUATION TEST
		// TODO REMOVE
		
		//TODO add score to the session
		
		RequestDispatcher dispatcher;
		if(counter == 10){
			// TODO add winner to session
			dispatcher = getServletContext().getRequestDispatcher("/winner.jsp"); 
		}else{
			//go to the category selection without starting a new game
			dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp"); 
		}
		dispatcher.forward(request, response);
	}

}
