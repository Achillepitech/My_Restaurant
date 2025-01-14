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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("manager")
    private RestaurantInfo managedRestaurant;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("manager")
    private List<Entree> entrees;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("manager")
    private List<Plat> plats;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("manager")
    private List<Dessert> desserts;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("manager")
    private List<MenuDuJour> menusDuJour;
}