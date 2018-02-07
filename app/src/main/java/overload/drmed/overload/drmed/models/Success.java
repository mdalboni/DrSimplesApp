package overload.drmed.overload.drmed.models;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Dalbo on 09/04/2017.
 */

public class Success extends SuccessLog{

    public interface CPFtest {
        @FormUrlEncoded
        @POST("/Paciente/GetCPF")
        Call<Success> testar(@Field("CPF") String CPF);
    }

    public interface Emailtest {
        @FormUrlEncoded
        @POST("/Paciente/GetEmail")
        Call<Success> testar(@Field("Email") String CPF);
    }

}
