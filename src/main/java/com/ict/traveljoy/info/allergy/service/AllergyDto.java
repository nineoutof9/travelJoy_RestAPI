package com.ict.traveljoy.info.allergy.service;

import java.sql.Date;
import java.time.LocalDateTime;

import com.ict.traveljoy.info.allergy.repository.Allergy;
import com.ict.traveljoy.users.service.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergyDto {
	private Long id;
	private String interestTopic;
	private boolean activityPlace;
	private String classification;
	
	public Allergy toEntity() {
		return Allergy.builder()
				.id(id)
				.interestTopic(interestTopic)
				.activityPlace(activityPlace)
				.classification(classification)
				.build();
	}
	
	public static AllergyDto toDto(Allergy allergy) {
		return AllergyDto.builder()
				.id(allergy.getId())
				.interestTopic(allergy.getInterestTopic())
				.activityPlace(allergy.getActivityPlace())
				.classification(allergy.getClassification())
				.build();
	}
}
