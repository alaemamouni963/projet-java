package service;

import model.User;
import java.util.Scanner;

public class ChatService {
    private Scanner scanner = new Scanner(System.in);

    public void startChatSession(User user) {
        System.out.println("\n────── 💬 CHAT EN TEMPS RÉEL ──────");
        System.out.println("Bienvenue dans le chat, " + user.getFirstName() + " !");
        System.out.println("\n⚠️  Cette fonctionnalité utilise le multi-threading pour simuler");
        System.out.println("    un chat en temps réel (simulation).");

        System.out.println("\nSalles de chat disponibles:");
        System.out.println("1. 💬 Général");
        System.out.println("2. 📚 Aide aux devoirs");
        System.out.println("3. 👥 Projets de groupe");
        System.out.println("4. ↩️ Retour");
        System.out.print("Choix: ");

        int choice = Integer.parseInt(scanner.nextLine());
        if (choice >= 1 && choice <= 3) {
            simulateChatRoom(user, choice);
        }
    }

    private void simulateChatRoom(User user, int roomId) {
        String[] roomNames = {"Général", "Aide aux devoirs", "Projets de groupe"};
        System.out.println("\n────── 💬 SALLE: " + roomNames[roomId-1] + " ──────");
        System.out.println("Utilisateurs en ligne: 12");
        System.out.println("\nMessages récents:");
        System.out.println("[14:30] Jean: Bonjour à tous !");
        System.out.println("[14:32] Marie: Quelqu'un peut m'aider avec l'exercice 3 ?");
        System.out.println("[14:35] Pierre: Oui, je peux t'aider");

        System.out.print("\nVotre message (ou 'exit' pour quitter): ");
        String message = scanner.nextLine();

        if (!message.equalsIgnoreCase("exit")) {
            System.out.println("[14:40] " + user.getFirstName() + ": " + message);
            System.out.println("✅ Message envoyé ! (simulation)");
        }
    }
}