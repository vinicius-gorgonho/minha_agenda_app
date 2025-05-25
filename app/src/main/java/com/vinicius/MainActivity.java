package com.vinicius;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import model.AppDatabase;
import model.Pessoa;
import model.PessoaDao;

public class MainActivity extends AppCompatActivity {
//1 - Criar o item ou o botão
    Button btnEntrar;
    Button btnListar;
    EditText campoNome;
    EditText campoTelefone;

    EditText campoEmail;
    static AppDatabase db;
    PessoaDao pessoaDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

 Thread t = new Thread(){
     public void run(){
  db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                 "contatos").build();
 pessoaDao = db.pessoaDao();
     }
 };
 t.start();
  // 2 - Recuperar elementos da TELA
        btnEntrar = findViewById(R.id.btnEntrar);
        btnListar = findViewById(R.id.btnListar);
        campoNome = findViewById(R.id.campoNome);
        campoTelefone = findViewById(R.id.campoTelefone);
        campoEmail = findViewById(R.id.campoEmail);
        // 3 - CRIAR A Acao
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//RECEBE OS VALORES DO CAMPO
String nome = campoNome.getText().toString();
String telefone = campoTelefone.getText().toString();
String email = campoEmail.getText().toString();
// Log.d("MOSTRAR O VALOR", nome);
  //Cria a pessoa para preencher
  Pessoa pessoa = new Pessoa(nome, telefone, email);
  // GRAVAR NO BANCO
Thread t2 = new Thread(){
  public void run(){
      pessoaDao.insertTodos(pessoa);
  }
};
t2.start();

   // 4 - CHAMAR A OUTRA TELA
 Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
 startActivity(intent);
            }
        });

btnListar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
        startActivity(intent);
    }
});
    }
}