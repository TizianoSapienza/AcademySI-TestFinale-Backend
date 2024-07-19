package com.academysi.controller;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.academysi.dto.UserLoginRequestDto;
import com.academysi.dto.UserLoginResponseDto;
import com.academysi.dto.UserRegistrationDto;
import com.academysi.model.User;
import com.academysi.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RestController
@Path("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	public UserLoginResponseDto issueToken(String email) {
		
		byte[] secret = "aufgauvwbfawbv928y429bapfvbp2gavfa7bd78g292g17vr7a0wafhb89wf2".getBytes();
	    Key key = Keys.hmacShaKeyFor(secret);
	    
	    User userInfo = userService.getUserByEmail(email);
	    Map<String, Object> map = new HashMap<>();
	    
	    map.put("username", userInfo.getUsername());
	    map.put("email", userInfo.getEmail());
	    
	    Date creationDate = new Date();
	    Date end = java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(15L));
		
	    String tokenJwt = Jwts.builder()
	    		.setClaims(map)
	    		.setIssuer("http://localhost:8080")
	    		.setIssuedAt(creationDate)
	    		.setExpiration(end)
	    		.signWith(key)
	    		.compact();
	    
	    UserLoginResponseDto token = new UserLoginResponseDto();
	    
	    token.setToken(tokenJwt);
	    token.setTokenCreationTime(creationDate);
	    token.setTtl(end);
	    
		return token;
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registration(@Valid @RequestBody UserRegistrationDto userDto) {
		try {
			if(!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}",
					userDto.getPassword())) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
			if(userService.existUserByEmail(userDto.getEmail())) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
			userService.registerUser(userDto);
			
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	
	@POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response login(@RequestBody UserLoginRequestDto userDto) {
		try {
        	if(userService.login(userDto)) {
        		return Response.ok(issueToken(userDto.getEmail())).build();
        	}
        } catch (Exception e) {
        	return Response.status(Response.Status.BAD_REQUEST).build();
        }
		
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

}
