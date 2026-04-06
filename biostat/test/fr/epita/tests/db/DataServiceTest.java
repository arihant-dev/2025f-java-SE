package fr.epita.tests.db;

import fr.epita.biostat.datamodel.BioStatEntry;
import fr.epita.biostat.services.BioStatEntryDataService;

import java.sql.*;
import java.util.List;

public class DataServiceTest {

    public static void main(String[] args) throws SQLException {
        // Use sysyem.get -> edit the run configuration use -Dconfig.file.path=S:\Work\ae\Epita.........
        BioStatEntryDataService service = new BioStatEntryDataService();
        BioStatEntry entry = new BioStatEntry("Thomas", "M", 39, null, null);
        entry.setId(1);
        service.create(entry);
        BioStatEntry qbe = new BioStatEntry("Tho", null, null, null, null);
        List<BioStatEntry> results = service.search(qbe);

        System.out.println(results);

        entry.setAge(40);

        service.update(entry);

        results = service.search(qbe);
        System.out.println(results);
    }
}
