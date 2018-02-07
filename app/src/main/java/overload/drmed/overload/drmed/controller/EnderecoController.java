/*package overload.drmed.overload.drmed.controller;


import android.app.Notification;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;

import overload.drmed.CadastroPacActivity;
import overload.drmed.overload.drmed.models.Endereco;
import overload.drmed.overload.drmed.models.StaticFiles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public final class EnderecoController extends AsyncTask<String, Void, Endereco> {

    public static final String API_URL = "https://viacep.com.br";

    public interface ViaCep {
        @GET("/ws/{cep}/json")
        Call<Endereco> endereco(
                @Path("cep") String cep);
    }

    public static void getEndereco(CadastroPacActivity pac) {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticFiles.getUrlCEP())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        ViaCep api = retrofit.create(ViaCep.class);
        Call<Endereco> call = api.endereco(cep);

        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                Log.d("Hue1", "Responsecode: " + response.code());
                StaticFiles.setEndereco(response.body());
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Log.d("Hue", "Failed: " + t.getMessage());
            }
        });

    }


    protected void onPreExecute() {
        //Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " + Thread.currentThread().getName());
        //load = ProgressDialog.show(MainActivity.this, "Por favor Aguarde ...",                    "Baixando Imagem ...");
    }


    protected Endereco doInBackground(String... params) {
        EnderecoController.getEndereco(params[0]);
        return StaticFiles.getEndereco();
    }


    protected void onPostExecute() {
        //load.dismiss();
    }


}*/