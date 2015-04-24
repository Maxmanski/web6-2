package at.ac.tuwien.big.we15.lab2.api;

public class User {

	String name;
	Integer score;
	Avatar avatar;
	
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

	/**
	 * 
	 * @return The User's Avatar
	 */
	public Avatar getAvatar() {
		return avatar;
	}

	/**
	 * 
	 * @param avatar The User's new Avatar
	 */
	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}
	
}
