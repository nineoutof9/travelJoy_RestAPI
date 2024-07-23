package com.ict.traveljoy.repository.report;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reports {

	private long id;
	
	private String 
}
