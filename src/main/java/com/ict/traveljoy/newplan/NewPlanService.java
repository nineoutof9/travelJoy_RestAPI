package com.ict.traveljoy.newplan;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class NewPlanService {

	@Autowired
    private final NewPlanRepository newPlanRepository;
    
	@Autowired
    private final UserRepository usersRepository;
	
	public NewPlanService(NewPlanRepository newPlanRepository, UserRepository usersRepository) {
        this.newPlanRepository = newPlanRepository;
        this.usersRepository = usersRepository;
    }

    // 새 계획을 저장하는 로직 (저장 시 문자열을 그대로 사용)
    public NewPlan createNewPlan(NewPlanDTO newPlanDTO) {
        // DTO를 엔티티로 변환한 후 저장
    	
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String email = authentication.getName();

         // 이메일로 Users 엔티티 조회
         Users currentUser = usersRepository.findByEmail(email)
                 .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));

        NewPlan newPlan = newPlanDTO.toEntity();
        newPlan.setUser(currentUser); // 현재 사용자 정보 설정
        return newPlanRepository.save(newPlan); // 데이터베이스에 저장
    }

    // 저장된 계획을 반환하는 로직
    public NewPlanDTO getNewPlan(Long id) {
        // 데이터베이스에서 NewPlan 엔티티를 찾아옴
        NewPlan newPlan = findNewPlanById(id);
        return NewPlanDTO.fromEntity(newPlan);
    }
    
    public List<NewPlanDTO> getNewPlanByEmail(String email) {
    	List<NewPlanDTO> newPlan = newPlanRepository.findAllByUser_Email(email).stream()
                .map(NewPlanDTO::fromEntity)
                .collect(Collectors.toList());
    	return newPlan;
    }

    // ID로 계획을 찾는 로직
    private NewPlan findNewPlanById(Long id) {
        return newPlanRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("해당 ID의 계획이 존재하지 않습니다: " + id));
    }
}

