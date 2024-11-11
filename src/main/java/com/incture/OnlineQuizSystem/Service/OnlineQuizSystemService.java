package com.incture.OnlineQuizSystem.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.OnlineQuizSystem.Entity.OnlineQuizSystem;
import com.incture.OnlineQuizSystem.Repository.OnlineQuizSystemRepository;

@Service
public class OnlineQuizSystemService {
	@Autowired
	private OnlineQuizSystemRepository onlinequizsystemRepository;
	
	public List<OnlineQuizSystem> getAllQuestions(){
		return onlinequizsystemRepository.findAll();
	}
	public List<OnlineQuizSystem> getQuestionsByCategory(String category){
		return onlinequizsystemRepository.findByCategory(category);
	}
	public List<OnlineQuizSystem> getQuestionsByDifficulty(String difficulty){
		return onlinequizsystemRepository.findByDifficulty(difficulty);
	}
	public List<OnlineQuizSystem> getQuestionsByCategoryAndDifficulty(String category,String difficulty){
		return onlinequizsystemRepository.findByCategoryAndDifficulty(category, difficulty);
	}
	public List<OnlineQuizSystem> getRandomQuestions(int limit){
		return onlinequizsystemRepository.findRandomQuestions(limit);
	}
	public List<OnlineQuizSystem> getRandomQuestionsByCategoryAndDifficulty(String category,String difficulty,int limit){
		List<OnlineQuizSystem> filteredQuestions=onlinequizsystemRepository.findByCategoryAndDifficulty(category, difficulty);
		if(filteredQuestions.size()<=limit) {
			return filteredQuestions;
		}
		Collections.shuffle(filteredQuestions);
		return filteredQuestions.subList(0, limit);
	}
	
	public OnlineQuizSystem addQuestion(OnlineQuizSystem onlinequizsystem) {
		return onlinequizsystemRepository.save(onlinequizsystem);
	}
	public OnlineQuizSystem updateQuestion(int id,OnlineQuizSystem updatedQuestion) {
		return onlinequizsystemRepository.findById(id).map(question->{
			question.setQuestion_test(updatedQuestion.getQuestion_test());
			question.setOption_a(updatedQuestion.getOption_a());
			question.setOption_b(updatedQuestion.getOption_b());
			question.setOption_c(updatedQuestion.getOption_c());
			question.setOption_d(updatedQuestion.getOption_d());
			question.setCorrect_option(updatedQuestion.getCorrect_option());
			question.setCategory(updatedQuestion.getCategory());
			question.setDifficulty(updatedQuestion.getDifficulty());
			return onlinequizsystemRepository.save(question);
		})
				.orElseThrow(()->new RuntimeException("Question not found"));
	}
	public void deleteQuestion(int id) {
		onlinequizsystemRepository.deleteById(id);
	}
}
