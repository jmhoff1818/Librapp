import java.util.ArrayList; // import the ArrayList class
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.BufferedWriter;

public class Server implements HttpHandler {

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    String htmlResponse = "";
    if("GET".equals(httpExchange.getRequestMethod())) {
       htmlResponse = handleGetRequest(httpExchange);
    }
     // else if("POST".equals(httpExchange)) {
     //   requestParamValue = handlePostRequest(httpExchange);
     //  }
    handleResponse(httpExchange, htmlResponse);
  }

  public String handleGetRequest(HttpExchange httpExchange) {
    // METADATA
    // url has this type: 'http://localhost:8006/test?[register/login]?[user]?[pass]'
    // parse uri from http request
    String url = httpExchange.getRequestURI().toString();
    System.out.println("\nServer Testing1: " + url);
    // parameters
    String[] params = url.split("\\?");
    String type = params[1]; // either "register", "login", or "search"
    // String user = url.split("\\?")[2];
    // String pass = url.split("\\?")[3];
    System.out.println("Server Testing2 - execute type: " + type);
    // main processing
    return processing(type, params);
  }

  public String processing(String type, String[] params) {
    // Connect to database
    System.out.println("Connecting to db...");
    // dbConnect connection = new dbConnect("jdbc:mysql://localhost:3306/testing", "root", "namCse201");
    dbConnect connection = new dbConnect("jdbc:mysql://35.223.64.237:3306/librapp", "root", "thisIsCse201");
    // Then, do the register or login
    if (type.equals("register")) {  // register
      String user = params[2];
      String pass = params[3];
      String fName = params[4];
      String lName = params[5];
      return connection.updateAcc(user, pass, fName, lName).toString();
    } else if (type.equals("login")) {  // if this is a login
      String user = params[2];
      String pass = params[3];
      return connection.checkAcc(user, pass).toString();
    } else {  // if this means search
      String metric = params[2];
      if (metric.equals("trending")) {  // get result based on rating
        ArrayList<ArrayList<String>> res = connection.trending();
        // testiing
        for (ArrayList<String> row :res) {
          for (String entry : row) {
            System.out.print(entry + ", ");
          }
          System.out.println();
        }
      } else {  // get result based on metric
        String searchData = params[3];
        ArrayList<ArrayList<String>> res = connection.search(metric, searchData);
        // testiing
        for (ArrayList<String> row :res) {
          for (String entry : row) {
            System.out.print(entry + ",");
          }
          System.out.println();
        }
      }
      return "good";  //d
    }
  }

   private void handleResponse(HttpExchange httpExchange, String htmlResponse)  throws  IOException {
      OutputStream outputStream = httpExchange.getResponseBody();
      /*Testing*/
      // StringBuilder htmlBuilder = new StringBuilder();
      // htmlBuilder.append("<html>").
      //       append("<body>").
      //       append("<h1>")
      //       .append(requestParamValue)
      //       .append("</h1>")
      //       .append("</body>")
      //       .append("</html>");
      // // encode HTML content

      System.out.println(htmlResponse);
      /*Testing*/
      // crafting the response headers
      httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
      httpExchange.sendResponseHeaders(200, htmlResponse.length());
      outputStream.write(htmlResponse.getBytes());
      outputStream.flush();
      outputStream.close();
    }

    public static void main(String[] args) {
      int portNum = 8010;
      try {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", portNum), 0);
        server.createContext("/test", new Server());
        server.setExecutor(null);
        server.start();
        System.out.println("Server start listening on " + portNum);
        // looping until
      }
      catch (Exception e) {
        System.out.println("Error connecting server");
        e.printStackTrace();
      }
    }
}
