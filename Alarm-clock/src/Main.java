import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){

        //creating scanner object to get users input
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime alarmTime = null;
        String filepath = "src\\pink-sky-by-johny-grimes.wav";

        while(alarmTime == null){
            try{
                System.out.print("Enter an alarm time (HH:MM:SS): ");
                String inputTime = scanner.nextLine();

                alarmTime = LocalTime.parse(inputTime, formatter);
                System.out.println("Alarm is set for: " + alarmTime);
            }
            catch(DateTimeParseException e){
                System.out.println(
                        """
                        Time entered is invalid.
                        For example, for 7:30 AM please type: 07:30:00
                        """
                );
            }
        }

        AlarmClock alarmClock = new AlarmClock(alarmTime, filepath);
        Thread alarmThread = new Thread(alarmClock);
        alarmThread.start();

        scanner.close();
    }
}