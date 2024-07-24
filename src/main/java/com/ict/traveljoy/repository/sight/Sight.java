package com.ict.traveljoy.repository.sight;

import com.ict.traveljoy.repository.region.Region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Sight {
	@Id
	@SequenceGenerator(name = "seq_sights" ,sequenceName = "seq_sights", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_sights", strategy = GenerationType.SEQUENCE )
	@Column(name = "sight_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "region_id",nullable = false)
	private Region region;
	
	@Column(name="is_has_image",nullable = false, columnDefinition = "CHAR(1 BYTE) default 'F'"  )
	private char isHasImage;
	
	@Column(name = "entrance_fee")
	private float entranceFee;
	
	@Column(name="sight_name",length = 50, nullable = false)
	private String sightName;
	
	@Column(length = 2000)
	private String descriptions;
	
	@Column(nullable = false, length = 200)
	private String address;
	
	@Column
	private float lat;
	
	@Column
	private float lng;
	
	@Column(name = "total_review_count")
	private Long totalReviewCount;
	
	@Column(name = "average_review_rate")
	private float averageReviewRate;
	
}
