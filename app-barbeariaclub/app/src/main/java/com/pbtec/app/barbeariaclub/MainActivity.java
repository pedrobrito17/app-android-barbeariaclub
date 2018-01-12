package com.pbtec.app.barbeariaclub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import com.pbtec.app.barbeariaclub.fragments.Cadastro;
import com.pbtec.app.barbeariaclub.fragments.FazerLogin;
import com.pbtec.app.barbeariaclub.fragments.HomeLogout;
import com.pbtec.app.barbeariaclub.fragments.TelaConstrucao;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;
import com.pbtec.app.barbeariaclub.interfaces.OnLoginCompletedListener;
import com.pbtec.app.barbeariaclub.telafuncionario.MainActivityFuncionario;
import com.pbtec.app.barbeariaclub.threds.ThreadLogin;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentListenner,OnLoginCompletedListener {

    private TextView fazer_login;
    private NavigationView navigationView;
    private int item_selecionado;
    private Cliente cliente;
    private AlertDialog dialog;
    private final String MY_PREF = "PreferênciasApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getSharedPreferences(MY_PREF,0);
        if(pref.getBoolean("logado",false)){ //testa se esta logado
            Intent it = new Intent(this,Main2Activity.class);
            startActivity(it);
            finish();
        }
        if(pref.getBoolean("logadoFuncionario",false)){ //testa se esta logado
            Intent it = new Intent(this,Main5Activity.class);
            startActivity(it);
            finish();
        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        fazer_login = (TextView)headerview.findViewById(R.id.text_fazer_login);
        RelativeLayout header = (RelativeLayout) headerview.findViewById(R.id.header_linear);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers(); //fecha o navigation drawer antes do fragment ser aberto
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, new FazerLogin());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        if(savedInstanceState==null){ //a activity é aberta com o fragment HomeLogout
            if(item_selecionado>-1)navigationView.getMenu().getItem(item_selecionado).setChecked(false);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frame, new HomeLogout());
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.a_barbearia){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame, new TelaConstrucao());
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.horario_func){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame, new TelaConstrucao());
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.precos){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame, new TelaConstrucao());
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.profissionais){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame, new TelaConstrucao());
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();
        } else if (id == R.id.promocao) {
            /*FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame2, new TelaConstrucao());
            ft.addToBackStack(Integer.toString(0)); //faz o botão voltar voltar para o fragment HomeLogged
            ft.commit();*/
            Intent it = new Intent(this, MainActivityFuncionario.class);
            startActivity(it);
        }  else if (id == R.id.sair) {
            finish();
        }

        navigationView.getMenu().getItem(item_selecionado).setChecked(true); //coloca a seleção no item clicado
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override //método da interface FragmentListenner
    public void chamarFragment(int i) {
        if(i==1){
            if(item_selecionado>-1)navigationView.getMenu().getItem(item_selecionado).setChecked(false); //retira a seleção do item que havia sido clicado
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame, new Cadastro());
            ft.addToBackStack(Integer.toString(0)); //adiciona a pilha de fragment o fragment HomeLogged a primeira posição
            ft.commit();
        }else if(i==2){

        }else if(i==3){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame, new HomeLogout());
            ft.commit();
        }
    }

    @Override
    public boolean verificarInternet() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo()!=null) {
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
        if(s.equals("Cadastro realizado com sucesso")){
            chamarFragment(3);
        }
    }


    @Override //método da interface OnLoginCompletedListener
    public void onLoginCompleted(Cliente result) {
        if(result!=null){
            //Grava na preferência os dados do cliente para buscar sempre que necessário
            //mesmo quando o app for fechado
            SharedPreferences pref = getSharedPreferences(MY_PREF,0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("nome_cliente",result.getNome());
            editor.putString("email_cliente", result.getEmail());
            editor.putInt("ddd_cliente", result.getDdd());
            editor.putString("telefone_cliente", result.getTelefone());
            editor.putString("senha_cliente", result.getSenha());
            editor.commit();

            Intent it = new Intent(this, Main2Activity.class);
            startActivity(it);
            finish();
        }
    }

    @Override //método da interface OnLoginCompletedListener
    public void onLoginCompletedFuncionario(Cliente funcionario) {
        if(funcionario!=null){
            //Grava na preferência os dados do funcionario para buscar
            SharedPreferences pref = getSharedPreferences(MY_PREF,0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("email_funcionario", funcionario.getEmail());
            editor.commit();

            Intent it = new Intent(this, Main5Activity.class);
            startActivity(it);
            finish();
        }
    }
}
