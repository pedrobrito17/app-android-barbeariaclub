package com.pbtec.app.barbeariaclub.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.threds.ThreadAtualizarSenha;

public class MudarSenha extends DialogFragment {
    private final String MY_PREF = "PreferênciasApp";
    private EditText nova_senha;
    private EditText conf_nova_senha;
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.content_mudar_senha,null);
        dialog.setView(view);

        nova_senha = (EditText) view.findViewById(R.id.mudar_senha);
        conf_nova_senha = (EditText)view.findViewById(R.id.conf_mudar_senha);

        dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String n1 = nova_senha.getText().toString();
                String n2 = conf_nova_senha.getText().toString();
                String email = getContext().getSharedPreferences(MY_PREF, 0).getString("email_cliente", "");
                if (n1.length() >= 6 && n1.equals(n2)) {
                    ThreadAtualizarSenha threadAtualizarSenha =
                            new ThreadAtualizarSenha(getContext(), email, n1);
                    threadAtualizarSenha.execute();
                } else if(!n1.equals(n2)) {
                    Toast.makeText(getContext(),"Senha não confere", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"Senha inválida", Toast.LENGTH_LONG).show();
                }

            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
        return dialog.create();
    }


}
