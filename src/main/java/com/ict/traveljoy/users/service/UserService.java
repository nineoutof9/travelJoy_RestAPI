package com.ict.traveljoy.users.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ict.traveljoy.image.repository.Image;
import com.ict.traveljoy.image.repository.ImageRepository;
import com.ict.traveljoy.image.service.S3Uploader;
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
	
	private final ImageRepository imageRepository;
	private final S3Uploader s3Uploader;
	
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
	        if(userDTO.getInterestAllow()==true)
	        	user.setInterestAllow(1);
	        if(userDTO.getAllergyAllow()==true)
	        	user.setAllergyAllow(1);
	        if(userDTO.getHandicapAllow()==true)
	        	user.setHandicapAllow(1);
	        // 필요한 경우 다른 필드도 추가 가능

	        // 데이터베이스에 업데이트된 사용자 정보 저장
	        userRepository.save(user);
	    } else {
	        throw new UsernameNotFoundException("존재하지 않는 유저입니다: " + userDTO.getEmail());
	    }
	}
    
	public Users updateProfileImage(String email, MultipartFile file) throws Exception {
        Optional<Users> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        Users user = userOptional.get();
        
        // 기존 프로필 이미지 삭제
        if (user.getProfileImage() != null) {
            Image oldImage = user.getProfileImage();
            s3Uploader.deleteFile(oldImage.getImageUrl());
            imageRepository.delete(oldImage);
        }

        // 새 이미지 업로드 및 저장
        String imageUrl = s3Uploader.upload(file, "profile-images");
        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setIsActive(1);
        image.setIsDelete(0);
        image.setImageType("profile");
        imageRepository.save(image);

        user.setProfileImage(image);
        return userRepository.save(user);
    }

    public Image getProfileImage(String email) {
        Optional<Users> userOptional = userRepository.findByEmail(email);
        return userOptional.map(Users::getProfileImage).orElse(null);
    }
    
    public List<Users> getAllUser(){
    	return userRepository.findAll();
    }

	public boolean isAdmin(String email) {
		Optional<Users> userOptional = userRepository.findByEmail(email);
		if(userOptional.get().getPermission().equalsIgnoreCase("ROLE_ADMIN")) {
			return true;
		}
		else return false;
	}
}
