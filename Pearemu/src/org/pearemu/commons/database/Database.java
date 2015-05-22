package org.pearemu.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.pearemu.commons.utils.Config;
/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Database {

    public Connection db;
    private static Database self = null;
    private ScheduledExecutorService scheduledCommit;
    private boolean _autocommit = false;

    private Database() {
        try {
            System.out.println("Connexion à la base de données : ");
            StringBuilder dsn = new StringBuilder();

            dsn.append("jdbc:mysql://");
            dsn.append(Config.DB_HOST);
            dsn.append("/").append(Config.DB_NAME);

            db = DriverManager.getConnection(
                    dsn.toString(),
                    Config.DB_USER,
                    Config.DB_PASS
            );

            scheduledCommit = Executors.newSingleThreadScheduledExecutor();
            scheduledCommit.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            if (!self._autocommit) {
                                try {
                                    synchronized(self.db){
                                        self.db.commit();
                                    }
                                    System.out.println("Commit Database");
                                } catch (SQLException ex) {
                                    System.out.println("Commit impossible !");
                                    ex.printStackTrace();
                                }
                            }
                        }
                    },
                    Config.DB_COMMIT_TIME,
                    Config.DB_COMMIT_TIME, TimeUnit.SECONDS
            );
            System.out.println("Ok");
        } catch (SQLException ex) {
            System.out.println("Connexion à la base de donnée impossible.");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public static void setAutocommit(boolean state) {
        self._autocommit = state;
        try {
            synchronized(self.db){
                if (state) {
                    System.out.println("Commit Database");
                    self.db.commit();
                }
                self.db.setAutoCommit(state);
            }
        } catch (SQLException ex) {
            System.out.println("Impossible de changer le mode autoCommit !");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public static ResultSet query(String query) {
        try {
            ResultSet RS;
            synchronized (self) {
                RS = self.db.createStatement().executeQuery(query);
                System.out.println("Execution de la requête : " + query);
            }
            return RS;
        } catch (SQLException e) {
            System.out.println("exécution de la requête '" + query + "' impossible !");
            e.printStackTrace();
            return null;
        }
    }

    public static PreparedStatement prepare(String query) {
        try {
            PreparedStatement stmt = self.db.prepareStatement(query);
            System.out.println("Préparation de la requête : " + query);
            return stmt;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Prépare une requête d'insertion (retourne l'id généré)
     *
     * @param query
     * @return
     */
    public static PreparedStatement prepareInsert(String query) {
        try {
            PreparedStatement stmt = self.db.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            return stmt;
        } catch (SQLException e) {
            return null;
        }
    }

    public static void connect() {
        if (self == null) {
            self = new Database();
            setAutocommit(false);
        }
    }

    public static void close() {
        try {
            System.out.println("Arrêt de database : ");
            self.scheduledCommit.shutdown();
            self.scheduledCommit = null;
            self.db.close();
            System.out.println("ok");
        } catch (SQLException ex) {
            System.out.println("Unable to close database");
            ex.printStackTrace();
        }
    }
}
