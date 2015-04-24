package at.ac.tuwien.big.we15.lab2.api;

public class User {

	String name;
	Integer score;
	
	/**
	 * 
	 * @return The User's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return The User's Score
	 */
	public Integer getScore() {
		return score;
	}
	
	/**
	 * 
	 * @param score The User's new Score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	
}
