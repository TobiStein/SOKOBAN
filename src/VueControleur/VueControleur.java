package VueControleur;

import modele.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
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
    private Menu menu;
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
    private String nomHero;

    private ImageIcon Menu;
    private ImageIcon bouton;
    private ImageIcon imgFinDePartie;
    private int nbPas;
    private boolean menuAffiche = false;

    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)

    public VueControleur(Jeu _jeu, String joueur) {

        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;
        nbPas = jeu.getPas();
        menu = new Menu(this);

        nomHero = joueur;
        chargerLesIcones(joueur); // met les images dans les attributs icon

        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();

        jeu.addObserver(this);

        mettreAJourAffichage();
        affPanelJeu();
    }

    public void setMenu(Menu menu){
        this.menuAffiche = true;
        this.menu = menu;
        getContentPane().add(menu, BorderLayout.NORTH);
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                if (!menuAffiche) {
                    switch (e.getKeyCode()) {  // on regarde quelle touche a été pressée

                        case KeyEvent.VK_LEFT:
                            jeu.deplacerHeros(Direction.gauche);
                            break;

                        case KeyEvent.VK_RIGHT:
                            jeu.deplacerHeros(Direction.droite);
                            break;

                        case KeyEvent.VK_DOWN:
                            jeu.deplacerHeros(Direction.bas);
                            break;

                        case KeyEvent.VK_UP:
                            jeu.deplacerHeros(Direction.haut);
                            break;
                    }
                }
            }
        });
    }


    private void chargerLesIcones(String joueur) {
        icoHero = chargerIcone("Images/"+joueur+".png");
        icoVide = chargerIcone("Images/v3sol.png");
        icoMur = chargerIcone("Images/v3mur.png");
        icoBloc = chargerIcone("Images/v3bloc.png");
        icoBlocObj1 = chargerIcone("Images/v3bloc_objectif1.png");
        icoCaseObj1 = chargerIcone("Images/v3case_objectif1.png");
        icoBlocObj2 = chargerIcone("Images/v3bloc_objectif2.png");
        icoCaseObj2 = chargerIcone("Images/v3case_objectif2.png");
        icoBlocObj3 = chargerIcone("Images/v3bloc_objectif3.png");
        icoCaseObj3 = chargerIcone("Images/v3case_objectif3.png");
        icoBlocObj4 = chargerIcone("Images/v3bloc_objectif4.png");
        icoCaseObj4 = chargerIcone("Images/v3case_objectif4.png");
        icoBlocObj5 = chargerIcone("Images/v3bloc_objectif5.png");
        icoCaseObj5 = chargerIcone("Images/v3case_objectif5.png");
        icoPiegeferme = chargerIcone("Images/v3piegeferme.png");
        icoPiegeouvert = chargerIcone("Images/v3piegeouvert.png");
        icoGlace = chargerIcone("Images/v2glace.png");
        icoPiegedeactive = chargerIcone("Images/v3piegedeactive.png");
        Menu = chargerIcone("Images/SOKOBAN.png");
        bouton = chargerIcone("Images/button.png");
        imgFinDePartie = chargerIcone("Images/finDePartie.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        Image image = null;

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(urlIcone));
            image = bufferedImage.getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
            Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {

        getContentPane().removeAll();
        setTitle("Sokoban");
        setSize(sizeX * 60, sizeY * 65);
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
        add(grilleJLabels,BorderLayout.NORTH);
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

                    if (e != null && !e.estDesactive()) {
                        if (c.getEntite() instanceof Heros) {
                            tabJLabel[x][y].setIcon(icoHero);
                        } else if (c.getEntite() instanceof BlocObjectif) {
                            switch (((BlocObjectif) c.getEntite()).getId()) {
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
                            switch (((CaseObjectif) jeu.getGrille()[x][y]).getId()) {
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
        // récupérer le processus graphique pour rafraichir
        // (normalement, à l'inverse, a l'appel du modèle depuis le contrôleur, utiliser un autre processus, voir classe Executor)

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mettreAJourAffichage();
                if (jeu.finDePartie()) {
                    nbPas = jeu.getPas();
                    System.out.println("FIN DE PARTIE");
                    affFinDePartie();
                }
            }
        });
    }

    public void reinitialiserJeu(int i) {
        Jeu jeu = new Jeu(i);
        VueControleur v = new VueControleur(jeu, "esrapido");
        v.setVisible(true);
    }

    public void reinitialiserJeu(int i, String joueur) {
        Jeu jeu = new Jeu(i);
        VueControleur v = new VueControleur(jeu, joueur);
        System.out.println("joueur modifié devenu : "+ joueur);
        v.setVisible(true);
    }

    public boolean score(){
        int pasMin = jeu.getPasMin();
        System.out.println("Nombre de pas dans score : "+nbPas);
        if(nbPas>pasMin){
            return false;
        }
        else {
            return true;
        }
    }
    public void affFinDePartie() {

        getContentPane().removeAll();
        setTitle("Fin de partie");
        menuAffiche = true;

        JPanel panel = new JPanel(new BorderLayout());
        ImageIcon imgFinDePartie= new ImageIcon("Images/finDePartie.png");
        JLabel fond = new JLabel(imgFinDePartie);
        fond.setLayout(new GridLayout(3,0));

        panel.add(fond, BorderLayout.CENTER);

        Color jaune = Color.decode("#9F8F4B");
        Font fontT = null;
        try {
            String fontPath = "Fonts/font2.ttf";
            fontT = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        fontT = fontT.deriveFont(Font.BOLD, 24);

        JLabel texte = new JLabel("Niveau "+jeu.getNiveau()+" terminé");
        texte.setFont(fontT);
        texte.setForeground(jaune);
        texte.setHorizontalAlignment(SwingConstants.CENTER);
        fond.add(texte);

        JPanel panScore = new JPanel(new GridLayout(4,0));
        panScore.setOpaque(false);

        fontT = fontT.deriveFont(Font.TRUETYPE_FONT, 18);
        Color vert = Color.decode("#089652");
        Color rouge = Color.decode("#BF111C");

        JLabel score= new JLabel();
        score.setFont(fontT);
        if (this.score()){
            score.setText("Vos pas: "+jeu.getPas());
            score.setForeground(vert);
        }else{
            score.setText("Vos pas: "+jeu.getPas());
            score.setForeground(rouge);
        }
        score.setHorizontalAlignment(SwingConstants.CENTER);

        panScore.add(score);

        JLabel minimum = new JLabel("Meilleurs pas: "+jeu.getPasMin());
        minimum.setFont(fontT);
        minimum.setForeground(Color.black);
        minimum.setHorizontalAlignment(SwingConstants.CENTER);

        panScore.add(minimum);

        JPanel vide = new JPanel();
        vide.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Ajout de marges internes (10 pixels au-dessus et en-dessous)
        vide.setOpaque(false);
        panScore.add(vide);

        JPanel boutonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        boutonsPanel.setOpaque(false);
        JButton rejouer = new JButton("Rejouer");
        rejouer.addActionListener(e->{
            reinitialiserJeu(jeu.getNiveau(), nomHero);
            dispose();
        });
        JButton menuA = new JButton("Menu");
        menuA.addActionListener(e->{
            menu = new Menu(this);
            setMenu(menu);
            //dispose();
        });
        boutonsPanel.add(rejouer);
        boutonsPanel.add(menuA);
        panScore.add(boutonsPanel, BorderLayout.SOUTH);

        fond.add(panScore);

        setSize(450,400);
        add(panel, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    public void affPanelJeu(){
        getContentPane().setBackground(Color.white);
        JPanel panelJeu = new JPanel();
        panelJeu.setLayout(new GridLayout(2,2,20,8));
        panelJeu.setBackground(Color.WHITE);

        JLabel pas = new JLabel("Pas: "+ jeu.getPas());
        pas.setFont(new Font("Monospace", Font.ITALIC+Font.BOLD, 14));
        panelJeu.add(pas);

        jeu.addObserver((o, arg) ->{
            pas.setText("Pas: " + jeu.getPas());
        });

        Color marron = Color.decode("#845723");

        JButton recommencer = new JButton("Recommencer");
        recommencer.setBorder(new LineBorder(marron));
        recommencer.setMargin(new Insets(10,4,2,4));
        recommencer.addActionListener(e -> {
            int niveau = jeu.getNiveau();
            reinitialiserJeu(niveau, nomHero);
            dispose();
        });
        panelJeu.add(recommencer);

        JLabel pasMin = new JLabel("Pas Minimum: "+ jeu.getPasMin());
        pasMin.setFont(new Font("Monospace", Font.ITALIC, 14));
        panelJeu.add(pasMin);

        JButton menu = new JButton("Menu");
        menu.setBorder(new LineBorder(marron));
        menu.setBorderPainted(true);
        menu.addActionListener(e -> {
            Menu menu2 = new Menu(this);
            setMenu(menu2);
        });
        panelJeu.add(menu);

        int pHeight= panelJeu.getPreferredSize().height;
        setSize(getWidth(), getHeight() + pHeight +25);
        panelJeu.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        getContentPane().add(panelJeu, BorderLayout.SOUTH);

        requestFocus();

    }

}


