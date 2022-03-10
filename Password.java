public class Password {
// instance variables
  private String value;
  // default constructor
  public Password() {
    // maybe generate a random password
    this.value = "";
  }
  // partial constructor
  public Password(String value) {
    this.value = value;
  }
  // getter and setter
  public String getValue() {
		return value;
	}
  public void setValue(String value) {
		this.value = value;
	}
}
