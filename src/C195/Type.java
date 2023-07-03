package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static C195.Lists.appointmentList;

/** Type class for storing all the types.
 * Allows for easy population of the type combobx on the reports page.
 */
public class Type {

    /** Stores the type */
    private static String type;
    /** Type observable list for storing all the types. */
    public static ObservableList<String> typeList = FXCollections.observableArrayList();

    /** Returns the type */
    public static String getType() {
        return type;
    }
    /** Sorts through all the appointments and adds all the types to the typeList. */
    public static void populateTypes() {
        for (Appointments existing : appointmentList) {
                if (!typeList.contains(existing.getType())) {
                    typeList.add(existing.getType());
                }
            }
        }
}