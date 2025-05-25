package com.vinicius;

import static com.vinicius.MainActivity.db;
import static com.vinicius.R.id.btnExcluir;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import adapters.PessoaAdapter;
import model.Pessoa;
import model.PessoaDao;

public class ListaActivity extends AppCompatActivity {

    ListView listView;
    PessoaAdapter adapter;
    ArrayList<Pessoa> listaPessoas = new ArrayList<>();
    PessoaDao pessoaDao;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        pessoaDao = db.pessoaDao();
        listView = findViewById(R.id.listView);

        adapter = new PessoaAdapter(getApplicationContext(), listaPessoas, this);
        listView.setAdapter(adapter);

        Thread t = new Thread() {
            public void run() {
                listaPessoas.addAll(pessoaDao.getTodos());
            }
        };
        t.start();
    }

    protected void onResume(){
        super.onResume();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("============================", ""+listaPessoas.size());
                handler.post(new Runnable() {
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}