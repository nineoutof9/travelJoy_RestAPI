package com.ict.traveljoy.users.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserDTO signUp(UserDTO dto) {
		userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일이 이미 사용중입니다.");
        });
		dto = dto.toBuilder()
		.password(bCryptPasswordEncoder.encode(dto.getPassword()))
		.permission("ROLE_USER")
		.build();
		return UserDTO.toDTO(userRepository.save(dto.toEntity()));
	}
	
	public UserDTO findByEmail(String username) {
	    Users user = userRepository.findByEmail(username)
	                               .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
	    return UserDTO.toDTO(user);
	}
	
	public Users findEntityByEmail(String email) {
	    return userRepository.findByEmail(email)
	        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	}

	public boolean checkEmailExists(String email) {
		return userRepository.existsByEmail(email);
	}

	public void updateProfile(UserDTO userDTO) {
	    // 사용자의 이메일을 기준으로 데이터베이스에서 기존 사용자 정보를 가져옵니다.
	    Optional<Users> optionalUser = userRepository.findByEmail(userDTO.getEmail());
	    
	    if (optionalUser.isPresent()) {
	        Users user = optionalUser.get();
	        
	        if (userDTO.getName() != null) {
	            user.setName(userDTO.getName());
	        }
	        if (userDTO.getNickname() != null) {
	            user.setNickname(userDTO.getNickname());
	        }
	        if (userDTO.getIntroduce() != null) {
	            user.setIntroduce(userDTO.getIntroduce());
	        }
	        if (userDTO.getBirthDate() != null) {
	            user.setBirthDate(userDTO.getBirthDate());
	        }
	        if (userDTO.getGender() != null)
	        	user.setGender(userDTO.getGender() ? 1 : 0);
	        if (userDTO.getGender() == null)
	        	user.setGender(null);
	        // 필요한 경우 다른 필드도 추가 가능

	        // 데이터베이스에 업데이트된 사용자 정보 저장
	        userRepository.save(user);
	    } else {
	        throw new UsernameNotFoundException("존재하지 않는 유저입니다: " + userDTO.getEmail());
	    }
	}
    
}
