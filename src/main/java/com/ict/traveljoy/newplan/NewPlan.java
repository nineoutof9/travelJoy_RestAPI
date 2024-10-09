package com.ict.traveljoy.newplan;

import com.ict.traveljoy.place.hotel.repository.Hotel;
import com.ict.traveljoy.place.region.repository.Region;
import com.ict.traveljoy.users.repository.Users;

import java.time.LocalDate;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NEW_PLAN")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_plan_seq")
    @SequenceGenerator(name = "new_plan_seq", sequenceName = "new_plan_seq", allocationSize = 1)
    private Long id;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "CLUSTER_DATA", columnDefinition = "CLOB")
    private String clusterData;

    @Column(name = "COUNTS", columnDefinition = "CLOB")
    private String counts;

    @Column(name = "DESTINATION", columnDefinition = "CLOB")
    private String destination;

    @Column(name = "OPTIONS", columnDefinition = "CLOB")
    private String options; // JSON 직렬화된 옵션들

    @Column(name = "PLANS", columnDefinition = "CLOB")
    private String plans; // JSON 직렬화된 계획들

    @Column(name = "RECOMMEND_HOTEL", columnDefinition = "CLOB")
    private String recommendHotel; // JSON 직렬화된 호텔 추천

    @Column(name = "SELECTED_HOTEL", columnDefinition = "CLOB")
    private String selectedHotel; // JSON 직렬화된 선택된 호텔들

    @Column(name = "SELECTED_PLACES", columnDefinition = "CLOB")
    private String selectedPlaces; // JSON 직렬화된 선택된 장소들

    @Column(name = "SELECTED_PLAN", columnDefinition = "CLOB")
    private String selectedPlan; // JSON 직렬화된 선택된 계획

    // 사용자 정보 참조 (Users 엔티티와 관계 설정)
    @ManyToOne
    @JoinColumn(name = "USER_EMAIL", referencedColumnName = "email", nullable = false) // email 컬럼 참조
    private Users user; // 사용자 이메일을 참조하는 Users 엔티티
}

