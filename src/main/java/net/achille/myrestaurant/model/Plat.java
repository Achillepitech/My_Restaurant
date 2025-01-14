package net.achille.myrestaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private Double prix;

    @Column(length = 1000)
    private String description;

    private String category;

    private Boolean menuDuJour;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
}