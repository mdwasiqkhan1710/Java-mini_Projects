import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class AlarmClock implements Runnable {
    private final LocalTime alarmTime;
    private final String filepath;

    AlarmClock(LocalTime alarmTime, String filepath) {
        this.alarmTime = alarmTime;
        this.filepath = filepath;
    }

    @Override
    public void run() {
        try {
            // Calculate initial time until alarm
            LocalTime now = LocalTime.now();

            // Check if alarm is for tomorrow (if alarm time is before current time)
            Duration timeUntilAlarm;
            if (now.isBefore(alarmTime)) {
                timeUntilAlarm = Duration.between(now, alarmTime);
            } else {
                // Alarm is for tomorrow (next day)
                timeUntilAlarm = Duration.between(now, alarmTime).plusHours(24);
            }

            long totalSecondsUntilAlarm = timeUntilAlarm.getSeconds();


            System.out.println("\nTimer started! Current time: " + now);
            System.out.println("Alarm will ring in: " + formatDuration(totalSecondsUntilAlarm));

            // Countdown until alarm
            while (totalSecondsUntilAlarm > 0) {
                Thread.sleep(1000); // Sleep for 1 second
                totalSecondsUntilAlarm--;

                // Clear previous line and update display
                System.out.print("\rTime until alarm: " + formatDuration(totalSecondsUntilAlarm));
                System.out.flush(); // Ensure output is displayed
            }

            System.out.println("\n\nALARM! ALARM! ALARM!");
            System.out.println("Time's up! Playing sound...");

            playSound(filepath);

        } catch (InterruptedException e) {
            System.out.println("Alarm was interrupted!");
        }
    }

    private String formatDuration(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void playSound(String filepath) {
        File audioFile = new File(filepath);

        if (!audioFile.exists()) {
            System.out.println("Audio file not found at: " + filepath);
            System.out.println("Please make sure the file exists in the correct location.");
            return;
        }

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Keep the clip playing for a while
            System.out.println("Playing alarm sound...");

            // Wait for the clip to finish or be interrupted
            Thread.sleep(clip.getMicrosecondLength() / 1000);

        } catch (UnsupportedAudioFileException e) {
            System.out.println("Audio file format not supported!");
            System.out.println("Please use WAV format audio files.");
        } catch (LineUnavailableException e) {
            System.out.println("Audio stream unavailable. Another application might be using the sound device.");
        } catch (IOException e) {
            System.out.println("Error reading audio file: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Sound playback was interrupted.");
        }
    }
}