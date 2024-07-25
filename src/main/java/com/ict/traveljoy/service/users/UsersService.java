package com.ict.traveljoy.service.users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.repository.users.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {
	private final UsersRepository usersRepository;
	private final ObjectMapper objectMapper;
	
	@Transactional
	public UsersDto signUp(UsersDto dto) {
		return UsersDto.toDto(usersRepository.save(dto.toEntity()));
	}
}
