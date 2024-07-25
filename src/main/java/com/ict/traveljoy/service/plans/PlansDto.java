package com.ict.traveljoy.service.plans;

import java.time.LocalDateTime;

import com.ict.traveljoy.repository.plans.Plans;

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
public class PlansDto {
	private long planId;
	private String planName;
	private String planDescriptions;
	private LocalDateTime createDate;
	private String isActive;
	private String isDelete;
	private LocalDateTime deleteDate;
	private Integer progress;
	
	public Plans toEntity() {
		return Plans.builder()
				.planId(planId)
				.planName(planName)
				.planDescriptions(planDescriptions)
				.createDate(createDate)
				.isActive(isActive)
				.isDelete(isDelete)
				.deleteDate(deleteDate)
				.progress(progress)
				.build();
	}
	public static PlansDto toDto(Plans plans) {
		return PlansDto.builder()
				.planId(plans.getPlanId())
				.planName(plans.getPlanName())
				.planDescriptions(plans.getPlanDescriptions())
				.createDate(plans.getCreateDate())
				.isActive(plans.getIsActive())
				.isDelete(plans.getIsDelete())
				.deleteDate(plans.getDeleteDate())
				.progress(plans.getProgress())
				.build();
	}
}
