package overload.drmed;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import overload.drmed.overload.drmed.adapters.ConsultaAdapter;
import overload.drmed.overload.drmed.adapters.ConvenioAdapter;
import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.models.CadastrarConvenio;
import overload.drmed.overload.drmed.models.Consulta;
import overload.drmed.overload.drmed.models.Convenio;
import overload.drmed.overload.drmed.models.PacienteConvenios;
import overload.drmed.overload.drmed.models.PesquisaConvenio;
import overload.drmed.overload.drmed.models.PesquisaPacienteConvenio;
import overload.drmed.overload.drmed.models.RetornoCadConvenio;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.utils.Dialog;
import overload.drmed.overload.drmed.utils.MenuHK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ConveniosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ConveniosActivity act = this;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    AlertDialog.Builder alertDialogBuilderUserInput;
    TextView lbEmail;
    TextView lbNome;

    View mView;

    String[] items;
    int[] itemsID;
    Context c = this;

    Retrofit retrofit;

    String[] extras = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenios);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                mView = layoutInflaterAndroid.inflate(R.layout.dialog_box, null);
                alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);


                retrofit = new Retrofit.Builder()
                        .baseUrl(StaticFiles.getUrlServer())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Interfaces.PesqConvenio api = retrofit.create(Interfaces.PesqConvenio.class);
                Call<PesquisaConvenio> call = api.getConvenios();

                call.enqueue(new Callback<PesquisaConvenio>() {
                    @Override
                    public void onResponse(Call<PesquisaConvenio> call, Response<PesquisaConvenio> response) {
                        boolean end = response.body().isSuccess();
                        System.out.println("RESPOSTA: " + end);
                        if (end) {
                            ArrayList<Convenio> array = response.body().getConvenio();
                            items = new String[array.size()];
                            itemsID = new int[array.size()];

                            for (int a = 0; a < array.size(); a++) {
                                items[a] = array.get(a).getNome_Conv();
                                System.out.println("RESPOSTA: " + items[a]);
                                itemsID[a] = Integer.parseInt(array.get(a).getID_Conv());
                                System.out.println("RESPOSTA: " + itemsID[a]);
                            }

                            final Spinner dropdown = (Spinner) mView.findViewById(R.id.cmbConvenios);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_dropdown_item, items);
                            dropdown.setAdapter(adapter);

                            final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                            alertDialogBuilderUserInput
                                    .setCancelable(true)
                                    .setPositiveButton("Criar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {

                                            Interfaces.CadConvenio api = retrofit.create(Interfaces.CadConvenio.class);
                                            final CadastrarConvenio cconv = new CadastrarConvenio();
                                            cconv.setAtivo(1);
                                            cconv.setCodigo(userInputDialogEditText.getText().toString());
                                            cconv.setId_Conv(itemsID[dropdown.getSelectedItemPosition()]);
                                            cconv.setId_Paci(Integer.parseInt(extras[2]));

                                            Call<RetornoCadConvenio> call = api.setConvenios(cconv);

                                            call.enqueue(new Callback<RetornoCadConvenio>() {
                                                @Override
                                                public void onResponse(Call<RetornoCadConvenio> call, Response<RetornoCadConvenio> response) {

                                                    atualizarTela();

                                                }

                                                @Override
                                                public void onFailure(Call<RetornoCadConvenio> call, Throwable t) {
                                                    //callOfTheDialog("Erro ao conectar", "Existe algum problema de rede!");
                                                }
                                            });


                                        }
                                    })
                                    .setNegativeButton("Cancelar",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialogBox, int id) {

                                                    dialogBox.cancel();
                                                    
                                                }
                                            }).create().show();


                        } else {
                            //callOfTheDialog("Erro ao conectar", "Suas credenciais estão erradas ou não existem.");
                        }
                    }

                    @Override
                    public void onFailure(Call<PesquisaConvenio> call, Throwable t) {
                        //callOfTheDialog("Erro ao conectar", "Existe algum problema de rede!");
                    }
                });

                //AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                //alertDialogAndroid.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.card_convenio);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        navigationView.setItemIconTintList(null);

        extras = getIntent().getExtras().getStringArray("paciente");
        View view = navigationView.getHeaderView(0);
        lbEmail = (TextView) view.findViewById(R.id.lbEmail);
        lbNome = (TextView) view.findViewById(R.id.lbNome);

        lbNome.setText(extras[1]);
        lbEmail.setText(extras[0]);

        atualizarTela();

    }

    private void atualizarTela() {
        System.out.println("Mas ué");
        System.out.println("ID_PAC:" + extras[2]);
        retrofit = new Retrofit.Builder()
                .baseUrl(StaticFiles.getUrlServer())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Dialog.callOfTheProgressBar(ConveniosActivity.this);

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
                    List<PesquisaPacienteConvenio.Pesquisa> list = lista.getPacienteConvenios();

                    mAdapter = new ConvenioAdapter(list, extras[2], act);
                    mRecyclerView.setAdapter(mAdapter);
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
        act.startActivity(MenuHK.acessarMenu(item.getTitle().toString(), act, extras));
        return true;
    }

}
