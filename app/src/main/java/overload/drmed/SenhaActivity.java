package overload.drmed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.models.Paciente;
import overload.drmed.overload.drmed.models.SPaciente;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.utils.Dialog;
import overload.drmed.overload.drmed.utils.MenuHK;
import overload.drmed.overload.drmed.utils.Testes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SenhaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView lbEmail;
    TextView lbNome;
    String[] extras = new String[3];

    TextInputLayout senha1;
    TextInputLayout senha2;

    EditText txtsenha1;
    EditText txtsenha2;

    Button btnalterar;

    SenhaActivity act = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //navigationView.setItemIconTintList(null);

        extras = getIntent().getExtras().getStringArray("paciente");

        View view = navigationView.getHeaderView(0);
        lbEmail = (TextView) view.findViewById(R.id.lbEmail);
        lbNome = (TextView) view.findViewById(R.id.lbNome);

        lbNome.setText(extras[1]);
        lbEmail.setText(extras[0]);

        senha1 = (TextInputLayout) findViewById(R.id.senha1);
        senha2 = (TextInputLayout) findViewById(R.id.senha2);

        txtsenha1 = senha1.getEditText();
        txtsenha2 = senha2.getEditText();

        btnalterar = (Button) findViewById(R.id.btnAlterar);
        btnalterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (Testes.isValidSenhas(txtsenha1, txtsenha2)) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(StaticFiles.getUrlServer())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    Interfaces.EditarSenha api = retrofit.create(Interfaces.EditarSenha.class);
                    Call<Object> call = api.setSenha(lbEmail.getText().toString(), txtsenha1.getText().toString());
                    call.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (response.code() == 200) {


                                CharSequence text = "Senha Alterada com sucesso!";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(getBaseContext(), text, duration);
                                toast.show();
                            } else {
                                Dialog.callOfTheDialog("Erro", "Não houve conexão com o servidor.", SenhaActivity.this);
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Log.d("Hue", "Failed: " + t.getMessage());
                            System.out.println(call.request().toString());
                            CharSequence text = "Algo de errado tente novamente :C";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getBaseContext(), text, duration);
                            toast.show();
                        }
                    });

                } else {

                }
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
