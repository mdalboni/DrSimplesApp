package overload.drmed;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.OkHttpClient;
import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.mask.Mask;
import overload.drmed.overload.drmed.models.Endereco;
import overload.drmed.overload.drmed.models.Paciente;
import overload.drmed.overload.drmed.models.SPaciente;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.models.Success;
import overload.drmed.overload.drmed.utils.Dialog;
import overload.drmed.overload.drmed.utils.MenuHK;
import overload.drmed.overload.drmed.utils.Testes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarPacActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditarPacActivity act = this;
    TextView lbEmail;
    TextView lbNome;
    String[] extras = new String[3];

    EditText txtNome;
    EditText txtNomeSocial;
    EditText txtNascimento;
    EditText txtTelefone;
    ToggleButton txtSexo;
    EditText txtCPF;
    EditText txtRG;
    EditText txtOrgao;
    EditText txtEmail;
    EditText txtcep;
    EditText txtEndereco;
    EditText txtNumero;
    EditText txtComplemento;
    EditText txtBairro;
    EditText txtCidade;
    EditText txtEstado;
    EditText txtPai;
    EditText txtMae;


    TextInputLayout Nome;
    TextInputLayout NomeSocial;
    TextInputLayout Nascimento;
    TextInputLayout Telefone;
    TextInputLayout CPF;
    TextInputLayout RG;
    TextInputLayout Orgao;
    TextInputLayout Email;
    TextInputLayout cep;
    TextInputLayout Endereco;
    TextInputLayout Numero;
    TextInputLayout Complemento;
    TextInputLayout Bairro;
    TextInputLayout Cidade;
    TextInputLayout Estado;
    TextInputLayout Pai;
    TextInputLayout Mae;


    private TextWatcher cpfMask;
    private TextWatcher nascMask;

    Button btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarpac);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        extras = getIntent().getExtras().getStringArray("paciente");
        View view = navigationView.getHeaderView(0);
        lbEmail = (TextView) view.findViewById(R.id.lbEmail);
        lbNome = (TextView) view.findViewById(R.id.lbNome);

        lbNome.setText(extras[1]);
        lbEmail.setText(extras[0]);


        Nome = (TextInputLayout) findViewById(R.id.nome);
        NomeSocial = (TextInputLayout) findViewById(R.id.nomesocial);
        Nascimento = (TextInputLayout) findViewById(R.id.nascimento);
        Telefone = (TextInputLayout) findViewById(R.id.telefone);
        txtSexo = (ToggleButton) findViewById(R.id.sexo);
        CPF = (TextInputLayout) findViewById(R.id.cpf);
        RG = (TextInputLayout) findViewById(R.id.rg);
        Orgao = (TextInputLayout) findViewById(R.id.orgao);
        Email = (TextInputLayout) findViewById(R.id.email);
        Endereco = (TextInputLayout) findViewById(R.id.endereco);
        Numero = (TextInputLayout) findViewById(R.id.numero);
        Complemento = (TextInputLayout) findViewById(R.id.complemento);
        Bairro = (TextInputLayout) findViewById(R.id.bairro);
        cep = (TextInputLayout) findViewById(R.id.cep);
        Mae = (TextInputLayout) findViewById(R.id.mae);
        Cidade = (TextInputLayout) findViewById(R.id.cidade);
        Estado = (TextInputLayout) findViewById(R.id.estado);
        Pai = (TextInputLayout) findViewById(R.id.pai);

        txtNome = Nome.getEditText();
        txtNomeSocial = NomeSocial.getEditText();
        txtNascimento = Nascimento.getEditText();
        txtTelefone = Telefone.getEditText();
        txtCPF = CPF.getEditText();
        txtRG = RG.getEditText();
        txtOrgao = Orgao.getEditText();
        txtEmail = Email.getEditText();
        txtEndereco = Endereco.getEditText();
        txtNumero = Numero.getEditText();
        txtComplemento = Complemento.getEditText();
        txtBairro = Bairro.getEditText();
        txtcep = cep.getEditText();
        txtCidade = Cidade.getEditText();
        txtEstado = Estado.getEditText();
        txtPai = Pai.getEditText();
        txtMae = Mae.getEditText();

        Dialog.callOfTheProgressBar(act);


        cpfMask = (TextWatcher) Mask.insert("###.###.###-##", txtCPF);
        txtCPF.addTextChangedListener(cpfMask);
        txtCPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!v.hasFocus()) {
                    if (Testes.validarCpf(txtCPF.getText().toString())) {
                        txtCPF.setError(null);
                    } else {
                        txtCPF.setError("Esse CPF não é valido");
                    }
                }
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticFiles.getUrlServer())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.

        Interfaces.PegarPaciente api = retrofit.create(Interfaces.PegarPaciente.class);
        Call<SPaciente> call = api.getPaciente(Integer.parseInt(extras[2]));
        call.enqueue(new Callback<SPaciente>() {
            @Override
            public void onResponse(Call<SPaciente> call, Response<SPaciente> response) {

                if (response.body().isSuccess()) {
                    Paciente paciente = response.body().getPacientes();

                    txtNome.setText(paciente.getNome_Paci());
                    txtNomeSocial.setText(paciente.getNomeSocial_Paci());

                    txtTelefone.setText(paciente.getTelefone_Paci());

                    txtCPF.setText(paciente.getCPF_Paci());
                    txtRG.setText(paciente.getRG_Paci());
                    txtOrgao.setText(paciente.getOrgao_Paci());

                    String ano = paciente.getDtNasc_Paci().substring(0, 4);
                    String mes = paciente.getDtNasc_Paci().substring(5, 7);
                    String dia = paciente.getDtNasc_Paci().substring(8, 10);

                    txtNascimento.setText(dia + "/" + mes + "/" + ano);


                    txtEmail.setText(paciente.getEmail_Paci());
                    txtMae.setText(paciente.getMae_Paci());
                    txtPai.setText(paciente.getPai_Paci());

                    if (paciente.getSexo_Paci().equals("Masculino")) {
                        txtSexo.setActivated(false);
                    } else {
                        txtSexo.setActivated(true);
                    }

                    txtcep.setText(paciente.getCEP_Paci());
                    txtEndereco.setText(paciente.getEndereco_Paci());
                    txtNumero.setText(paciente.getNumero_Paci());
                    txtBairro.setText(paciente.getBairro_Paci());
                    txtCidade.setText(paciente.getCidade_Paci());
                    txtEstado.setText(paciente.getEstado_Paci());
                    txtComplemento.setText(paciente.getComplemento_Paci());

                    Dialog.dismissOfTheProgressBar();
                } else {
                    Dialog.dismissOfTheProgressBar();
                    Dialog.callOfTheDialog("Erro", "Opa algo deu errado, tente novamente.", EditarPacActivity.this);
                }
            }

            @Override
            public void onFailure(Call<SPaciente> call, Throwable t) {
                Log.d("Hue", "Failed: " + t.getMessage());
                System.out.println(call.request().toString());
                Dialog.dismissOfTheProgressBar();
                Dialog.callOfTheDialog("Erro", "Não houve conexão com o servidor.", EditarPacActivity.this);
            }
        });

        nascMask = (TextWatcher) Mask.insert("##/##/####", txtNascimento);
        txtNascimento.addTextChangedListener(nascMask);


        txtcep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtcep.getText().toString().length() > 7) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(StaticFiles.getUrlCEP())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    // Create an instance of our GitHub API interface.
                    Interfaces.ViaCep api = retrofit.create(Interfaces.ViaCep.class);
                    Call<Endereco> call = api.endereco(txtcep.getText().toString());

                    call.enqueue(new Callback<Endereco>() {
                        @Override
                        public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                            Log.d("Hue1", "Responsecode: " + response.code());
                            overload.drmed.overload.drmed.models.Endereco end = response.body();
                            txtCidade.setText(end.getLocalidade());
                            txtEndereco.setText(end.getLogradouro());
                            txtEstado.setText(end.getUf());
                            txtBairro.setText(end.getBairro());
                        }

                        @Override
                        public void onFailure(Call<Endereco> call, Throwable t) {
                            Log.d("Hue", "Failed: " + t.getMessage());
                        }
                    });
                }


            }
        });


        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validarCompleto()) {
                    Dialog.callOfTheProgressBar(EditarPacActivity.this);
                    Paciente paciente = new Paciente();

                    paciente.setID_Paci(extras[2]);
                    paciente.setNome_Paci(txtNome.getText().toString());
                    paciente.setNomeSocial_Paci(txtNomeSocial.getText().toString());

                    paciente.setSexo_Paci(txtSexo.getText().toString());

                    paciente.setCPF_Paci(txtCPF.getText().toString());
                    paciente.setRG_Paci(txtRG.getText().toString());
                    paciente.setOrgao_Paci(txtOrgao.getText().toString());

                    //-----VAI DAR MERDA------
                    // AVISA RAPAZ DO IPHONE JSON EDIT TA POSSUIDO!

                    String ano = txtNascimento.getText().toString().substring(6, 10);
                    String mes = txtNascimento.getText().toString().substring(3, 5);
                    String dia = txtNascimento.getText().toString().substring(0, 2);

                    paciente.setDtNasc_Paci(mes + "/" + dia + "/" + ano);
                    // AVISA RAPAZ DO IPHONE JSON EDIT TA POSSUIDO!
                    //-----VAI DAR MERDA------
                    paciente.setEmail_Paci(txtEmail.getText().toString());


                    paciente.setCEP_Paci(txtcep.getText().toString());
                    paciente.setEndereco_Paci(txtEndereco.getText().toString());
                    paciente.setComplemento_Paci(txtComplemento.getText().toString());
                    paciente.setNumero_Paci(txtNumero.getText().toString());
                    paciente.setBairro_Paci(txtBairro.getText().toString());
                    paciente.setCidade_Paci(txtCidade.getText().toString());
                    paciente.setEstado_Paci(txtEstado.getText().toString());

                    paciente.setMae_Paci(txtMae.getText().toString());
                    paciente.setPai_Paci(txtPai.getText().toString());
                    paciente.setTelefone_Paci(txtTelefone.getText().toString());

                    boolean stats = true;


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(StaticFiles.getUrlServer())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();



                    // Create an instance of our GitHub API interface.
                    Interfaces.EditarPaciente api = retrofit.create(Interfaces.EditarPaciente.class);
                    Call<SPaciente> call = api.editarPaciente(paciente.getID_Paci(),
                            paciente.getNome_Paci(),
                            paciente.getNomeSocial_Paci(),
                            paciente.getDtNasc_Paci(),
                            paciente.getSexo_Paci(),
                            paciente.getCPF_Paci(),
                            paciente.getRG_Paci(),
                            paciente.getOrgao_Paci(),
                            paciente.getEmail_Paci(),
                            paciente.getCEP_Paci(),
                            paciente.getEndereco_Paci(),
                            paciente.getNumero_Paci(),
                            paciente.getComplemento_Paci(),
                            paciente.getBairro_Paci(),
                            paciente.getCidade_Paci(),
                            paciente.getEstado_Paci(),
                            paciente.getMae_Paci(),
                            paciente.getPai_Paci(),
                            paciente.getTelefone_Paci());

                    call.enqueue(new Callback<SPaciente>() {
                        @Override
                        public void onResponse(Call<SPaciente> call, Response<SPaciente> response) {
                            Log.d("Hue1", "Responsecode: " + response.code());
                            Log.d("Hue2", "Responsecode: " + response.message());
                            Log.d("Hue3", "Responsecode: " + response.body().isSuccess());
                            Log.d("Hue3", "Responsecode: " + response.body().getMessage());

                            Dialog.dismissOfTheProgressBar();

                            CharSequence text = "Dados alterados com sucesso!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getBaseContext(), text, duration);
                            toast.show();
                        }

                        @Override
                        public void onFailure(Call<SPaciente> call, Throwable t) {
                            Log.d("Hue", "Failed: " + t.getMessage());
                            Dialog.dismissOfTheProgressBar();
                            Dialog.callOfTheDialog("Erro", "Algo deu errado com a conexão tente novamente.", act);
                        }
                    });
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

    private boolean validarCompleto() {

        if (txtNumero.getText().toString().length() == 0) {
            txtNumero.setError("É necessário ter um número");
            txtNumero.requestFocus();
            return false;
        } else {
            txtNumero.setError(null);
        }

        if (txtTelefone.getText().toString().length() < 12) {
            txtTelefone.setError("É necessário um telefone.");
            txtTelefone.requestFocus();
            return false;
        } else {
            txtTelefone.setError(null);
        }

        if (txtNome.getText().toString().length() < 7) {
            txtNome.setError("Digite seu nome completo");
            txtNome.requestFocus();
            return false;
        } else {
            txtNome.setError(null);
        }

        if (txtcep.getText().toString().length() < 8) {
            txtcep.setError("Digite seu CEP.");
            txtcep.requestFocus();
            return false;
        } else {
            txtcep.setError(null);
        }

        if (txtEndereco.getText().toString().length() < 3) {
            txtEndereco.setError("Digite seu endereço.");
            txtEndereco.requestFocus();
            return false;
        } else {
            txtEndereco.setError(null);
        }

        if (txtBairro.getText().toString().length() < 3) {
            txtBairro.setError("Digite seu bairro.");
            txtBairro.requestFocus();
            return false;
        } else {
            txtBairro.setError(null);
        }

        if (txtCidade.getText().toString().length() < 3) {
            txtCidade.setError("Digite sua cidade.");
            txtCidade.requestFocus();
            return false;
        } else {
            txtCidade.setError(null);
        }


        if (txtEstado.getText().toString().length()!= 2) {
            txtEstado.setError("Digite a sigla de seu estado.");
            txtEstado.requestFocus();
            return false;
        } else {
            txtEstado.setError(null);
        }

        if (!Testes.validarCpf(txtCPF.getText().toString())) {
            txtCPF.setError("Não é um CPF válido.");
            txtCPF.requestFocus();
            return false;
        } else {
            txtCPF.setError(null);
        }
        if (!Testes.isValidDate(txtNascimento.getText().toString())) {
            txtNascimento.setError("Não é uma data válida.");
            txtNascimento.requestFocus();
            return false;
        } else {
            txtNascimento.setError(null);
        }


        return true;
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        txtNascimento.setText(sdf.format(myCalendar.getTime()));
        txtCPF.requestFocus();
    }


}
