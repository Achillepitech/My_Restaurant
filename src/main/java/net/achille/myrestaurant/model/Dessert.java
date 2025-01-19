package net.achille.myrestaurant.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dessert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Double prix;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"entrees", "plats", "desserts", "menus", "managedRestaurant"})
    private User manager;

    @ManyToMany(mappedBy = "desserts")
    @JsonIgnoreProperties({"entrees", "plats", "desserts", "manager"})
    private List<MenuDuJourNew> menus;
}