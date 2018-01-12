package com.pbtec.app.barbeariaclub.threds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.comunicadorwebservice.ClienteWS;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;

/**
 * Created by root on 18/08/16.
 */
public class ThreadAtualizarPerfil extends AsyncTask<Cliente,Void,String>{
    private ProgressDialog load;
    private String email_antigo;
    private Context context;
    private final String MY_PREF = "PreferênciasApp";
    private Cliente cliente;
    private FragmentListenner mListener;

    public ThreadAtualizarPerfil(Context context, String email_antigo){
        this.context = context;
        this.email_antigo = email_antigo;
        if (context instanceof FragmentListenner) {
            mListener = (FragmentListenner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " deve implementar FragmentListenner");
        }
    }

    @Override
    protected void onPreExecute() {
        load = ProgressDialog.show(context,"","Aguarde...");
    }

    @Override
    protected String doInBackground(Cliente... clientes) {
        this.cliente = clientes[0];
        return new ClienteWS().atualizarCliente(clientes[0],this.email_antigo);
    }

    @Override
    protected void onPostExecute(String s) {
        load.dismiss();
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
        if(s.equals("Cadastro atualizado")){
            SharedPreferences pref = context.getSharedPreferences(MY_PREF, 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("nome_cliente",cliente.getNome());
            editor.putString("email_cliente", cliente.getEmail());
            editor.putInt("ddd_cliente", cliente.getDdd());
            editor.putString("telefone_cliente", cliente.getTelefone());
            editor.commit();
        }
        mListener.verificarRetorno(s);
    }
}
