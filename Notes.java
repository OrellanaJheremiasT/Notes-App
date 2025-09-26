import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Notes {
    private static Path notesFolder;
    private static final int NOTES_PER_PAGE= 5;
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initializeNotesFolder();
        showMainMenu();
    }

    private static void initializeNotesFolder() {
        System.out.println("=== Welcome to the Notes Application ===");
        System.out.print("Enter the folder location for notes: ");
        String folderPath = sc.nextLine().trim();
        notesFolder = Paths.get(folderPath);

        try {
            if (!Files.exists(notesFolder)) {
                Files.createDirectories(notesFolder);
                System.out.println("Folder Create at:" + notesFolder.toAbsolutePath());
            }
            System.out.println("Notes folder: " + notesFolder.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error creating/accessing folder: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Create new note");
            System.out.println("2. View notes list");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            try {
                int option = Integer.parseInt(sc.nextLine());

                switch (option) {
                    case 1 -> createNote();
                    case 2 -> listNotes();
                    case 3 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void createNote() {
        System.out.println("=== CREATE NEW NOTE ===");
        System.out.println("Enter file name (without .txt extension): ");
        String fileName = sc.nextLine().trim();

        if (fileName.isEmpty()) {
            System.out.println("Filename cannot be empty.");
            return;
        }

        if (!fileName.toLowerCase().endsWith(".txt")) {
            fileName += ".txt";
        }

        Path notePath = notesFolder.resolve(fileName);

        if (Files.exists(notePath)) {
            System.out.println("A note with this name already exists. Overwrite? (y/n):");
            String reponse = sc.nextLine().trim().toLowerCase();
            if (!reponse.equals("y")) {
                return;
            }
        }

        System.out.println("Enter note content (press Enter on an empty line to finish):");
        StringBuilder content = new StringBuilder();
        String line;

        while (true) {
            line = sc.nextLine();
            if (line.isEmpty()) {
                break;
            }
            content.append(line).append("\n");
        }

        try {
            Files.write(notePath, content.toString().getBytes());
            System.out.println("Note saved successfully: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving note: " + e.getMessage());
        }
    }

    private static void listNotes() {
        int currentPage = 0;
        List<Path> noteFiles = getNoteFiles();

        if (noteFiles.isEmpty()) {
            System.out.println("No notes found.");
            return;
        }

        while (true) {
            displayNotesPage(noteFiles, currentPage);
            System.out.println("\n");
            System.out.println("1-5: Select note to view/edit/delete");
            System.out.println("6: Next page");
            System.out.println("7: Previous page");
            System.out.println("8: Back to main menu");
            System.out.print("Select an option: ");

            try {
                int option = Integer.parseInt(sc.nextLine());
                if (option >= 1 && option <= 5) {
                    int noteIndex = currentPage * NOTES_PER_PAGE + (option - 1);
                    if (noteIndex < noteFiles.size()) {
                        manageNote(noteFiles.get(noteIndex));
                        noteFiles = getNoteFiles();
                    } else {
                        System.out.println("Invalid note selection.");
                    }
                } else if (option == 6) {
                    if ((currentPage + 1) * NOTES_PER_PAGE < noteFiles.size()) {
                        currentPage++;
                    } else {
                        System.out.println("No more pages.");
                    }
                } else if (option == 7) {
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        System.out.println("Already at the first page.");
                    }
                } else if (option == 8) {
                    break;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    private static List <Path> getNoteFiles() {
        try {
            return Files.list(notesFolder)
                    .filter(path -> path.toString().toLowerCase().endsWith(".txt"))
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error accessing notes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    private static void displayNotesPage (List<Path> noteFiles, int page) {
        int startIndex = page * NOTES_PER_PAGE;
        int endIndex = Math.min(startIndex + NOTES_PER_PAGE, noteFiles.size());

        System.out.println("\n=== NOTES LIST (Page " + (page + 1) + ") ===");
        
        if (startIndex >= noteFiles.size()) {
            System.out.println("No notes to display.");
            return;
        }

        for (int i = startIndex; i < endIndex; i++) {
            String fileName = noteFiles.get(i).getFileName().toString();
            System.out.println((i - startIndex + 1) + ". " + fileName);
        }

        if (endIndex < noteFiles.size()) {
            System.out.println("6. Next page");
        }
        if (page > 0) {
            System.out.println("7. Previous page");
        }
    }

    private static void manageNote (Path notePath) {
        while (true){
            System.out.println("\n=== MANAGE NOTE: " + notePath.getFileName() + " ===");
            System.out.println("1. View content");
            System.out.println("2. Edit note");
            System.out.println("3. Delete note");
            System.out.println("4. Back to list");
            System.out.print("Select an option: ");

            try {
                 int option = Integer.parseInt(sc.nextLine());

                 switch (option){
                    case 1 -> viewNote(notePath);
                    case 2 -> editNote(notePath);
                    case 3 -> {
                        deleteNote(notePath);
                        return;
                    }
                        case 4 -> {
                            return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");

                 }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void viewNote (Path notePath) {
        try {
            String content = Files.readString(notePath);
            System.out.println("\n=== CONTENT OF: " + notePath.getFileName() + " ===");
            System.out.println(content);
            System.out.println("=== END OF NOTE ===");
        } catch (IOException e) {
            System.out.println("Error reading note: " + e.getMessage());
        }
    }
    
    private static void editNote (Path notePath) {
        try {
            String currentContent = Files.readString(notePath);
            System.out.println("\n=== EDIT NOTE: " + notePath.getFileName() + " ===");
            System.out.println("Current content:");
            System.out.println(currentContent);
            System.out.println("=== ENTER NEW CONTENT ===");
            System.out.println("(Press Enter on an empty line to finish)");

            StringBuilder newContent = new StringBuilder();
            String line;

            while (true) {
                line = sc.nextLine();
                if (line.isEmpty()) {
                    break;
                }
                newContent.append(line).append("\n");
            }
            Files.write(notePath, newContent.toString().getBytes());
            System.out.println("Note updated successfully.");


        } catch (IOException e) {
            System.out.println("Error editing note: " + e.getMessage());
        }
    }

    private static void deleteNote (Path notePath) {
        System.out.println("Are you sure you want to delete " + notePath.getFileName() + "? (y/n): ");
        String response = sc.nextLine().trim().toLowerCase();

        if (response.equals("y")) {
            try {
                Files.delete(notePath);
                System.out.println("Note deleted successfully.");
            } catch (IOException e) {
                System.out.println("Error deleting note: " + e.getMessage());
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
}
