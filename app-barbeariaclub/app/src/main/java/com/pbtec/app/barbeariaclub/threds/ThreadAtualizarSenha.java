package com.pbtec.app.barbeariaclub.threds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.comunicadorwebservice.ClienteWS;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;

/**
 * Created by root on 18/08/16.
 */
public class ThreadAtualizarSenha extends AsyncTask<Void,Void,String>{
    private Context context;
    private final String MY_PREF = "PreferênciasApp";
    private ProgressDialog load;
    private String email, nova_senha;
    private FragmentListenner mListener;

    public ThreadAtualizarSenha(Context context, String email, String nova_senha){
        this.context = context;
        this.email = email;
        this.nova_senha = nova_senha;
        if (context instanceof FragmentListenner) {
            mListener = (FragmentListenner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " deve implementar FragmentListenner");
        }
    }

    @Override
    protected void onPreExecute() {
        load = ProgressDialog.show(this.context,"","Aguarde...");
    }

    @Override
    protected String doInBackground(Void... voids) {
        return new ClienteWS().atualizarSenha(email,nova_senha);
    }

    @Override
    protected void onPostExecute(String s) {
        load.dismiss();
        Toast.makeText(this.context,s,Toast.LENGTH_LONG).show();
        if(s.equals("Senha alterada com sucesso")){
            SharedPreferences pref = context.getSharedPreferences(MY_PREF, 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("senha_cliente",this.nova_senha);
            editor.commit();
        }
        mListener.verificarRetorno(s);
    }
}
