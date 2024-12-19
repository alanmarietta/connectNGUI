package edu.wm.cs.cs301.connectn.model;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class LeaderboardManager {
    private Map<String, String> scores = new HashMap<>();
    private final String leaderboardFilePath = "resources/leaderboard.txt";

    public LeaderboardManager() {
        loadScores();  // Load scores
    }

    private void loadScores() {
        File file = new File(leaderboardFilePath);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(": ");
                    if (parts.length == 2) {
                        scores.put(parts[0].trim(), parts[1].trim());
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        } else {
            // Initialize scores with "No score yet" if the file does not exist
            scores.put("small", "No score yet");
            scores.put("medium", "No score yet");
            scores.put("large", "No score yet");
        }
    }

    public void updateScore(String gameMode, int newScore) {
        String existingEntry = scores.get(gameMode);
        int existingScore = (existingEntry != null && !existingEntry.equals("No score yet")) ?
                            Integer.parseInt(existingEntry.split(" ")[0]) : Integer.MAX_VALUE;

        if (newScore < existingScore) {
            String name = JOptionPane.showInputDialog("New best score! Enter your name (max 30 chars):");
            if (name != null && !name.isEmpty()) {
                name = name.substring(0, Math.min(name.length(), 30)); // Trim name to 30 characters
                scores.put(gameMode, newScore + " turns | Player: " + name);
                saveScores();
            }
        }
    }

    public void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(leaderboardFilePath))) {
            for (Map.Entry<String, String> entry : scores.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the leaderboard: " + e.getMessage());
        }
    }

    public void displayLeaderboard() {
        StringBuilder displayText = new StringBuilder("<html><h1>Leaderboard</h1><br>");
        scores.forEach((key, value) -> {
            displayText.append(key).append(" - ").append(value.equals("No score yet") ? "No score yet" : value).append("<br>");
        });
        displayText.append("</html>");
        JOptionPane.showMessageDialog(null, displayText.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }
}
