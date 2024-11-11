package com.incture.OnlineQuizSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.OnlineQuizSystem.Entity.User;
import com.incture.OnlineQuizSystem.Repository.UserRepository;
import com.incture.UserException.AuthenticationFailedException;
import com.incture.UserException.UserAlreadyExistsException;


@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;	
	
	 public User register(User user) {
	        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
	            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists.");
	        }
	        return userRepository.save(user);
	    }

	    // Login with username and password
	    public void login(String username, String password) {
	        User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new AuthenticationFailedException("Invalid username or password."));
	        
	        if (!user.getPassword().equals(password)) {
	            throw new AuthenticationFailedException("Invalid username or password.");
	        }
	    }
}
