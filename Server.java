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
    Boolean requestParamValue = false;
    if("GET".equals(httpExchange.getRequestMethod())) {
       requestParamValue = handleGetRequest(httpExchange);
    }
     // else if("POST".equals(httpExchange)) {
     //   requestParamValue = handlePostRequest(httpExchange);
     //  }
    handleResponse(httpExchange,requestParamValue);
  }

  public Boolean handleGetRequest(HttpExchange httpExchange) {
    // METADATA
    // url has this type: 'http://localhost:8006/test?[register/login]?[user]?[pass]'
    // parse uri from http request
    String url = httpExchange.getRequestURI().toString();
    System.out.println("Testing1: " + url);
    String type = url.split("\\?")[1]; // either "register" or "login"
    String user = url.split("\\?")[2];
    String pass = url.split("\\?")[3];
    System.out.println("Testing2: " + type + ", " + user + ", " + pass);
    // main processing
    return processing(type, user, pass);
  }

  public Boolean processing(String type, String user, String pass) {
    // Connect to database
    System.out.println("hello");
    dbConnect connection = new dbConnect("jdbc:mysql://localhost:3306/testing", "root", "namCse201");

    // Then, do the register or login
    if (type.equals("register")) {  // register
      return connection.updateAcc(user, pass);
    } else {  // if this is a login
      return connection.checkAcc(user, pass);
    }
  }

   private void handleResponse(HttpExchange httpExchange, Boolean requestParamValue)  throws  IOException {
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
      String htmlResponse = requestParamValue.toString();
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
