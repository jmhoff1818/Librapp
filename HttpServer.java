private class HttpServer implements HttpHandler {

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    String requestParamValue=null;
    if("GET".equals(httpExchange.getRequestMethod())) {
       requestParamValue = handleGetRequest(httpExchange);
     }else if("POST".equals(httpExchange)) {
       requestParamValue = handlePostRequest(httpExchange);
      }
    handleResponse(httpExchange,requestParamValue);
  }

  // private String handleGetRequest(HttpExchange httpExchange) {
  //           return httpExchange.
  //                   getRequestURI()
  //                   .toString()
  //                   .split("\\?")[1]
  //                   .split("=")[1];
  // }

   public void handlePostRequest(HttpExchange he) throws IOException {
      // parse request
      Map<String, Object> parameters = new HashMap<String, Object>();
      InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
      BufferedReader br = new BufferedReader(isr);
      String query = br.readLine();
      parseQuery(query, parameters);

      // send response
      String response = "";
      for (String key : parameters.keySet())
               response += key + " = " + parameters.get(key) + "\n";
      he.sendResponseHeaders(200, response.length());
      OutputStream os = he.getResponseBody();
      os.write(response.toString().getBytes());
      os.close();
    }

   private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
      OutputStream outputStream = httpExchange.getResponseBody();
      StringBuilder htmlBuilder = new StringBuilder();
      htmlBuilder.append("<html>").
            append("<body>").
            append("<h1>").
            append("Hello ")
            .append(requestParamValue)
            .append("</h1>")
            .append("</body>")
            .append("</html>");
      // encode HTML content
      String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());
      // this line is a must
      httpExchange.sendResponseHeaders(200, htmlResponse.length());
      outputStream.write(htmlResponse.getBytes());
      outputStream.flush();
      outputStream.close();
    }

    public static void main(String[] args) {
      int portNum = 5000;
      HttpServer server = HttpServer.create(new InetSocketAddress("localhost", portNum), 0);
      server.createContext("/test", new  MyHttpHandler());
      server.setExecutor(null);
      server.start();
      logger.info(" Server started on port" + portNum);
      server.start();
    }
}
