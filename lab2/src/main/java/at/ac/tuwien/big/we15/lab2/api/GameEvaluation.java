package at.ac.tuwien.big.we15.lab2.api;

import java.util.List;

public interface GameEvaluation {

	/**
	 * Determines if the specified question was answered correctly assuming the specified
	 * answers were given.
	 * 
	 * @param q The question to against which the answers should be checked
	 * @param givenAnswers The player's given answers
	 * @return TRUE if the question was answered completely right or FALSE if it was not
	 */
	public boolean evaluate(Question q, List<Answer> givenAnswers);
	
}
