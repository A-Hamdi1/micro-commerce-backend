package org.ms.facture_service.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Produit {
    private Long id;
    private String name;
    private double price;
    private long quantity;
    private String description;
    private long quantiteVendue;
}