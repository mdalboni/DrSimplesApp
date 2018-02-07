package overload.drmed.overload.drmed.models;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Dalbo on 12/04/2017.
 */

public class PesquisaConvenio {

    private ArrayList<Convenio> Convenio;
    private boolean success;

    public ArrayList<Convenio> getConvenio() {
        return Convenio;
    }

    public void setConvenio(ArrayList<Convenio> convenio) {
        this.Convenio = convenio;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}

