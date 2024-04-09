
import VueControleur.VueControleur;
import VueControleur.Menu;
import modele.Jeu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Main {
    public static void main(String[] args) {
        Jeu jeu = new Jeu(1);

        VueControleur vc = new VueControleur(jeu, "esrapido");
        Menu menu = new Menu(vc);
        vc.setMenu(menu);

        vc.setVisible(true);

    }
}
