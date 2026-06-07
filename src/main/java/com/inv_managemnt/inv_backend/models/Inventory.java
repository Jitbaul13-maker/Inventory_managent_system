package com.inv_managemnt.inv_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "inv_seq_gen")
    @SequenceGenerator(
            name = "inv_seq_gen",
            sequenceName = "inv_seq_gen",
            allocationSize = 1
    )
    private Integer iid;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Integer availableQuantity = 0;

    @Column(nullable = false)
    private Integer reservedQuantity = 0;

    @OneToOne
    @JoinColumn(
            name = "pid",
            nullable = false,
            unique = true
    )
    private Product product;
}
