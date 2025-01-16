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
    private String description;
    private Double prix;

    @Column(nullable = false)
    private Boolean actif = true;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
}