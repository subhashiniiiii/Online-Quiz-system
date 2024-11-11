package com.incture.OnlineQuizSystem.Repository;

import com.incture.OnlineQuizSystem.Entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
	List<UserAnswer> findByQuizAttemptId(Long attemptId);

}
