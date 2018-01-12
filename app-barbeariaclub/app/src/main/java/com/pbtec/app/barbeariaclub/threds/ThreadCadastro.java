package com.pbtec.app.barbeariaclub.threds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.comunicadorwebservice.ClienteWS;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import com.pbtec.app.barbeariaclub.fragments.HomeLogout;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;

/**
 * Created by root on 14/08/16.
 */
public class ThreadCadastro extends AsyncTask<Cliente,Void,String>{
    private ProgressDialog load;
    private Context context;
    private FragmentListenner mListener;

    public ThreadCadastro(Context context){
        this.context = context;
        if (context instanceof FragmentListenner) {
            mListener = (FragmentListenner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " deve implementar FragmentListenner");
        }
    }

    @Override
    protected void onPreExecute() {
        load = ProgressDialog.show(context,"Cadastrando","Aguarde...");
    }

    @Override
    protected String doInBackground(Cliente... clientes) {
        return new ClienteWS().insertCliente(clientes[0]);
    }

    @Override
    protected void onPostExecute(String resposta) {
        load.dismiss();
        Toast.makeText(context,resposta,Toast.LENGTH_LONG).show();
        mListener.verificarRetorno(resposta);
    }
}
