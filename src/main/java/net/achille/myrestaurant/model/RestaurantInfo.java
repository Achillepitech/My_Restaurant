package net.achille.myrestaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private String adresse;
    private String telephone;

    @ElementCollection
    private Map<String, String> horaires = new HashMap<>();

    private String ville;

    @Column(length = 1000)
    private String presentationText;

    // Pour les horaires d'ouverture
    @ElementCollection
    @CollectionTable(name = "restaurant_horaires")
    @MapKeyColumn(name = "jour")
    @Column(name = "heures")
    private Map<String, String> heuresOuverture = new HashMap<>();
}