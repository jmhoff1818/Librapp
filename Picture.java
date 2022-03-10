public class Picture {
  // instance variables
  private String src, alternate;
  private double width, height;
  // default constructor
  public Picture() {
    this("", "", 0.0, 0.0);
  }
  // partial constructor
  public Picture(String src, String alternate, double width, double height) {
    this.src = src;
    this.alternate = alternate;
    this.width = width;
    this.height = height;
  }

  // getters and setters
  public String getSrc() {
  	return src;
  }
  public void setSrc(String src) {
  	this.src = src;
  }
  public String getAlternate() {
  	return alternate;
  }
  public void setAlternate(String alternate) {
  	this.alternate = alternate;
  }
  public double getWidth() {
  	return width;
  }
  public void setWidth(double width) {
  	this.width = width;
  }
  public double getHeight() {
  	return height;
  }
  public void setHeight(double height) {
  	this.height = height;
  }

}
