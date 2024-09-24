package com.ict.traveljoy.info.userinterest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ict.traveljoy.info.interest.repository.Interest;
import com.ict.traveljoy.info.interest.repository.InterestRepository;
import com.ict.traveljoy.info.userallergy.repository.UserAllergy;
import com.ict.traveljoy.info.userinterest.repository.UserInterest;
import com.ict.traveljoy.info.userinterest.repository.UserInterestRepository;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInterestService {
	private final UserInterestRepository userInterestRepository;
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;

    // 새로운 UserInterest를 저장하는 메서드
    public UserInterestDTO saveUserInterest(UserInterestDTO dto) {
        Users user = userRepository.findById(dto.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다: " + dto.getUser().getId()));
        Interest interest = interestRepository.findById(dto.getInterest().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 관심사를 찾을 수 없습니다: " + dto.getInterest().getId()));

        UserInterest userInterest = dto.toEntity();
        userInterest.setUser(user);
        userInterest.setInterest(interest);

        UserInterest savedEntity = userInterestRepository.save(userInterest);
        return UserInterestDTO.toDTO(savedEntity);
    }

    // ID로 특정 UserInterest를 조회하는 메서드
    public UserInterestDTO findById(Long id) {
        Optional<UserInterest> userInterests = userInterestRepository.findById(id);
        return userInterests.map(userInterest->UserInterestDTO.toDTO(userInterest))
                .orElseThrow(() -> new IllegalArgumentException("해당 ID로 유저 관심사를 찾을 수 없습니다: " + id));
    }

    // 모든 UserInterest를 조회하는 메서드
    public List<UserInterestDTO> findAll() {
        List<UserInterest> userInterests = userInterestRepository.findAll();
        return userInterests.stream()
                .map(userInterest->UserInterestDTO.toDTO(userInterest))
                .collect(Collectors.toList());
    }

    // 특정 UserInterest를 삭제하는 메서드
    public void deleteById(Long id) {
        if (!userInterestRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 ID로 유저 관심사를 찾을 수 없습니다: " + id);
        }
        userInterestRepository.deleteById(id);
    }

    // 특정 UserInterest를 업데이트하는 메서드
//    public UserInterestDTO updateUserInterest(Long id, UserInterestDTO dto) {
//        UserInterest existingEntity = userInterestRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 ID로 유저 관심사를 찾을 수 없습니다: " + id));
//
//        Users user = userRepository.findById(dto.getUser().getId())
//                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다: " + dto.getUser().getId()));
//        Interest interest = interestRepository.findById(dto.getInterest().getId())
//                .orElseThrow(() -> new IllegalArgumentException("해당 관심사를 찾을 수 없습니다: " + dto.getInterest().getId()));
//
//        existingEntity.setUser(user);
//        existingEntity.setInterest(interest);
//
//        UserInterest updatedEntity = userInterestRepository.save(existingEntity);
//        return UserInterestDTO.toDTO(updatedEntity);
//    }
    
    public List<String> findInterestsByUser(String useremail) {
		Users user = userRepository.findByEmail(useremail).get();

		List<UserInterest> userinterests = userInterestRepository.findAllByUser_Id(user.getId());
		List<String> response = new ArrayList<String>();

		for(UserInterest interest:userinterests) {
			response.add(interest.getInterest().getInterestTopic());
		}

		return response;
	}
    
    
    
    public List<UserInterestDTO> updateUserInterest(String useremail, List<String> updatedInterests) {
        Users user = userRepository.findByEmail(useremail).orElseThrow(() -> new RuntimeException("User not found"));
        List<UserInterest> userInterests = userInterestRepository.findAllByUser_Id(user.getId());
        List<UserInterestDTO> response = new ArrayList<>();

        // 기존 사용자 관심사를 추출하여 Set으로 관리
        Set<String> currentInterests = userInterests.stream()
            .map(userInterest -> userInterest.getInterest().getInterestTopic())
            .collect(Collectors.toSet());

        // 추가된 관심사: updatedInterests에 있지만 currentInterests에 없는 항목
        List<String> addedInterests = updatedInterests.stream()
            .filter(interest -> !currentInterests.contains(interest))
            .collect(Collectors.toList());

        // 삭제된 관심사: currentInterests에 있지만 updatedInterests에 없는 항목
        List<UserInterest> removedInterests = userInterests.stream()
            .filter(userInterest -> !updatedInterests.contains(userInterest.getInterest().getInterestTopic()))
            .collect(Collectors.toList());

        // 삭제된 관심사 처리
        for (UserInterest removed : removedInterests) {
            userInterestRepository.delete(removed);
        }

        // 추가된 관심사 처리
        for (String added : addedInterests) {
            Interest interest = interestRepository.findByInterestTopic(added);
            
            if (interest == null) {
                throw new RuntimeException("Interest not found: " + added);
            }

            boolean existingUserInterest = userInterestRepository.existsByUserAndInterest(user, interest);
            
            if (!existingUserInterest) {
                UserInterest newUserInterest = new UserInterest();
                newUserInterest.setUser(user);
                newUserInterest.setInterest(interest);
                
                userInterestRepository.save(newUserInterest);

                response.add(UserInterestDTO.toDTO(newUserInterest));
            }
        }
        
        if(userInterestRepository.existsByUser(user)) {
            user.setAllergy(1);
        } else {
            user.setAllergy(0);
        }
	    userRepository.save(user);

        return response;
    }
}
