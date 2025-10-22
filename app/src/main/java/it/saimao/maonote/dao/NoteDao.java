package it.saimao.maonote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.saimao.maonote.entity.NoteEntity;

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

    @Query("SELECT * FROM note_entity WHERE id = :id")
    NoteEntity getNoteById(int id);

}
