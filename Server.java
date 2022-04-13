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
    String requestParamValue = null;
    if("GET".equals(httpExchange.getRequestMethod())) {
       requestParamValue = handleGetRequest(httpExchange);
    }
     // else if("POST".equals(httpExchange)) {
     //   requestParamValue = handlePostRequest(httpExchange);
     //  }
    handleResponse(httpExchange,requestParamValue);
  }

  public String handleGetRequest(HttpExchange httpExchange) {
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

  public String processing(String type, String user, String pass) {
    // File name
    String fileName = "writer.txt";
    // prepare
    boolean exist = false;
    boolean userExist = false;  // only check the user
    // check if the pair of user and password already exist
    try {
      Scanner myReader = new Scanner(new File(fileName));
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        String thisUser = data.split(",")[0];
        String thisPass = data.split(",")[1];
        if (user.equals(thisUser)) {
          userExist = true;
          if (pass.equals(thisPass)) {
            exist = true;
            break;
          }
        }
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error process - reading");
      e.printStackTrace();
    }

    // then, do the register or login
    if (type.equals("register")) {
      // if there's already an account
      if (userExist) {
        return "Already have an account";
      }
      // otherwise, write the information to a text file (later database)
      try {
        FileWriter fw = new FileWriter(new File(fileName), true);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(user + "," + pass + "\n");
        writer.close();
      } catch (IOException e) {
        System.out.println("Error processing - writing");
        e.printStackTrace();
      }
      return "Done registering";
    } else {  // if this is a login
      if (exist) {
        return "Login succesfully";
      } else {
        return "Login fail";
      }
    }
  }

   private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
      OutputStream outputStream = httpExchange.getResponseBody();
      /*Testing*/
      StringBuilder htmlBuilder = new StringBuilder();
      htmlBuilder.append("<html>").
            append("<body>").
            append("<h1>")
            .append(requestParamValue)
            .append("</h1>")
            .append("</body>")
            .append("</html>");
      // encode HTML content
      String htmlResponse = htmlBuilder.toString();
      /*Testing*/
      // this line is a must
      httpExchange.sendResponseHeaders(200, htmlResponse.length());
      outputStream.write(htmlResponse.getBytes());
      outputStream.flush();
      outputStream.close();
    }

    public static void main(String[] args) {
      int portNum = 8005;
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
