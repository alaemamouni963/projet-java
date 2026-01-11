package service;

import model.*;
import dao.Database;
import java.util.Scanner;

public class ForumService {
    private Database db = Database.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public void accessCourseForum(User user, Course course) {
        System.out.println("\n────── 💬 FORUM DU COURS ──────");
        System.out.println("Cours: " + course.getTitle());
        System.out.println("\nOptions:");
        System.out.println("1. 📋 Voir les discussions");
        System.out.println("2. 💬 Créer une nouvelle discussion");
        System.out.println("3. ↩️ Retour");
        System.out.print("Choix: ");

        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            System.out.println("\nDiscussions (simulation):");
            System.out.println("1. Question sur le multithreading");
            System.out.println("2. Problème avec l'exercice 3");
            System.out.println("3. Partage de ressources utiles");
        } else if (choice == 2) {
            System.out.print("Titre de la discussion: ");
            String title = scanner.nextLine();
            System.out.print("Message: ");
            String message = scanner.nextLine();
            System.out.println("✅ Discussion créée avec succès !");
        }
    }

    public void displayMainMenu(User user) {
        System.out.println("\n────── 💬 FORUM PRINCIPAL ──────");
        System.out.println("Bienvenue sur le forum, " + user.getFirstName() + " !");
        System.out.println("\n1. 📚 Forums par cours");
        System.out.println("2. 🔍 Rechercher des discussions");
        System.out.println("3. 💬 Mes messages");
        System.out.println("4. ↩️ Retour");
        System.out.print("Choix: ");
    }
}