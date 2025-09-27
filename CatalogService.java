
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class CatalogService {

    private Map<String, Book> hashmap; //key: isbn, value: Book object

    public CatalogService(){
        hashmap = new HashMap<>();
    }

    //this will handle user inputs (probably a librarian adding a new book to the library)
    public void addBook(Scanner sc){  //When the System.in is closed, creating a new Scanner don't reopen the Stream, thus pass the Scanner as a paremeter 

        boolean added = false;
        //Scanner sc = new Scanner(System.in);

        while(!added){
            try{

                System.out.print("Enter ISBN: ");
                String isbn = sc.nextLine().trim(); //" 123 " = "123"

                System.out.print("Enter Title: ");
                String title = sc.nextLine().trim(); //" " becomes "" in this case isBlank() will catch IllegalArgumentException

                System.out.print("Enter Copies: ");
                int copies = sc.nextInt();
                sc.nextLine(); //**** must have to clear newLine that was after the int value if we use nextInt() */
                
                //Book currBook = new Book(isbn, title, copies); //this will throw IllegalArgumentException if 1 of the value is not valid
                //hashmap.put(isbn, currBook);

                addBook(isbn, title, copies);  //addBook(isbn, title, copies) throws (checked) catalogException, thus Caller must handle exception

                added = true;
                System.out.println("The book with title " + title + " is added");

               

            }catch (InputMismatchException e){
                sc.nextLine(); //InputMismatchException occurs when a user inputs invalid type (e.g., String) instead ot int for copies, thus we need sc.nextLine() here
                // System.out.print(e.getMessage()); //e.getMessage() is usuall null for this exception 
                System.out.println("Copies must be an integer"); //so write our own message
            }
            catch (CatalogException | IllegalArgumentException e){

                System.out.println(e.getMessage());
            }
        }// while loop ends  
        
         //sc.close(); //MUST CLOSE outside the loop **** important
    } //end addBook(Scanner sc)

    //core API ---not supposed to catch anything but throws and let caller handle it
    public void addBook(String isbn, String title, int copies) throws CatalogException{ //caller must handle CatalogException (checked!)

        if(hashmap.containsKey(isbn)){
                throw new CatalogException("Already Existing ISBN: " + isbn);
        }

        if(isbn == null || isbn.isBlank()) throw new IllegalArgumentException("ISBN cannot be null or blank");
        Book currBook = new Book(isbn, title, copies);
        hashmap.put(isbn, currBook);

    }// end of addBook(isbn, title, copies)

    //look up a book
    public Book findByIsbn(String isbn) throws CatalogException{

        Book currBook = hashmap.get(isbn);

        if(currBook == null) throw new CatalogException("No such Book with that ISBN");

        return currBook;
    }

    public void removeBook(String isbn) throws CatalogException{

        /* don't need to look up twice 
        if(!hashmap.containsKey(isbn)){
            throw new CatalogException("No Such Book exists with that ISBN");
        }
        */
        Book removedBook = hashmap.remove(isbn);
        
        if(removedBook == null){
            throw new CatalogException("No Such Book exists with that ISBN");
        }

        System.out.println(removedBook.getTitle() + " is removed");
    }//end removeBook(isbn)

    
    public void incrementCopies(String isbn, int amount) throws CatalogException{
    
       Book currBook = hashmap.get(isbn);

       if (currBook == null){
        throw new CatalogException("No Such Book exists with that ISBN");
       }

        if(amount <= 0) throw new IllegalArgumentException("increment amount must be greater than 0"); //does not make sense to increment 0 copies

        //hashmap.put(isbn, Book.setcopies(amount)); can't do this

        currBook.setCopies(currBook.getCopies() + amount); //this will update the copies of the book with the key(isbn) since my HashMap stores Book objects and not copies of Books

        System.out.println(currBook.getTitle() + "'s copies are updated to " + currBook.getCopies());
        
    }//end incrementCopies(isbn, amount)


    public void decrementCopies(String isbn, int amount) throws CatalogException { //only have to throws 1 since AvailabilityException extends CatalogException



        Book currBook = hashmap.get(isbn);

        if(currBook == null) throw new CatalogException("No Such Book exists with that ISBN");

        if(amount <= 0) throw new IllegalArgumentException("decrement amount must be greater than 0");


        if(amount > currBook.getCopies()){
            throw new AvailabilityException("Not enough copies for ISBN: " + isbn); 
        }

        currBook.setCopies(currBook.getCopies() - amount);

        System.out.println(currBook.getTitle() + "'s copies are updated to " + currBook.getCopies());

        
    }//end of decrementCopies(isbn, amount)


    public void printAllBooks(){ 

        for(Book currBook : hashmap.values()){ 
            System.out.println(currBook); 
        } 
    }

}