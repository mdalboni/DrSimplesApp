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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import overload.drmed.ConsultaActivity;
import overload.drmed.R;
import overload.drmed.overload.drmed.controller.Interfaces;

import overload.drmed.overload.drmed.models.PacConsulta;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.models.Success;
import overload.drmed.overload.drmed.utils.Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.ViewHolder> {

    private List<PacConsulta.Pacientes> mDataset;
    private Context context;
    public View mView;
    AlertDialog.Builder alertDialogBuilderUserInput;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public View mView;
        public TextView data;
        public TextView nome;
        public TextView convenio;
        public TextView situacao;
        public TextView consultorio;
        public TextView especialidade;
        public LinearLayout link;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            //context = v.getContext();

            data = (TextView) mView.findViewById(R.id.lbl_data);
            especialidade = (TextView) mView.findViewById(R.id.lbl_especialidade);
            nome = (TextView) mView.findViewById(R.id.lbl_nome);
            situacao = (TextView) mView.findViewById(R.id.lbl_situacao);
            consultorio = (TextView) mView.findViewById(R.id.lbl_consultorio);
            convenio = (TextView) mView.findViewById(R.id.lbl_convenio);
            link = (LinearLayout) mView.findViewById(R.id.btnLinear);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConsultaAdapter(List<PacConsulta.Pacientes> consulta, Context mcontext) {
        mDataset = consulta;
        context = mcontext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ConsultaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_consulta, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.nome.setText(mDataset.get(position).getNome_Medico());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(mDataset.get(position).getData());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.data.setText(formatter2.format(date));
        holder.situacao.setText(mDataset.get(position).getSituacao());
        holder.consultorio.setText(mDataset.get(position).getNome_Consul());
        holder.convenio.setText(mDataset.get(position).getNome_Conv());
        holder.especialidade.setText(mDataset.get(position).getArea());

        if (holder.situacao.getText().toString().toUpperCase().equals("EM ESPERA")) {
            holder.link.setBackgroundColor(context.getResources().getColor(R.color.colorEmEspera));
        } else if (holder.situacao.getText().toString().toUpperCase().equals("CONFIRMADO")) {
            holder.link.setBackgroundColor(context.getResources().getColor(R.color.colorConfirmado));
        } else if (holder.situacao.getText().toString().toUpperCase().equals("CANCELADA")) {
            holder.link.setBackgroundColor(context.getResources().getColor(R.color.colorCancelado));
        } else if (holder.situacao.getText().toString().toUpperCase().equals("FALTOU")) {
            holder.link.setBackgroundColor(context.getResources().getColor(R.color.colorFaltou));
        }

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog.callOfTheProgressBar(context);
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                mView = layoutInflaterAndroid.inflate(R.layout.dialog_consulta, null);
                alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);

                final TextView lbldata = (TextView) mView.findViewById(R.id.lbl_data);
                final TextView lblmedico = (TextView) mView.findViewById(R.id.lbl_medico);
                final TextView lblsituacao = (TextView) mView.findViewById(R.id.lbl_situacao);
                final TextView lblconsultorio = (TextView) mView.findViewById(R.id.lbl_consultorio);
                final TextView lblmotivo = (TextView) mView.findViewById(R.id.lbl_motivo);

                lbldata.setText("Data: " + mDataset.get(position).getData());
                lblmedico.setText("Médico: " + mDataset.get(position).getNome_Medico());
                lblsituacao.setText("Situação: " + mDataset.get(position).getSituacao());
                lblconsultorio.setText("Consultorio: " + mDataset.get(position).getNome_Consul());

                if(mDataset.get(position).getSituacao().equals("Cancelada")){
                    lblmotivo.setText("Motivo:\n" + mDataset.get(position).getMotivo());
                }else{
                    lblmotivo.setText("");
                }


                Dialog.dismissOfTheProgressBar();
                if (mDataset.get(position).getSituacao().equals("Em Espera")) {
                    alertDialogBuilderUserInput
                            .setCancelable(true).setPositiveButton("Cancelar Consulta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (mDataset.get(position).getSituacao().equals("Em Espera")) {
                                Dialog.callOfTheProgressBar(context);
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(StaticFiles.getUrlServer())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                final Interfaces.CancelarConsulta api = retrofit.create(Interfaces.CancelarConsulta.class);
                                Call<Success> call = api.cancelarConsulta(mDataset.get(position).getID_Consa());

                                call.enqueue(new Callback<Success>() {
                                    @Override
                                    public void onResponse(Call<Success> call, Response<Success> response) {
                                        try {
                                            if (response.body().isSuccess()) {
                                                Toast.makeText(context, "Consulta cancelada com sucesso!",
                                                        Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(context, "Algo deu errado tente novamente.",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        } catch (NullPointerException ex) {
                                            Toast.makeText(context, "Algo deu errado tente novamente.",
                                                    Toast.LENGTH_LONG).show();
                                        }

                                        Dialog.dismissOfTheProgressBar();

                                    }

                                    @Override
                                    public void onFailure(Call<Success> call, Throwable t) {
                                        Toast.makeText(context, "Algo deu errado tente novamente.",
                                                Toast.LENGTH_LONG).show();
                                        Dialog.dismissOfTheProgressBar();
                                    }
                                });


                            } else {

                            }
                        }
                    })
                            .setNegativeButton("Voltar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            dialogBox.cancel();
                                        }
                                    }).create().show();
                }else{

                    alertDialogBuilderUserInput
                            .setCancelable(true).
                            setNegativeButton("Voltar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            Dialog.dismissOfTheProgressBar();
                                            dialogBox.cancel();
                                        }
                                    }).create().show();

                }
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public void view(View v) {

        File pdfFile = new File(Environment.getDataDirectory() + "/testthreepdf/" + "maven.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            context.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Não foi possivel baixar o Exame", Toast.LENGTH_SHORT).show();
        }
    }

}