package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import at.ac.tuwien.big.we15.lab2.api.User;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleAIPlayer;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleGameEvaluation;

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
		Question question = (Question) session.getAttribute("currentQuestion");
		
		// get the user's answers
		List<Answer> answersList = new ArrayList<Answer>();
		if(request.getParameterValues("answers") != null){
			for(String answerId: request.getParameterValues("answers")){
				try{
		    		int selectedAnswerId = Integer.parseInt(answerId);
		    		for(Answer answer : question.getAllAnswers()){
		        		if(answer.getId() == selectedAnswerId){
		        			answersList.add(answer);
		        		}
		    		}
		    	}catch(Exception e){
		    		System.out.println("Question is not available: " + e.getMessage());
		    	}
				
			}
		}
		
		List<Category> categories = (List<Category>) session.getAttribute("categories");
		List<Question> possibleQuestions = new ArrayList<Question>();
		for(Category c: categories){
			for(Question q: c.getQuestions()){
				if(!q.isAnswered()){
					possibleQuestions.add(q);
				}
			}
		}
		
		Collections.shuffle(possibleQuestions);
		Question aiQuestion = possibleQuestions.get((int)(Math.random() * possibleQuestions.size()));
		aiQuestion.setAnswered(true);
		
		// evaluate questions
		AIPlayer ai = new SimpleAIPlayer(25.0, 75.0);
		List<Answer> answersAiList = ai.answer(aiQuestion);
		GameEvaluation evaluation = new SimpleGameEvaluation(question);
		boolean correctAnswer = evaluation.evaluate(answersList);
		boolean correctAiAnswer = evaluation.evaluate(answersAiList);
		
		User user = (User) session.getAttribute("user");
		User opponent = (User) session.getAttribute("opponent");
		
		if(correctAnswer){
			user.setScore(user.getScore()+question.getValue()*10);
		}else{
			user.setScore(user.getScore()-question.getValue()*10);
		}
		
		if(correctAiAnswer){
			opponent.setScore(opponent.getScore()+aiQuestion.getValue()*10);
		}else{
			opponent.setScore(opponent.getScore()-aiQuestion.getValue()*10);
		}
		
		session.setAttribute("user", user);
		session.setAttribute("opponent", opponent);
		session.setAttribute("categories", categories);
		
		RequestDispatcher dispatcher;
		// after 10 rounds: redirect to winner.jsp
		if(counter == 10){
			if(user.getScore() >= opponent.getScore()){
				session.setAttribute("winner", user);
				session.setAttribute("loser", opponent);
			}else{
				session.setAttribute("winner", opponent);
				session.setAttribute("loser", user);
			}
			dispatcher = getServletContext().getRequestDispatcher("/winner.jsp"); 
		}else{
			//go to the category selection without starting a new game
			dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp"); 
		}
		dispatcher.forward(request, response);
	}

}
