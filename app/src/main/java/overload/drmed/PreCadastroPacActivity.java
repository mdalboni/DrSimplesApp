package overload.drmed;

import android.graphics.Color;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import overload.drmed.overload.drmed.mask.Mask;
import overload.drmed.overload.drmed.models.APIEmail;
import overload.drmed.overload.drmed.models.StaticFiles;
import overload.drmed.overload.drmed.models.Success;
import overload.drmed.overload.drmed.utils.Dialog;
import overload.drmed.overload.drmed.utils.Testes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PreCadastroPacActivity extends AppCompatActivity {

    TextInputLayout cpf;
    TextInputLayout email;
    Button btnCadastrar;

    EditText txtcpf;
    EditText txtemail;

    TextView lbcpf;
    TextView lbemail;

    String _cpf;
    String _email;

    boolean cpfok = false;
    boolean emailok = false;

    private TextWatcher cpfMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_cadastro_pac);

        cpf = (TextInputLayout) findViewById(R.id.cpf);
        email = (TextInputLayout) findViewById(R.id.email);

        lbcpf = (TextView) findViewById(R.id.lbCpfOk);
        lbemail = (TextView) findViewById(R.id.lbEmailOk);


        txtcpf = cpf.getEditText();
        txtemail = email.getEditText();

        cpfMask = Mask.insert("###.###.###-##", txtcpf);
        txtcpf.addTextChangedListener(cpfMask);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testarEMAIL();
                testarCPF();
            }
        });
    }

    private void testarCPF() {
        if (txtcpf.getText().toString().length() > 12) {
            String valor = txtcpf.getText().toString();
            if (Testes.validarCpf(valor)) {

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
                            lbcpf.setText("Seu CPF já se encontra cadastrado no sistema.");
                            lbcpf.setTextColor(Color.RED);
                            cpfok = false;
                        } else {
                            lbcpf.setText("Seu CPF não se já se encontra cadastrado no sistema.");
                            lbcpf.setTextColor(Color.BLUE);
                            cpfok = true;
                        }

                    }

                    @Override
                    public void onFailure(Call<Success> call, Throwable t) {
                        Log.d("Hue", "Failed: " + t.getMessage());
                    }
                });

            } else {
                lbcpf.setText("Esse não é um CPF valido.");
                lbcpf.setTextColor(Color.RED);
                cpfok = false;
            }
        } else {
            lbcpf.setText("Esse não é um CPF valido.");
            lbcpf.setTextColor(Color.RED);
            cpfok = false;
        }
    }

    private void testarEMAIL() {
        final String valor = txtemail.getText().toString();
        if (Testes.isValidEmail(valor)) {
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
                        lbemail.setText("Seu e-mail já se encontra cadastrado no sistema.");
                        lbemail.setTextColor(Color.RED);
                        emailok = false;
                        System.out.println("Falso--");
                        System.out.println("Testado" + valor);
                        System.out.println(response.message());

                    } else {
                        lbemail.setText("Seu e-mail não se encontra cadastrado no sistema.");
                        lbemail.setTextColor(Color.BLUE);
                        emailok = true;
                        emailok = true;
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


        } else {
            lbemail.setText("Esse não é um e-mail valido.");
            lbemail.setTextColor(Color.RED);
            emailok = false;
        }


    }

}
