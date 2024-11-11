package com.incture.OnlineQuizSystem.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class QuizAttempt {

    @Id
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int score;
    
 // Additional fields for detailed reporting
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Many-to-one relationship with User
    
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private OnlineQuizSystem onlineQuizSystem; // Many-to-one relationship with OnlineQuizSystem

    // Getters and Setters

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OnlineQuizSystem getOnlineQuizSystem() {
        return onlineQuizSystem;
    }

    public void setOnlineQuizSystem(OnlineQuizSystem onlineQuizSystem) {
        this.onlineQuizSystem = onlineQuizSystem;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public int getIncorrectAnswers() {
		return incorrectAnswers;
	}

	public void setIncorrectAnswers(int incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}
	public void incrementCorrectAnswers() {
        this.correctAnswers++;
    }

    public void incrementIncorrectAnswers() {
        this.incorrectAnswers++;
    }

}
