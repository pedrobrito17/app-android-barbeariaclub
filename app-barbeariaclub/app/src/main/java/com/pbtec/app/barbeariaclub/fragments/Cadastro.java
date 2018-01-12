package com.pbtec.app.barbeariaclub.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;
import com.pbtec.app.barbeariaclub.threds.ThreadCadastro;


public class Cadastro extends Fragment {
    private FragmentListenner mListener;
    private Cliente cliente;
    private EditText nome,email,ddd,telefone,senha,conf_senha;
    private Button cadastrar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListenner) {
            mListener = (FragmentListenner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " deve implementar FragmentListenner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro, container, false);

        nome = (EditText)view.findViewById(R.id.nome);
        email = (EditText)view.findViewById(R.id.email);
        ddd = (EditText)view.findViewById(R.id.ddd);
        telefone = (EditText)view.findViewById(R.id.telefone);
        senha = (EditText)view.findViewById(R.id.senha);
        conf_senha = (EditText)view.findViewById(R.id.conf_senha);

        Button cadastrar = (Button)view.findViewById(R.id.cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mListener.verificarInternet()){
                    return;
                }

                if (senha.getText().toString().length() >= 6 && senha.getText().toString().equals
                        (conf_senha.getText().toString())) {

                    if (!nome.getText().toString().isEmpty() || !email.getText().toString().isEmpty()
                            || !telefone.getText().toString().isEmpty()) {
                        cliente = new Cliente();
                        cliente.setDdd(Integer.parseInt(ddd.getText().toString()));
                        cliente.setNome(nome.getText().toString());
                        cliente.setEmail(email.getText().toString());
                        cliente.setTelefone(telefone.getText().toString());
                        cliente.setSenha(senha.getText().toString());

                        ThreadCadastro thread = new ThreadCadastro(getContext());
                        thread.execute(cliente);
                    } else {
                        Toast.makeText(view.getContext(),"Preencha todos os campos",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(view.getContext(), "A senha deve ter pelo menos 6 caracteres.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
