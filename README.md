# Micro-Commerce Backend

Un système de gestion commerciale basé sur une architecture microservices, développé avec Spring Boot et Spring Cloud.

## Architecture

Le projet est composé des microservices suivants :

- **Config Service** (Port: 5555) : Service de configuration centralisé
- **Eureka Discovery Service** (Port: 8761) : Service de découverte et d'enregistrement des microservices
- **Gateway Service** (Port: 8080) : Point d'entrée unique pour toutes les requêtes
- **Auth Service** (Port: 8087) : Gestion de l'authentification et des autorisations
- **Client Service** (Port: 8081) : Gestion des clients
- **Produit Service** (Port: 8082) : Gestion des produits
- **Facture Service** (Port: 8083) : Gestion des factures
- **Reglement Service** (Port: 8084) : Gestion des règlements

## Technologies Utilisées

- Java 17
- Spring Boot
- Spring Cloud
- Spring Security
- Spring Data JPA
- MySQL 8.0
- Redis
- Docker & Docker Compose
- Maven

## Prérequis

- JDK 17 ou supérieur
- Maven
- Docker et Docker Compose
- Git

## Installation

1. Cloner le repository :
```bash
git clone https://github.com/A-Hamdi1/micro-commerce-backend.git
cd micro-commerce-backend
```

2. Construire les services :
```bash
./build-all.ps1
```

3. Démarrer les services avec Docker Compose :
```bash
docker-compose up -d
```

## Configuration

### Base de données
Le système utilise MySQL avec les bases de données suivantes :
- auth_db
- client_db
- produit_db
- facture_db
- reglement_db

Les identifiants par défaut sont :
- Username: miniproject_user
- Password: miniproject_password

### Services
Chaque service est configuré via le Config Service et peut être personnalisé dans le dossier `cloud-conf`.

## Points d'accès

- Eureka Dashboard: http://localhost:8761
- Gateway: http://localhost:8080
- Config Service: http://localhost:5555

## Structure du Projet

```
micro-commerce-backend/
├── config-service/          # Service de configuration
├── eureka-discoveryservice/ # Service de découverte
├── gatewayservice/         # API Gateway
├── authentificationservice/ # Service d'authentification
├── client-service/         # Service de gestion des clients
├── produit-service/        # Service de gestion des produits
├── factureservice/         # Service de gestion des factures
├── reglement-service/      # Service de gestion des règlements
├── cloud-conf/            # Configuration centralisée
├── docker-compose.yml     # Configuration Docker
└── setup.sql             # Script d'initialisation de la base de données
```

## Développement

### Construire un service individuel
```bash
cd <service-name>
./mvnw clean package -DskipTests
```

### Vérifier les logs
```bash
docker-compose logs -f <service-name>
```

## Sécurité

- Les services communiquent via HTTPS
- L'authentification est gérée par le Auth Service
- Les tokens JWT sont utilisés pour la sécurité
- Redis est utilisé pour la gestion des sessions

## Monitoring

Chaque service expose des endpoints Actuator pour le monitoring :
- Health Check: /actuator/health
- Metrics: /actuator/metrics
- Info: /actuator/info

## Contribution

1. Fork le projet
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Créer une Pull Request

## Licence

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.

## Support

Pour toute question ou problème, veuillez créer une issue dans le repository GitHub. 
