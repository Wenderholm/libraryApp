package pl.javastart.library.app;

import pl.javastart.library.exception.DataExportException;
import pl.javastart.library.exception.DataImportException;
import pl.javastart.library.exception.NoSuchOptionException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;
import pl.javastart.library.io.file.FileManager;
import pl.javastart.library.io.file.FileManagerBuilder;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;
import pl.javastart.library.model.comparator.AlphabeticComparator;
import pl.javastart.library.model.comparator.DataComparator;

import java.util.Arrays;
import java.util.InputMismatchException;

class LibraryControl {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private Library library = new Library();
    private FileManager fileManager;

    LibraryControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            library = fileManager.importData();
            printer.printLine("Zaimportowano dane z pliku");
        } catch (DataImportException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjowano nową bazę");
            library = new Library();
        }
    }

    void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Nie ma takiej opcji, wprowadź ponownie: ");
            }
        } while (option != Option.EXIT);
    }



    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                printer.printLine("wprowadzono wartość, która nie jest liczbą. podaj ponownie");
            }
        }
        return option;
    }

    private void printOptions() {
        printer.printLine("Wybierz opcję: ");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addBook(book);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojeości, nie można dodać kolejnej książki");
        }
    }

    private void deleteBook() {
        try{
            Book book = dataReader.readAndCreateBook();
            if(library.removePublication(book))
                printer.printLine("Usunięto książkę");
            else
                printer.printLine("Brak wskazanej książki");
        }catch(InputMismatchException e){
            printer.printLine("Nie udało się utworzyć książki, nipoprawne dane");
        }
    }


    private void printMagazines() {
        Publication[] publications = getSortedPublications();
        printer.printMagazines(publications);
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addMagazine(magazine);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojeości, nie można dodać kolejnego magazynu");
        }
    }

    private void deleteMagazine() {
        try{
            Magazine magazine = dataReader.readAndCreateMagazine();
            if(library.removePublication(magazine))
                printer.printLine("Usunięto magazyn");
            else
                printer.printLine("Brak wskazanego magazynu");
        }catch(InputMismatchException e){
            printer.printLine("Nie udało się utworzyć magazynu, nipoprawne dane");
        }

    }

    private void printBooks() {
        Publication[] publications = getSortedPublications();
        printer.printBooks(publications);
    }

    private Publication[] getSortedPublications() {
        Publication[] publications = library.getPublications();
//        sortowanie po nazwach
        Arrays.sort(publications, new AlphabeticComparator());
//        sortowanie po datach
//        Arrays.sort(publications, new DataComparator());
        return publications;
    }

    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Export danych do pliku zakończony powodzeniem");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());

        }

        printer.printLine("Koniec programu, papa!");
        // zamykamy strumień wejścia
        dataReader.close();
    }

    private enum Option {
        EXIT(0, "wyjście z porgramu"),
        ADD_BOOK(1, "dodanie nowej książki"),
        ADD_MAGAZINE(2, "dodanie nowego magazynu"),
        PRINT_BOOKS(3, "wyświetl dostępne książki"),
        PRINT_MAGAZINES(4, "wyświetl dostępnych magazynów"),
        DELETE_BOOK(5, "Usuń książkę"),
        DELETE_MAGAZINE(6, "Usuń magazyn");

        private final int value;
        private final String description;


        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o id " + option);
            }

        }
    }


}
