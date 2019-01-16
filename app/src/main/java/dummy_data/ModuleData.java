package dummy_data;

import java.util.ArrayList;
import java.util.List;

import models.Module;

/**
 * Created by menuka on 3/24/17.
 */

public class ModuleData {
    private static List<Module> modules;

    public static List<Module> getModules() {
        modules = new ArrayList<>();
        addData();
        return modules;
    }

    private static void addData() {
        modules.add(new Module("CS1032", "Programming Fundamentals", "A-", "3"));
        modules.add(new Module("MT1022", "Properties of Materials", "A-", "2"));
        modules.add(new Module("CE1022", "Fluid Mechanics", "A+", "2"));
        modules.add(new Module("ME1013", "Mechanics", "A+", "2"));
    }

}
