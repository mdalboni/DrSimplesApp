package overload.drmed;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import overload.drmed.overload.drmed.adapters.ConsultaAdapter;
import overload.drmed.overload.drmed.adapters.ExameAdapter;
import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.models.Exame;
import overload.drmed.overload.drmed.models.PacConsulta;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.utils.Dialog;
import overload.drmed.overload.drmed.utils.MenuHK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    HomeActivity act = this;
    TextView lbEmail;
    TextView lbNome;
    String[] extras = new String[3];

    private RecyclerView mRecyclerViewExame;
    private RecyclerView.Adapter mAdapterExame;
    private RecyclerView.LayoutManager mLayoutManagerExame;

    private RecyclerView mRecyclerViewConsulta;
    private RecyclerView.Adapter mAdapterConsulta;
    private RecyclerView.LayoutManager mLayoutManagerConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        mRecyclerViewExame = (RecyclerView) findViewById(R.id.exames_card);
        mRecyclerViewExame.setHasFixedSize(true);
        mLayoutManagerExame = new LinearLayoutManager(this);
        mRecyclerViewExame.setLayoutManager(mLayoutManagerExame);

        mRecyclerViewConsulta = (RecyclerView) findViewById(R.id.consulta_card);
        mRecyclerViewConsulta.setHasFixedSize(true);
        mLayoutManagerConsulta = new LinearLayoutManager(this);
        mRecyclerViewConsulta.setLayoutManager(mLayoutManagerConsulta);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);

        extras = getIntent().getExtras().getStringArray("paciente");
        View view = navigationView.getHeaderView(0);
        lbEmail = (TextView) view.findViewById(R.id.lbEmail);
        lbNome = (TextView) view.findViewById(R.id.lbNome);

        lbNome.setText(extras[1]);
        lbEmail.setText(extras[0]);

        atualizarExames();
        atualizarConsulta();
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


    void atualizarConsulta() {
        String tipo = "DataPos";

        Dialog.callOfTheProgressBar(act);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticFiles.getUrlServer())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Interfaces.PesquisarConsulta api = retrofit.create(Interfaces.PesquisarConsulta.class);
        Call<PacConsulta> call = api.getConsultaPaciente(extras[2], tipo);

        call.enqueue(new Callback<PacConsulta>() {
            @Override
            public void onResponse(Call<PacConsulta> call, Response<PacConsulta> response) {
                boolean end = response.body().isSuccess();
                System.out.println("RESPOSTA: " + end);

                ArrayList<PacConsulta.Pacientes> lista = response.body().getPacientes();
                mAdapterConsulta = new ConsultaAdapter(lista, act);
                mRecyclerViewConsulta.setAdapter(mAdapterConsulta);
                Dialog.dismissOfTheProgressBar();

            }

            @Override
            public void onFailure(Call<PacConsulta> call, Throwable t) {
                Dialog.dismissOfTheProgressBar();
                Dialog.callOfTheDialog("Erro ao conectar", "Existe algum problema de rede!", act);
            }
        });
    }

    void atualizarExames() {
        String tipo = "DataPos";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticFiles.getUrlServer())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Interfaces.PesquisarExames api = retrofit.create(Interfaces.PesquisarExames.class);
        Call<Exame> call = api.getExames(extras[2], tipo);

        call.enqueue(new Callback<Exame>() {
            @Override
            public void onResponse(Call<Exame> call, Response<Exame> response) {
                boolean end = response.body().isSuccess();
                System.out.println("RESPOSTA: " + end);
                System.out.println("RESPOSTA: " + response.body().getExames().size());

                ArrayList<Exame.exames> lista = response.body().getExames();
                mAdapterExame = new ExameAdapter(lista, extras[0], act);
                mRecyclerViewExame.setAdapter(mAdapterExame);
            }

            @Override
            public void onFailure(Call<Exame> call, Throwable t) {
                Dialog.callOfTheDialog("Erro ao conectar", "Existe algum problema de rede!",act);
            }
        });
    }

}
