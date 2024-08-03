package com.ict.traveljoy.image.repository;

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
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

	@Id
	@SequenceGenerator(name = "seq_image",sequenceName = "seq_image",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_image",strategy = GenerationType.SEQUENCE)
	@Column(name="user_id")
	private long id;
	
	@Column(length = 200)
	private String imageUrl;
}
