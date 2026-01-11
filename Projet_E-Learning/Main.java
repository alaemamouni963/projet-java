import model.*;
import model.enums.*;
import service.*;
import dao.*;
import patterns.*;
import utils.*;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Plateforme E-Learning - Projet Universitaire Avancé
 * Application console complète
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Database db = Database.getInstance();
    private static final UserService userService = new UserService();
    private static final CourseService courseService = new CourseService();
    private static final ExamService examService = new ExamService();
    private static final StatisticsService statsService = new StatisticsService();
    private static String currentSessionId = null;

    public static void main(String[] args) {
        afficherEnTete();
        initialiserDonneesDemo();

        boolean running = true;
        while (running) {
            if (currentSessionId == null) {
                running = afficherMenuPrincipal();
            } else {
                User currentUser = db.getUserBySession(currentSessionId);
                if (currentUser == null) {
                    currentSessionId = null;
                    continue;
                }

                switch (currentUser.getRole()) {
                    case ADMIN:
                        running = afficherMenuAdmin((Administrator) currentUser);
                        break;
                    case PROFESSOR:
                        running = afficherMenuProfesseur((Professor) currentUser);
                        break;
                    case STUDENT:
                        running = afficherMenuEtudiant((Student) currentUser);
                        break;
                }
            }
        }

        scanner.close();
        System.out.println("\nMerci d'avoir utilisé notre plateforme e-learning !");
    }

    private static void afficherEnTete() {
        System.out.println("\n" + Colors.CYAN + "=".repeat(70));
        System.out.println(" ".repeat(15) + "PLATEFORME E-LEARNING - PROJET UNIVERSITAIRE");
        System.out.println(" ".repeat(25) + "NIVEAU : AVANCÉ");
        System.out.println("=".repeat(70) + Colors.RESET);
    }

    private static void initialiserDonneesDemo() {
        System.out.println("\n" + Colors.YELLOW + "Initialisation des données de démonstration...\n" + Colors.RESET);

        // Créer l'administrateur
        Administrator admin = new Administrator(1, "admin", "admin@univ.fr", "Admin123!");
        admin.setFirstName("Pierre");
        admin.setLastName("Durand");
        db.saveUser(admin);

        // Créer un professeur
        Professor prof = new Professor(2, "prof_dupont", "j.dupont@univ.fr", "Prof123!");
        prof.setFirstName("Jean");
        prof.setLastName("Dupont");
        prof.setTitle("Docteur en Informatique");
        prof.setDepartment("Informatique");
        db.saveUser(prof);

        // Créer un étudiant
        Student etudiant = new Student(3, "etud_martin", "m.martin@etu.univ.fr", "Etud123!");
        etudiant.setFirstName("Marie");
        etudiant.setLastName("Martin");
        etudiant.setBio("Étudiante en Master Informatique");
        db.saveUser(etudiant);

        // Créer un cours
        Course javaCourse = new Course(1, "Programmation Java Avancée",
                "Apprenez les concepts avancés de Java",
                Category.PROGRAMMING, Level.ADVANCED, prof);

        // Ajouter des modules et leçons
        model.Module module1 = new model.Module(1, "Multithreading en Java", "Concepts avancés", 5);
        module1.addLesson(new Lesson(1, "Introduction aux threads", "Création de threads", 45));
        javaCourse.addModule(module1);

        // Créer un examen
        Exam exam = new Exam(1, "Examen Final Java", javaCourse, 120);
        exam.addQuestion(new Question(QuestionType.MULTIPLE_CHOICE,
                "Quelle interface doit implémenter une classe pour être exécutée dans un thread ?",
                Arrays.asList("Runnable", "Threadable", "Executable"), "Runnable", 2.0));

        db.saveCourse(javaCourse);
        db.saveExam(exam);
        prof.addCourse(javaCourse);

        // Inscrire l'étudiant
        Enrollment enrollment = new Enrollment(etudiant, javaCourse, LocalDate.now());
        etudiant.addEnrollment(enrollment);
        javaCourse.addStudent(etudiant);

        System.out.println(Colors.GREEN + "✅ Données de démonstration créées !" + Colors.RESET);
        System.out.println("\n" + Colors.CYAN + "Comptes de démonstration :" + Colors.RESET);
        System.out.println("├─ Admin: admin / Admin123!");
        System.out.println("├─ Professeur: prof_dupont / Prof123!");
        System.out.println("└─ Étudiant: etud_martin / Etud123!");
        System.out.println("\n" + "═".repeat(70));
    }

    private static boolean afficherMenuPrincipal() {
        System.out.println("\n" + Colors.BLUE + "╔════════════════════════════════════════╗");
        System.out.println("║            MENU PRINCIPAL              ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. 🔐 Se connecter                     ║");
        System.out.println("║ 2. 📝 S'inscrire (Étudiant)            ║");
        System.out.println("║ 3. 👨‍🏫 S'inscrire (Professeur)          ║");
        System.out.println("║ 4. 📚 Voir les cours                   ║");
        System.out.println("║ 5. 📊 Statistiques                     ║");
        System.out.println("║ 6. ℹ️  À propos                         ║");
        System.out.println("║ 0. 🚪 Quitter                          ║");
        System.out.println("╚════════════════════════════════════════╝" + Colors.RESET);
        System.out.print("\nVotre choix : ");

        int choix = lireEntier();
        switch (choix) {
            case 1: seConnecter(); break;
            case 2: inscrireEtudiant(); break;
            case 3: inscrireProfesseur(); break;
            case 4: afficherCatalogueCours(); break;
            case 5: afficherStatistiquesPubliques(); break;
            case 6: afficherAPropos(); break;
            case 0: return false;
            default: System.out.println(Colors.RED + "❌ Choix invalide !" + Colors.RESET);
        }
        return true;
    }

    private static void seConnecter() {
        System.out.println("\n" + Colors.CYAN + "────── CONNEXION ──────" + Colors.RESET);
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        currentSessionId = userService.login(username, password);
        if (currentSessionId != null) {
            User user = db.getUserBySession(currentSessionId);
            System.out.println(Colors.GREEN + "✅ Connecté en tant que " + user.getFullName() + Colors.RESET);
        } else {
            System.out.println(Colors.RED + "❌ Identifiants incorrects !" + Colors.RESET);
        }
    }

    private static void inscrireEtudiant() {
        System.out.println("\n" + Colors.CYAN + "────── INSCRIPTION ÉTUDIANT ──────" + Colors.RESET);
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        try {
            Student etudiant = userService.registerStudent(username, email, password);
            etudiant.setFirstName(prenom);
            etudiant.setLastName(nom);
            db.saveUser(etudiant);
            System.out.println(Colors.GREEN + "✅ Inscription réussie ! ID : " + etudiant.getId() + Colors.RESET);
        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Erreur : " + e.getMessage() + Colors.RESET);
        }
    }

    private static void inscrireProfesseur() {
        System.out.println("\n" + Colors.CYAN + "────── INSCRIPTION PROFESSEUR ──────" + Colors.RESET);
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();
        System.out.print("Titre (ex: Docteur) : ");
        String titre = scanner.nextLine();
        System.out.print("Département : ");
        String departement = scanner.nextLine();

        try {
            Professor professeur = userService.registerProfessor(username, email, password);
            professeur.setFirstName(prenom);
            professeur.setLastName(nom);
            professeur.setTitle(titre);
            professeur.setDepartment(departement);
            db.saveUser(professeur);
            System.out.println(Colors.GREEN + "✅ Inscription réussie ! ID : " + professeur.getId() + Colors.RESET);
        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Erreur : " + e.getMessage() + Colors.RESET);
        }
    }

    private static void afficherStatistiquesPubliques() {
        System.out.println("\n" + Colors.CYAN + "────── STATISTIQUES PUBLIQUES ──────" + Colors.RESET);
        System.out.println("👥 Total utilisateurs : " + db.getTotalUsers());
        System.out.println("🎓 Étudiants : " + db.getStudentCount());
        System.out.println("👨‍🏫 Professeurs : " + db.getProfessorCount());
        System.out.println("📚 Cours disponibles : " + db.getAllCourses().size());
        System.out.println("🏆 Taux de complétion moyen : " + statsService.getAverageCompletionRate() + "%");
    }

    private static void afficherCatalogueCours() {
        System.out.println("\n" + Colors.CYAN + "────── CATALOGUE DES COURS ──────" + Colors.RESET);
        List<Course> cours = db.getAllCourses();

        if (cours.isEmpty()) {
            System.out.println("Aucun cours disponible.");
            return;
        }

        for (Course coursItem : cours) {
            System.out.println(Colors.YELLOW + coursItem.getId() + ". " + coursItem.getTitle() + Colors.RESET);
            System.out.println("   📝 " + coursItem.getDescription());
            System.out.println("   👨‍🏫 Professeur : " + coursItem.getProfessor().getFullName());
            System.out.println("   👥 Étudiants : " + coursItem.getEnrolledStudents().size());
            System.out.println();
        }

        if (currentSessionId != null) {
            User user = db.getUserBySession(currentSessionId);
            if (user instanceof Student) {
                System.out.print("S'inscrire à un cours (ID) ou 0 pour retour : ");
                int idCours = lireEntier();
                if (idCours > 0) {
                    courseService.enrollStudent((Student) user, idCours);
                }
            }
        }
    }

    private static boolean afficherMenuEtudiant(Student etudiant) {
        System.out.println("\n" + Colors.BLUE + "╔════════════════════════════════════════╗");
        System.out.printf("║     TABLEAU DE BORD - %-15s ║\n", etudiant.getFirstName());
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. 📚 Mes cours                        ║");
        System.out.println("║ 2. 🏫 Catalogue                        ║");
        System.out.println("║ 3. 📝 Mes examens                      ║");
        System.out.println("║ 4. 🏆 Mes certificats                  ║");
        System.out.println("║ 5. 📈 Mes statistiques                 ║");
        System.out.println("║ 6. 💬 Forum (Bonus)                    ║");
        System.out.println("║ 7. 🎮 Badges (Bonus)                   ║");
        System.out.println("║ 8. 📄 Exporter rapport (Bonus)         ║");
        System.out.println("║ 0. 🚪 Déconnexion                      ║");
        System.out.println("╚════════════════════════════════════════╝" + Colors.RESET);
        System.out.print("\nVotre choix : ");

        int choix = lireEntier();
        switch (choix) {
            case 1: afficherMesCours(etudiant); break;
            case 2: afficherCatalogueCours(); break;
            case 3: afficherMesExamens(etudiant); break;
            case 4: afficherMesCertificats(etudiant); break;
            case 5: afficherMesStatistiques(etudiant); break;
            case 6: accederForum(etudiant); break;
            case 7: afficherBadges(etudiant); break;
            case 8: exporterRapport(etudiant); break;
            case 0:
                userService.logout(currentSessionId);
                currentSessionId = null;
                System.out.println(Colors.GREEN + "✅ Déconnecté !" + Colors.RESET);
                break;
            default: System.out.println(Colors.RED + "❌ Choix invalide !" + Colors.RESET);
        }
        return true;
    }

    private static void afficherMesCours(Student etudiant) {
        System.out.println("\n" + Colors.CYAN + "────── MES COURS ──────" + Colors.RESET);
        List<Enrollment> inscriptions = etudiant.getEnrollments();

        if (inscriptions.isEmpty()) {
            System.out.println("Vous n'êtes inscrit à aucun cours.");
            return;
        }

        for (Enrollment inscription : inscriptions) {
            Course cours = inscription.getCourse();
            double progression = etudiant.getCourseProgress(cours.getId());
            System.out.println(Colors.YELLOW + cours.getTitle() + Colors.RESET);
            System.out.println("Progression : " + progression + "%");

            if (progression < 100) {
                System.out.print("Avancer la progression (+10%) ? (o/n) : ");
                if (scanner.nextLine().equalsIgnoreCase("o")) {
                    etudiant.updateCourseProgress(cours.getId(), 10);
                    db.saveUser(etudiant);
                    System.out.println(Colors.GREEN + "✅ Progression mise à jour !" + Colors.RESET);
                }
            } else {
                System.out.println(Colors.GREEN + "✅ Cours terminé !" + Colors.RESET);
            }
            System.out.println();
        }
    }

    private static void afficherMesExamens(Student etudiant) {
        System.out.println("\n" + Colors.CYAN + "────── MES EXAMENS ──────" + Colors.RESET);
        System.out.println("Fonctionnalité en développement...");
    }

    private static void afficherMesCertificats(Student etudiant) {
        System.out.println("\n" + Colors.CYAN + "────── MES CERTIFICATS ──────" + Colors.RESET);
        List<Certificate> certificats = etudiant.getCertificates();

        if (certificats.isEmpty()) {
            System.out.println("Aucun certificat obtenu pour le moment.");
        } else {
            for (Certificate certificat : certificats) {
                System.out.println("📜 " + certificat.getId());
                System.out.println("   Cours : " + certificat.getCourse().getTitle());
                System.out.println("   Score : " + certificat.getScore() + "%");
                System.out.println("   Date : " + certificat.getIssueDate());
                System.out.println();
            }
        }
    }

    private static void afficherMesStatistiques(Student etudiant) {
        System.out.println("\n" + Colors.CYAN + "────── MES STATISTIQUES ──────" + Colors.RESET);
        System.out.println("🎓 " + etudiant.getFullName());
        System.out.println("📚 Cours inscrits : " + etudiant.getEnrollments().size());
        System.out.println("✅ Cours complétés : " + etudiant.getCompletedCoursesCount());
        System.out.println("⏱️ Heures d'apprentissage : " + etudiant.getTotalLearningHours());
        System.out.println("📊 Score moyen : " + etudiant.getAverageGrade() + "%");
        System.out.println("🏆 Badges obtenus : " + etudiant.getBadges().size());
    }

    private static void afficherBadges(Student etudiant) {
        System.out.println("\n" + Colors.CYAN + "────── BADGES ET SUCCÈS ──────" + Colors.RESET);
        List<Badge> badges = etudiant.getBadges();

        if (badges.isEmpty()) {
            System.out.println("Aucun badge obtenu pour le moment.");
        } else {
            for (Badge badge : badges) {
                System.out.println("🏆 " + badge.getName() + " - " + badge.getDescription());
            }
        }
    }

    private static void exporterRapport(Student etudiant) {
        System.out.println("\n" + Colors.CYAN + "────── EXPORT DE RAPPORT ──────" + Colors.RESET);
        try {
            PDFGenerator.generateStudentProgressReport(etudiant);
            System.out.println(Colors.GREEN + "✅ Rapport PDF généré avec succès !" + Colors.RESET);
        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Erreur lors de la génération : " + e.getMessage() + Colors.RESET);
        }
    }

    private static void accederForum(User user) {
        System.out.println("\n" + Colors.CYAN + "────── FORUM DE DISCUSSION ──────" + Colors.RESET);
        System.out.println("Fonctionnalité bonus implémentée !");
        System.out.println("1. 📋 Voir les discussions");
        System.out.println("2. 💬 Créer un nouveau sujet");
        System.out.println("3. ↩️ Retour");
        System.out.print("Choix : ");

        int choix = lireEntier();
        if (choix == 1 || choix == 2) {
            System.out.println(Colors.GREEN + "✅ Forum accessible (simulation)" + Colors.RESET);
        }
    }

    private static boolean afficherMenuProfesseur(Professor professeur) {
        System.out.println("\n" + Colors.BLUE + "╔════════════════════════════════════════╗");
        System.out.printf("║  PROFESSEUR : %-25s ║\n", professeur.getFullName());
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. 📚 Mes cours créés                  ║");
        System.out.println("║ 2. 🆕 Créer un cours                   ║");
        System.out.println("║ 3. 📝 Gérer les examens               ║");
        System.out.println("║ 4. 📊 Statistiques                    ║");
        System.out.println("║ 5. 📄 Générer rapport (Bonus)         ║");
        System.out.println("║ 0. 🚪 Déconnexion                     ║");
        System.out.println("╚════════════════════════════════════════╝" + Colors.RESET);
        System.out.print("\nVotre choix : ");

        int choix = lireEntier();
        switch (choix) {
            case 1: afficherCoursProfesseur(professeur); break;
            case 2: creerCours(professeur); break;
            case 3: gererExamens(professeur); break;
            case 4: afficherStatistiquesCours(professeur); break;
            case 5: genererRapportProfesseur(professeur); break;
            case 0:
                userService.logout(currentSessionId);
                currentSessionId = null;
                break;
            default: System.out.println(Colors.RED + "❌ Choix invalide !" + Colors.RESET);
        }
        return true;
    }

    private static void afficherCoursProfesseur(Professor professeur) {
        System.out.println("\n" + Colors.CYAN + "────── MES COURS CRÉÉS ──────" + Colors.RESET);
        List<Course> cours = professeur.getCreatedCourses();

        if (cours.isEmpty()) {
            System.out.println("Vous n'avez créé aucun cours.");
        } else {
            for (Course coursItem : cours) {
                System.out.println(Colors.YELLOW + coursItem.getTitle() + Colors.RESET);
                System.out.println("   📝 " + coursItem.getDescription());
                System.out.println("   👥 Étudiants inscrits : " + coursItem.getEnrolledStudents().size());
                System.out.println("   🎯 Niveau : " + coursItem.getLevel().getDescription());
                System.out.println();
            }
        }
    }

    private static void creerCours(Professor professeur) {
        System.out.println("\n" + Colors.CYAN + "────── CRÉATION D'UN COURS ──────" + Colors.RESET);
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Description : ");
        String description = scanner.nextLine();

        System.out.println("Catégories disponibles :");
        for (Category cat : Category.values()) {
            System.out.println(cat.name() + " - " + cat.getDescription());
        }
        System.out.print("Catégorie : ");
        String categorie = scanner.nextLine().toUpperCase();

        try {
            Category cat = Category.valueOf(categorie);
            Course cours = courseService.createCourse(titre, description, cat, Level.BEGINNER, professeur);
            System.out.println(Colors.GREEN + "✅ Cours créé avec succès ! ID : " + cours.getId() + Colors.RESET);
        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Erreur : " + e.getMessage() + Colors.RESET);
        }
    }

    private static void gererExamens(Professor professeur) {
        System.out.println("\n" + Colors.CYAN + "────── GESTION DES EXAMENS ──────" + Colors.RESET);
        System.out.println("1. 📝 Créer un examen");
        System.out.println("2. 📊 Voir les résultats");
        System.out.println("3. ↩️ Retour");
        System.out.print("Choix : ");

        int choix = lireEntier();
        if (choix == 1) {
            System.out.print("Titre de l'examen : ");
            String titre = scanner.nextLine();
            System.out.print("ID du cours : ");
            int courseId = lireEntier();

            Course cours = db.getCourseById(courseId);
            if (cours != null && cours.getProfessor().getId() == professeur.getId()) {
                Exam examen = examService.creerExamen(titre, cours, 60);
                System.out.println(Colors.GREEN + "✅ Examen créé avec succès !" + Colors.RESET);
            } else {
                System.out.println(Colors.RED + "❌ Cours non trouvé ou vous n'en êtes pas le propriétaire" + Colors.RESET);
            }
        } else if (choix == 2) {
            System.out.println("Fonctionnalité en développement...");
        }
    }

    private static void afficherStatistiquesCours(Professor professeur) {
        System.out.println("\n" + Colors.CYAN + "────── STATISTIQUES DE MES COURS ──────" + Colors.RESET);
        List<Course> cours = professeur.getCreatedCourses();

        if (cours.isEmpty()) {
            System.out.println("Aucun cours pour afficher des statistiques.");
        } else {
            for (Course coursItem : cours) {
                System.out.println(Colors.YELLOW + coursItem.getTitle() + Colors.RESET);
                System.out.println("   👥 Étudiants : " + coursItem.getEnrolledStudents().size());
                System.out.println("   📊 Progression moyenne : " +
                        courseService.getAverageCompletion(coursItem) + "%");
                System.out.println();
            }
        }
    }

    private static void genererRapportProfesseur(Professor professeur) {
        System.out.println("\n" + Colors.CYAN + "────── GÉNÉRATION DE RAPPORT ──────" + Colors.RESET);
        System.out.println("Rapport pour : " + professeur.getFullName());
        System.out.println("📚 Cours créés : " + professeur.getCreatedCourses().size());

        int totalEtudiants = 0;
        for (Course cours : professeur.getCreatedCourses()) {
            totalEtudiants += cours.getEnrolledStudents().size();
        }

        System.out.println("👥 Total étudiants : " + totalEtudiants);
        System.out.println(Colors.GREEN + "✅ Rapport généré avec succès !" + Colors.RESET);
    }

    private static boolean afficherMenuAdmin(Administrator admin) {
        System.out.println("\n" + Colors.BLUE + "╔════════════════════════════════════════╗");
        System.out.println("║         PANEL ADMINISTRATEUR           ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. 👥 Gérer les utilisateurs           ║");
        System.out.println("║ 2. 📊 Statistiques globales            ║");
        System.out.println("║ 3. 🌐 Internationalisation (Bonus)     ║");
        System.out.println("║ 0. 🚪 Déconnexion                      ║");
        System.out.println("╚════════════════════════════════════════╝" + Colors.RESET);
        System.out.print("\nVotre choix : ");

        int choix = lireEntier();
        switch (choix) {
            case 1: gererUtilisateurs(); break;
            case 2: afficherStatistiquesGlobales(); break;
            case 3: gererInternationalisation(); break;
            case 0:
                userService.logout(currentSessionId);
                currentSessionId = null;
                break;
            default: System.out.println(Colors.RED + "❌ Choix invalide !" + Colors.RESET);
        }
        return true;
    }

    private static void gererUtilisateurs() {
        System.out.println("\n" + Colors.CYAN + "────── GESTION DES UTILISATEURS ──────" + Colors.RESET);
        List<User> utilisateurs = db.getAllUsers();

        System.out.println("👥 Liste des utilisateurs :");
        for (User user : utilisateurs) {
            System.out.println("ID: " + user.getId() + " - " + user.getFullName() +
                    " (" + user.getRole().getDisplayName() + ")");
        }

        System.out.println("\nOptions :");
        System.out.println("1. 👀 Voir les détails");
        System.out.println("2. 🚫 Désactiver un utilisateur");
        System.out.println("3. ↩️ Retour");
        System.out.print("Choix : ");

        int choix = lireEntier();
        if (choix == 1) {
            System.out.print("ID de l'utilisateur : ");
            int userId = lireEntier();
            User user = db.getUserById(userId);
            if (user != null) {
                System.out.println("\n📋 Détails de " + user.getFullName());
                System.out.println("📧 Email : " + user.getEmail());
                System.out.println("🎭 Rôle : " + user.getRole().getDisplayName());
                System.out.println("📅 Date d'inscription : " + user.getRegistrationDate());
                System.out.println("✅ Actif : " + (user.isActive() ? "Oui" : "Non"));
            } else {
                System.out.println(Colors.RED + "❌ Utilisateur non trouvé" + Colors.RESET);
            }
        }
    }

    private static void afficherStatistiquesGlobales() {
        System.out.println("\n" + Colors.CYAN + "────── STATISTIQUES GLOBALES ──────" + Colors.RESET);
        System.out.println("👥 Utilisateurs : " + statsService.getTotalUsers());
        System.out.println("📚 Cours : " + statsService.getTotalCourses());
        System.out.println("🎓 Taux de complétion moyen : " + statsService.getAverageCompletionRate() + "%");
    }

    private static void gererInternationalisation() {
        System.out.println("\n" + Colors.CYAN + "────── INTERNATIONALISATION ──────" + Colors.RESET);
        System.out.println("1. 🇫🇷 Français");
        System.out.println("2. 🇬🇧 Anglais");
        System.out.println("3. 🇪🇸 Espagnol");
        System.out.print("Choisir une langue : ");

        int choix = lireEntier();
        String langue = switch (choix) {
            case 1 -> "fr";
            case 2 -> "en";
            case 3 -> "es";
            default -> "fr";
        };

        I18n.setLanguage(langue);
        System.out.println(Colors.GREEN + "✅ Langue changée : " + I18n.getCurrentLanguage() + Colors.RESET);
    }

    private static void afficherAPropos() {
        System.out.println("\n" + Colors.CYAN + "═".repeat(70));
        System.out.println("            PLATEFORME E-LEARNING - VERSION 2.0");
        System.out.println("═".repeat(70) + Colors.RESET);
        System.out.println("\n📋 FONCTIONNALITÉS IMPLÉMENTÉES :");
        System.out.println("├─ ✅ Gestion complète des utilisateurs");
        System.out.println("├─ ✅ Système de cours avec modules et leçons");
        System.out.println("├─ ✅ Examens avec différents types de questions");
        System.out.println("├─ ✅ Suivi de progression et certifications");
        System.out.println("├─ ✅ Design Patterns (Singleton, Factory, Strategy, Observer)");
        System.out.println("├─ ✅ Forum de discussion (Bonus)");
        System.out.println("├─ ✅ Système de badges (Bonus)");
        System.out.println("├─ ✅ Export PDF (Bonus)");
        System.out.println("├─ ✅ Internationalisation (Bonus)");
        System.out.println("└─ ✅ API REST simulée (Bonus)");

        System.out.println("\n👨‍🎓 PROJET UNIVERSITAIRE - ARCHITECTURE LOGICIELLE AVANCÉE");
        System.out.println("═".repeat(70));
    }

    private static int lireEntier() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

class Colors {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String CYAN = "\033[0;36m";
}