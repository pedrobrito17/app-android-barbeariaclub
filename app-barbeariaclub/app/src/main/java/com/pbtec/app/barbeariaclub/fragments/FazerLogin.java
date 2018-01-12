package com.pbtec.app.barbeariaclub.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;
import com.pbtec.app.barbeariaclub.threds.ThreadLogin;


public class FazerLogin extends Fragment{
    private View view;
    private TextView email, senha;
    private Button entrar;
    private FragmentListenner mListener; //interface que recebe a Classe para comandar
    private Context context;
    private Cliente cliente;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
        view = inflater.inflate(R.layout.fragment_fazer_login, container, false);

        email = (TextView)view.findViewById(R.id.login_email);
        senha = (TextView)view.findViewById(R.id.login_senha);
        entrar = (Button)view.findViewById(R.id.entrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mListener.verificarInternet()){
                    return;
                }else if(email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Digite seu e-mail e senha", Toast.LENGTH_SHORT).show();
                }else{
                    String email_cliente = email.getText().toString();
                    String senha_cliente = senha.getText().toString();

                    ThreadLogin thread = new ThreadLogin(getContext());
                    thread.execute(email_cliente,senha_cliente);
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
