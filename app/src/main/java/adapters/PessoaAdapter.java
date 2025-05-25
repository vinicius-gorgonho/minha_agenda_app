package adapters;




import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

import com.vinicius.EditarActivity;
import com.vinicius.ListaActivity;
import com.vinicius.MainActivity;
import com.vinicius.R;

import java.io.Serializable;
import java.util.ArrayList;

import model.AppDatabase;
import model.PessoaDao;

public class PessoaAdapter<Pessoa> extends ArrayAdapter<Pessoa> {

    Context mContext;
    private ArrayList<Pessoa> listaPessoas;

    ImageButton btnExcluir;
    ImageButton btnEditar;
    PessoaDao pessoaDao;
    AppDatabase db;

    Activity activity;
    public PessoaAdapter(@NonNull Context context, @NonNull ArrayList<Pessoa> lista, Activity listaActivity) {
        super(context, R.layout.item_lista, lista);
        this.mContext = context;
        this.listaPessoas = lista;
        this.activity = listaActivity;

        Thread t = new Thread(){
            public void run(){
                db = Room.databaseBuilder(context, AppDatabase.class,
                        "contatos").build();
                pessoaDao = db.pessoaDao();
            }
        };
        t.start();
    }

    @Override
    public int getCount() {
        if(listaPessoas != null){
            return listaPessoas.size();
        }
        return 0;
    }


    @Override
    public long getItemId(int i) {
        model.Pessoa pessoa = (model.Pessoa) listaPessoas.get(i);
        return pessoa.getCodigo();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // RECUPERA UMA PESSOA DA LISTA
 model.Pessoa pessoa = (model.Pessoa) listaPessoas.get(i);

 if(view == null) {
     // CRIA o LAYOUT QUE NÓS FIZEMOS NO XML
     LayoutInflater inflater = LayoutInflater.from(getContext());
     // Carrega o XML
     view = inflater.inflate(R.layout.item_lista, viewGroup, false);
// Recupera os campos para preencher
     TextView campoNome = view.findViewById(R.id.txtNome);
     campoNome.setText(pessoa.getNome());
     TextView campoTelefone = view.findViewById(R.id.txtTelefone);
     campoTelefone.setText(pessoa.getTelefone());

     btnEditar = view.findViewById(R.id.btnEditar);
     btnEditar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(getContext(), EditarActivity.class);
             Bundle bundle = new Bundle();
             bundle.putSerializable("pessoa", (Serializable) pessoa);

             intent.putExtras(bundle);
             activity.startActivity(intent);
         }
     });

     btnExcluir = view.findViewById(R.id.btnExcluir);
     btnExcluir.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Log.d("CLICOU", "==========================");
             Thread t = new Thread(){
                 public void run() {
                     try {
                         model.Pessoa pessoa = (model.Pessoa) listaPessoas.get(i);
                         Log.d("Tentando Remover", pessoa.getNome());
                         pessoaDao.delete(pessoa);

                     } catch (Exception e) {
                         e.printStackTrace();
                         Log.d("DEU ERRO AO REMOVER", "ERRO");
                     }
                 }
             };

             AlertDialog.Builder builder = new AlertDialog.Builder(activity);
             builder.setMessage("Deseja Excluir?")
                     .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             t.start();
                             Toast.makeText(getContext(), "Excluído com sucesso", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(getContext(), MainActivity.class);
                             activity.startActivity(intent);
                         }
                     })
                     .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
          Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();

                         }
                     });
             // Create the AlertDialog object and return it.
             builder.create().show();

         }

     });



 }
 return view;
    }
}
