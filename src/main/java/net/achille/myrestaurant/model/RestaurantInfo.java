package net.achille.myrestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String ville;

    @Column(length = 1000)
    private String presentationText;

    @ElementCollection
    @CollectionTable(name = "restaurant_horaires")
    @MapKeyColumn(name = "jour")
    @Column(name = "heures")
    private Map<String, String> heuresOuverture = new HashMap<>();

    @OneToOne
    @JoinColumn(name = "manager_id", unique = true)
    @JsonIgnoreProperties({"managedRestaurant", "entrees", "plats", "desserts", "menusDuJour"})
    private User manager;
}