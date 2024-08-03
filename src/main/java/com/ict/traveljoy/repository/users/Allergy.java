package com.ict.traveljoy.repository.users;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="allergy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Allergy {
	@Id
	@SequenceGenerator(name = "seq_allergy",sequenceName = "seq_allergy",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_allergy",strategy = GenerationType.SEQUENCE)
	private long allergyId;
	
	@Column(length = 50,nullable = false)
	private String interestTopic;
	
	@Column
	private boolean activityPlace;
	
	@Column(length = 50)
	private String classification;
	
	@OneToMany(mappedBy = "allergy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAllergy> userAllergy;
}
