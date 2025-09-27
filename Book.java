
public class Book {

    private String isbn;
    private String title;
    private int copies;

    public Book(String isbn, String title, int copies){

        if(isbn == null || isbn.isBlank() ) throw new IllegalArgumentException("isbn cannot be null or blank");
        if(title == null || title.isBlank()) throw new IllegalArgumentException("title cannot be null or blank");
        if(copies <= 0) throw new IllegalArgumentException("number of copies must be greater than 0");
        
        this.isbn = isbn;
        this.title = title;
        this.copies = copies;
        
    }

    @Override
    public String toString(){
        return "Book{" +
                "ISBN= " + isbn +
                ", Title= " + title +
                ", Copies= " + copies + 
                "}";
    }

    //getters and setters
    public String getIsbn() { return this.isbn;}
    public void setIsbn(String isbn) {
        if(isbn == null || isbn.isBlank()) throw new IllegalArgumentException("isbn cannot be null or blank");
        this.isbn = isbn;
    }

    public String getTitle(){return this.title;}
    public void setTitle(String title){
        if(title == null || title.isBlank()) throw new IllegalArgumentException("title cannot be null or blank");
        this.title = title;
    }

    public int getCopies() {return this.copies;}
    public void setCopies(int copies){
        if(copies <= 0) throw new IllegalArgumentException("number of copies must be greater than 0");
        this.copies = copies;
    }

}// end of Book
