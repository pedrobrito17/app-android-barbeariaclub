package com.pbtec.app.barbeariaclub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.arrayadapter.AdapterRecycleView;
import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.interfaces.AgendaListenner;
import com.pbtec.app.barbeariaclub.threds.ThreadAgenda;
import com.pbtec.app.barbeariaclub.threds.ThreadAgendaFuncionario;

import java.util.List;

public class Main3Activity extends AppCompatActivity implements AgendaListenner{
    private final String MY_PREF = "PreferênciasApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //a key logado recebe o valor verdadeiro, pois o cliente está logado
        //e esta Activity será encerrada
        SharedPreferences pref = getSharedPreferences(MY_PREF,0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("logadoFuncionario",true);
        editor.commit();

        ThreadAgendaFuncionario thread = new ThreadAgendaFuncionario(this);
        thread.execute(getSharedPreferences(MY_PREF, 0).getString("email_funcionario", "nulo"));
        RecyclerView listView = (RecyclerView) findViewById(R.id.recyclerView_agenda);

    }

    @Override
    public void popularListView(List<Atendimento> lista) {
        if (lista == null) {
            Toast.makeText(this, "Desculpe! Não foi possível acessar sua agenda", Toast.LENGTH_LONG).show();
        } else {
            try {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_agenda);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);

                RecyclerView.Adapter adapter = new AdapterRecycleView(this, lista);
                recyclerView.setAdapter(adapter);
            } catch (NullPointerException e) {
                Log.i("Erro no recyclerView: ", e.toString());
            }

        }
    }
}
