# Game

### Project made in 2022 / 2023.

## Versions:

### Java version: 17 Apache Gradle

## Our team :

### GARCIA Antony

### JACQUOT Maxime

# Informations techniques (en français)
### Globalement
Le projet est composé de trois parties: L'application client, l'application serveur et une bibliotheque partagée (Shared).

Le jeu utilise une communication de type client-serveur avec le protocol TCP.

Pour plus de flexibilitée et éviter les dépendances fortes, un système de Service est implémenté.
Il permet de récuperer statiquement (depuis n'importe où) des instances de IService.
Ce système permet au projet d'être séparé en plusieurs morceaux sans se marcher sur les pieds en tant que développeurs.

### Le jeu

L'environnement de jeu est représenté par un ensemble d'élément stoquées dans un "monde" (classe World)

Ce monde tourne grace à la GameLoop, et reçois des évenement exterieurs en tant que IngameEventListener car il s'est abonné à l'IngameEventService

Les entitées présentes dans le monde (classe Entity) sont typé avec l'énumération EntityType, qui allège leurs synchronization au réseau (en envoie que l'instance d'énumeration EntityType, et non l'instance d'Entity).

Les entitées peuvent définir des EntityProperty qui sont automatiquement synchronisée avec le réseau (classe EntitySyncManager).

### Le réseau

Le serveur et chaque clients disposent d'une simulation d'un environement de jeu implémentée avec World et GameLoop.

Le client envoie des IRequest au serveur qui lui retourne des IResponse.
Le serveur peut aussi envoyer des IngameEvent, qui sont des informations sur l'état du World du serveur.

### Authentification

Le client et le serveur utilisent IRequest et IResponse pour échanger des infromations lors de la procédure d'authentification décrite ci dessous:
1)Le client envoie nom d'utilisateur et mot de passe au serveur.
2)Le serveur interoge la base de donnée sur l'existence d'un couple Username+Password correspondant.
3)Le serveur envoie le résultat au client et lui créer un personnage en jeu pour qu'il puisse jouer.

EN AUCUN CAS LES MOTS DE PASSE SONT HACHÉS OU ENCRYPTÉS, QUE CE SOIT LORS DE LA COMMUNICATION ENTRE CLIENT ET SERVEUR OU LORS DE LEURS
ENREGISTREMENT EN BASE

### La base de donnée
La base de donnée utilisée est celle de l'IUT (addresse de connexion "jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1")
Cette addresse peut facilement être changée dans le code (projet serveur, classe Main), mais aucun dispositif n'a été prévu pour le configurer après la compilation du code.

La base de donnée nécessite seulement d'une table nommée LEJEU_CLIENT composée de trois colones:
 * 	ID de type numérique et disposant d'un système d'auto-incrémentation (SEQUENCE SQL)
 *  USERNAME de type varchar2 et de taille 20 charactère minimum
 *  PASSWORD de type varchar2 et de taille 20 charactère minimum