import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList; // import the ArrayList class


//"jdbc:mysql://localhost:3306/testing", "root", "namCse201"

// // getting the result back
// while (res.next()) {
//     String user = res.getString("userName");
//     System.out.println(user);
// }

public class dbConnect {
  // instance variable
  public String url, userName, password;
  Connection con;

  // // main static method
  // public static void main(String[] mode) {
  //   System.out.println("hello");
  //   // testing
  //   dbConnect connection = new dbConnect("jdbc:mysql://35.223.64.237:3306/librapp", "root", "thisIsCse201");
  //   // Boolean test = connection.updateAcc("nina", "asdfadsf");
  //   // System.out.println(test);
  // }

  // constructor
  public dbConnect(String url, String userName, String password) {
    // prepare the connection parameter
    this.url = url;
    this.userName = userName;
    this.password = password;
    con = null;
    // set up the connection
    try {
      // Load driver according to the used server
      Class.forName("com.mysql.cj.jdbc.Driver");
      // Connect to database
      con = DriverManager.getConnection(url, userName, password);
      System.out.println("Connection success!");
    }
    catch (SQLException ex) {
      System.err.println("Connection fail:\n" + ex);
    } catch (ClassNotFoundException ex) {
      System.err.println("Driver Fail:\n" + ex);
    }
  }

  // like destructor
  @Override
  protected void finalize() throws Throwable {
    try{
      // close connection if possible
      if (con != null) {
          con.close();
      }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    } catch(Throwable t){
      System.out.println(t.getMessage());
    } finally{
      super.finalize();
    }
  }

  public Boolean checkAcc(String userName, String password) {
    // prepare
    Boolean check = new Boolean(false);  // intialize the final result
    // let's go
    Statement st = null;
    try {
        // create statement
        st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // craft query
        String query = String.format("SELECT * FROM profile WHERE email = '%s' AND password = '%s'", userName, password);
        System.out.println(query);
        // and send queries
        ResultSet res = st.executeQuery(query);
        // validate the information
        res.last();
        // System.out.println(res.getRow());
        // res.beforeFirst();
        // only true if there's 1 row having the same pair of username and pass
        check = res.getRow() == 1;
    } catch (SQLException e ) {
      System.err.println("Account validation fail: \n" + e);
    } finally {  // close the statement connection
      try {
        if (st != null) {
          st.close();
        }
      } catch(SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
    // result
    return check;
  }

  public Boolean updateAcc(String userName, String password, String fName, String lName) {
    // initialize final return
    Boolean update = new Boolean(false);
    // let's go
    Statement st = null;
    try {
      // create statement
      st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      // first check if user already exit
      String query = String.format("SELECT * FROM profile WHERE email = '%s'", userName);
      System.out.println(query);
      ResultSet res = st.executeQuery(query);
      if (!res.last()) {  // if there's no row
        // craft second query
        String query2 = String.format("INSERT INTO profile(email, password, fName, lname) VALUES('%s', '%s', '%s', '%s')", userName, password, fName, lName);
        System.out.println(query2);
        // and send queries
        st.executeUpdate(query2);
        // complete successful
        update = true;
      }
    } catch (SQLException e ) {
      System.err.println("Account Create fail: \n" + e);
    } finally {  // close the statement connection
      try {
        if (st != null) {
          st.close();
        }
      } catch(SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
    // result
    return update;
  }

  public ArrayList<ArrayList<String>> search(String metric, String searchData) {
    // initialize final return
    ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    // let's go
    Statement st = null;
    try {
      // create statement
      st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      // first check if user already exit
      String query = String.format("SELECT * FROM book WHERE %s LIKE '%%%s%%'", metric, searchData);
      System.out.println(query);
      ResultSet res = st.executeQuery(query);
      // add query data into returned DS
      int numCol = res.getMetaData().getColumnCount();
      while (res.next()) {  // for each row
        ArrayList<String> row = new ArrayList<String>();
        // adding each column
        for (int i = 1; i <= numCol; i++) {
          row.add(res.getString(i));
        }
        table.add(row);
      }
    } catch (SQLException e ) {
      System.err.println("Searching fail: \n" + e);
    } finally {  // close the statement connection
      try {
        if (st != null) {
          st.close();
        }
      } catch(SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
    // result
    return table;
  }

  public ArrayList<ArrayList<String>> trending() {
    // initialize final return
    ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    // let's go
    Statement st = null;
    try {
      // create statement
      st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      // first check if user already exit
      String query = "SELECT * FROM book WHERE rating > 3 ORDER BY rating DESC";
      System.out.println(query);
      ResultSet res = st.executeQuery(query);
      // add query data into returned DS
      int numCol = res.getMetaData().getColumnCount();
      while (res.next()) {  // for each row
        ArrayList<String> row = new ArrayList<String>();
        // adding each column
        for (int i = 1; i <= numCol; i++) {
          row.add(res.getString(i));
        }
        table.add(row);
      }
    } catch (SQLException e ) {
      System.err.println("Trending fail: \n" + e);
    } finally {  // close the statement connection
      try {
        if (st != null) {
          st.close();
        }
      } catch(SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
    // result
    return table;
  }
}
