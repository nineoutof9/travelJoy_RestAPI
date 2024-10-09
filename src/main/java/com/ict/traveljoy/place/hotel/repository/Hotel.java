package com.ict.traveljoy.place.hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.ict.traveljoy.place.region.repository.Region;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
    @SequenceGenerator(name = "seq_hotels", sequenceName = "seq_hotels", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "seq_hotels", strategy = GenerationType.SEQUENCE)
    @Column(name = "hotel_id")
    private Long id;
    
    @Column(name = "is_has_image", nullable = false, columnDefinition = "NUMBER(1, 0)")
    @ColumnDefault("0")
    private Integer isHasImage;
    
    @Column(name = "average_price", nullable = false)
    private String averagePrice;
    
    @Column(name = "hotel_name", length = 50, nullable = false)
    private String hotelName;
    
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
    
    @Column(name = "AVERAGE_REVIEW_RATE")
    private Float averageReviewRate;
    
    @ElementCollection
    @CollectionTable(name = "hotel_images", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;
    
    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;
    
    @Column
	private Float lat;
	
	@Column
	private Float lng;
}
