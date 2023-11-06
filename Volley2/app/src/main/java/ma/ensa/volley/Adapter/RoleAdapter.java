package ma.ensa.volley.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.ensa.volley.Role;
import ma.ensa.volley.R;

public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.ViewHolder> {
    private List<Role> roles;

    public RoleAdapter(List<Role> roles) {
        this.roles = roles;
    }

    @NonNull
    @Override
    public RoleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_role, parent, false);
        return new RoleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoleAdapter.ViewHolder holder, int position) {
        Role role = roles.get(position);
        holder.nameTextView.setText(role.getName());

    }

    @Override
    public int getItemCount() {
        return roles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);

        }
    }
}