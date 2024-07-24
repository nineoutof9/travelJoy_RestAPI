package com.ict.traveljoy.service.users;


import java.sql.Date;
import java.time.LocalDateTime;
import com.ict.traveljoy.repository.users.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//일단 모든 정보를 담고있는 DTO 만들어놨음(여기서 잘라서 용도에 맞는 DTO로 분해)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDto {
	//엔터티의 필드중 데이타 전달에 필요한 필드만 지정
	private String email;
	private String password;
	private String name;
	private String nickname;
	private Date birthDate;
	private LocalDateTime signInDate;
	private boolean gender;
	private boolean isKakao;
	private boolean isGoogle;
	private boolean isNaver;
	private boolean handicap;
	private boolean handicapAllow;
	private boolean allergy;
	private boolean allergyAllow;
	private boolean interest;
	private boolean interestAllow;
	private long reported;
	private boolean isDeleteId;
	private LocalDateTime deleteIdDate;
	private boolean isActive;
	private LocalDateTime updateDate;
	
	//DTO를 ENTITY로 변환하는 메소드
	public Users toEntity() {
		return Users.builder()
				.email(email)
				.password(password)
				.name(name)
				.nickname(nickname)
				.birthDate(birthDate)
				.signInDate(signInDate)
				.gender(gender)
				.isKakao(isKakao)
				.isGoogle(isGoogle)
				.isNaver(isNaver)
				.handicap(handicap)
				.handicapAllow(handicapAllow)
				.allergy(allergy)
				.allergyAllow(allergyAllow)
				.interest(interest)
				.interestAllow(interestAllow)
				.reported(reported)
				.isDeleteId(isDeleteId)
				.deleteIdDate(deleteIdDate)
				.isActive(isActive)
				.updateDate(updateDate)
				.build();
	}
	//ENTITY를 DTO로 변환하는 메소드
	public static UsersDto toDto(Users users) {
		return UsersDto.builder()
				.email(users.getEmail())
				.password(users.getPassword())
				.name(users.getName())
				.nickname(users.getNickname())
				.birthDate(users.getBirthDate())
				.signInDate(users.getSignInDate())
				.gender(users.isGender())
				.isKakao(users.isKakao())
				.isGoogle(users.isGoogle())
				.isNaver(users.isNaver())
				.handicap(users.isHandicap())
				.handicapAllow(users.isHandicapAllow())
				.allergy(users.isAllergy())
				.allergyAllow(users.isAllergyAllow())
				.interest(users.isInterest())
				.interestAllow(users.isInterestAllow())
				.reported(users.getReported())
				.isDeleteId(users.isDeleteId())
				.deleteIdDate(users.getDeleteIdDate())
				.isActive(users.isActive())
				.updateDate(users.getUpdateDate())
				.build();
	}
}
