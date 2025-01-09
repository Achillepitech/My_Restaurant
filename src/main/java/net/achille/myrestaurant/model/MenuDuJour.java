package net.achille.myrestaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDuJour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "entree_id")
    private Plat entree;

    @ManyToOne
    @JoinColumn(name = "plat_principal_id")
    private Plat platPrincipal;

    @ManyToOne
    @JoinColumn(name = "dessert_id")
    private Plat dessert;

    private Double prix;

    private boolean actif = true;
}