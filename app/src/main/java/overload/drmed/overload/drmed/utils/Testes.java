package overload.drmed.overload.drmed.utils;

import android.icu.util.Calendar;
import android.support.annotation.IntegerRes;
import android.widget.EditText;

/**
 * Created by Dalbo on 09/04/2017.
 */

public class Testes {

    private static String _cpf;

    private static void removerCaracteres() {
        _cpf = _cpf.replace("-", "");
        _cpf = _cpf.replace(".", "");
    }

    private static boolean verificarSeDigIguais(String cpf) {
        //char primDig = cpf.charAt(0);
        char primDig = '0';
        char[] charCpf = cpf.toCharArray();
        for (char c : charCpf)
            if (c != primDig)
                return false;
        return true;
    }

    private static String calculoComCpf(String cpf) {
        int digGerado = 0;
        int mult = cpf.length() + 1;
        char[] charCpf = cpf.toCharArray();
        for (int i = 0; i < cpf.length(); i++)
            digGerado += (charCpf[i] - 48) * mult--;
        if (digGerado % 11 < 2)
            digGerado = 0;
        else
            digGerado = 11 - digGerado % 11;
        return String.valueOf(digGerado);
    }


    public static boolean validarCpf(String cpf) {
        if (cpf.length() > 9) {
            if (cpf == null) {
                return false;
            } else {
                String cpfGerado = "";
                _cpf = cpf;

                removerCaracteres();

                if (verificarSeDigIguais(_cpf))
                    return false;

                cpfGerado = _cpf.substring(0, 9);
                cpfGerado = cpfGerado.concat(calculoComCpf(cpfGerado));
                cpfGerado = cpfGerado.concat(calculoComCpf(cpfGerado));

                if (!cpfGerado.equals(_cpf))
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    public final static boolean isValidDate(String target) {
        if (target.length() == 10) {
            if (Integer.parseInt(target.substring(0, 2)) < 32) {
                if (Integer.parseInt(target.substring(3, 5)) < 13) {

                    if (Integer.parseInt(target.substring(6, 10)) < 2017) {
                        return true;
                    }
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public final static boolean isValidSenhas(EditText t1, EditText t2) {
        String s1 = t1.getText().toString();
        String s2 = t2.getText().toString();

        if (!s1.equals(s2)) {
            t1.requestFocus();
            t1.setError("Senhas diferentes!");
            return false;
        } else {
            t1.setError(null);
        }

        if (s1.length() < 4) {
            t1.requestFocus();
            t1.setError("Senha muito curta");
            return false;
        } else {
            t1.setError(null);
        }

        if (!s1.matches("[a-zA-Z.0-9]*")) {
            t1.requestFocus();
            t1.setError("Senha possui caracteres invalidos, use somente letras e numero.");
            return false;
        } else {
            t1.setError(null);
        }


        return true;
    }
}
