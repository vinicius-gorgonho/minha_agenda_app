package model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Pessoa.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PessoaDao pessoaDao();

}
