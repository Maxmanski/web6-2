package at.ac.tuwien.big.we15.lab2.api.impl;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.big.we15.lab2.api.AIPlayer;
import at.ac.tuwien.big.we15.lab2.api.Answer;
import at.ac.tuwien.big.we15.lab2.api.Question;

/**
 * A simple, chance-based AI for answering questions.
 */
public class SimpleAIPlayer implements AIPlayer{

	private double incorrectProbability, correctProbability;
	
	public SimpleAIPlayer(){
		this.correctProbability = 66.6;
		this.incorrectProbability = 33.3;
	}
	
	public SimpleAIPlayer(double incorrectP, double correctP){
		this.incorrectProbability = incorrectP % 100;
		this.correctProbability = correctP % 100;
	}
	
	@Override
	public List<Answer> answer(Question q) {
		List<Answer> correctAnswers, allAnswers, pickedAnswers;
		correctAnswers = q.getCorrectAnswers();
		allAnswers = q.getAllAnswers();
		pickedAnswers = new ArrayList<Answer>();
		
		for(Answer a: allAnswers){
			double rnd = Math.random() * 100;
			
			if(correctAnswers.contains(a)){
				if(rnd <= correctProbability){
					pickedAnswers.add(a);
				}
			}else{
				if(rnd <= incorrectProbability){
					pickedAnswers.add(a);
				}
			}
		}
		
		return pickedAnswers;
	}

}
