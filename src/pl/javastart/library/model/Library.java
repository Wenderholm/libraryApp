package pl.javastart.library.model;

import pl.javastart.library.exception.PublicationAlredyExistException;
import pl.javastart.library.exception.UserAlredyExistException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Library implements Serializable {

    private Map<String,Publication> publications = new HashMap<>();
    private Map<String,LibraryUser> users = new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public void addBook(Book book) {
        addPublication(book);
    }

    public void addMagazine(Magazine magazine) {
        addPublication(magazine);
    }

    public void addPublication(Publication publication) {
        if(publications.containsKey(publication.getTitle())){
            throw new PublicationAlredyExistException(
                    "publikacja o takim tytule już istnieje "+ publication.getTitle()
            );
        }
        publications.put(publication.getTitle(),publication);
    }

    public void addUser(LibraryUser user){
        if(users.containsKey(user.getPesel())){
            throw new UserAlredyExistException(
                    "użytkownik o takim numerze pesel już istnieje "+ user.getPesel()
            );
        }
        users.put(user.getPesel(), user);
    }

    public boolean removePublication(Publication pub){
        if(publications.containsValue(pub)){
            publications.remove(pub.getTitle());
            return true;
        }
        return false;
    }

//    BEDZIE DODANE USUWANIE PRZEZ NUMER ISBN

//    public boolean removePublicationByIsbnNumber(String isbnNumber){
//        final int notFound = -1;
//        int found = notFound;
//        int i = 0;
//        while(i<publicationsNumber && found == notFound){
//            if(isbnNumber.equals(publications[i].)){
//                found = i;
//            }else{
//                i++;
//            }
//        }
//        if(found !=notFound){
//            System.arraycopy(publications,found+1,publications,found,publications.length -found-1);
//            publicationsNumber--;
//            publications[publicationsNumber ] = null;
//        }
//        return found != notFound;
//    }
}
