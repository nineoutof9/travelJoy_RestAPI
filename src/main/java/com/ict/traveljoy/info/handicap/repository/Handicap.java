package com.ict.traveljoy.info.handicap.repository;

import java.util.List;

import com.ict.traveljoy.info.userhandicap.repository.UserHandicap;

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
@Table(name="handicap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Handicap {
	@Id
	@SequenceGenerator(name = "seq_handicap",sequenceName = "seq_handicap",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_handicap",strategy = GenerationType.SEQUENCE)
	private Long handicapId;
	
	@Column(length = 50)
	private String handicapCode;
	
	@Column(length = 2000)
	private String handicapType;
	
	@OneToMany(mappedBy = "handicap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserHandicap> userHandicap;
}
