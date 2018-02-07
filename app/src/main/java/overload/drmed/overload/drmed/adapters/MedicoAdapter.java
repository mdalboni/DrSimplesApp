package overload.drmed.overload.drmed.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import overload.drmed.CadastroConsultaActivity;
import overload.drmed.DataConsultaActivity;
import overload.drmed.HomeActivity;
import overload.drmed.R;
import overload.drmed.overload.drmed.models.Areas;
import overload.drmed.overload.drmed.models.Consulta;
import overload.drmed.overload.drmed.models.ConsultorioConvenio;

public class MedicoAdapter extends RecyclerView.Adapter<MedicoAdapter.ViewHolder> {

    private List<ConsultorioConvenio.ConsConv> mDataset;
    private Context context;
    public View mView;
    private String[] mExtras;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public View mView;
        public TextView lbl_nome;
        public TextView lbl_area;
        public TextView lbl_convenio;
        public TextView lbl_consultorio;
        public RelativeLayout acao;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            //context = v.getContext();

            lbl_nome = (TextView) mView.findViewById(R.id.lbl_nome);
            //lbl_area = (TextView) mView.findViewById(R.id.lbl_nome);
            lbl_convenio = (TextView) mView.findViewById(R.id.lbl_convenio);
            lbl_consultorio = (TextView) mView.findViewById(R.id.lbl_consultorio);
            acao = (RelativeLayout) mView.findViewById(R.id.btnLinear);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MedicoAdapter(List<ConsultorioConvenio.ConsConv> consulta, Context mcontext,String[] extras) {
        mDataset = consulta;
        context=mcontext;
        mExtras=extras;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MedicoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_medico, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.lbl_nome.setText(mDataset.get(position).getNome_Medi());
        holder.lbl_consultorio.setText(mDataset.get(position).getNome_Consu());
        //holder.lbl_area.setText(mDataset.get(position).getN());
        holder.lbl_convenio.setText(mDataset.get(position).getNome_Conv());

        holder.acao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context , DataConsultaActivity.class);
                String[] extras= new String[8];
                extras[0] = mExtras[0];
                extras[1] = mExtras[1];
                extras[2] = mExtras[2];
                extras[3] = mDataset.get(position).getNome_Conv();
                extras[4] = mDataset.get(position).getNome_Consu();
                extras[5] = mDataset.get(position).getNome_Medi();
                extras[6] = mDataset.get(position).getId_ConsuConv();
                extras[7] = mExtras[3];

                intent.putExtra("paciente",extras);

                context.startActivity(intent);

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