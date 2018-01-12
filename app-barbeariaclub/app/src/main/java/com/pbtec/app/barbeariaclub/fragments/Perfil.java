package com.pbtec.app.barbeariaclub.fragments;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.dialogs.MudarSenha;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;
import com.pbtec.app.barbeariaclub.threds.ThreadAtualizarPerfil;

public class Perfil extends Fragment {
    private final String MY_PREF = "PreferênciasApp";
    private AlertDialog dialog;
    private EditText nome,email,ddd,telefone,nova_senha,conf_nova_senha;
    private FragmentListenner fragmentListenner;

    public Perfil(Context context){
        if(context instanceof FragmentListenner){
            fragmentListenner = (FragmentListenner) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " deve implementar FragmentListenner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);


        nome = (EditText)view.findViewById(R.id.nome);
        nome.setText(getContext().getSharedPreferences(MY_PREF,0).getString("nome_cliente", ""));
        email = (EditText)view.findViewById(R.id.email);
        email.setText(getContext().getSharedPreferences(MY_PREF,0).getString("email_cliente", ""));
        ddd = (EditText)view.findViewById(R.id.ddd);
        ddd.setText(String.valueOf(getContext().getSharedPreferences(MY_PREF,0).getInt("ddd_cliente", 0)));
        telefone = (EditText)view.findViewById(R.id.telefone);
        telefone.setText(getContext().getSharedPreferences(MY_PREF,0).getString("telefone_cliente",""));

        Button alterar_senha = (Button)view.findViewById(R.id.alterar_senha);
        alterar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragmentListenner.verificarInternet()) {
                    DialogFragment dialog_mudar_senha = new MudarSenha();
                    dialog_mudar_senha.show(getActivity().getFragmentManager(), "MudarSenha");
                }
            }
        });

        Button confirmar = (Button)view.findViewById(R.id.confirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragmentListenner.verificarInternet()) {
                    Cliente cliente = new Cliente();
                    cliente.setDdd(Integer.parseInt(ddd.getText().toString()));
                    cliente.setNome(nome.getText().toString());
                    cliente.setEmail(email.getText().toString());
                    cliente.setTelefone(telefone.getText().toString());
                    ThreadAtualizarPerfil threadAtualizarPerfil = new ThreadAtualizarPerfil(getContext(),
                            getContext().getSharedPreferences(MY_PREF, 0).getString("email_cliente", ""));
                    threadAtualizarPerfil.execute(cliente);
                }
            }
        });

        return view;
    }
}
