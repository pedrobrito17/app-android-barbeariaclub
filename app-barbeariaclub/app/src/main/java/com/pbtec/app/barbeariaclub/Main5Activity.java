package com.pbtec.app.barbeariaclub;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.arrayadapter.AdapterRecycleView;
import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.interfaces.AgendaListenner;
import com.pbtec.app.barbeariaclub.threds.ThreadAgendaFuncionario;

import org.w3c.dom.Text;

import java.util.List;

public class Main5Activity extends AppCompatActivity implements AgendaListenner{
    private final String MY_PREF = "PreferênciasApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main5, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sair) {
            SharedPreferences pref = getSharedPreferences(MY_PREF,0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("logadoFuncionario",false);
            editor.commit();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void popularListView(List<Atendimento> lista) {
        if (lista == null) {
            TextView textView = new TextView(this);

            Log.i("PopularView", "nulo***************************");

            textView.setText("Hoje você não possui atendimento agendado");
            textView.setTextSize(20);
            textView.setTextColor(Color.parseColor("#ffffff"));
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(0, 5, 0, 0);
            textView.setLayoutParams(p);

            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relative5);
            relativeLayout.addView(textView);
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
