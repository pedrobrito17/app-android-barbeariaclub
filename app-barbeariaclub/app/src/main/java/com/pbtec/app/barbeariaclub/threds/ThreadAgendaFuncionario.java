package com.pbtec.app.barbeariaclub.threds;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.comunicadorwebservice.AtendimentoWS;
import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.interfaces.AgendaListenner;

import java.util.List;

/**
 * Created by pedrobrito on 29/03/17.
 */

public class ThreadAgendaFuncionario extends AsyncTask<String,Void,List<Atendimento>> {
    private ProgressDialog load;
    private Context context;
    private AgendaListenner mListenner;

    public ThreadAgendaFuncionario(Context context){
        this.context = context;
        if(context instanceof AgendaListenner){
            mListenner = (AgendaListenner) context;
        }else{
            throw new RuntimeException("Activity precisa implementar Interface AgendaListenner");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        load = ProgressDialog.show(context,"","Carregando...");
    }

    @Override
    protected List<Atendimento> doInBackground(String... strings) {
        Log.i("AtendimentoWS",strings[0]);
        return new AtendimentoWS().selectAtendimento(2,"nulo",strings[0],"nulo");
    }

    @Override
    protected void onPostExecute(List<Atendimento> atendimentos) {
        if(atendimentos.isEmpty()){
            load.dismiss();
            mListenner.popularListView(null);
        }else{
            load.dismiss();
            if(!atendimentos.isEmpty()){Log.i("AgendaFuncionario", atendimentos.get(0).getEmail_cliente());}
            mListenner.popularListView(atendimentos);
        }
    }
}
