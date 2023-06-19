package com.adryell.uniung.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.adryell.uniung.R;
import com.adryell.uniung.fragment.FeedFragment;
import com.adryell.uniung.fragment.PerfilFragment;
import com.adryell.uniung.fragment.PesquisaFragment;
import com.adryell.uniung.fragment.PostagemFragment;
import com.adryell.uniung.helper.ConfiguracaoFirebase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configurar a toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("UniUng");
        setSupportActionBar( toolbar );

        // Configuracoes de objetos
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        habilitarnavegacao(bottomNavigationView);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.viewPage, new FeedFragment()).commit();

    }

    private void habilitarnavegacao(BottomNavigationView view){

        // Configurando Bottom Naviogation
        FragmentManager fragmentManager = getSupportFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



        bottomNavigationView = findViewById(R.id.bottom_navigation);




        //Use NavigationBarView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener) instead.
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuHome:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.viewPage, new FeedFragment())
                                .commit();
                        return true;
                    case R.id.menuSearch:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.viewPage, new PesquisaFragment())
                                .commit();
                        return true;
                    case R.id.menuPhotos:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.viewPage, new PostagemFragment())
                                .commit();
                        return true;
                    case R.id.menuUser:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.viewPage, new PerfilFragment())
                                .commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_sair:
                deslogarUsuario();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){
        try {
            autenticacao.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}