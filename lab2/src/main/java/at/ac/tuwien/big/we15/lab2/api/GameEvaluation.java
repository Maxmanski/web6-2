package at.ac.tuwien.big.we15.lab2.api;

import java.util.List;

public interface GameEvaluation {

	/**
	 * Sets the question for the GameEvaluation to evaluate.
	 * 
	 * @param q The question to against which the answers should be checked
	 */
	public void setQuestion(Question q);
	
	/**
	 * Determines if the specified question was answered correctly assuming the specified
	 * answers were given.
	 * 
	 * @param givenAnswers The player's given answers
	 * @return TRUE if the question was answered completely right or FALSE if it was not
	 */
	public boolean evaluate(List<Answer> givenAnswers);
	
}
