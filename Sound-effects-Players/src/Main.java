import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String filepath = "src\\Sounds\\Firas_Jannah_Nasheed.wav";
        File file = new File(filepath);

        try (Scanner scanner = new Scanner(System.in);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file)) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            String response = "";

            while (!response.equals("Q")) {
                System.out.println("P for Play,\nS for Stop,\nR for Reset,\nQ for Quit playing...");
                System.out.print("Enter your choice (P/S/R/Q): ");

                response = scanner.next().toUpperCase();

                switch (response) {
                    case "P" -> clip.start();
                    case "S" -> clip.stop();
                    case "R" -> clip.setMicrosecondPosition(0);
                    case "Q" -> clip.close();
                    default -> System.out.println("Invalid Input");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (LineUnavailableException e) {
            System.out.println("Unable to access audio resource.");
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Audio file is not supported!!");
        } catch (IOException e) {
            System.out.println("Something went wrong!!");
        } finally {
            System.out.println("Bye....Hope you enjoyed!!");
        }
    }
}