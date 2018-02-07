package overload.drmed;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;

import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import overload.drmed.overload.drmed.adapters.ConsultaAdapter;
import overload.drmed.overload.drmed.adapters.MedicoAdapter;
import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.models.Areas;
import overload.drmed.overload.drmed.models.Consulta;
import overload.drmed.overload.drmed.models.ConsultorioConvenio;
import overload.drmed.overload.drmed.models.PesquisaPacienteConvenio;
import overload.drmed.overload.drmed.models.SPaciente;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.utils.Dialog;
import overload.drmed.overload.drmed.utils.MenuHK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroConsultaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CadastroConsultaActivity act = this;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    Spinner cmb_Convenios;
    Spinner cmb_Area;
    TextInputLayout cidade;
    EditText txt_cidade;

    TextInputLayout pesquisa;
    EditText txt_pesquisar;

    ImageButton btn_pesquisar;

    TextView lbEmail;
    TextView lbNome;

    String[] extras = new String[4];

    String[] items;
    int[] itemsID;

    String[] items2;
    int[] itemsID2;
    int[] itemsIDPC2;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_consulta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.medico_card);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)


        String[] extras2 = getIntent().getExtras().getStringArray("paciente");
        extras[0] = extras2[0];
        extras[1] = extras2[1];
        extras[2] = extras2[2];
        View view = navigationView.getHeaderView(0);
        lbEmail = (TextView) view.findViewById(R.id.lbEmail);
        lbNome = (TextView) view.findViewById(R.id.lbNome);

        lbNome.setText(extras[1]);
        lbEmail.setText(extras[0]);

        cidade = (TextInputLayout) findViewById(R.id.txt_cidade);
        txt_cidade = cidade.getEditText();

        pesquisa = (TextInputLayout) findViewById(R.id.txt_pesquisa);
        txt_pesquisar = pesquisa.getEditText();

        //cmb_Area = (Spinner) findViewById(R.id.cmb_Area);
        //cmb_Convenios = (Spinner) findViewById(R.id.cmb_Convenios);


        retrofit = new Retrofit.Builder()
                .baseUrl(StaticFiles.getUrlServer())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Dialog.callOfTheProgressBar(CadastroConsultaActivity.this);

        // Create an instance of our GitHub API interface.


        preencherCidade(retrofit);
        preencherSpinnerArea(retrofit);
        preencherSpinnerConvenio(retrofit);


        btn_pesquisar = (ImageButton) findViewById(R.id.btn_pesquisar);

        btn_pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Interfaces.PesquisarMedicoConsultorio api = retrofit.create(Interfaces.PesquisarMedicoConsultorio.class);
                Call<ConsultorioConvenio> call = api.getMedicoConsultori(itemsID2[cmb_Convenios.getSelectedItemPosition()], itemsID[cmb_Area.getSelectedItemPosition()], txt_cidade.getText().toString(), txt_pesquisar.getText().toString());

                call.enqueue(new Callback<ConsultorioConvenio>() {
                    @Override
                    public void onResponse(Call<ConsultorioConvenio> call, Response<ConsultorioConvenio> response) {
                        boolean end = response.body().isSuccess();
                        if (response.code() == 200 && end) {
                            List<ConsultorioConvenio.ConsConv> lista = response.body().getConsConv();
                            extras[3] = itemsIDPC2[cmb_Convenios.getSelectedItemPosition()]+"";
                            mAdapter = new MedicoAdapter(lista, act,extras);
                            mRecyclerView.setAdapter(mAdapter);


                        } else {
                            Dialog.callOfTheDialog("Erro de dados", "Existe algum problema inesperado", act);
                        }
                    }

                    @Override
                    public void onFailure(Call<ConsultorioConvenio> call, Throwable t) {
                        System.out.println(t.getMessage());
                        Dialog.callOfTheDialog("Erro ao conectar", "Existe algum problema de rede!", act);
                    }
                });
            }
        });


        navigationView.setItemIconTintList(null);
    }

    /*


    PREENCHER AREA



    */
    private void preencherSpinnerArea(Retrofit retrofit) {

        Interfaces.PegarArea api = retrofit.create(Interfaces.PegarArea.class);
        Call<Areas> call = api.getAreas();

        call.enqueue(new Callback<Areas>() {
            @Override
            public void onResponse(Call<Areas> call, Response<Areas> response) {
                boolean end = response.body().isSuccess();
                if (response.code() == 200 && end) {

                    ArrayList<Areas.Area> array = response.body().getArea();
                    items = new String[array.size()];
                    itemsID = new int[array.size()];

                    for (int a = 0; a < array.size(); a++) {
                        items[a] = array.get(a).getNome_Area();
                        System.out.println("RESPOSTA: " + items[a]);
                        itemsID[a] = Integer.parseInt(array.get(a).getID_Area());
                        System.out.println("RESPOSTA: " + itemsID[a]);
                    }

                    cmb_Area = (Spinner) findViewById(R.id.cmb_Area);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_dropdown_item, items);
                    cmb_Area.setAdapter(adapter);
                } else {
                    Dialog.callOfTheDialog("Erro de Conexão", "Existe algum problema preenchendo a caixa de Area de atuação.", act);
                }
            }

            @Override
            public void onFailure(Call<Areas> call, Throwable t) {
                System.out.println(t.getMessage());
                Dialog.callOfTheDialog("Erro ao conectar", "Existe algum problema de rede!", act);
            }
        });

    }

    /*


   PREENCHER CONVENIO PACIENTE



   */
    private void preencherSpinnerConvenio(Retrofit retrofit) {

        retrofit = new Retrofit.Builder()
                .baseUrl(StaticFiles.getUrlServer())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Interfaces.PacienteConvenio api = retrofit.create(Interfaces.PacienteConvenio.class);
        Call<PesquisaPacienteConvenio> call = api.getConvenios(extras[2]);

        call.enqueue(new Callback<PesquisaPacienteConvenio>() {
            @Override
            public void onResponse(Call<PesquisaPacienteConvenio> call, Response<PesquisaPacienteConvenio> response) {

                Dialog.dismissOfTheProgressBar();
                if (response.body() == null) {
                    System.out.println("erro:" + response.message());
                } else {

                    PesquisaPacienteConvenio lista = response.body();
                    List<PesquisaPacienteConvenio.Pesquisa> array = lista.getPacienteConvenios();

                    items2 = new String[array.size()];
                    itemsID2 = new int[array.size()];
                    itemsIDPC2 = new int[array.size()];

                    for (int a = 0; a < array.size(); a++) {
                        items2[a] = array.get(a).getNome_Convenio();
                        itemsID2[a] = Integer.parseInt(array.get(a).getID_Convenio());
                        itemsIDPC2[a] = Integer.parseInt(array.get(a).getID_PaciConv());
                    }

                    cmb_Convenios = (Spinner) findViewById(R.id.cmb_Convenios);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_dropdown_item, items2);
                    cmb_Convenios.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<PesquisaPacienteConvenio> call, Throwable t) {
                Dialog.dismissOfTheProgressBar();
                System.out.println(call.request().body().toString());
                System.out.println("ERRRORRR:" + t.getMessage());
            }
        });

    }

    /*


    PREENCHER CIDADE DO PACIENTE



     */
    private void preencherCidade(Retrofit retrofit) {

        Interfaces.PegarPaciente api = retrofit.create(Interfaces.PegarPaciente.class);
        Call<SPaciente> call = api.getPaciente(Integer.parseInt(extras[2]));

        call.enqueue(new Callback<SPaciente>() {
            @Override
            public void onResponse(Call<SPaciente> call, Response<SPaciente> response) {
                boolean end = response.body().isSuccess();
                if (response.code() == 200 && end) {
                    txt_cidade.setText(response.body().getPacientes().getCidade_Paci());
                } else {
                    Dialog.callOfTheDialog("Erro de dados", "Existe algum problema inesperado", act);
                }
            }

            @Override
            public void onFailure(Call<SPaciente> call, Throwable t) {
                System.out.println(t.getMessage());
                Dialog.callOfTheDialog("Erro ao conectar", "Existe algum problema de rede!", act);
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Intent intent = MenuHK.acessarMenu(item.getTitle().toString(), act, extras);
        act.startActivity(intent);
        return true;
    }
}
