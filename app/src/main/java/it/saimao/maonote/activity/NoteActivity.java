package it.saimao.maonote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.saimao.maonote.R;
import it.saimao.maonote.dao.NoteDao;
import it.saimao.maonote.database.MaoDatabase;
import it.saimao.maonote.databinding.ActivityNoteBinding;
import it.saimao.maonote.entity.NoteEntity;

/*
Update Mode : Note has content
Add Mode : Note is null
 */

public class NoteActivity extends AppCompatActivity {
    private ActivityNoteBinding binding;
    private NoteDao noteDao;
    private NoteEntity note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initDatabase();
        initActions();
        loadDataFromIntent();
    }

    private void loadDataFromIntent() {
        Intent it = getIntent();
        int noteId = it.getIntExtra("note_id", -1);
        if (noteId != -1) {
            // Edit
            note = noteDao.getNoteById(noteId);
            binding.etTitle.setText(note.getTitle());
            binding.etContent.setText(note.getContent());
            binding.btSave.setText("Update");
            binding.btClear.setText("Delete");
        }
    }

    private void initDatabase() {
        noteDao = MaoDatabase.getInstance(this).noteDao();
    }

    private void clearNote() {
        binding.etTitle.getText().clear();
        binding.etContent.setText("");
    }

    private void deleteNote() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    noteDao.deleteNote(note);
                    Toast.makeText(this, "Delete Note Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
    private void initActions() {
        binding.btClear.setOnClickListener(v -> {
            if (note == null) {
                clearNote();
            } else {
                deleteNote();
            }
        });

        binding.btSave.setOnClickListener(v -> {
            if (note == null) {
                addNote();
            } else {
                updateNote();
            }
        });
    }


    private void updateNote() {
        String title = binding.etTitle.getText().toString();
        String content = binding.etContent.getText().toString();
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill in title and content to save a note", Toast.LENGTH_SHORT).show();
            return;
        }
        note.setTitle(title);
        note.setContent(content);
        noteDao.updateNote(note);
        finish();
        Toast.makeText(this, "Note saved successfully!", Toast.LENGTH_SHORT).show();
    }

    private void addNote() {
        String title = binding.etTitle.getText().toString();
        String content = binding.etContent.getText().toString();
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill in title and content to save a note", Toast.LENGTH_SHORT).show();
            return;
        }
        noteDao.addNote(new NoteEntity(title, content));
        finish();
        Toast.makeText(this, "Note saved successfully!", Toast.LENGTH_SHORT).show();
    }


}