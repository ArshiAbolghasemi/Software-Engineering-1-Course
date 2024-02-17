package ut.app;

import java.util.ArrayList;
import com.opencsv.CSVReader;
import java.io.FileReader;
import util.Date;
import java.util.Optional;

public class App 
{

    public static final int VALID_COLUMN_COUNTS = 8;
    public static final String INPUT_CSV_FILE_PATH = "../data/input.csv";

    public static final int PLAYER_NAME_COL = 0;
    public static final int TEAM_NAME_COL = 1;

    public static final int MEMBERSHIP_START_DATE_DAY_COL = 2;
    public static final int MEMBERSHIP_START_DATE_MONTH_COL = 3;
    public static final int MEMBERSHIP_START_DATE_YEAR_COL = 4;

    public static final int MEMBERSHIP_END_DATE_DAY_COL = 2;
    public static final int MEMBERSHIP_END_DATE_MONTH_COL = 3;
    public static final int MEMBERSHIP_END_DATE_YEAR_COL = 4;


    public static void main( String[] args )
    {
        try {
            ArrayList<Player> players = readInputCsv();

            Optional<Player> foundedPlayer = players.stream()
                .filter(player -> player.getName().equals("Gholam"))
                .findFirst();
        
            if (foundedPlayer == null) {
                System.out.println("Gholam was not found!");
            }

            Player gholam = foundedPlayer.get();
            System.out.printf(
                "Gholam playe %d days in %s", 
                gholam.getMembershipsDayCount("Golgohar"),         // Optional<Player> player = players.stream()
                "Golgohar"
            );
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
    }

    private static ArrayList<Player> readInputCsv() throws Exception
    {
        try (CSVReader reader = new CSVReader(new FileReader(INPUT_CSV_FILE_PATH))) {
            ArrayList<Player> players = new ArrayList<Player>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length != VALID_COLUMN_COUNTS) {
                    throw new Exception("Invalid file column structure!");
                }
                
                String[] row = line;
                Optional<Player> foundedPlayer = players.stream()
                        .filter(player -> player.getName().equals(row[PLAYER_NAME_COL]))
                        .findFirst();

                Player player = foundedPlayer != null ? 
                    foundedPlayer.get() : new Player(row[PLAYER_NAME_COL]);

                player.addMembership(
                    new Membership(
                        row[TEAM_NAME_COL], 
                        new Date(
                            Integer.parseInt(row[MEMBERSHIP_START_DATE_DAY_COL]), 
                            Integer.parseInt(row[MEMBERSHIP_START_DATE_MONTH_COL]), 
                            Integer.parseInt(row[MEMBERSHIP_START_DATE_YEAR_COL])
                        ),
                        new Date(
                            Integer.parseInt(row[MEMBERSHIP_END_DATE_DAY_COL]), 
                            Integer.parseInt(row[MEMBERSHIP_END_DATE_MONTH_COL]), 
                            Integer.parseInt(row[MEMBERSHIP_END_DATE_YEAR_COL])
                        )
                    )
                );

                if (foundedPlayer != null) continue;

                players.add(player);
            }

            return players;
        }
    }
}
