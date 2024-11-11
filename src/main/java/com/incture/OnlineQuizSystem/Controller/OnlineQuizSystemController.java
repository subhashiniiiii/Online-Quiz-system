package com.incture.OnlineQuizSystem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.OnlineQuizSystem.Entity.OnlineQuizSystem;
import com.incture.OnlineQuizSystem.Service.OnlineQuizSystemService;

@RestController
@RequestMapping("/api/questions")
public class OnlineQuizSystemController{
	@Autowired
	private OnlineQuizSystemService onlinequizsystemService;
	@GetMapping
	public List<OnlineQuizSystem> getAllQuestions(){
		return onlinequizsystemService.getAllQuestions();
	}
	// Get questions by category
    @GetMapping("/category/{category}")
    public List<OnlineQuizSystem> getQuestionsByCategory(@PathVariable String category) {
        return onlinequizsystemService.getQuestionsByCategory(category);
    }

    // Get questions by difficulty
    @GetMapping("/difficulty/{difficulty}")
    public List<OnlineQuizSystem> getQuestionsByDifficulty(@PathVariable String difficulty) {
        return onlinequizsystemService.getQuestionsByDifficulty(difficulty);
    }

    // Get random questions (e.g., for a limited quiz session)
    @GetMapping("/random/{limit}")
    public List<OnlineQuizSystem> getRandomQuestions(@PathVariable int limit) {
        return onlinequizsystemService.getRandomQuestions(limit);
    }

    // Get random questions by category and difficulty
    @GetMapping("/random/{category}/{difficulty}/{limit}")
    public List<OnlineQuizSystem> getRandomQuestionsByCategoryAndDifficulty(
            @PathVariable String category,
            @PathVariable String difficulty,
            @PathVariable int limit) {
        return onlinequizsystemService.getRandomQuestionsByCategoryAndDifficulty(category, difficulty, limit);
    }
	@PostMapping
	public OnlineQuizSystem addQuestion(@RequestBody OnlineQuizSystem onlinequizSystem) {
		return onlinequizsystemService.addQuestion(onlinequizSystem);
	}
	@PutMapping("/{id}")
	public OnlineQuizSystem updateQuestion(@PathVariable int id,@RequestBody OnlineQuizSystem updatedQuestion) {
		return onlinequizsystemService.updateQuestion(id,updatedQuestion);
	}
	@DeleteMapping("/{id}")
	public void deleteQuestion(@PathVariable int id) {
		onlinequizsystemService.deleteQuestion(id);
	}
}