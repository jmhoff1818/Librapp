public class Book {
	//-------------------------------------------------------------- Properties
	private String title, author, genre, description;
	private String[] comments;
	private int isbnNumber;
	private double rating;

	//------------------------------------------------------------ Constructors
	public Book(String title, String author, String genre, int isbnNumber,
			String[] comments, String description, double rating) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.description = description;
		this.isbnNumber = isbnNumber;
		this.rating = rating;

		this.comments = new String[comments.length];
		for(int i = 0; i < comments.length; i++) this.comments[i] = comments[i];
	}

	//----------------------------------------------------- Getters and Setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getComments() {
		return comments;
	}

	public void setComments(String[] comments) {
		this.comments = comments;
	}

	public int getIsbnNumber() {
		return isbnNumber;
	}

	public void setIsbnNumber(int isbnNumber) {
		this.isbnNumber = isbnNumber;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}


}
