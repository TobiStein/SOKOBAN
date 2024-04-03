package VueControleur;

import modele.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon icoHero;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoBloc;
    private ImageIcon icoBlocObj1;
    private ImageIcon icoCaseObj1;
    private ImageIcon icoBlocObj2;
    private ImageIcon icoCaseObj2;
    private ImageIcon icoBlocObj3;
    private ImageIcon icoCaseObj3;
    private ImageIcon icoBlocObj4;
    private ImageIcon icoCaseObj4;
    private ImageIcon icoBlocObj5;
    private ImageIcon icoCaseObj5;
    private ImageIcon icoPiegeferme;
    private ImageIcon icoPiegeouvert;
    private ImageIcon icoPiegedeactive;
    private ImageIcon icoGlace;


    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleur(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;

        chargerLesIcones(); // met les images dans les attributs icon
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();

        jeu.addObserver(this);

        mettreAJourAffichage();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée

                    case KeyEvent.VK_LEFT : jeu.deplacerHeros(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : jeu.deplacerHeros(Direction.droite); break;
                    case KeyEvent.VK_DOWN : jeu.deplacerHeros(Direction.bas); break;
                    case KeyEvent.VK_UP : jeu.deplacerHeros(Direction.haut); break;


                }
            }
        });
    }


    private void chargerLesIcones() {
        icoHero = chargerIcone("Images/v2hero.png");
        icoVide = chargerIcone("Images/v2vide.png");
        icoMur = chargerIcone("Images/v2mur.png");
        icoBloc = chargerIcone("Images/v2bloc.png");
        icoBlocObj1 = chargerIcone("Images/v2bloc_objectif1.png");
        icoCaseObj1 = chargerIcone("Images/v2case_objectif1.png");
        icoBlocObj2 = chargerIcone("Images/v2bloc_objectif2.png");
        icoCaseObj2 = chargerIcone("Images/v2case_objectif2.png");
        icoBlocObj3 = chargerIcone("Images/v2bloc_objectif3.png");
        icoCaseObj3 = chargerIcone("Images/v2case_objectif3.png");
        icoBlocObj4 = chargerIcone("Images/v2bloc_objectif4.png");
        icoCaseObj4 = chargerIcone("Images/v2case_objectif4.png");
        icoBlocObj5 = chargerIcone("Images/v2bloc_objectif5.png");
        icoCaseObj5 = chargerIcone("Images/v2case_objectif5.png");
        icoPiegeferme = chargerIcone("Images/v2piegeferme.png");
        icoPiegeouvert = chargerIcone("Images/v2piegeouvert.png");
        icoGlace = chargerIcone("Images/v2glace.png");
        icoPiegedeactive = chargerIcone("Images/v2piegedeactive.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        Image image = null;

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(urlIcone));
            image = bufferedImage.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
            Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {

        getContentPane().removeAll();

        setTitle("Sokoban");
        setSize(sizeX*40, sizeY*43);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {


        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {

                Case c = jeu.getGrille()[x][y];

                if (c != null) {

                    Entite e = c.getEntite();

                    if (e!= null && !e.isDesactive()) {
                        if (c.getEntite() instanceof Heros) {
                            tabJLabel[x][y].setIcon(icoHero);
                        } else if (c.getEntite() instanceof BlocObjectif) {
                            switch (((BlocObjectif) c.getEntite()).getId()){
                                case 1:
                                    tabJLabel[x][y].setIcon(icoBlocObj1);
                                    break;
                                case 2:
                                    tabJLabel[x][y].setIcon(icoBlocObj2);
                                    break;
                                case 3:
                                    tabJLabel[x][y].setIcon(icoBlocObj3);
                                    break;
                                case 4:
                                    tabJLabel[x][y].setIcon(icoBlocObj4);
                                    break;
                                case 5:
                                    tabJLabel[x][y].setIcon(icoBlocObj5);
                                    break;
                            }
                        } else if (c.getEntite() instanceof Bloc) {
                            tabJLabel[x][y].setIcon(icoBloc);
                        }
                    } else {
                        if (jeu.getGrille()[x][y] instanceof Mur) {
                            tabJLabel[x][y].setIcon(icoMur);
                        } else if (jeu.getGrille()[x][y] instanceof CaseObjectif) {
                            switch (((CaseObjectif) jeu.getGrille()[x][y]).getId()){
                                case 1:
                                    tabJLabel[x][y].setIcon(icoCaseObj1);
                                    break;
                                case 2:
                                    tabJLabel[x][y].setIcon(icoCaseObj2);
                                    break;
                                case 3:
                                    tabJLabel[x][y].setIcon(icoCaseObj3);
                                    break;
                                case 4:
                                    tabJLabel[x][y].setIcon(icoCaseObj4);
                                    break;
                                case 5:
                                    tabJLabel[x][y].setIcon(icoCaseObj5);
                                    break;
                            }
                        } else if (jeu.getGrille()[x][y] instanceof Vide) {
                            tabJLabel[x][y].setIcon(icoVide);
                        } else if (jeu.getGrille()[x][y] instanceof Piege) {
                            if (((Piege) jeu.getGrille()[x][y]).isOuvert()) {
                                if (((Piege) jeu.getGrille()[x][y]).isDeactive()) {
                                    tabJLabel[x][y].setIcon(icoPiegedeactive);
                                } else {
                                    tabJLabel[x][y].setIcon(icoPiegeouvert);
                                }
                            } else {
                                tabJLabel[x][y].setIcon(icoPiegeferme);
                            }
                        } else if (jeu.getGrille()[x][y] instanceof Glace) {
                            tabJLabel[x][y].setIcon(icoGlace);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        if (jeu.finDePartie()) {
            System.out.println("FIN DE PARTIE");
            grilleMurFinDePartie();
        }
        /*

        // récupérer le processus graphique pour rafraichir
        // (normalement, à l'inverse, a l'appel du modèle depuis le contrôleur, utiliser un autre processus, voir classe Executor)


        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }

    public void reinitialiserJeu() {
        int niveau = jeu.getNiveau();
        Jeu jeu= new Jeu(niveau);
        VueControleur v = new VueControleur(jeu);
        v.setVisible(true);
    }

    public void niveauSuivant() {
        int niveau = jeu.getNiveau();
        Jeu jeu= new Jeu(niveau+1);
        VueControleur v = new VueControleur(jeu);
        v.setVisible(true);
//        int n = jeu.getNiveau();
//        jeu.updateNiveau(n);
    }/*créer une fonction dans jeu pour changer le niveau, "jeu.chargerNsuivant(n)"; au lieu de faire un new etc... à la fin de la fonction chargerNsuivant on
    fait un setC..(observable) ?? et on fait un remove.all sur le jframe dans le chargement des composantes. ; on gere ca avec une boite de dialogue, plus simple.*/




    public void grilleMurFinDePartie() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(null);

        JLabel labelFin = new JLabel("Niveau " + jeu.getNiveau() + " gagné!");
        labelFin.setFont(new Font("Arial", Font.BOLD, 24));
        panel.setBackground(Color.GRAY);
        panel.setOpaque(true);

        int labelWidth = labelFin.getPreferredSize().width;
        int labelHeight = labelFin.getPreferredSize().height;
        int labelX = (getWidth() - labelWidth) / 2; // position horizontale
        int labelY = (getHeight() - labelHeight) / 4; // position verticale

        labelFin.setBounds(labelX, labelY, labelWidth, labelHeight);

        JButton nSuivant = new JButton("niveau suivant");
        JButton rejouer = new JButton("rejouer");

        int boutonW = nSuivant.getPreferredSize().width;
        int boutonH = nSuivant.getPreferredSize().height;
        int boutonWNS = nSuivant.getPreferredSize().width + 40; //pour le bouton suivant (texte plus long doc plus grand)

        int boutonX = (getWidth() - boutonW) / 2;
        int boutonY = labelY + 50;
        int boutonXNS = (getWidth() - boutonWNS) / 2;

        nSuivant.setBounds(boutonXNS, boutonY, boutonWNS, boutonH);
        rejouer.setBounds(boutonX, (boutonY + 30), boutonW, boutonH);

        nSuivant.addActionListener(e -> {
            niveauSuivant();
            dispose();
        });

        rejouer.addActionListener(e -> {
            reinitialiserJeu();
            dispose();
        });

        panel.add(labelFin);
        panel.add(rejouer);
        panel.add(nSuivant);

        add(panel);
        revalidate();
        repaint();
    }
}
