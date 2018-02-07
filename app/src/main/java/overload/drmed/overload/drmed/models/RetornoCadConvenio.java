package overload.drmed.overload.drmed.models;

/**
 * Created by Dalbo on 13/04/2017.
 */

public class RetornoCadConvenio {
    boolean success;
    int Id_PaciConv;

    public void setId_PaciConv(int id_PaciConv) {
        Id_PaciConv = id_PaciConv;
    }

    public int getId_PaciConv() {
        return Id_PaciConv;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

}