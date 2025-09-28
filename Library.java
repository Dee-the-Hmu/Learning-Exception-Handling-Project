
import java.util.Scanner;
import java.util.HashMap;
import java.util.InputMismatchException;

public class Library {

    private static Scanner sc; //static since I want to share them across static helper
    private static CatalogService catalogService;

    private static void showMenu(){

        System.out.println("===== Library Menu =====");
        System.out.println("1. Add Book: ");
        System.out.println("2. Find Book: ");
        System.out.println("3. Remove Book: ");
        System.out.println("4. Increment Copies: ");
        System.out.println("5. Decrement Copies: ");
        System.out.println("6. List All Books: ");
        System.out.println("7. Exit :( ");
        System.out.println("=========================");
        System.out.print("Enter your choice: ");

    }//end of showMenu

    private static int getUserChoice(Scanner sc){

      
        int userChoice = -1;

        boolean getValidInput = false;

        while(!getValidInput){

              //display the menu 
             showMenu();

            try{
                userChoice = sc.nextInt();
                sc.nextLine();

                if(userChoice <= 0 || userChoice > 7){
                    throw new IllegalArgumentException();
                }

                getValidInput = true;

            }catch (InputMismatchException e){
                sc.nextLine();

                System.out.println("\nInput must be an integer!");
                System.out.println("Please choose again");

               
            }catch (IllegalArgumentException e){
            
                //don't need to do, sc.nextLine() here since the input value is valid and we've already done sc.nextLine() after sc.nextInt()

                System.out.println("\nInput must be an integer between 1 to 7!");
                System.out.println("Please choose again");
            }
        }//end while loop

        return userChoice; 
    
    }//end of getUserChoice

    public static void handleUserChoice(int userChoice, Scanner sc, CatalogService catalogService){

        switch(userChoice){ //enhanced switch

            //add book
            case 1 -> catalogService.addBook(sc);

            //find book (findByIsbn throws CatalogException)
            case 2 -> {
                try{
                    System.out.print("Search by ISBN: ");
                    String isbn = sc.nextLine();

                    Book foundBook = catalogService.findByIsbn(isbn);

                    System.out.println("Found your book!");
                    System.out.println(foundBook);

                }catch(CatalogException e){
                    System.out.println(e.getMessage());
                }   
            }//case 2

            //Remove Book
            case 3 -> {
                try{
                    System.out.print("Remove by ISBN: ");
                    String isbn = sc.nextLine(); 
                    catalogService.removeBook(isbn); 
                }catch (CatalogException e){
                    System.out.println(e.getMessage());
                }
            }//case 3

            //increment copies 
            case 4 -> {
                try{
                    System.out.print("Search by ISBN: ");
                    String isbn = sc.nextLine();

                    Book currBook = catalogService.findByIsbn(isbn);
                    
                    System.out.print("Enter the number of increment: ");
                    int amount = sc.nextInt();
                    sc.nextLine();

                    catalogService.incrementCopies(isbn, amount);   
                    
                }catch(InputMismatchException e){
                    sc.nextLine();
                    System.out.println("Invalid input type!");
                }catch(IllegalArgumentException | CatalogException e){
                    System.out.println(e.getMessage());
                }
            }//case 4

            //decrement copies
            case 5-> {
                try{
                    System.out.print("Search by ISBN: ");
                    String isbn = sc.nextLine();

                    System.out.print("Enter the number of decrement: ");
                    int amount = sc.nextInt();
                    sc.nextLine();

                    catalogService.decrementCopies(isbn, amount);
                    
                }catch(InputMismatchException e){
                    sc.nextLine();
                    System.out.println("Invalid input type!");
                }catch(IllegalArgumentException | CatalogException e){
                    System.out.println(e.getMessage());
                }

            }//case 5

            //List all books
            case 6-> catalogService.printAllBooks();


        }




    }//end of catalogService

    public static void main(String[] args){
        
        //create a scanner
        sc = new Scanner(System.in); //NOTES: can't use "this" for static context

        //create a new Catalog
        catalogService = new CatalogService(); //now we can use catalogService.hashmap

     
        
        while(true){
            int userChoice = getUserChoice(sc);

            if (userChoice == 7){
                sc.close();
                System.out.println("I am sad to see you go :'(");
                System.exit(0);
            }
            else{
                //handleUserChoice
                handleUserChoice(userChoice, sc, catalogService);
            }
        }

        //sc.close(); //close the scanner before exit //don't need it beacuse of how I handle case 7
    }//end of main

}// end of Library