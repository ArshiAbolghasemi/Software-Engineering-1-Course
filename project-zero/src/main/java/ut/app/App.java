package ut.app;

import java.util.ArrayList;
import com.opencsv.CSVReader;
import java.io.FileReader;
import util.Date;
import java.util.Optional;

public class App 
{

    public static final int VALID_COLUMN_COUNTS = 8;
    public static final String INPUT_CSV_FILE_PATH = "/project-zero/data/input.csv";

    public static final int PLAYER_NAME_COL = 0;
    public static final int TEAM_NAME_COL = 1;

    public static final int MEMBERSHIP_START_DATE_DAY_COL = 2;
    public static final int MEMBERSHIP_START_DATE_MONTH_COL = 3;
    public static final int MEMBERSHIP_START_DATE_YEAR_COL = 4;

    public static final int MEMBERSHIP_END_DATE_DAY_COL = 5;
    public static final int MEMBERSHIP_END_DATE_MONTH_COL = 6;
    public static final int MEMBERSHIP_END_DATE_YEAR_COL = 7;


    public static void main( String[] args )
    {
        try {
            ArrayList<Player> players = readInputCsv();

            String targetPlayerName = "Gholam";
            String targetTeamName = "Golgohar";

            Optional<Player> foundedPlayer = foundPlayer(players, targetPlayerName);

            if (!foundedPlayer.isPresent()) {
                System.out.printf("%s was not found!", targetPlayerName);
                return;
            }

            Player player = foundedPlayer.get();
            System.out.printf(
                "%s player %d days in %s",
                targetPlayerName,
                player.getMembershipsDayCount(targetTeamName),
                targetTeamName
            );
        } catch (Throwable exception) {
            System.out.print(exception.getMessage());
        }
    }

    private static ArrayList<Player> readInputCsv() throws Exception
    {
        try (CSVReader reader =
                     new CSVReader(new FileReader(System.getProperty("user.dir") + INPUT_CSV_FILE_PATH))
        ) {
            ArrayList<Player> players = new ArrayList<Player>();
            String[] row;
            while ((row = reader.readNext()) != null) {
                if (row.length != VALID_COLUMN_COUNTS) {
                    throw new Exception("Invalid file column structure!");
                }

                Optional<Player> foundedPlayer = foundPlayer(players, row[PLAYER_NAME_COL]);

                Player player = foundedPlayer.isPresent() ?
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

                if (foundedPlayer.isPresent()) continue;

                players.add(player);
            }

            return players;
        }
    }

    private static Optional<Player> foundPlayer(ArrayList<Player> players, String playerName)
    {
        return players.stream()
            .filter(player -> player.getName().equals(playerName))
            .findFirst();
    }
}
