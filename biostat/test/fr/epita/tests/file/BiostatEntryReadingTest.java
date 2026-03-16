package fr.epita.tests.file;

import fr.epita.biostat.datamodel.BioStatEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BiostatEntryReadingTest {

    public static void main(String[] args) throws FileNotFoundException {
        List<BioStatEntry> entries = new ArrayList<>();
        File file = new File("biostat/biostat.csv");
        Scanner scanner = new Scanner(file);
        scanner.nextLine(); //skip first line = headers
        while (scanner.hasNext()){
            String currentLine = scanner.nextLine();
            System.out.println(currentLine);
            String[] parts = currentLine.split(",");
            BioStatEntry entry = new BioStatEntry(
                    parts[0].trim(),
                    parts[1].trim(),
                    Integer.parseInt(parts[2].trim()),
                    Integer.parseInt(parts[3].trim()),
                    Integer.parseInt(parts[4].trim())
            );
            entries.add(entry);
        }
        System.out.println(entries.size());

        //1.compute average age, average height, average weight, min/max for age.
        //2.compute distribution over gender (count each category instances).
    }

}
