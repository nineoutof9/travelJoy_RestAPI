package com.ict.traveljoy.repository.move;


import com.ict.traveljoy.repository.transportation.Transportation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Move {

    @Id
    @SequenceGenerator(name = "seq_move", sequenceName = "seq_move", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "seq_move", strategy = GenerationType.SEQUENCE)
    @Column(name = "MOVE_ID")
    private Long id;

    @OneToMany(mappedBy = "move")
    private List<Transportation> transportations;
}
