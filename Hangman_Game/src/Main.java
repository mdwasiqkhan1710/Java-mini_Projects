import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Java Hangman Guessing Game

        String filepath = "src\\words.txt";
        ArrayList<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Wrong Path! Unable to retrieve file for words...");
        } catch (IOException e) {
            System.out.println("Something went wrong....");
        }

        // Creating Random obj so that one of the words can be chosen randomly
        Random random = new Random();

        String word = words.get(random.nextInt(words.size()));

        Scanner scanner = new Scanner(System.in);
        ArrayList<Character> wordState = new ArrayList<>();
        int wrongGuesses = 0; // variable to store count of wrong guesses

        for (int i = 0; i < word.length(); i++) {
            wordState.add('_'); // storing an underscore for each character in word
        }

        System.out.println("********************************************");
        System.out.println("Welcome to Hangman Game.....!!!");
        System.out.println("********************************************");

        while (wrongGuesses < 6) {
            System.out.println(getHangmanArt(wrongGuesses)); // displaying the hangman art
            // Making the fill in the blanks for the word
            System.out.print("Word: ");

            for (char c : wordState) {
                System.out.print(c + " ");
            }
            System.out.println();

            // Taking input from the user
            System.out.print("Guess a letter: ");
            char guess = scanner.next().toLowerCase().charAt(0);

            if (word.indexOf(guess) >= 0) {
                System.out.println("Correct Guess!!");

                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == guess) {
                        wordState.set(i, guess);
                    }
                }

                // Checking if user guessed all the letters correctly
                if (!wordState.contains('_')) {
                    System.out.print(getHangmanArt(wrongGuesses));
                    System.out.println("You Win!!");
                    System.out.printf("The word was: %s \n", word);
                    break;
                }

            } else {
                wrongGuesses++;
                System.out.println("Wrong Guess!");
            }
        }

        if (wrongGuesses >= 6) {
            System.out.print(getHangmanArt(wrongGuesses));
            System.out.println("You Lose.....Game Over!!");
            System.out.printf("The word was: %s", word);
        }

        scanner.close();
    }

    static String getHangmanArt(int wrongGuesses) {
        return switch (wrongGuesses) {
            case 0 -> """





                    """;
            case 1 -> """
                       O




                    """;
            case 2 -> """
                       O
                       |
                       |


                    """;
            case 3 -> """
                       O
                      /|
                     / |


                    """;
            case 4 -> """
                       O
                      /|\\
                     / | \\


                    """;
            case 5 -> """
                       O
                      /|\\
                     / | \\
                      /
                     /
                    """;
            case 6 -> """
                       O
                      /|\\
                     / | \\
                      / \\
                     /   \\
                    """;
            default -> "";
        };
    }
}