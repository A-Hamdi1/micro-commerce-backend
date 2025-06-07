package org.ms.client_service;

import org.ms.client_service.entities.Client;
import org.ms.client_service.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMethodSecurity(prePostEnabled = true)
public class ClientServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ClientRepository clientRepository,
            RepositoryRestConfiguration repositoryRestConfiguration) {
        repositoryRestConfiguration.exposeIdsFor(Client.class);
        return args -> {
            // Insérer trois clients de test dans la BD
            clientRepository.save(new Client(null, "Akram Hamdi", "akram.hamdi@gmail.com", "71234567", "123 Rue Gremda"));
            clientRepository.save(new Client(null, "Zied Hechmi", "zied.hachemi@gmail.com", "71234568", "456 Rue Mahdia"));
            clientRepository.save(new Client(null, "Hamza Ben Marouen", "hamza.benmarouen@gmail.com", "71234569", "789 Rue Tunis"));
            // Afficher les clients existants dans la BD
            for (Client client : clientRepository.findAll()) {
                System.out.println(client.toString());
            }
        };
    }
}