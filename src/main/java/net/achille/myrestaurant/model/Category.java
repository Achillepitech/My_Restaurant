//package net.achille.myrestaurant.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//
//
//public class Category {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String nom;
//    private String description;
//    private Integer ordreAffichage;
//    @JsonIgnore
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private List<Plat> plats = new ArrayList<>();
//}