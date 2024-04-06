package VueControleur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel{
    private VueControleur controleur;
    private JButton[] boutonsNiveaux;



    public void Menu(VueControleur controleur){
        this.controleur = controleur;

        setLayout(new GridLayout(0, 1)); // Utilisez GridLayout avec 1 colonne pour les boutons des niveaux

        // Créer les boutons des niveaux
        boutonsNiveaux = new JButton[5]; // Supposons que vous avez 5 niveaux
        for (int i = 0; i < boutonsNiveaux.length; i++) {
            int numeroNiveau = i + 1;
            boutonsNiveaux[i] = new JButton("Niveau " + numeroNiveau);
            boutonsNiveaux[i].addActionListener(e -> {
                controleur.reinitialiserJeu(numeroNiveau);
            });
            add(boutonsNiveaux[i]);
        }
        afficher();
    }

    public void afficher() {
        controleur.getContentPane().removeAll();
        controleur.setTitle("Menu Sokoban");
        controleur.setSize(450, 400);
        controleur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controleur.getContentPane().add(this);
        controleur.setVisible(true);
    }
}
