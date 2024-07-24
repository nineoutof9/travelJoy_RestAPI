package com.ict.traveljoy.repository.events;

import java.time.LocalDateTime;

import com.ict.traveljoy.repository.region.Region;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Events {

    @Id
    @SequenceGenerator(name = "seq_events" ,sequenceName = "seq_events", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "seq_events",strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Long id;

    @ManyToOne
	@JoinColumn(name = "region_id",nullable = false)
    private Region region;

    @Column(name = "IS_HAS_IMAGE", nullable = false)
    private char isHasImage;

    @Column(name = "EVENT_START_DATE")
    private LocalDateTime eventStartDate;

    @Column(name = "EVENT_END_DATE")
    private LocalDateTime eventEndDate;

    @Column(name = "ENTRANCE_FEE")
    private Float entranceFee;

    @Column(name = "EVENT_NAME", length = 50)
    private String eventName;

    @Column(name = "DESCRIPTIONS",  length = 2000)
    private String descriptions;

    @Column(name = "ADDRESS", length = 200)
    private String address;

    @Column(name = "LAT")
    private Float lat;

    @Column(name = "LNG")
    private Float lng;

    @Column(name = "TOTAL_REVIEW_COUNT")
    private Long totalReviewCount;

    @Column(name = "AVERAGE_REVIEW_RATE")
    private Float averageReviewRate;
}
