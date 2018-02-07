package overload.drmed.overload.drmed.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.azure.storage.StorageException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import overload.drmed.ExamesActivity;
import overload.drmed.R;
import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.models.Exame;
import overload.drmed.overload.drmed.models.PesquisaPacienteConvenio;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.models.Success;
import overload.drmed.overload.drmed.utils.ConexaoAzure;
import overload.drmed.overload.drmed.utils.Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExameAdapter extends RecyclerView.Adapter<ExameAdapter.ViewHolder> {

    private List<Exame.exames> mDataset;
    private Context context;
    public View mView;
    private String ID_PaciConv;
    ConexaoAzure con;
    Retrofit retrofit;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public View mView;
        public TextView laboratorio;
        public TextView protocolo;
        public TextView senha;
        public TextView situacao;
        public TextView data;
        public LinearLayout link;


        public ViewHolder(View v) {
            super(v);
            mView = v;
            //context = v.getContext();

            laboratorio = (TextView) mView.findViewById(R.id.lbl_laboratorio);
            protocolo = (TextView) mView.findViewById(R.id.lbl_protocolo);
            situacao = (TextView) mView.findViewById(R.id.lbl_situacao);
            senha = (TextView) mView.findViewById(R.id.lbl_senha);
            data = (TextView) mView.findViewById(R.id.lbl_data);

            link = (LinearLayout) mView.findViewById(R.id.btnLinear);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExameAdapter(List<Exame.exames> lista, String ID_PaciConv, Context mcontext) {
        this.ID_PaciConv = ID_PaciConv;
        mDataset = lista;
        context = mcontext;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_exame, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.laboratorio.setText(mDataset.get(position).getLaboratorio());
        holder.senha.setText(mDataset.get(position).getSenhaProtocolo());
        if (!mDataset.get(position).getRealizado()) {
            holder.situacao.setText("EM ESPERA");
        } else {
            holder.situacao.setText("PRONTO");
        }

        if (holder.situacao.getText().toString().toUpperCase().equals("EM ESPERA")) {
            holder.link.setBackgroundColor(context.getResources().getColor(R.color.colorEmEspera));
        } else if (holder.situacao.getText().toString().toUpperCase().equals("PRONTO")) {
            holder.link.setBackgroundColor(context.getResources().getColor(R.color.colorConfirmado));
        }

        holder.protocolo.setText(mDataset.get(position).getProtocolo());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(mDataset.get(position).getData());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.data.setText(formatter2.format(date));

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDataset.get(position).getRealizado()) {
                    new AlertDialog.Builder(context)
                            .setTitle("Confirmar alteração")
                            .setMessage("Você realmente deseja excluir esse exame?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    retrofit = new Retrofit.Builder()
                                            .baseUrl(StaticFiles.getUrlServer())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    Interfaces.CancelarExames api = retrofit.create(Interfaces.CancelarExames.class);
                                    Call<Success> call = api.cancelarExame(Integer.parseInt(mDataset.get(position).getId_Exame()));

                                    call.enqueue(new Callback<Success>() {
                                        @Override
                                        public void onResponse(Call<Success> call, Response<Success> response) {
                                            if (response.body().isSuccess()) {
                                                Toast.makeText(context, "Exame excluido com sucesso.",
                                                        Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(context, "Algo deu errado.\nErro: 0x001",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Success> call, Throwable t) {
                                            System.out.println(t.getMessage());
                                            Dialog.callOfTheDialog("Erro ao conectar", "Existe algum problema de rede!", context);
                                        }
                                    });

                                }
                            })
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();

                } else {
                    con = new ConexaoAzure();
                    try {
                        if (con.ConectarAzure()) {

                            new Handler(Looper.getMainLooper())
                                    .post(new Runnable() {
                                              @Override
                                              public void run() {

                                                  Dialog.callOfTheProgressBar(context);
                                                  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                  StrictMode.setThreadPolicy(policy);

                                                  con.checkArquivo(context, mDataset.get(position).getNomeArquivo());
                                              }
                                          }
                                    );

                        } else {
                            Toast.makeText(context, "Erro de conexão....",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /*public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            Dialog.callOfTheProgressBar(context);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con.checkArquivo(context, url.toString());
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("URL content", result);
        }
    }
*/

}