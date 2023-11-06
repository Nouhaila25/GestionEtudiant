package ma.ensa.volley.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ma.ensa.volley.Filiere;
import ma.ensa.volley.R;

public class FiliereAdapter extends RecyclerView.Adapter<FiliereAdapter.ViewHolder> {
    private List<Filiere> filieres;

    public FiliereAdapter(List<Filiere> filieres) {
        this.filieres = filieres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filiere, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Filiere filiere = filieres.get(position);
        holder.codeTextView.setText(filiere.getCode());
        holder.libelleTextView.setText(filiere.getLibelle());
    }

    @Override
    public int getItemCount() {
        return filieres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView codeTextView;
        TextView libelleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            codeTextView = itemView.findViewById(R.id.codeTextView);
            libelleTextView = itemView.findViewById(R.id.libelleTextView);
        }
    }
}
