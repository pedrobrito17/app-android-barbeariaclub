package com.pbtec.app.barbeariaclub.threds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.Main2Activity;
import com.pbtec.app.barbeariaclub.comunicadorwebservice.ClienteWS;
import com.pbtec.app.barbeariaclub.comunicadorwebservice.FuncionarioWS;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import com.pbtec.app.barbeariaclub.fragments.Cadastro;
import com.pbtec.app.barbeariaclub.fragments.HomeLogged;
import com.pbtec.app.barbeariaclub.fragments.HomeLogout;
import com.pbtec.app.barbeariaclub.interfaces.OnLoginCompletedListener;

/**
 * Created by root on 13/08/16.
 */
public class ThreadLogin extends AsyncTask<String,Void,Cliente>{
    private Context context;
    private ProgressDialog progressDialog;
    private OnLoginCompletedListener onLoginCompletedListener;

    public ThreadLogin(Context context) {
        this.context = context;
        if(context instanceof OnLoginCompletedListener){
            onLoginCompletedListener = (OnLoginCompletedListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " deve implementar onLoginCompletedListener");
        }
    }

    @Override
    protected void onPreExecute() {
        Log.i("ProgressDialog","Começando...");
        progressDialog = ProgressDialog.show(context, "", "Aguarde...");
    }

    @Override
    protected Cliente doInBackground(String... strings) {
        Log.i("Chamando WebService", "método verificarCliente");
        try {
            Cliente cliente =  new ClienteWS().verificarCliente(strings[0], strings[1]);
            if(cliente!=null){
                return cliente;
            }
        }catch(RuntimeException e){
            Log.i("Erro no ThreadLogin",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Cliente cliente) {
        if(cliente==null){
            progressDialog.dismiss();
            Toast.makeText(context,"Desculpe! Senha ou e-mail incorreto.",Toast.LENGTH_LONG).show();
        }else if(cliente.getTelefone().equals("nada")){
            Log.i("ProgressDialog","Finalizando...");
            progressDialog.dismiss();
            onLoginCompletedListener.onLoginCompletedFuncionario(cliente);
        }
        else if(cliente!=null){
            Log.i("ProgressDialog","Finalizando...");
            progressDialog.dismiss();
            onLoginCompletedListener.onLoginCompleted(cliente);
        }
    }

}


