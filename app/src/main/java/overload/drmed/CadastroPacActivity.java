package overload.drmed;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ToggleButton;


import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.mask.Mask;
import overload.drmed.overload.drmed.models.Endereco;
import overload.drmed.overload.drmed.models.PacCreate;
import overload.drmed.overload.drmed.models.Paciente;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.models.Success;
import overload.drmed.overload.drmed.utils.Dialog;
import overload.drmed.overload.drmed.utils.Testes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class CadastroPacActivity extends AppCompatActivity {


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
    EditText txtSenha1;
    EditText txtSenha2;

    TextInputLayout nome;
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
    TextInputLayout Senha1;
    TextInputLayout Senha2;

    private TextWatcher cpfMask;
    private TextWatcher nascMask;

    Button btnCadastrar;

    CadastroPacActivity act = this;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pac);

        Dialog.callOfTheDialog("Atenção", "Sabemos que preencher o dados é algo cansativo, mas por favor preencha com calma e corretamente.", this);

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
        Senha1 = (TextInputLayout) findViewById(R.id.senha1);
        Senha2 = (TextInputLayout) findViewById(R.id.senha2);

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
        txtSenha1 = Senha1.getEditText();
        txtSenha2 = Senha2.getEditText();

        TextWatcher telMask = (TextWatcher) Mask.insert("(##)#####-####", txtTelefone);
        txtTelefone.addTextChangedListener(telMask);

        cpfMask = (TextWatcher) Mask.insert("###.###.###-##", txtCPF);
        txtCPF.addTextChangedListener(cpfMask);

        txtCPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!v.hasFocus()) {
                    if (Testes.validarCpf(txtCPF.getText().toString())) {
                        txtCPF.setError(null);
                        testarCPF();
                    } else {
                        txtCPF.setError("Esse CPF não é valido");
                    }
                }
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


        /*txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!v.hasFocus()) {
                    if (Testes.isValidEmail(txtEmail.getText().toString())) {
                        testarEMAIL();
                    } else {
                        txtEmail.requestFocus();
                        txtEmail.setError("Esse não é um e-mail valido");
                    }
                }else{
                    txtEmail.requestFocus();
                }
            }
        });*/




        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog.callOfTheProgressBar(CadastroPacActivity.this);
                if (validarCompleto()) {
                    Paciente paciente = new Paciente();


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
//                    paciente.setDtNasc_Paci(txtNascimento.getText().toString());

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

                    Interfaces.CriarPaciente api = retrofit.create(Interfaces.CriarPaciente.class);
                    Call<PacCreate> call = api.criar(
                            paciente.getNome_Paci(), paciente.getNomeSocial_Paci(), paciente.getDtNasc_Paci(),
                            paciente.getSexo_Paci(), paciente.getCPF_Paci(), paciente.getRG_Paci(), paciente.getOrgao_Paci(),
                            paciente.getEmail_Paci(), paciente.getCEP_Paci(), paciente.getEndereco_Paci(), paciente.getNumero_Paci(), paciente.getComplemento_Paci(),
                            paciente.getBairro_Paci(), paciente.getCidade_Paci(), paciente.getEstado_Paci(), paciente.getMae_Paci(),
                            paciente.getPai_Paci(), paciente.getTelefone_Paci(), txtSenha1.getText().toString());


                    call.enqueue(new Callback<PacCreate>() {
                        @Override
                        public void onResponse(Call<PacCreate> call, Response<PacCreate> response) {
                            Log.d("Response", "Responsecode: " + response.code());
                            Log.d("Response", "Responsecode: " + response.message());
                            Log.d("Response", "Responsecode: " + response.body().getMessage());

                            if (response.body().isSuccess()) {
                                System.out.println("kkk e ae paciente:" + response.body().isSuccess());
                                System.out.println("kkk e ae paciente:" + response.body().getMessage());
                                Dialog.dismissOfTheProgressBar();

                                new AlertDialog.Builder(act)
                                        .setTitle("Cadastro bem sucedido!")
                                        .setMessage("Seus dados foram cadastrados, bem vindo ao Dr.Med!")
                                        .setPositiveButton("Começar a usar!", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                act.startActivity(new Intent(act, LoginPActivity.class));
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            } else {
                                Dialog.callOfTheDialog("Erro", "Confira as informações digitadas.", CadastroPacActivity.this);
                            }
                        }

                        @Override
                        public void onFailure(Call<PacCreate> call, Throwable t) {
                            Dialog.dismissOfTheProgressBar();
                            Log.d("Failure", "Failed: " + t.getMessage());
                            System.out.println(call.request().toString());
                            Dialog.callOfTheDialog("Erro de erre", "Confira sua conexão com a internet e tente novamente.", CadastroPacActivity.this);
                        }
                    });
                }else{
                    Dialog.dismissOfTheProgressBar();
                }

            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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



        if (!txtSenha1.getText().toString().equals(txtSenha2.getText().toString())) {
            txtSenha1.setError("As senhas não estão iguais");
            txtSenha1.requestFocus();
            return false;
        } else {
            txtSenha1.setError(null);
        }
        if (txtSenha1.getText().toString().length() < 4) {
            txtSenha1.setError("A senha é muito curta");
            txtSenha1.requestFocus();
            return false;
        } else {
            txtSenha1.setError(null);
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


    private void testarCPF() {
        String valor = txtCPF.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticFiles.getUrlServer())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Success.CPFtest api = retrofit.create(Success.CPFtest.class);
        Call<Success> call = api.testar(valor);

        call.enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                Log.d("Hue1", "Responsecode: " + response.code());
                if (response.body().isSuccess()) {
                    txtCPF.setText("Seu CPF já se encontra cadastrado no sistema.");
                    txtCPF.requestFocus();
                } else {
                    txtCPF.setError(null);
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                Log.d("Hue", "Failed: " + t.getMessage());
            }
        });
    }

    private void testarEMAIL() {
        final String valor = txtEmail.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticFiles.getUrlServer())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Success.Emailtest api = retrofit.create(Success.Emailtest.class);
        Call<Success> call = api.testar(valor);

        call.enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                Log.d("Hue1", "Responsecode: " + response.code());
                if (response.body().isSuccess()) {
                    txtEmail.setError("Seu e-mail já esta cadastrado!");
                    System.out.println("Falso--");
                    System.out.println("Testado" + valor);
                    System.out.println(response.message());
                    txtEmail.requestFocus();

                } else {
                    txtEmail.setError(null);
                    System.out.println("Verdadeiro--");
                    System.out.println("Testado" + valor);
                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                Log.d("Hue", "Failed: " + t.getMessage());
            }
        });


    }


}

