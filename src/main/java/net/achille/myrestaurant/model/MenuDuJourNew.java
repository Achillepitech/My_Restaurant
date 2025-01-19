package net.achille.myrestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class MenuDuJourNew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private Boolean actif;


    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"menus", "entrees", "plats", "desserts", "restaurants"})
    private User manager;

    @ManyToMany
    @JoinTable

            (
            name = "menu_plat",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "plat_id")
    )
    @JsonIgnoreProperties("menus")
    private List<Plat> plats;


    @ManyToMany
    @JoinTable(
            name= "menu_entree",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "entree_id")
    )
    @JsonIgnoreProperties("menus")
    private List<Entree> entrees;

    @ManyToMany
    @JoinTable(
            name = "menu_dessert",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dessert_id")
    )
    @JsonIgnoreProperties("menus")
    private List<Dessert> desserts = new ArrayList<>();






}