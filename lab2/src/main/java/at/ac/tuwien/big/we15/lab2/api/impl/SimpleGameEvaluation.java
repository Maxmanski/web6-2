package at.ac.tuwien.big.we15.lab2.api.impl;

import java.util.List;

import at.ac.tuwien.big.we15.lab2.api.Answer;
import at.ac.tuwien.big.we15.lab2.api.GameEvaluation;
import at.ac.tuwien.big.we15.lab2.api.Question;

public class SimpleGameEvaluation implements GameEvaluation {

	private Question q;
	
	public SimpleGameEvaluation(Question q){
		this.q = q;
	}
	
	@Override
	public boolean evaluate(List<Answer> givenAnswers) {
		List<Answer> rightAnswers, allAnswers;
		rightAnswers = q.getCorrectAnswers();
		allAnswers = q.getAllAnswers();
		
		// when an answer is right but was not chosen: WRONG
		// when an answer was chosen but is not right: WRONG
		for(Answer a: allAnswers){
			if(givenAnswers.contains(a) && !rightAnswers.contains(a)){
				return false;
			}else if(rightAnswers.contains(a) && !givenAnswers.contains(a)){
				return false;
			}
		}
		return true;
	}

	@Override
	public void setQuestion(Question q) {
		this.q = q;
	}

}
