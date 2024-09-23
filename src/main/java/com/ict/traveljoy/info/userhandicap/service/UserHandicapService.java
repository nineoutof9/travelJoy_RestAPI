package com.ict.traveljoy.info.userhandicap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ict.traveljoy.info.handicap.repository.Handicap;
import com.ict.traveljoy.info.handicap.repository.HandicapRepository;
import com.ict.traveljoy.info.userhandicap.repository.UserHandicap;
import com.ict.traveljoy.info.userhandicap.repository.UserHandicapRepository;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserHandicapService {
	
	private final UserHandicapRepository userHandicapRepository;
	private final HandicapRepository handicapRepository;
	private final UserRepository userRepository;

    public List<UserHandicapDTO> getAll() {
        List<UserHandicap> userHandicaps = userHandicapRepository.findAll();
        return userHandicaps.stream()
            .map(userHandicap->UserHandicapDTO.toDTO(userHandicap))
            .collect(Collectors.toList());
    }

    public UserHandicapDTO getById(Long id) {
        UserHandicap userHandicap = userHandicapRepository.findById(id).orElse(null);
        return UserHandicapDTO.toDTO(userHandicap);
    }
    
    
    public List<String> findHandicapsByUser(String useremail) {
		Users user = userRepository.findByEmail(useremail).get();

		List<UserHandicap> userhandicaps = userHandicapRepository.findAllByUser_Id(user.getId());
		List<String> response = new ArrayList<String>();

		for(UserHandicap handicap:userhandicaps) {
			response.add(handicap.getHandicap().getHandicapType());
		}

		return response;
	}
    
    public List<UserHandicapDTO> updateUserHandicap(String useremail, List<String> updatedHandicaps) {
        Users user = userRepository.findByEmail(useremail).orElseThrow(() -> new RuntimeException("User not found"));
        List<UserHandicap> userHandicaps = userHandicapRepository.findAllByUser_Id(user.getId());
        List<UserHandicapDTO> response = new ArrayList<>();

        // 기존 사용자 핸디캡의 handicapType을 추출하여 Set으로 관리
        Set<String> currentHandicaps = userHandicaps.stream()
            .map(userHandicap -> userHandicap.getHandicap().getHandicapType())
            .collect(Collectors.toSet());

        // 추가된 핸디캡: updatedHandicaps에 있지만 currentHandicaps에 없는 항목
        List<String> addedHandicaps = updatedHandicaps.stream()
            .filter(handicap -> !currentHandicaps.contains(handicap))
            .collect(Collectors.toList());

        // 삭제된 핸디캡: currentHandicaps에 있지만 updatedHandicaps에 없는 항목
        List<UserHandicap> removedHandicaps = userHandicaps.stream()
            .filter(userHandicap -> !updatedHandicaps.contains(userHandicap.getHandicap().getHandicapType()))
            .collect(Collectors.toList());

        // 삭제된 핸디캡 처리
        for (UserHandicap removed : removedHandicaps) {
            userHandicapRepository.delete(removed);
        }

        // 추가된 핸디캡 처리
        for (String added : addedHandicaps) {
            // handicapType으로 핸디캡을 검색
            Handicap handicap = handicapRepository.findByHandicapType(added);
            
            if (handicap == null) {
                throw new RuntimeException("Handicap not found with type: " + added);
            }

            boolean existingUserHandicap = userHandicapRepository.existsByUserAndHandicap(user, handicap);
            
            if (!existingUserHandicap) {
                UserHandicap newUserHandicap = new UserHandicap();
                newUserHandicap.setUser(user);
                newUserHandicap.setHandicap(handicap);
                newUserHandicap.setHandicapLevel(1L);
                
                userHandicapRepository.save(newUserHandicap);

                response.add(UserHandicapDTO.toDTO(newUserHandicap));
            }
        }
        
        if(userHandicapRepository.existsByUser(user)) {
            user.setAllergy(1);
        } else {
            user.setAllergy(0);
        }
	    userRepository.save(user);

        return response;
    }
}
