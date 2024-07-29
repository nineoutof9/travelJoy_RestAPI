package com.ict.traveljoy.repository.hotel;

<<<<<<< HEAD
import org.hibernate.annotations.ColumnDefault;

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
public class Hotel {
	@Id
	@SequenceGenerator(name = "seq_hotels",sequenceName = "seq_hotels",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_hotels",strategy = GenerationType.SEQUENCE)
	@Column(name="hotel_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name ="region_id", nullable = false)
	private Region region;
	
	@Column(name="is_has_image",nullable = false)
	@ColumnDefault("F")
=======
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
public class Hotel {
	@Id
	@SequenceGenerator(name = "seq_hotels",sequenceName = "seq_hotels",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_hotels",strategy = GenerationType.SEQUENCE)
	@Column(name="hotel_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name ="region_id", nullable = false)
	private Region region;
	
	@Column(name="is_has_image",nullable = false, columnDefinition = "CHAR(1 BYTE) default 'F'"  )
>>>>>>> branch 'main' of https://github.com/nineoutof9/travelJoy_RestAPI.git
	private boolean isHasImage;
	
	@Column(name="average_price", nullable = false)
	private float averagePrice;
	
	@Column(name="hotel_name", length = 50, nullable = false)
	private String hotelName;
	
	@Column(name = "descriptions",length = 2000)
	private String descriptions;
	
	@Column(name="address", length=200, nullable = false)
	private String address;
	
	@Column(name = "lat")
	private float lat;
	
	@Column(name = "lng")
    private float lng;
	
	@Column(name = "TOTAL_REVIEW_COUNT")
	private Long totalReviewCount;
	
	@Column(name = "AVERAGE_REVIEW_RATE")
	private float averageReviewRate;
	
	
}
