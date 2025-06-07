package org.ms.produit_service;
import org.ms.produit_service.entities.Produit;
import org.ms.produit_service.repository.ProduitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;



@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ProduitServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProduitServiceApplication.class, args);
    }
    
    @Bean
    CommandLineRunner start(ProduitRepository produitRepository, RepositoryRestConfiguration repositoryRestConfiguration) {
        repositoryRestConfiguration.exposeIdsFor(Produit.class);
        return args -> {
            produitRepository.save(new Produit(null, "Ordinateur Portable", 75000, 50, "Ordinateur portable performant avec 16 Go de RAM et SSD 512 Go", 0));
            produitRepository.save(new Produit(null, "Souris sans fil", 1500, 200, "Souris sans fil ergonomique", 0));
            produitRepository.save(new Produit(null, "Clavier mécanique", 4500, 150, "Clavier mécanique rétroéclairé", 0));
            produitRepository.save(new Produit(null, "Écran 27 pouces", 25000, 30, "Écran LED Full HD 27 pouces", 0));
            produitRepository.save(new Produit(null, "Casque gamer", 3500, 80, "Casque audio avec microphone intégré", 0));
            
            produitRepository.findAll().forEach(p -> {
                System.out.println(p.getName() + ":" + p.getPrice() + ":" + p.getQuantity() + 
                                 ":" + p.getDescription() + ":vendu=" + p.getQuantiteVendue());
            });
        };
    }
}