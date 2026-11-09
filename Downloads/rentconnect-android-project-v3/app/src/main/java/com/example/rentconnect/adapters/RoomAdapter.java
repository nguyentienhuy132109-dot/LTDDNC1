package com.example.rentconnect.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentconnect.R;
import com.example.rentconnect.activities.RoomDetailActivity;
import com.example.rentconnect.models.Room;
import java.util.List;
import java.util.Locale;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private final Context context;
    private final List<Room> roomList;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvTitle.setText(room.getTitle());
        holder.tvAddress.setText(room.getAddressCity() + " - " + room.getAddressDetail());
        holder.tvPrice.setText(String.format(Locale.getDefault(), "%,.0f VNĐ/tháng", room.getPrice()));
        holder.tvAreaCap.setText(String.format(Locale.getDefault(), "%.1f m² | Tối đa %d người", room.getArea(), room.getCapacity()));
        holder.tvStatusTag.setText(room.getStatus().equals("AVAILABLE") ? "Còn trống" : "Đã thuê");

        // Click handler on the entire item or status tag -> Open Room Detail Activity
        View.OnClickListener openDetail = v -> {
            Intent intent = new Intent(context, RoomDetailActivity.class);
            intent.putExtra("ROOM_ID", room.getId());
            context.startActivity(intent);
        };

        holder.itemView.setOnClickListener(openDetail);
        holder.tvStatusTag.setOnClickListener(openDetail);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAddress, tvPrice, tvAreaCap, tvStatusTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRoomTitle);
            tvAddress = itemView.findViewById(R.id.tvRoomAddress);
            tvPrice = itemView.findViewById(R.id.tvRoomPrice);
            tvAreaCap = itemView.findViewById(R.id.tvRoomAreaCap);
            tvStatusTag = itemView.findViewById(R.id.tvStatusTag);
        }
    }
}
