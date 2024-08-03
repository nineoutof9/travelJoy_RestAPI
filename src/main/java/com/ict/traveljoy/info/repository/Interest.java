package com.ict.traveljoy.info.repository;

import java.util.List;

import com.ict.traveljoy.info.userinfo.repository.UserInterest;
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
@Table(name="interest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interest {
	@Id
	@SequenceGenerator(name = "seq_interest",sequenceName = "seq_interest",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_interest",strategy = GenerationType.SEQUENCE)
	private long interestId;
	
	@Column(length = 50,nullable = false)
	private String interestTopic;
	
	@Column
	private boolean activityPlace;
	
	@Column(length = 50)
	private String classification;
	
	@OneToMany(mappedBy = "interest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInterest> userInterest;
}
