package fr.devops.server;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class Login {
    static Connection connection;
    static OracleDataSource source;
    static int lastid = 0;
    public static void main(String[ ] args){
        try {
            source = new OracleDataSource();

            // CONNECTION DE BDD
            source.setURL("jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1");
            source.setUser("p2206689");
            source.setPassword("671812");

            System.out.println("\n - TABLE DES SORTS - \n");
            displaySortTable();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Affichage de tout les enregistrements de la table sort
     */
    private static void displaySortTable() {
        try {
            connection = source.getConnection();

            // Requête et Connection pour Table sort
            PreparedStatement getSorts = connection.prepareStatement("SELECT * FROM sort");
            ResultSet firstResult = getSorts.executeQuery();

            // Affichage de toute la table ligne par ligne avec leurs propriétés
            while (firstResult.next()) {
                lastid = Integer.parseInt(firstResult.getString("ID"));
                var id = firstResult.getString("ID");
                var damages = firstResult.getString("DEGAT");
                var magicPoints = firstResult.getString("POINT_MAGIE");
                var incantation = firstResult.getString("INCANTATION");
                System.out.println("\n ===== " +
                        "\nSort " + id +
                        "\nDamages : " + damages +
                        "\nCost : " + magicPoints +
                        "\nIncantation : " + incantation);
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
