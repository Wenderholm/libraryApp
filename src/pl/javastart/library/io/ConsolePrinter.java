package pl.javastart.library.io;

import pl.javastart.library.model.Book;
import pl.javastart.library.model.LibraryUser;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;

import java.util.Collection;

public class ConsolePrinter {
    public void printBooks(Collection<Publication> publications) {
//        int counter = 0;
//        for (Publication publication : publications) {
//            if(publication instanceof Book) {
//                printLine(publication.toString());
//                counter++;
//            }
//        }
//        if (counter == 0)
//            printLine("Brak książek w bibliotece");

//        powyższa informacja zapisana w sturmieniu
        long count = publications.stream()
                .filter(p -> p instanceof Book)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if (count == 0)
            printLine("Brak książek w bibliotece");
    }
    public void printMagazines(Collection<Publication> publications) {
        long count = publications.stream()
                .filter(p -> p instanceof Magazine)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if (count == 0)
            printLine("Brak magazynu w bibliotece");
    }

    public void printUsers(Collection<LibraryUser> users) {
//        for (LibraryUser user : users) {
//            printLine(user.toString());
//        }

//        zamiana na strumień
        users.stream()
                .map(LibraryUser::toString)
                .forEach(this::printLine);
    }

    public void printLine(String text) {
        System.out.println(text.toUpperCase());
    }
}
