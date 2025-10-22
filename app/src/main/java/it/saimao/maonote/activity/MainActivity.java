package it.saimao.maonote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.List;

import it.saimao.maonote.entity.NoteEntity;
import it.saimao.maonote.adapter.NoteAdapter;
import it.saimao.maonote.dao.NoteDao;
import it.saimao.maonote.database.MaoDatabase;
import it.saimao.maonote.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private NoteDao noteDao;
    private NoteAdapter noteAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initDatabase();
        initRecyclerView();
        initActions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDataFromDatabase();
    }

    private void initActions() {
        binding.fabAddNote.setOnClickListener(v -> {
            Intent it = new Intent(this, NoteActivity.class);
            startActivity(it);
        });
    }

    private void initRecyclerView() {
        noteAdapter = new NoteAdapter(this);
        noteAdapter.setOnDeleteNoteListener(note -> {
            // TODO : show confirm dialog
            noteDao.deleteNote(note);
            initDataFromDatabase();
            Toast.makeText(this, "Delete Note Successfully!", Toast.LENGTH_SHORT).show();
        });

        noteAdapter.setOnEditNoteListener(note -> {
            Intent it = new Intent(this, NoteActivity.class);
            it.putExtra("note_id", note.getId());
            startActivity(it);
        });

        binding.rvNotes.setAdapter(noteAdapter);
        binding.rvNotes.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void initDataFromDatabase() {
        List<NoteEntity> notes = noteDao.getAllNotes();
        noteAdapter.submitList(notes);
    }

    private void initDatabase() {
        noteDao = MaoDatabase.getInstance(this).noteDao();
    }
}