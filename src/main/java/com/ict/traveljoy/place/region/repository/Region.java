package com.ict.traveljoy.place.region.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "region")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Region {

    @Id
    @SequenceGenerator(name = "seq_region",sequenceName = "seq_region",allocationSize = 1,initialValue = 1)
    @GeneratedValue(generator = "seq_region", strategy = GenerationType.SEQUENCE)
    @Column(name="region_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name="region_info",length = 2000)
    private String regionInfo;
    
    @Column
    private Float lat;
    
    @Column
    private Float lng;
    
    @Column(name="IMAGE_URL",length = 1000)
	private String imageUrl;
    
    @Column
    private Float dist;
    
	    //String을 입력받는 생성자 추가
	    public Region(String name) {
	    	this.name =name;
	    }
    
	}