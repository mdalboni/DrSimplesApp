package overload.drmed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import overload.drmed.overload.drmed.controller.Interfaces;
import overload.drmed.overload.drmed.models.Login;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.models.UsuarioPaciente;
import overload.drmed.overload.drmed.utils.Dialog;
import overload.drmed.overload.drmed.utils.SharedPrefUtil;
import overload.drmed.overload.drmed.utils.Testes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class LoginPActivity extends AppCompatActivity {


    LoginPActivity act;

    TextInputLayout txtEmail2;
    EditText txtEmail;
    TextInputLayout txtSenha2;
    EditText txtSenha;

    Button btnConectar;
    Button btnRegistrar;
    AppCompatCheckBox btnMostrar;
    AppCompatCheckBox btnSalvar;
    SharedPrefUtil shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_p);

        act = this;

        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnMostrar = (AppCompatCheckBox) findViewById(R.id.btnMostrar);
        btnSalvar = (AppCompatCheckBox) findViewById(R.id.btnSalvar);

        txtEmail2 = (TextInputLayout) findViewById(R.id.txtEmail);
        txtEmail = txtEmail2.getEditText();


        shared = new SharedPrefUtil();
        txtEmail.setText(shared.sharedPrefGetString(act, shared.LAST_LOGIN));
        if(!shared.LAST_LOGIN.equals("")) {
            btnSalvar.setChecked(true);
            System.out.println("Pegou o email:" + shared.sharedPrefGetString(act, shared.LAST_LOGIN));
        }



        txtSenha2 = (TextInputLayout) findViewById(R.id.txtSenha);
        txtSenha = txtSenha2.getEditText();

        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                if (TextUtils.isEmpty(txtEmail.getText().toString())) {
                    txtEmail.setError(getString(R.string.error_field_required));
                    cancel = true;
                } else if (!Testes.isValidEmail(txtEmail.getText().toString())) {
                    txtEmail.setError(getString(R.string.error_invalid_email));
                    cancel = true;
                }

                if (TextUtils.isEmpty(txtSenha.getText().toString())) {
                    txtSenha.setError(getString(R.string.error_field_required));
                    cancel = true;
                } else if (!Testes.isPasswordValid(txtSenha.getText().toString()) && !Testes.isValidEmail(txtSenha.getText().toString())) {
                    txtSenha.setError(getString(R.string.error_invalid_password));
                    cancel = true;
                }

                if (!cancel) {


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(StaticFiles.getUrlServer())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Dialog.callOfTheProgressBar(LoginPActivity.this);

                    // Create an instance of our GitHub API interface.
                    UsuarioPaciente usuario = new UsuarioPaciente();
                    usuario.setUsuario_UsuPaci(txtEmail.getText().toString());
                    usuario.setSenha_UsuPaci(txtSenha.getText().toString());

                    Interfaces.UsuarioLogin api = retrofit.create(Interfaces.UsuarioLogin.class);
                    Call<Login> call = api.createUser(usuario);

                    call.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            boolean end = response.body().isSuccess();
                            if (response.body().getUsuario().size() > 0) {
                                Intent intent = new Intent(getBaseContext(), HomeActivity.class);

                                String[] extras = new String[3];
                                extras[0] = response.body().getUsuario().get(0).getEmail_Paci();
                                extras[1] = response.body().getUsuario().get(0).getNome_Paci();
                                extras[2] = response.body().getUsuario().get(0).getID_Paci() + "";

                                intent.putExtra("paciente", extras);

                                if (btnSalvar.isChecked()) {

                                    shared = new SharedPrefUtil();
                                    shared.sharedPrefPutString(act, shared.LAST_LOGIN, txtEmail.getText().toString());

                                } else {

                                }
                                act.startActivity(intent);
                                Dialog.dismissOfTheProgressBar();

                            } else {
                                Dialog.dismissOfTheProgressBar();
                                callOfTheDialog("Erro ao conectar", "Suas credenciais estão erradas ou não existem.");
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            System.out.println(t.getMessage());
                            Dialog.dismissOfTheProgressBar();
                            callOfTheDialog("Erro ao conectar", "Existe algum problema de rede!");
                        }
                    });
                }

                //act.startActivity(new Intent(act, ConsultaActivity.class));
            }

        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // act.startActivity(new Intent(act, CadastroPacActivity.class));
                act.startActivity(new Intent(act, CadastroPacActivity.class));
            }
        });


        btnMostrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnMostrar.isChecked()) {
                    txtSenha.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    txtSenha.setInputType(129);
                }
            }
        });


    }


    private void callOfTheDialog(String titulo, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginPActivity.this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
    }
}
