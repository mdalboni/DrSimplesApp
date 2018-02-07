/*package overload.drmed.overload.drmed.controller;

import android.os.AsyncTask;

import overload.drmed.overload.drmed.models.Endereco;
import overload.drmed.overload.drmed.models.StaticFiles;

public abstract class EnderecoTask  extends AsyncTask<String, Void, Endereco>  {


    private class TarefaDownload {
        protected void onPreExecute(){
            //Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " + Thread.currentThread().getName());
            //load = ProgressDialog.show(MainActivity.this, "Por favor Aguarde ...",                    "Baixando Imagem ...");
        }


        protected Endereco doInBackground(String... params) {
            EnderecoController.getEndereco(params[0]);
            return StaticFiles.getEndereco();
        }


        protected void onPostExecute(){
            //load.dismiss();
        }
    }
}
*/