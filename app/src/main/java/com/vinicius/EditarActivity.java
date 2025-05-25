package com.vinicius;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import model.Pessoa;
import model.PessoaDao;

public class EditarActivity extends AppCompatActivity {

    EditText campoNome;
    EditText campoTelefone;

    EditText campoEmail;
    Button btnConfirmar;

    PessoaDao pessoaDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);


        pessoaDao = MainActivity.db.pessoaDao();

        Pessoa pessoa = (Pessoa) getIntent().getExtras().getSerializable("pessoa");

        campoNome = findViewById(R.id.campoNome);
        campoTelefone = findViewById(R.id.campoTelefone);
        campoEmail = findViewById(R.id.campoEmail);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        campoNome.setText(pessoa.getNome());
        campoTelefone.setText(pessoa.getTelefone());
        campoEmail.setText(pessoa.getEmail());

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = campoNome.getText().toString();
                String telefone = campoTelefone.getText().toString();
                String email = campoEmail.getText().toString();
                Pessoa pessoaAtualizada = new Pessoa(pessoa.getCodigo(), nome, telefone, email);

                Thread t = new Thread(){
                    public void run(){
                        pessoaDao.update(pessoaAtualizada);
                    }
                };
                t.start();
                Toast.makeText(getApplicationContext(),"Atualizado comsucesso", Toast.LENGTH_SHORT ).show();

  Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
                startActivity(intent);
            }
        });

    }
}