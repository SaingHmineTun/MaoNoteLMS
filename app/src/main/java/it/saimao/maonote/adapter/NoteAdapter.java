package it.saimao.maonote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import it.saimao.maonote.databinding.ItemNoteBinding;
import it.saimao.maonote.entity.NoteEntity;

public class NoteAdapter extends ListAdapter<NoteEntity, NoteAdapter.NoteViewHolder> {

    public interface OnDeleteNoteListener {
        void onDeleteNote(NoteEntity noteEntity);
    }
    public interface OnEditNoteListener {
        void onEditNote(NoteEntity noteEntity);
    }
    private OnDeleteNoteListener onDeleteNoteListener;
    private OnEditNoteListener onEditNoteListener;

    public void setOnDeleteNoteListener(OnDeleteNoteListener onDeleteNoteListener) {
        this.onDeleteNoteListener = onDeleteNoteListener;
    }

    public void setOnEditNoteListener(OnEditNoteListener onEditNoteListener) {
        this.onEditNoteListener = onEditNoteListener;
    }

    private static final DiffUtil.ItemCallback<NoteEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<NoteEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final Context context;

    public NoteAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(LayoutInflater.from(context), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteEntity note = getCurrentList().get(position);
        holder.bind(note);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private ItemNoteBinding binding;

        public NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NoteEntity noteEntity) {
            binding.tvTitle.setText(noteEntity.getTitle());
            binding.tvContent.setText(noteEntity.getContent());
            binding.getRoot().setOnClickListener(v -> onEditNoteListener.onEditNote(noteEntity));
            binding.getRoot().setOnLongClickListener(v -> {
                onDeleteNoteListener.onDeleteNote(noteEntity);
                return true;
            });
        }

    }
}
