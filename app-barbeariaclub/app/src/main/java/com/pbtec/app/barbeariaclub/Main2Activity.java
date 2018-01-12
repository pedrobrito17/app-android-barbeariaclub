package com.pbtec.app.barbeariaclub;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.arrayadapter.AdapterRecycleView;
import com.pbtec.app.barbeariaclub.arrayadapter.ArrayAdapterAtendimento;
import com.pbtec.app.barbeariaclub.dialogs.ListaHoraDisponivel;
import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import com.pbtec.app.barbeariaclub.fragments.Agenda;
import com.pbtec.app.barbeariaclub.fragments.AgendarHorario;
import com.pbtec.app.barbeariaclub.fragments.HomeLogged;
import com.pbtec.app.barbeariaclub.fragments.Perfil;
import com.pbtec.app.barbeariaclub.fragments.TelaConstrucao;
import com.pbtec.app.barbeariaclub.interfaces.AgendaListenner;
import com.pbtec.app.barbeariaclub.interfaces.DataHoraListenner;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;
import com.pbtec.app.barbeariaclub.telafuncionario.MainActivityFuncionario;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentListenner,DataHoraListenner, AgendaListenner {

    private NavigationView navigationView;
    private int item_selecionado;
    private AlertDialog dialog;
    private final String MY_PREF = "PreferênciasApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //a key logado recebe o valor verdadeiro, pois o cliente está logado
        //e esta Activity será encerrada
        SharedPreferences pref = getSharedPreferences(MY_PREF,0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("logado",true);
        editor.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        TextView bem_vindo = (TextView)headerview.findViewById(R.id.bem_vindo);
        Cliente cliente = new Cliente();
        cliente.setNome(getSharedPreferences(MY_PREF, 0).getString("nome_cliente", ""));
        bem_vindo.setText(cliente.getPrimeiroNome()+", "+"bem vindo a BarbeariaClub");

        if(savedInstanceState==null){ //a activity é aberta com o fragment HomeLogout
            if(item_selecionado>-1)navigationView.getMenu().getItem(item_selecionado).setChecked(false);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frame2, new HomeLogged());
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        //limpa a pilha e retorna para o primeiro fragment iniciado
        FragmentManager fm = getSupportFragmentManager();
        int i = fm.getBackStackEntryCount(); //retorna a quantidade de fragments na pilha
        while(i>0){
            fm.popBackStack();
            i--;
        }
        //limpa a seleção do navigation drawer
        for(int j = 0 ; j<navigationView.getMenu().size() ; j++){
            navigationView.getMenu().getItem(j).setChecked(false);
        }
        for(int k = 0 ; k < navigationView.getMenu().getItem(0).getSubMenu().size() ; k ++){
            navigationView.getMenu().getItem(0).getSubMenu().getItem(k).setChecked(false);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.perfil) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new Perfil(this));
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.ag_horario) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new AgendarHorario(this));
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.agenda){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new Agenda(this));
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.a_barbearia){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new TelaConstrucao());
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.horario_func){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new TelaConstrucao());
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.precos){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new TelaConstrucao());
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.profissionais){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new TelaConstrucao());
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.promocao){
            /*FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new TelaConstrucao());
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();*/
            Intent it = new Intent(this, MainActivityFuncionario.class);
            startActivity(it);

        } else if (id == R.id.sair){
            SharedPreferences pref = getSharedPreferences(MY_PREF,0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("logado",false);
            editor.commit();
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void chamarFragment(int i) {
        if(i==1) {
            if (item_selecionado > -1)
                navigationView.getMenu().getItem(item_selecionado).setChecked(false);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new AgendarHorario(this));
            ft.addToBackStack(Integer.toString(0)); //adiciona a pilha de fragment o fragment HomeLogged a primeira posição
            ft.commit();
        }else if(i==2) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new HomeLogged());
            ft.addToBackStack(Integer.toString(0));
            ft.commit();
        }
    }

    @Override
    public boolean verificarInternet() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alerta");
            builder.setMessage("Verique sua conexão com a internet");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            });
            dialog = builder.create();
            dialog.show();
            conectado = false;
        }
        return conectado;
    }

    @Override
    public void verificarRetorno(String s) {
        if(s.equals("Senha alterada com sucesso")){
            chamarFragment(2);
        }else if(s.equals("Cadastro atualizado")){
            chamarFragment(2);
        }
    }


    @Override
    public void hora_selecionada(String hora) {
        Button select_hor = (Button)findViewById(R.id.selecionar_hora);
        select_hor.setText(hora);
    }

    @Override
    public void data_selecionada(String data) {
        Button select_date = (Button)findViewById(R.id.selecionar_data);
        DateFormat df = new SimpleDateFormat("E, dd MMM yyyy");
        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = new Date(df2.parse(data).getTime());
            select_date.setText(df.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this,"Sistema indisponível",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void inserirHorariosDisponiveis(List<String> horarios_indisponivies) {
        List<String> lista_horarios = new ArrayList<String>();
        lista_horarios.add("08 : 00");
        lista_horarios.add("08 : 30");
        lista_horarios.add("09 : 00");
        lista_horarios.add("09 : 30");
        for(int i = 10 ; i < 20 ; i++){
            lista_horarios.add(i+" : 00");
            lista_horarios.add(i+" : 30");
        }
        for(String horario : horarios_indisponivies){
            lista_horarios.remove(horario);
        }
        if(lista_horarios.isEmpty()){
            Toast.makeText(this, "Desculpe! Não há horários disponíveis", Toast.LENGTH_SHORT).show();
        }else {
            DialogFragment dialogFragment = new ListaHoraDisponivel(lista_horarios);
            dialogFragment.show(getFragmentManager(), "horarios");
        }
    }

    @Override
    public void popularListView(List<Atendimento> lista) {
        if(lista==null){
            Toast.makeText(this,"Desculpe! Não foi possível acessar sua agenda",Toast.LENGTH_LONG).show();
        }else {
            try {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_agenda);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);

                RecyclerView.Adapter adapter = new AdapterRecycleView(this, lista);
                recyclerView.setAdapter(adapter);
            }catch(NullPointerException e){
                Log.i("Erro no recyclerView: ", e.toString());
            }




            /*ArrayAdapterAtendimento adapter = new ArrayAdapterAtendimento(Main2Activity.this, -1, lista);
            ListView listView = (ListView) findViewById(R.id.listView_agenda);
            //estava dando erro sem explicação certa: java.lang.NullPointerException:.
            //Isso acontecia quando retornava e havia aberto muitas páginas
            try {
                listView.setAdapter(adapter);
            } catch(Exception e){
                Log.i("Exceção no listview: ", e.toString());
            }*/
        }
    }
}
