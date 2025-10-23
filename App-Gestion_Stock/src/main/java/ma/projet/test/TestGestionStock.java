package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestGestionStock {

    public static void main(String[] args) {
        try {

            CategorieService categorieService = new CategorieService();
            ProduitService produitService = new ProduitService();
            CommandeService commandeService = new CommandeService();
            LigneCommandeService ligneCommandeService = new LigneCommandeService();


            Categorie catOrdinateurs = new Categorie("Ordinateurs", "Ordinateurs de bureau et portables");
            Categorie catPeriph = new Categorie("Périphériques", "Souris, claviers, écrans");
            Categorie catLogiciels = new Categorie("Logiciels", "Logiciels et applications");

            categorieService.create(catOrdinateurs);
            categorieService.create(catPeriph);
            categorieService.create(catLogiciels);


            Produit p1 = new Produit("ES12", "Ordinateur portable Dell", 120, 10, catOrdinateurs);
            Produit p2 = new Produit("ZR85", "Souris optique", 100, 25, catPeriph);
            Produit p3 = new Produit("EE85", "Écran LCD 24 pouces", 200, 5, catPeriph);
            Produit p4 = new Produit("AB45", "Clavier mécanique", 80, 15, catPeriph);
            Produit p5 = new Produit("CD67", "Logiciel antivirus", 150, 20, catLogiciels);

            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            produitService.create(p4);
            produitService.create(p5);



            List<Produit> produitsOrdinateurs = produitService.findByCategorie(catOrdinateurs);
            System.out.println("Produits de la catégorie Ordinateurs :");
            for (Produit p : produitsOrdinateurs) {
                System.out.println("- " + p.getReference() + " : " + p.getDesignation() + " (" + p.getPrix() + " DH)");
            }

            List<Produit> produitsPeriph = produitService.findByCategorie(catPeriph);
            System.out.println("\nProduits de la catégorie Périphériques :");
            for (Produit p : produitsPeriph) {
                System.out.println("- " + p.getReference() + " : " + p.getDesignation() + " (" + p.getPrix() + " DH)");
            }


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdf.parse("06/03/2003");
            Date date2 = sdf.parse("23/10/2025");
            Date date3 = sdf.parse("18/12/2022");

            Commande cmd1 = new Commande(date1, "mechach");
            Commande cmd2 = new Commande(date2, "marouane");
            Commande cmd3 = new Commande(date3, "messi");

            commandeService.create(cmd1);
            commandeService.create(cmd2);
            commandeService.create(cmd3);



            LigneCommande lc1 = new LigneCommande(7, 120, cmd1, p1);
            LigneCommande lc2 = new LigneCommande(14, 100, cmd1, p2);
            LigneCommande lc3 = new LigneCommande(5, 200, cmd1, p3);

            LigneCommande lc4 = new LigneCommande(3, 150, cmd2, p5);
            LigneCommande lc5 = new LigneCommande(2, 80, cmd2, p4);

            ligneCommandeService.create(lc1);
            ligneCommandeService.create(lc2);
            ligneCommandeService.create(lc3);
            ligneCommandeService.create(lc4);
            ligneCommandeService.create(lc5);



            produitService.afficherProduitsCommande(cmd1.getId());


            Date dateDebut = sdf.parse("06/03/2003");
            Date dateFin = sdf.parse("23/10/2025");

            List<Produit> produitsCommandes = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
            System.out.println("Produits commandés entre le " + sdf.format(dateDebut) + " et le " + sdf.format(dateFin) + " :");
            System.out.println("Référence       Prix        Quantité" );

            for (Produit p : produitsCommandes) {
                System.out.println("- " + p.getReference() + " : "  + "       " + p.getPrix() + " DH        "+p.getQuantiteStock());
            }


            List<Produit> produitsChers = produitService.findProduitsPrixSuperieur(100);
            System.out.println("Produits avec prix > 100 DH :");
            System.out.println("Référence       Prix        Quantité" );

            for (Produit p : produitsChers) {
                System.out.println("- " + p.getReference() + " : " +"       " + p.getPrix() + " DH" + "        "+p.getQuantiteStock());
            }


        } catch (Exception e) {
            System.err.println("Erreur lors des tests : " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}