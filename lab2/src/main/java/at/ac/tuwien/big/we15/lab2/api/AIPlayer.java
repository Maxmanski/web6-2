package at.ac.tuwien.big.we15.lab2.api;

import java.util.List;

public interface AIPlayer {

	/**
	 * Lets the Artificial Intelligence try to answer the question.
	 * 
	 * @param q The question for the AI to answer
	 * @return A list of answers picked by the AI
	 */
	public List<Answer> answer(Question q);
}
