import java.util.ArrayList;


public class UserProfile{
  // instance variables
  private String username, firstName, lastName;
  private Password pass;
  Picture avatar;
  private ArrayList<UserProfile> friends;
  private ArrayList<String> favorGenre;

  // default constructor
  public UserProfile() {
    this("", "", "", new Password(), new Picture(),
          new ArrayList<UserProfile>(), new ArrayList<String>());
  }
  // partial constructor
  public UserProfile(String username, String firstName, String lastName,
                      Password pass, Picture avatar,
                      ArrayList<UserProfile> friends,
                      ArrayList<String> favorGenre) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.pass = pass;
    this.avatar = avatar;
    this.friends = friends;
    this.favorGenre = favorGenre;
  }

  // getter and setter
  public String getUsername() {
  	return username;
  }

  public void setUsername(String username) {
  	this.username = username;
  }

  public String getFirstName() {
  	return firstName;
  }

  public void setFirstName(String firstName) {
  	this.firstName = firstName;
  }

  public String getLastName() {
  	return lastName;
  }

  public void setLastName(String lastName) {
  	this.lastName = lastName;
  }
  // public Password getPass() {
  // 	return pass;
  // }
  // public void setPass(Password pass) {
  // 	this.pass = pass;
  // }
  public Picture getAvatar() {
  	return avatar;
  }
  public void setAvatar(Picture avatar) {
  	this.avatar = avatar;
  }
  public ArrayList<UserProfile> getFriends() {
  	return friends;
  }
  public void setFriends(ArrayList<UserProfile> friends) {
  	this.friends = friends;
  }
  public ArrayList<String> getFavorGenre() {
  	return favorGenre;
  }
  public void setFavorGenre(ArrayList<String> favorGenre) {
  	this.favorGenre = favorGenre;
  }

  // toString
  @Override
  public String toString() {
    String res = "";
    res += String.format("My Name is %s %s.\nHere's a list of my favorite genres:", firstName, lastName);
    for (String genre : favorGenre) {
      res += "\n" + genre;
    }
    return res;
  }

  // testing method
  public static void main(String[] args) {
    String userName = "hoangnd@miamioh.edu";
    String firstName = "hoangnd@miamioh.edu";
    String lastName = "hoangnd@miamioh.edu";
    Password pass = new Password("miami");
    Picture avatar = new Picture();
    ArrayList<UserProfile> friends = new ArrayList();
    ArrayList<String> genres = new ArrayList();
    genres.add("Romantic");
    genres.add("Scientific");
    genres.add("Non-fiction");
    UserProfile user = new UserProfile(userName, firstName, lastName,
                                        pass, avatar, friends, genres);
    System.out.println(user.toString());
  }

}
