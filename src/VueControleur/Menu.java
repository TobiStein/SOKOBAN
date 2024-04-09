package VueControleur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu extends JPanel{
    private final VueControleur controleur;
    private JButton[] boutonsNiveaux;
    private ImageIcon imgFond;
    private ImageIcon imgTitre;
    private String personnage;



    public Menu(VueControleur controleur) {
        this.controleur = controleur;
        personnage = "esrapido";

        setLayout(new BorderLayout());

        imgFond= new ImageIcon("Images/SOKOBAN.png");

        Image imageFond = imgFond.getImage().getScaledInstance(450, 400, Image.SCALE_DEFAULT);
        ImageIcon image = new ImageIcon(imageFond);
        JLabel fond = new JLabel(image);
        fond.setLayout(new GridBagLayout());

        add(fond,BorderLayout.CENTER);

        // structure pour stocker les boutons
        JPanel panelBoutons = new JPanel(new GridBagLayout());
        panelBoutons.setOpaque(false); // Rendre le JPanel transparent
        GridBagConstraints gbc = new GridBagConstraints();

        //Créer les boutons des niveaux
        JPanel panelNiveaux = new JPanel(new GridLayout(0,2,5,5));
        panelNiveaux.setOpaque(false); // Rendre le JPanel transparent

        // Ajout des boutons au JPanel transparent
        boutonsNiveaux = new JButton[6];
        for (int i = 0; i < boutonsNiveaux.length; i++) {
            int num = i + 1;
            boutonsNiveaux[i] = new JButton("Niveau " + num);
            boutonsNiveaux[i].addActionListener(e -> {
                controleur.reinitialiserJeu(num, getPersonnage());
                controleur.dispose();
            });
            boutonsNiveaux[i].setMargin(new Insets(4, 20, 4, 20));

            panelNiveaux.add(boutonsNiveaux[i]);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelBoutons.add(panelNiveaux, gbc);

        // Bouton choix du personngae
        String[] options = {"esrapido", "totobi", "lumineau"};
        JComboBox<String> choixperso;
        choixperso = new JComboBox<>(options);

        choixperso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer l'option sélectionnée
                setPersonnage((String) choixperso.getSelectedItem());
                System.out.println("Option sélectionnée : " + personnage);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.ipady = 10;
        panelBoutons.add(choixperso, gbc);

        fond.add(panelBoutons);

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

    private void setPersonnage(String personnage) {
        this.personnage = personnage;
    }

    public String getPersonnage() {
        return personnage;
    }
}
