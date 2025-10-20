package it.saimao.maonote;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NoteEntity.class}, version = 1)
public abstract class MaoDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    private static MaoDatabase maoDatabase;
    public static MaoDatabase getInstance(Context context) {
        if (maoDatabase == null) {
            maoDatabase = Room.databaseBuilder(context, MaoDatabase.class, "mao_notes").allowMainThreadQueries().build();
        }
        return maoDatabase;
    }

}
