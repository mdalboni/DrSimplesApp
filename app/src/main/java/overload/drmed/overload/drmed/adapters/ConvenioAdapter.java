package overload.drmed.overload.drmed.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import overload.drmed.ConveniosActivity;
import overload.drmed.R;
import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.models.Convenio;
import overload.drmed.overload.drmed.models.PesquisaPacienteConvenio;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.models.Success;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConvenioAdapter extends RecyclerView.Adapter<ConvenioAdapter.ViewHolder> {

    private List<PesquisaPacienteConvenio.Pesquisa> mDataset;
    private Context context;
    public View mView;
    private String ID_PaciConv;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public View mView;
        public TextView nome;
        public TextView codigo;
        public CheckBox ativo;
        public LinearLayout link;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            //context = v.getContext();

            codigo = (TextView) mView.findViewById(R.id.lbl_numero);
            nome = (TextView) mView.findViewById(R.id.lbl_nome);
            ativo = (CheckBox) mView.findViewById(R.id.lbl_ativo);
            link = (LinearLayout) mView.findViewById(R.id.btnLinear);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConvenioAdapter(List<PesquisaPacienteConvenio.Pesquisa> lista, String ID_PaciConv, Context mcontext) {
        this.ID_PaciConv = ID_PaciConv;
        mDataset = lista;
        context = mcontext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ConvenioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_convenio, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.nome.setText(mDataset.get(position).getNome_Convenio());

        if (mDataset.get(position).getAtivo() == 1) {
            holder.ativo.setChecked(true);
        } else {
            holder.ativo.setChecked(false);
        }
        holder.codigo.setText(mDataset.get(position).getCodigo());
        //

        holder.ativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Confirmar alteração")
                        .setMessage("Você realmente deseja alterar?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(StaticFiles.getUrlServer())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                Call<Success> call;
                                Interfaces.EditarPacienteConvenio api = retrofit.create(Interfaces.EditarPacienteConvenio.class);
                                if (mDataset.get(position).getAtivo() == 1) {
                                    call = api.getConvenios(ID_PaciConv, "0");
                                } else {
                                    call = api.getConvenios(ID_PaciConv, "1");
                                }

                                call.enqueue(new Callback<Success>() {
                                    @Override
                                    public void onResponse(Call<Success> call, Response<Success> response) {

                                        if (response.body() == null) {
                                            System.out.println("erro:" + response.message());
                                        } else {
                                            System.out.println("erro:" + response.message());
                                            Toast.makeText(context, "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Success> call, Throwable t) {
                                        System.out.println(call.request().body().toString());
                                        System.out.println("ERRRORRR:" + t.getMessage());
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (mDataset.get(position).getAtivo() == 1){
                                    holder.ativo.setChecked(true);
                                }else{
                                    holder.ativo.setChecked(false);
                                }
                            }
                        }).show();

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}