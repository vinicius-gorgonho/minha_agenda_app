package model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PessoaDao {

    @Query("SELECT * FROM pessoa where codigo = :codigo")
    Pessoa getPorCodigo(int codigo);
    @Query("SELECT * FROM pessoa")
    List<Pessoa> getTodos();
    @Insert
    void insertTodos(Pessoa... pessoas);

    @Update
    void update(Pessoa pessoa);
    @Delete
    void delete(Pessoa pessoa);
}
