import java.util.ArrayList;

public class Wishlist {

	private ArrayList<Book> wishlist;

	public boolean addBook(Book b) {
		if(wishlist.contains(b)) return false;
		wishlist.add(b);
		return true;
	}

	public boolean removeBook(Book b) {
		if(!wishlist.contains(b)) return false;
		wishlist.remove(b);
		return true;
	}
}
