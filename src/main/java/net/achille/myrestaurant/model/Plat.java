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
public class Plat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private Boolean actif;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonIgnoreProperties({"entrees", "plats", "desserts", "menus", "managedRestaurant"})
    private User manager;

    @ManyToMany(mappedBy = "plats")
    @JsonIgnoreProperties({"entrees", "plats", "desserts", "manager"})
    private List<MenuDuJourNew> menus;
}