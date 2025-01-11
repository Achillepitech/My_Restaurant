package net.achille.myrestaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(length = 1000)
    private String description;

    private Double prix;

    @ElementCollection
    @CollectionTable(name = "plat_allergenes")
    @Column(name = "allergene")
    private List<String> allergenes = new ArrayList<>();

    private String category;

    private boolean menuDuJour;
    private boolean actif = true;
}