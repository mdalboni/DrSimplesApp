package overload.drmed.overload.drmed.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import overload.drmed.R;
import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.controller.Laboratorios;
import overload.drmed.overload.drmed.mask.Mask;
import overload.drmed.overload.drmed.models.PacConsulta;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.models.Success;
import overload.drmed.overload.drmed.utils.Dialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaboratorioAdapter extends RecyclerView.Adapter<LaboratorioAdapter.ViewHolder> {

    private List<Laboratorios.Laboratorio> mDataset;
    private Context context;
    public View mView;
    AlertDialog.Builder alertDialogBuilderUserInput;
    String[] extras;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public View mView;
        public TextView rua;
        public TextView nome;
        public TextView bairro;
        public TextView estado;
        public TextView cidade;
        public TextView especialidade;

        public LinearLayout link;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            //context = v.getContext();

            rua = (TextView) mView.findViewById(R.id.lbl_rua);
            nome = (TextView) mView.findViewById(R.id.lbl_nome);
            cidade = (TextView) mView.findViewById(R.id.lbl_city);
            especialidade = (TextView) mView.findViewById(R.id.lbl_especialidade);
            estado = (TextView) mView.findViewById(R.id.lbl_estado);
            bairro = (TextView) mView.findViewById(R.id.lbl_bairro);

            link = (LinearLayout) mView.findViewById(R.id.btnLinear);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LaboratorioAdapter(List<Laboratorios.Laboratorio> consulta, Context mcontext, String[] extras) {
        mDataset = consulta;
        context = mcontext;
        this.extras = extras;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LaboratorioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_laboratorio, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.nome.setText(mDataset.get(position).getNome_Labo());
        System.out.println(mDataset.get(position).getCidade_Labo());
        holder.cidade.setText(mDataset.get(position).getCidade_Labo());
        holder.estado.setText(mDataset.get(position).getEstado_Labo());
        holder.rua.setText(mDataset.get(position).getEndereco_Labo());
        holder.bairro.setText(mDataset.get(position).getBairro_Labo());
        holder.especialidade.setText(mDataset.get(position).getId_Tipo());


        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog.callOfTheProgressBar(context);
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                mView = layoutInflaterAndroid.inflate(R.layout.dialog_laboratorio, null);
                alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);

                final TextView lblnome = (TextView) mView.findViewById(R.id.lbl_nome);
                final TextView lblrua = (TextView) mView.findViewById(R.id.lbl_rua);
                final TextView lblbairro = (TextView) mView.findViewById(R.id.lbl_bairro);
                final TextView lblcidade = (TextView) mView.findViewById(R.id.lbl_cidade);
                final EditText txtprotocolo = (EditText) mView.findViewById(R.id.txt_protocolo);
                final EditText txtsenha = (EditText) mView.findViewById(R.id.txt_senha);
                final EditText txtentrega = (EditText) mView.findViewById(R.id.txt_data);
                final TextWatcher nascMask;
                nascMask = (TextWatcher) Mask.insert("##/##/####", txtentrega);
                txtentrega.addTextChangedListener(nascMask);

                lblnome.setText("Laboratório: " + mDataset.get(position).getNome_Labo());
                lblrua.setText("Endereço: " + mDataset.get(position).getEndereco_Labo());
                lblbairro.setText("Bairro: " + mDataset.get(position).getBairro_Labo());
                lblcidade.setText("Cidade: " + mDataset.get(position).getCidade_Labo());

                Dialog.dismissOfTheProgressBar();

                alertDialogBuilderUserInput
                        .setCancelable(true).setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //if (txtprotocolo.getText().toString().length() > 4) {
                        Dialog.callOfTheProgressBar(context);
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(StaticFiles.getUrlServer())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        String ano = txtentrega.getText().toString().substring(6, 10);
                        String mes = txtentrega.getText().toString().substring(3, 5);
                        String dia = txtentrega.getText().toString().substring(0, 2);

                        String data = mes + "/" + dia + "/" + ano;


                        final Interfaces.CadastrarExame api = retrofit.create(Interfaces.CadastrarExame.class);
                        Call<Success> call = api.criarExame(data,
                                extras[3],
                                mDataset.get(position).getID_Labo(),
                                txtprotocolo.getText().toString(),
                                txtsenha.getText().toString());
                        System.out.println("Data: " + data
                                + "\nIDPaciConv: " + extras[3]
                                + "\nIDLaboConv: " + mDataset.get(position).getID_ConvLabo()
                                + "\nProtocolo: " + txtprotocolo.getText().toString()
                                + "\nSenha: " + txtsenha.getText().toString());

                        call.enqueue(new Callback<Success>() {
                            @Override
                            public void onResponse(Call<Success> call, Response<Success> response) {
                                try {
                                    if (response.body().isSuccess()) {
                                        Toast.makeText(context, "Exame cadastrado com sucesso.",
                                                Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(context, "Algo deu errado tente novamente.\nCodigo: 0x001",
                                                Toast.LENGTH_LONG).show();
                                    }
                                } catch (NullPointerException ex) {
                                    Toast.makeText(context, "Algo deu errado tente novamente.\nCodigo: 0x000",
                                            Toast.LENGTH_LONG).show();
                                }

                                Dialog.dismissOfTheProgressBar();

                            }

                            @Override
                            public void onFailure(Call<Success> call, Throwable t) {
                                Toast.makeText(context, "Algo deu errado tente novamente.\nCodigo: 0x002",
                                        Toast.LENGTH_LONG).show();
                                Dialog.dismissOfTheProgressBar();
                            }
                        });

                    }
                })
                        .setNegativeButton("Voltar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                }).create().show();
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