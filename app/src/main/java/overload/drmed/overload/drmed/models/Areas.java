package overload.drmed.overload.drmed.models;

import java.util.ArrayList;

/**
 * Created by Dalbo on 20/04/2017.
 */

public class Areas {


    boolean success;
    ArrayList<Area> Area;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Area> getArea() {
        return Area;
    }

    public void setArea(ArrayList<Area> areas) {
        Area = areas;
    }

    public class Area {
        private String ID_Area;
        private String Nome_Area;

        public String getID_Area() {
            return ID_Area;
        }

        public void setID_Area(String ID_Area) {
            this.ID_Area = ID_Area;
        }

        public String getNome_Area() {
            return Nome_Area;
        }

        public void setNome_Area(String nome_Area) {
            Nome_Area = nome_Area;
        }
    }
}
