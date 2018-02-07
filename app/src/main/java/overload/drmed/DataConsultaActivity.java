package overload.drmed;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;


import com.squareup.timessquare.CalendarPickerView;

import java.util.Date;

import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.models.ListaHorarios;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.models.Success;

import overload.drmed.overload.drmed.utils.Dialog;
import overload.drmed.overload.drmed.utils.MenuHK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataConsultaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView lbEmail;
    TextView lbNome;
    String[] extras = new String[8];
    DataConsultaActivity act = this;
    CalendarPickerView calendar;
    View mView;
    AlertDialog.Builder alertDialogBuilderUserInput;
    Context c = this;
    DateFormat df;
    Boolean bugFix=false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ANDROID
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_consulta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ANDROID

        //MEU

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        extras = getIntent().getExtras().getStringArray("paciente");

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        final Date today = new Date();
        calendar.init(today, nextYear.getTime());

        calendar.setCellClickInterceptor(new CalendarPickerView.CellClickInterceptor() {
            @Override
            public boolean onCellClicked(Date date) {
                if(!bugFix) {
                    if(date.after(today)) {
                        return atualizarCombo(date);
                    }else{

                        return  false;
                    }
                }else{
                    return false;
                }
            }

        });


        View view = navigationView.getHeaderView(0);
        lbEmail = (TextView) view.findViewById(R.id.lbEmail);
        lbNome = (TextView) view.findViewById(R.id.lbNome);

        lbNome.setText(extras[1]);
        lbEmail.setText(extras[0]);


    }


    public boolean atualizarCombo(Date date) {
        bugFix=true;
        Dialog.callOfTheProgressBar(DataConsultaActivity.this);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        mView = layoutInflaterAndroid.inflate(R.layout.dialog_data_consulta, null);
        alertDialogBuilderUserInput = new AlertDialog.Builder(c);
        alertDialogBuilderUserInput.setView(mView);

        final Spinner dropdown = (Spinner) mView.findViewById(R.id.cmbHorario);
        final TextView lblnome = (TextView) mView.findViewById(R.id.lbl_nome);
        final TextView lbldia = (TextView) mView.findViewById(R.id.lbl_data);
        final TextView lblcons = (TextView) mView.findViewById(R.id.lbl_consultorio);
        final TextView lblconv = (TextView) mView.findViewById(R.id.lbl_convenio);

        df = new SimpleDateFormat("dd/MM/yyyy");

        lblnome.setText(extras[5]);
        lbldia.setText(df.format(date));
        lblcons.setText(extras[4]);
        lblconv.setText(extras[3]);


        //extras[3] = Nome Convenio
        //extras[4] = Nome Consultorio
        //extras[5] = Nome Medico
        //extras[6] = ID ConsuConv
        //extras[7] = ID PaciConv

        df = new SimpleDateFormat("MM/dd/yyyy");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticFiles.getUrlServer())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Interfaces.PesquisarHorariosConsulta api = retrofit.create(Interfaces.PesquisarHorariosConsulta.class);
        Call<ListaHorarios> call = api.getListaHorarios(extras[6], df.format(date));
        System.out.println(extras[6]);
        System.out.println(df.format(date));

        call.enqueue(new Callback<ListaHorarios>() {
            @Override
            public void onResponse(Call<ListaHorarios> call, Response<ListaHorarios> response) {

                try {
                    System.out.println("RESPOSTA: " + response.body().isSuccess());
                    boolean end = response.body().isSuccess();
                    System.out.println("Tamanho: " + response.body().getHorariosDisponiveis().size());

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
                    ArrayList<String> horarios = new ArrayList<>();

                    for (int a = 0; a < response.body().getHorariosDisponiveis().size(); a++) {
                        try {
                            if (response.body().getHorariosDisponiveis().get(a).isDisponivel()) {
                                Date date = formatter.parse(response.body().getHorariosDisponiveis().get(a).getDtMarcada());
                                horarios.add(formatter2.format(date));
                                System.out.println(formatter2.format(date));
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(act, android.R.layout.simple_spinner_dropdown_item, horarios);
                    dropdown.setAdapter(adapter);

                    System.out.println("-----Abrindo tela-----");
                    Dialog.dismissOfTheProgressBar();
                    alertDialogBuilderUserInput.setCancelable(false)
                            .setPositiveButton("Confirmar!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    bugFix=false;
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(StaticFiles.getUrlServer())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();


                                    Interfaces.CadastrarConsulta api = retrofit.create(Interfaces.CadastrarConsulta.class);

                                    String ano = lbldia.getText().toString().substring(6, 10);
                                    String mes = lbldia.getText().toString().toString().substring(3, 5);
                                    String dia = lbldia.getText().toString().toString().substring(0, 2);

                                    String concatData = mes+"/"+dia+"/"+ano+" "+dropdown.getSelectedItem().toString().substring(0,5);
                                    System.out.println("Data: "+concatData);
                                    System.out.println("IDCC: "+extras[7]);
                                    System.out.println("IDPC: "+extras[6]);

                                    Call<Success> call = api.criarConsulta(concatData, extras[6], extras[7]);

                                    call.enqueue(new Callback<Success>() {
                                        @Override
                                        public void onResponse(Call<Success> call, Response<Success> response) {

                                            Dialog.dismissOfTheProgressBar();
                                            if(response.body().isSuccess()){
                                                Dialog.callOfTheDialog("Sucesso","Cadastrado com sucesso!",act);
                                            }else if(response.body().getMessage().equals("Horário Ocupado")){
                                                Dialog.callOfTheDialog("Algo deu errado :(","Não foi possivel fazer o pedido de consulta.\nTente outro horário.",act);
                                            }else{
                                                Dialog.callOfTheDialog("Algo deu errado :(","Tente novamente.",act);
                                                System.out.println("ERRO:\n"+response.body().getMessage());
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<Success> call, Throwable t) {
                                            System.out.println(t.getMessage());
                                            Dialog.dismissOfTheProgressBar();
                                        }
                                    });




                                }
                            })
                            .setNegativeButton("Cancelar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            bugFix=false;
                                            dialogBox.cancel();
                                        }
                                    }).create().show();

                } catch (NullPointerException ex) {
                    ex.getMessage();
                    bugFix=false;
                }
            }

            @Override
            public void onFailure(Call<ListaHorarios> call, Throwable t) {
                System.out.println(t.getMessage());
                bugFix=false;
            }
        });


        return true;
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
        getMenuInflater().inflate(R.menu.data_consulta, menu);
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
