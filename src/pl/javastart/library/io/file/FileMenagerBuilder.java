package pl.javastart.library.io.file;

import pl.javastart.library.exception.NoSuchFileTypeException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;

public class FileMenagerBuilder {
    private ConsolePrinter printer;
    private DataReader reader;

    public FileMenagerBuilder(ConsolePrinter printer, DataReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public FileMenager build() {
        printer.printLine("WYbierz format danych: ");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL:
                return new SerializableFileMenager();
            default:
                throw new NoSuchFileTypeException("Nieobsługiwany typ danych");
        }
    }

    private FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOk = true;
            } catch (IllegalArgumentException e) {
                printer.printLine("Nieobsługiwany typ danych, wybierz ponownie");
            }
        } while (!typeOk);
        return result;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }
}
