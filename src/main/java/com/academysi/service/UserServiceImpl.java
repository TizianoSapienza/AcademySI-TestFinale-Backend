package com.academysi.service;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academysi.dao.UserDao;
import com.academysi.dto.UserLoginRequestDto;
import com.academysi.dto.UserRegistrationDto;
import com.academysi.model.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
    private UserDao userDao;

	@Override
	public void registerUser(UserRegistrationDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        
        String sha256hex = DigestUtils.sha256Hex(userDto.getPassword());
	    user.setPassword(sha256hex);
	    
	    userDao.save(user);
	}

	@Override
	public boolean login(UserLoginRequestDto userLoginRequest) {
		String email = userLoginRequest.getEmail();
		String password = userLoginRequest.getPassword();
		
		Optional<User> userOptional = userDao.findByEmail(email);
		
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			
			String hashedPassword = DigestUtils.sha256Hex(password);
            
	        return hashedPassword.equals(user.getPassword());
		}
		return false;
	}

	@Override
	public boolean existUserByEmail(String email) {
		return userDao.existsByEmail(email);
	}

	@Override
	public User getUserByEmail(String email) {
		Optional<User> userOptionalDb = userDao.findByEmail(email);

        if (!userOptionalDb.isPresent()) {
            return null;
        }

        return userOptionalDb.get();
	}

}
