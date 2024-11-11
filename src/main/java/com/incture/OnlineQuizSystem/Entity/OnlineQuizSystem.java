package com.incture.OnlineQuizSystem.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class OnlineQuizSystem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String question_test;
	private String option_a;
	private String option_b;
	private String option_c;
	private String option_d;
	private String correct_option;
	private String category;
	private String difficulty;
	 @OneToMany(mappedBy = "onlineQuizSystem", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<QuizAttempt> quizAttempts; // One-to-many relationship with QuizAttempt
	
	public OnlineQuizSystem() {
        // Default constructor
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion_test() {
		return question_test;
	}
	public void setQuestion_test(String question_test) {
		this.question_test = question_test;
	}
	public String getOption_a() {
		return option_a;
	}
	public void setOption_a(String option_a) {
		this.option_a = option_a;
	}
	public String getOption_b() {
		return option_b;
	}
	public void setOption_b(String option_b) {
		this.option_b = option_b;
	}
	public String getOption_c() {
		return option_c;
	}
	public void setOption_c(String option_c) {
		this.option_c = option_c;
	}
	public String getOption_d() {
		return option_d;
	}
	public void setOption_d(String option_d) {
		this.option_d = option_d;
	}
	public String getCorrect_option() {
		return correct_option;
	}
	public void setCorrect_option(String correct_option) {
		this.correct_option = correct_option;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
}
