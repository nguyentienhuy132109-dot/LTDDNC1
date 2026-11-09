package com.example.rentconnect.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentconnect.R;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Ticket;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final Context context;
    private final List<Ticket> ticketList;
    private final boolean isLandlord;
    private final RentConnectDbHelper dbHelper;

    public TicketAdapter(Context context, List<Ticket> ticketList, boolean isLandlord) {
        this.context = context;
        this.ticketList = ticketList;
        this.isLandlord = isLandlord;
        this.dbHelper = new RentConnectDbHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket t = ticketList.get(position);
        holder.tvCategoryPriority.setText("[" + t.getCategory() + "] - Ưu tiên: " + t.getPriority());
        holder.tvDescription.setText(t.getDescription());
        holder.tvStatus.setText("Trạng thái: " + t.getStatus());

        if (isLandlord && !t.getStatus().equals("RESOLVED")) {
            holder.btnUpdateStatus.setVisibility(View.VISIBLE);
            holder.btnUpdateStatus.setText(t.getStatus().equals("PENDING") ? "Xử lý ngay" : "Hoàn tất & Đóng");

            holder.btnUpdateStatus.setOnClickListener(v -> {
                String nextStatus = t.getStatus().equals("PENDING") ? "IN_PROGRESS" : "RESOLVED";
                dbHelper.updateTicketStatus(t.getId(), nextStatus);
                Toast.makeText(context, "Cập nhật trạng thái sự cố: " + nextStatus, Toast.LENGTH_SHORT).show();
                holder.tvStatus.setText("Trạng thái: " + nextStatus);
                if (nextStatus.equals("RESOLVED")) {
                    holder.btnUpdateStatus.setVisibility(View.GONE);
                } else {
                    holder.btnUpdateStatus.setText("Hoàn tất & Đóng");
                }
            });
        } else {
            holder.btnUpdateStatus.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryPriority, tvDescription, tvStatus;
        Button btnUpdateStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryPriority = itemView.findViewById(R.id.tvTicketCategoryPriority);
            tvDescription = itemView.findViewById(R.id.tvTicketDescription);
            tvStatus = itemView.findViewById(R.id.tvTicketStatus);
            btnUpdateStatus = itemView.findViewById(R.id.btnUpdateTicketStatus);
        }
    }
}
