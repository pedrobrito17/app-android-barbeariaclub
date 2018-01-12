package com.pbtec.app.barbeariaclub.threds;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.comunicadorwebservice.AtendimentoWS;
import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;

/**
 * Created by root on 23/08/16.
 */
public class ThreadInserirAtendimento extends AsyncTask<Atendimento,Void,Boolean>{
    private ProgressDialog load;
    private Context context;
    private FragmentListenner mListenner;

    public ThreadInserirAtendimento(Context context){
        this.context = context;
        if(context instanceof FragmentListenner){
            mListenner = (FragmentListenner) context;
        }else{
            throw new RuntimeException("Activity precisa implementar interface FragmentListenner");
        }
    }

    @Override
    protected void onPreExecute() {
        load = ProgressDialog.show(context,"Aguarde","Agendando seu atendimento...");
    }

    @Override
    protected Boolean doInBackground(Atendimento... atendimentos) {
        Log.i("insertAtendimento", atendimentos[0].getEmail_cliente());
        Log.i("insertAtendimento", atendimentos[0].getEmail_funcionario());
        Log.i("insertAtendimento", atendimentos[0].getData_atendimento());
        Log.i("insertAtendimento", atendimentos[0].getHora_atendimento());
        Log.i("insertAtendimento", atendimentos[0].getDesc_servico());
        return new AtendimentoWS().insertAtendimento(atendimentos[0]);
    }

    @Override
    protected void onPostExecute(Boolean a) {
        if (a==true){
            load.dismiss();
            mListenner.chamarFragment(2);
            Toast.makeText(context, "Seu atendimento foi agendado", Toast.LENGTH_SHORT).show();
        }else{
            load.dismiss();
            Toast.makeText(context, "Desculpe! Não foi possível agendar",Toast.LENGTH_LONG).show();
        }
    }
}
