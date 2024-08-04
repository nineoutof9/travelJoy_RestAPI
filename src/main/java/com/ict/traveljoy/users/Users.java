package com.ict.traveljoy.users;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.ict.traveljoy.repository.converter.PermissionToNumberConverter;
import com.ict.traveljoy.users.special.UserAllergy;
import com.ict.traveljoy.users.special.UserHandicap;
import com.ict.traveljoy.users.special.UserInterest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
	
	@Id
	@SequenceGenerator(name = "seq_users",sequenceName = "seq_users",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_users",strategy = GenerationType.SEQUENCE)
	@Column(name="user_id")
	private Long id;
	
	@Column(length = 320, unique = true, nullable = false)
	private String email;
	
	@Column(length = 64,nullable = false)
	private String password;
	
	@Column(length = 30)
	private String name;
	
	@Column(length = 30,nullable = false)
	private String nickname;
	
	@Column
	private java.sql.Date birthDate;
	
	@Column(nullable = false)
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime signInDate;
	
	@Column
	private boolean gender;
	
	@Column(nullable = false)
	@ColumnDefault("'F'")
	private boolean isKakao;
	
	@Column(nullable = false)
	@ColumnDefault("'F'")
	private boolean isGoogle;
	
	@Column(nullable = false)
	@ColumnDefault("'F'")
	private boolean isNaver;
	
	@Convert(converter = PermissionToNumberConverter.class)
	@Column
	@ColumnDefault("0")
	private String permission;
	
	@Column
	private boolean handicap;
	
	@Column(nullable = false)
	@ColumnDefault("'F'")
	private boolean handicapAllow;
	
	@Column
	private boolean allergy;
	
	@Column(nullable = false)
	@ColumnDefault("'F'")
	private boolean allergyAllow;
	
	@Column
	private boolean interest;
	
	@Column(nullable = false)
	@ColumnDefault("'F'")
	private boolean interestAllow;
	
	@Column
	private long reported;
	
	@Column(nullable = false)
	@ColumnDefault("'F'")
	private boolean isDeleteId;
	
	@Column
	private LocalDateTime deleteIdDate;
	
	@Column(nullable = false)
	@ColumnDefault("'T'")
	private boolean isActive;
	
	@Column
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime updateDate;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInterest> userInterest;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAllergy> userAllergy;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserHandicap> userHandicap;
}
