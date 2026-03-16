package fr.epita.tests.file;

import fr.epita.biostat.datamodel.BioStatEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

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
        double totalAge = 0;
        for(BioStatEntry entry : entries){
            totalAge += entry.getAge();
        }
        double averageAge = totalAge / entries.size();

        Function<BioStatEntry, Integer> f = BioStatEntry::getAge;
        computeAverage(entries, BioStatEntry::getAge);
        computeAverage(entries, BioStatEntry::getHeight);
        computeAverage(entries, BioStatEntry::getWeight);

        entries.stream().mapToInt(BioStatEntry::getAge).average();
        entries.stream().mapToInt(BioStatEntry::getHeight).average();
        entries.parallelStream().mapToInt(BioStatEntry::getAge).average();


        //2.compute distribution over gender (count each category instances).

        Map<String, Integer> distribution = new HashMap<>();
        for (BioStatEntry e: entries){
            Integer i = distribution.get(e.getSex());
            if (i == null){
                i = 1;
            }else {
                i++;
            }
            distribution.put(e.getSex(), i);
        }
        System.out.println(distribution);
    }

    private static void computeAverage(List<BioStatEntry> entries, Function<BioStatEntry, Integer> f) {
        double total = 0;
        for(BioStatEntry entry : entries){
            total += f.apply(entry);
        }
        double average = total / entries.size();
    }

}
