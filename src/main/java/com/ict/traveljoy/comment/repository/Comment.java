package com.ict.traveljoy.comment.repository;


import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.ict.traveljoy.users.repository.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
	@Id
	@SequenceGenerator(name = "seq_comment",sequenceName = "seq_comment",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_comment",strategy = GenerationType.SEQUENCE)
	@Column(name="COMMENT_ID")
	private Long id;
	
	@Column
	private String target;
	
	@Column
	private Long targetId;
	
	@ManyToOne
    @JoinColumn(name = "writer", nullable = false, referencedColumnName = "user_id")
    private Users user;
	
	@Column
	private String contents;
	
	@Column(name = "CREATE_DATE", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "IS_ACTIVE", nullable = false,columnDefinition = "NUMBER(1, 0)")
    @ColumnDefault("1")
    private Integer isActive;

    @Column(name = "IS_DELETE", nullable = false,columnDefinition = "NUMBER(1, 0)")
    @ColumnDefault("0")
    private Integer isDelete;

    @Column(name = "DELETE_DATE")
    private LocalDateTime deleteDate;
}
