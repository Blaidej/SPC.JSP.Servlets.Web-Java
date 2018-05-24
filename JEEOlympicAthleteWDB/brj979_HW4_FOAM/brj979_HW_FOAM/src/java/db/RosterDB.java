package db;
/* 
 * Name: Blaine Jacques
 * CSCI2466 HW4: Freedonia Olympic Athlete Servlet
 * 
 * Freedonia Olympic athlete Management System (FOAM). The FOAM System
 * is a web based application that will allow users to view, add, edit, and
 * delete athletes from the roster.
 */
import edu.saintpaul.csci2466.foam.model.Athlete;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import model.AthleteBean;

/**
 * Roster DB has methods which execute various SQL statements to perform
 * operations on the foam database's athlete table. The Roster DB is part of the
 * model. It is responsible for managing the data of the application and makes 
 * a connection to the database.
 *
 * @author Blaine Jacques
 */
public class RosterDB {

    /* holds the NationalID for the athlete in case of an Edit to it's 
       NationalID, it needs the priorID to locate the athlete's record
     */
    private String priorNatID;

    private DataSource RosterDB;

    private RosterDB() {

        try {
            RosterDB = getRosterDB();

        } catch (NamingException ex) {
            Logger.getLogger(RosterDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns an instance of the to access the Roster DataBase
     *
     * @return an access to the rosterDB
     */
    public static RosterDB getInstance() {
        return RosterDBHolder.INSTANCE;
    }

    /**
     * Returns a list of all Athletes in the database
     *
     * @return list of all athletes from the database
     */
    public List<AthleteBean> findAll() {

        final String SQL_STMT = "SELECT NationalID, FirstName, LastName, DateOfBirth FROM foam.athletes";
        LinkedList<AthleteBean> athletes = new LinkedList<>();

        //USE TRY(with RESOURCES)as an easy way to handle the exceptions
        //and it will automagically close them also
        try (Connection connection = RosterDB.getConnection();
                PreparedStatement pStmt = prepStatement(connection, SQL_STMT);
                ResultSet rs = pStmt.executeQuery();) {

            while (rs.next()) {
                String nationalID = rs.getString("NationalID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                Date dob = rs.getDate("DateOfBirth");
                //convert sql date to local date
                LocalDate javaDate = dob.toLocalDate();

                AthleteBean athlete = new AthleteBean(nationalID, firstName, lastName, javaDate);
                athletes.add(athlete);
                Logger.getLogger(RosterDB.class.getName()).log(Level.INFO,
                        String.format("%s %s %s %s", nationalID, firstName, lastName, javaDate));
            }

        } catch (SQLException ex) {
            Logger.getLogger(RosterDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return athletes;
    }

    /**
     * Returns the athlete record for with the specified nationalID
     *
     * @param NationalID national ID specific to only one athlete
     * @return an AthleteBean containing all of the athletes info
     */
    public AthleteBean find(String NationalID) {

        final String SQL_STMT = "SELECT NationalID, FirstName, LastName,"
                + " DateOfBirth FROM foam.athletes WHERE NationalID = ?";

        AthleteBean athlete = null;

        try (Connection connection = RosterDB.getConnection();
                PreparedStatement pStmt = prepStatement(connection, SQL_STMT, NationalID);
                ResultSet rs = pStmt.executeQuery();) {

            if (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String nationalID = rs.getString("NationalID");
                priorNatID = nationalID;
                Date dob = rs.getDate("DateOfBirth");
                //convert sql date to local date
                LocalDate javaDate = dob.toLocalDate();

                athlete = new AthleteBean(nationalID, firstName, lastName, javaDate);

                athlete.setFirstName(firstName);
                athlete.setLastName(lastName);
                athlete.setNationalID(nationalID);
                athlete.setDateOfBirth(javaDate);

            }

        } catch (SQLException ex) {
            Logger.getLogger(RosterDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return athlete;
    }

    /**
     * Checks whether a specified athlete is on the roster
     *
     * @param NationalID the nationalID the user input on an add or edit
     * @return whether a user's nationalID is available or used already, true =
     * on Roster | false = not on Roster
     */
    public boolean isOnRoster(String NationalID) {

        NationalID = NationalID.toUpperCase();
        boolean onRoster = false;
        final String SQL_STMT = "SELECT NationalID FROM foam.athletes WHERE NationalID = ?";
        //AthleteBean athlete = null;

        try (Connection connection = RosterDB.getConnection();
                PreparedStatement pStmt = prepStatement(connection, SQL_STMT, NationalID);
                ResultSet rs = pStmt.executeQuery();) {

            if (rs.next()) {
                onRoster = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(RosterDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return onRoster;
    }

    /**
     * Adds the specified athlete to this roster.
     *
     * @param athlete the user input which builds an athlete info to be added to
     * the database
     * @return whether the athlete was added successfully or not, true = added |
     * false = not added
     */
    public boolean add(Athlete athlete) {

        boolean successAdd;
        final String SQL_STMT = "INSERT INTO foam.athletes (NationalID, "
                + "FirstName, LastName, DateOfBirth)"
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = RosterDB.getConnection();
                PreparedStatement pStmt = prepStatement(connection, SQL_STMT, athlete);) {

            pStmt.executeUpdate();
            successAdd = true;
            Logger.getLogger(RosterDB.class.getName()).log(Level.INFO,
                    String.format(" %s %s", successAdd, pStmt));

        } catch (SQLException ex) {
            Logger.getLogger(RosterDB.class.getName()).log(Level.SEVERE, null, ex);
            successAdd = false;
        }
        return successAdd;
    }

    /**
     * Replaces the existing record for the athlete with the record specified.
     *
     * @param athlete the user input which builds an athlete info to be updated
     * into the database
     * @return whether the athlete was updated successfully or not, true =
     * updated | false = not updated
     */
    public boolean update(Athlete athlete) {

        boolean successUpdate = false;

        /*  Need to be able to change the NationalID, and the AthleteID never
            changes, so we need to find the matching user NationalID, get the 
            athleteID from that. 
         */
        final String SQL_STMT = "SELECT AthleteID FROM foam.athletes WHERE NationalID = ? ";

        /*  We need to find the Athlete's ID via the NationalID since we might
            be changing the nationalID itself
         */
        try (Connection connection = RosterDB.getConnection();
                PreparedStatement pStmt = prepStatement(connection, SQL_STMT, priorNatID);
                ResultSet rs = pStmt.executeQuery();) {

            /*  if rs.next succeeds, it means we have an athlete that matches
                and we need to update that athlete
             */
            if (rs.next()) {

                String SQL_UPDATE_STMT = "UPDATE foam.athletes SET "
                        + "NationalID = ?, "
                        + "FirstName = ?, "
                        + "LastName = ?, "
                        + "DateOfBirth = ? "
                        + "WHERE AthleteID = ?";
               
                PreparedStatement pStmtUpDate = prepStatement(connection, SQL_UPDATE_STMT, rs, athlete);
                int updateCount = pStmtUpDate.executeUpdate();
                if (updateCount > 0) {
                    successUpdate = true;
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(RosterDB.class.getName()).log(Level.SEVERE, null, ex);
            successUpdate = false;
        }

        return successUpdate;
    }

    /**
     * Removes the record with the specified nationalID from the roster.
     *
     * @param nationalID of the specific athlete that you want to delete
     * @return whether the record was successfully deleted, true = deleted |
     * false = not deleted
     */
    public boolean delete(String nationalID) {

        boolean successDelete;

        final String SQL_STMT = "DELETE FROM foam.athletes WHERE NationalID = ? ";

        try (Connection connection = RosterDB.getConnection();
                PreparedStatement pStmtDelete = prepStatement(connection, SQL_STMT, nationalID);) {

            pStmtDelete.execute();
            successDelete = true;

        } catch (SQLException ex) {
            Logger.getLogger(RosterDB.class.getName()).log(Level.SEVERE, null, ex);
            successDelete = false;
        }

        return successDelete;
    }

    /**
     * Prepares a PreparedStatment, overloaded method, to prepare different
     * types of statements for different types of queries
     *
     * @param conn a database connection
     * @param SQL_STMT SQL statement compatible with a PreparedStatement
     * @return a preparedstatement, with set parameter values via parameter
     * index.
     * @throws SQLException if a parameter index doesn't correspond to a
     * parameter marker in the SQL statement or if a database access error
     * occurs, or this method calls on a closed prepared statement.
     */
    public PreparedStatement prepStatement(final Connection conn, final String SQL_STMT) throws SQLException {
        PreparedStatement pStmt = conn.prepareStatement(SQL_STMT);

        return pStmt;
    }

    /**
     * Prepares a PreparedStatment, overloaded method, to prepare different
     * types of statements for different types of queries
     *
     * @param conn a database connection
     * @param SQL_STMT SQL statement compatible with a PreparedStatement
     * @param NationalID id of the athlete being looked up
     * @return a prepared statement, with set parameter values via parameter
     * index.
     * @throws SQLException if a parameter index doesn't correspond to a
     * parameter marker in the SQL statement or if a database access error
     * occurs, or this method calls on a closed prepared statement.
     */
    public PreparedStatement prepStatement(final Connection conn, final String SQL_STMT, String NationalID) throws SQLException {
        PreparedStatement pStmt = conn.prepareStatement(SQL_STMT);
        pStmt.setString(1, NationalID);
        return pStmt;
    }

    /**
     * Prepares a PreparedStatment, overloaded method, to prepare different
     * types of statements for different types of queries
     *
     * @param conn a database connection
     * @param SQL_STMT SQL statement compatible with a PreparedStatement
     * @param athlete an entire athlete with it's information about itself
     * @return a prepared statement, with set parameter values via parameter
     * index.
     * @throws SQLException if a parameter index doesn't correspond to a
     * parameter marker in the SQL statement or if a database access error
     * occurs, or this method calls on a closed prepared statement.
     */
    public PreparedStatement prepStatement(final Connection conn, final String SQL_STMT, Athlete athlete) throws SQLException {
        PreparedStatement pStmt = conn.prepareStatement(SQL_STMT);
        //converts date from local date to sql date
        Date date = Date.valueOf(athlete.getDateOfBirth());

        pStmt.setString(1, athlete.getNationalID().toUpperCase());
        pStmt.setString(2, athlete.getFirstName());
        pStmt.setString(3, athlete.getLastName());
        pStmt.setDate(4, date);
        return pStmt;
    }

    /**
     * Prepares a PreparedStatment, overloaded method, to prepare different
     * types of statements for different types of queries
     *
     * @param conn a database connection
     * @param SQL_STMT SQL statement compatible with a PreparedStatement
     * @param rs the resultset of the the previous query, so we update via the
     * athlete's id rather than it's nationalID
     * @param athlete an entire athlete with it's information about itself
     * @return a prepared statement, with set parameter values via parameter
     * index.
     * @throws SQLException if a parameter index doesn't correspond to a
     * parameter marker in the SQL statement or if a database access error
     * occurs, or this method calls on a closed prepared statement.
     */
    public PreparedStatement prepStatement(final Connection conn, final String SQL_STMT, ResultSet rs, Athlete athlete) throws SQLException {
        PreparedStatement pStmt = conn.prepareStatement(SQL_STMT);
        //converts date from local date to sql date
        Date date = Date.valueOf(athlete.getDateOfBirth());

        pStmt.setString(1, athlete.getNationalID().toUpperCase());
        pStmt.setString(2, athlete.getFirstName());
        pStmt.setString(3, athlete.getLastName());
        pStmt.setDate(4, date);
        pStmt.setInt(5, rs.getInt("AthleteID"));

        return pStmt;
    }

    private static class RosterDBHolder {

        private static final RosterDB INSTANCE = new RosterDB();
    }

    private DataSource getRosterDB() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/RosterDB");
    }

}
