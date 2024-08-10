package com.ict.traveljoy.question.repository;

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
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

	@Id
	@SequenceGenerator(name = "seq_answer",sequenceName = "seq_answer",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_answer",strategy = GenerationType.SEQUENCE)
	@Column(name="answer_id")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "question_id")
	private Question question;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
	private Users user; //답변자
	
	@Column(length = 50)
	private String answerHandlerName;
	
	@Column(nullable = false)
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime answerDate;
	
	@Column(length = 2000)
	private String answerContent;
}
