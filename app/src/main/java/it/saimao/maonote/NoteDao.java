package it.saimao.maonote;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void addNote(NoteEntity note);

    @Delete
    void deleteNote(NoteEntity note);

    @Update
    void updateNote(NoteEntity note);

    @Query("SELECT * FROM note_entity;")
    List<NoteEntity> getAllNotes();

}
