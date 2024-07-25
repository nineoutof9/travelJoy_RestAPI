package com.ict.traveljoy.repository.images;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Images {

    @Id
    @SequenceGenerator(name = "seq_images",sequenceName = "seq_images",allocationSize = 1,initialValue = 1)
    @GeneratedValue(generator = "seq_images", strategy = GenerationType.SEQUENCE)
    @Column(name = "IMAGE_ID", nullable = false)
    private Long imageId;

    @Column(name = "IMAGE_URL", length = 200)
    private String imageUrl;

}