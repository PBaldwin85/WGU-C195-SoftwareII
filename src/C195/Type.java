package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static C195.Lists.appointmentList;

public class Type {

    private static String type;


    public static ObservableList<String> typeList = FXCollections.observableArrayList();

    public static String getType() {
        return type;
    }

    public static void populateTypes() {
        typeList.clear();
        for (Appointments existing : appointmentList) {
                if (!typeList.contains(existing.getType())) {
                    typeList.add(existing.getType());
                }
            }

        }


}