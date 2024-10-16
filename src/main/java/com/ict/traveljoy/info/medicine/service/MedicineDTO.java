package com.ict.traveljoy.info.medicine.service;

import com.ict.traveljoy.info.medicine.repository.Medicine;

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
public class MedicineDTO {
	private Long id;
	private String description;
	private String medicineName;
	
	public Medicine toEntity() {
		return Medicine.builder()
				.id(id)
				.description(description)
				.medicineName(medicineName)
				.build();
		
	}
	public static MedicineDTO toDTO(Medicine medicine) {
		return MedicineDTO.builder()
				.id(medicine.getId())
				.description(medicine.getDescription())
				.medicineName(medicine.getMedicineName())
				.build();
		
	}
}
