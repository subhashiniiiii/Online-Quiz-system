package com.incture.OnlineQuizSystem.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.incture.OnlineQuizSystem.Entity.OnlineQuizSystem;
import com.incture.OnlineQuizSystem.Entity.QuizAttempt;

@Repository
public interface OnlineQuizSystemRepository extends JpaRepository<OnlineQuizSystem,Integer>{
	List<OnlineQuizSystem> findByCategory(String category);
	List<OnlineQuizSystem> findByDifficulty(String difficulty);
	List<OnlineQuizSystem> findByCategoryAndDifficulty(String category,String difficulty );
    // Custom query for randomized selection (using native SQL for better randomization)
	@Query(value = "SELECT * FROM online_quiz_system ORDER BY RAND() LIMIT 1", nativeQuery = true)
    List<OnlineQuizSystem> findRandomQuestions(int limit);
	Optional<OnlineQuizSystem> findById(Long questionId);
	 
}




