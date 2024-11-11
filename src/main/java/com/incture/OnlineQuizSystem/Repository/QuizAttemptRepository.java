package com.incture.OnlineQuizSystem.Repository;

import com.incture.OnlineQuizSystem.Entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByUserId(Long userId); // Get quiz attempts by user ID
}
