package com.ict.traveljoy.info.userallergy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.info.allergy.repository.Allergy;
import com.ict.traveljoy.info.allergy.repository.AllergyRepository;
import com.ict.traveljoy.info.userallergy.repository.UserAllergy;
import com.ict.traveljoy.info.userallergy.repository.UserAllergyRepository;
import com.ict.traveljoy.notice.service.NoticeDTO;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAllergyService {
	private final UserAllergyRepository userAllergyRepository;
	private final AllergyRepository allergyRepository;
	private final UserRepository userRepository;

	public List<UserAllergyDTO> getAll() {
		List<UserAllergy> userAllergies = userAllergyRepository.findAll();
		return userAllergies.stream()
				.map(userAllergie->UserAllergyDTO.toDTO(userAllergie))
				.collect(Collectors.toList());
	}


	public UserAllergyDTO getById(Long id) {
		UserAllergy userAllergy = userAllergyRepository.findById(id).orElse(null);
		return UserAllergyDTO.toDTO(userAllergy);
	}


	public List<String> findAllergiesByUser(String useremail) {
		Users user = userRepository.findByEmail(useremail).get();

		List<UserAllergy> userallergies = userAllergyRepository.findAllByUser_Id(user.getId());
		List<String> response = new ArrayList<String>();

		for(UserAllergy allergy:userallergies) {
			response.add(allergy.getAllergy().getInterestTopic());
		}

		return response;
	}


	public List<UserAllergyDTO> updateUserAllergy(String useremail, List<String> updatedAllergies) {
	    Users user = userRepository.findByEmail(useremail).orElseThrow(() -> new RuntimeException("User not found"));
	    List<UserAllergy> userallergies = userAllergyRepository.findAllByUser_Id(user.getId());
	    List<UserAllergyDTO> response = new ArrayList<>();

	    // 기존 사용자 알레르기의 interestTopic을 추출하여 Set으로 관리
	    Set<String> currentAllergies = userallergies.stream()
	        .map(userAllergy -> userAllergy.getAllergy().getInterestTopic())
	        .collect(Collectors.toSet());

	    // 추가된 알레르기: updatedAllergies에 있지만 currentAllergies에 없는 항목
	    List<String> addedAllergies = updatedAllergies.stream()
	        .filter(allergy -> !currentAllergies.contains(allergy))
	        .collect(Collectors.toList());

	    // 삭제된 알레르기: currentAllergies에 있지만 updatedAllergies에 없는 항목
	    List<UserAllergy> removedAllergies = userallergies.stream()
	        .filter(userAllergy -> !updatedAllergies.contains(userAllergy.getAllergy().getInterestTopic()))
	        .collect(Collectors.toList());

	    // 삭제된 알레르기 처리
	    for (UserAllergy removed : removedAllergies) {
	        userAllergyRepository.delete(removed);
	    }

	    // 추가된 알레르기 처리
	    for (String added : addedAllergies) {
	        Allergy allergy = allergyRepository.findByInterestTopic(added);
	        
	        if (allergy == null) {
	            throw new RuntimeException("Allergy not found: " + added);
	        }

	        // 기존 UserAllergy가 있는지 확인
	        boolean existingUserAllergy = userAllergyRepository.existsByUserAndAllergy(user, allergy);
	        
	        if (!existingUserAllergy) {
	            UserAllergy newUserAllergy = new UserAllergy();
	            newUserAllergy.setUser(user);
	            newUserAllergy.setAllergy(allergy);
	            newUserAllergy.setAllergyLevel(1L);
	            
	            userAllergyRepository.save(newUserAllergy);

	            response.add(UserAllergyDTO.toDTO(newUserAllergy));
	        }
	    }

	    return response;
	}




}
