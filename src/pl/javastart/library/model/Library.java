package pl.javastart.library.model;

public class Library {
    private static final int MAX_BOOKS = 1000;
    private static final int MAX_MAGAZINES = 1000;
    private Book[] books = new Book[MAX_BOOKS];
    private Magazine[] magazines = new Magazine[MAX_MAGAZINES];
    private int booksNumber = 0;
    private int magazinesNumber = 0;

    private static final int MAX_PUBLICATIONS = 2000;
    private int publicationsNumber = 0;
    private Publication[] publications = new Publication[MAX_PUBLICATIONS];

    public void addBook(Book book){
        if( publicationsNumber < MAX_PUBLICATIONS){
            publications[publicationsNumber] = book;
            publicationsNumber++;
        }else{
            System.out.println("Maxymalna liczba książek zostala osiągnięta");
        }
    }
    public void printBooks(){
        int countBooks = 0;
        for (int i = 0; i < publicationsNumber; i++) {
           if(publications[i] instanceof Book){
               publications[i].printInfo();
               countBooks++;
           }
        }
        if(countBooks == 0){
            System.out.println("brak książek w bazie");
        }

    }

    public void addMagazine(Magazine magazine){
        if( publicationsNumber < MAX_PUBLICATIONS){
            publications[publicationsNumber] = magazine;
            publicationsNumber++;
        }else{
            System.out.println("Maxymalna liczba magazynów zostala osiągnięta");
        }
    }
    public void printMagazines(){
        int countMagazines = 0;
        for (int i = 0; i < publicationsNumber; i++) {
            if(publications[i] instanceof Magazine){
                publications[i].printInfo();
                countMagazines++;
            }
        }
        if(countMagazines == 0){
            System.out.println("brak magazynów w bazie");
        }
    }
}
