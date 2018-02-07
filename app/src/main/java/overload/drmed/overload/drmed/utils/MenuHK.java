package overload.drmed.overload.drmed.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import overload.drmed.ConsultaActivity;
import overload.drmed.ConveniosActivity;
import overload.drmed.EditarPacActivity;
import overload.drmed.ExamesActivity;
import overload.drmed.HomeActivity;
import overload.drmed.LoginPActivity;
import overload.drmed.SenhaActivity;

/**
 * Created by Dalbo on 13/04/2017.
 */

public class MenuHK {

    public static Intent acessarMenu(String item, AppCompatActivity act,String[] paciente) {

        Intent intent;

        if (item.equals("Home")) {
            intent = new Intent(act, HomeActivity.class);
            intent.putExtra("paciente",paciente);
            return intent;
        } else if (item.equals("Consulta")) {
            intent = new Intent(act, ConsultaActivity.class);
            intent.putExtra("paciente",paciente);
            return intent;
        } else if (item.equals("Exames")) {
            //intent = new Intent(act, HomeActivity.class);
            intent = new Intent(act, ExamesActivity.class);
            intent.putExtra("paciente",paciente);
            return intent;
        } else if (item.equals("Liberar Acesso")) {
            intent = new Intent(act, HomeActivity.class);
            intent.putExtra("paciente",paciente);
            return intent;
        } else if (item.equals("Alterar Dados")) {
            intent = new Intent(act, EditarPacActivity.class);
            intent.putExtra("paciente",paciente);
            return intent;
        } else if (item.equals("Convênios")) {
            intent = new Intent(act, ConveniosActivity.class);
            intent.putExtra("paciente",paciente);
            return intent;
        } else if (item.equals("Sair")) {
            intent = new Intent(act, LoginPActivity.class);
            return intent;
        }else if (item.equals("Alterar Senha")) {
            intent = new Intent(act, SenhaActivity.class);
            intent.putExtra("paciente",paciente);
            return intent;
        }
        return null;
    }
}
