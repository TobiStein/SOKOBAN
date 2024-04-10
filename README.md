# SOKOBAN

Projet en Programmation Orientée Objet réalisé par Khalissa Rhoulam et Esra Colmek.

------

## Présentation

Le Sokoban est un jeu de réflexion dans lequel le joueur doit déplacer des blocs vers leur emplacement cible.

**Règles du jeu :**

- Seule une boîte peut être déplacée à la fois.
- Les boîtes ne peuvent pas être tirées.
- Le joueur ne peut pas traverser les boîtes ou les murs.
- Le puzzle est résolu lorsque toutes les boîtes sont placées sur les objectifs.
- Lorsque différents types de boîtes sont présents, chaque boîte doit être sur son objectif correspondant pour résoudre le puzzle (par exemple, les boîtes bleues doivent être placées sur les objectifs bleus).
- Les boîtes marron n'ont pas d'objectifs.

Dans cette version, plusieurs obstacles ont été intégré :

- Les pièges (niveau 4).
- La glace (niveau 5).
- Les rails (niveau 6).

------

## Installation des dépendances

Après avoir cloné le projet, assurez-vous d'installer la bibliothèque Jackson Databind, nécessaire pour lire les fichiers JSON des niveaux.

### Depuis IntelliJ IDEA

1. Ouvrez IntelliJ IDEA.
2. Accédez à **File > Project Structure**.
3. Sélectionnez **Libraries** dans la colonne de gauche.
4. Cliquez sur le bouton **+** et choisissez **From Maven**.
5. Recherchez `com.fasterxml.jackson.core:jackson-databind:2.9.8`.
6. Appuyez sur **OK**.

### Depuis Maven

Ajoutez la dépendance suivante à votre fichier `pom.xml` :

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.8</version>
</dependency>
```

------

## Création de votre propre niveau

Utilisez ce modèle de fichier JSON pour créer vos propres niveaux (⚠️ ne supprimez aucune section ⚠️) :

```json
{   
    "SIZE_X": 7, // Taille en longueur
    "SIZE_Y": 7, // Taille en hauteur
    "numObjectif": 4, // Nombre d'objectifs à atteindre
    "pasMinimum": 7, // Nombre de mouvements minimum pour résoudre le puzzle
    "hero":
        {"x": 3, "y": 3}, // Coordonnées du personnage
    "BlocObjectif": [
        {"x": 3, "y": 2, "id": 1} // Coordonnées + ID des blocs à placer (l'ID doit correspondre à leur objectif)
    ],
    "CaseObjectif": [
        {"x": 3, "y": 1, "id": 1} // Coordonnées et ID des objectifs
    ],
    "Bloc": [ // Coordonnées des blocs sans objectifs
    ],
    "Mur": [ // Coordonnées des murs
    ],
    "Piege": [ // Coordonnées des pièges
      {"x": 1,"y": 1, "ouvert": true} // Optionnel : définir "ouvert" sur true pour créer un piège déjà ouvert
    ],
    "Glace": [ // Coordonnées des cases de glace
    ],
    "Rail": [ // Coordonnées des rails
      {"x": 3,"y": 2, "type": "angle_haut_droit"}
      // Le type peut prendre les valeurs suivantes :
      // horizontal, vertical, angle_bas_droit, angle_bas_gauche, angle_haut_droit, angle_haut_gauche
    ]
}
```

- Si le niveau ne contient pas un élément, laissé la liste vide (ex : `"Glace": [],` pour indiquer qu'il n'y a pas de case glace).
- Les sections **SIZE_X**, **SIZE_Y**, **numObjectif**, **pasMinimum** doivent être **obligatoirement** renseigné.
- numObjectif doit être **exactement egal** au nombre de blocs objectifs.

Assurez-vous de respecter ce format pour créer vos propres niveaux.