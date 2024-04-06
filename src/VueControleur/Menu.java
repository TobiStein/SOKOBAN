package VueControleur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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



    public Menu(VueControleur controleur) {
        this.controleur = controleur;

        setLayout(new BorderLayout());

        imgFond= new ImageIcon("Images/SOKOBAN.png");

        Image imageFond = imgFond.getImage().getScaledInstance(450, 400, Image.SCALE_DEFAULT);
        ImageIcon image = new ImageIcon(imageFond);
        JLabel fond = new JLabel(image);
        fond.setLayout(new GridBagLayout());

        add(fond,BorderLayout.CENTER);

        //Créer les boutons des niveaux
        JPanel panelBoutons = new JPanel(new GridLayout(0,1,5,5));
        panelBoutons.setOpaque(false); // Rendre le JPanel transparent

        // Ajout des boutons au JPanel transparent
        boutonsNiveaux = new JButton[5];
        for (int i = 0; i < boutonsNiveaux.length; i++) {
            int num = i + 1;
            boutonsNiveaux[i] = new JButton("Niveau " + num);
            boutonsNiveaux[i].addActionListener(e -> {
                controleur.reinitialiserJeu(num);
                controleur.dispose();
            });
            boutonsNiveaux[i].setMargin(new Insets(4, 20, 4, 20));

            panelBoutons.add(boutonsNiveaux[i]);
        }
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
}
