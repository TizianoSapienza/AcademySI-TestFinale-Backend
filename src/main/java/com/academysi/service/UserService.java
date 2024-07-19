package com.academysi.service;

import com.academysi.dto.UserLoginRequestDto;
import com.academysi.dto.UserRegistrationDto;
import com.academysi.model.User;

public interface UserService {
	
	void registerUser(UserRegistrationDto userDto);
	boolean login(UserLoginRequestDto userLoginRequest);
	
	boolean existUserByEmail(String email);
	User getUserByEmail(String email);
}
