package com.ict.traveljoy.place.sight.repository;

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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sight_name", "lat", "lng"})
    }
)
public class Sight {
    @Id
    @SequenceGenerator(name = "seq_sights", sequenceName = "seq_sights", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "seq_sights", strategy = GenerationType.SEQUENCE)
    @Column(name = "sight_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "is_has_image", nullable = false, columnDefinition = "NUMBER(1, 0)")
    @ColumnDefault("0")
    private Integer isHasImage;

    @Column(name = "entrance_fee")
    private Float entranceFee;

    @Column(name = "sight_name", length = 50, nullable = false)
    private String sightName;

    @Column(length = 2000)
    private String descriptions;

    @Column(nullable = false, length = 200)
    private String address;

    @ElementCollection
    @CollectionTable(name = "sight_images", joinColumns = @JoinColumn(name = "sight_id"))
    @Column(name = "image_url", length = 1000)
    private List<String> imageUrls;

    @Column
    private Float lat;

    @Column
    private Float lng;

    @Column(name = "total_review_count")
    private Long totalReviewCount;

    @Column(name = "average_review_rate")
    private Float averageReviewRate;
}
