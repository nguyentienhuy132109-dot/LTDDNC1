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
import com.example.rentconnect.models.Booking;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private final Context context;
    private final List<Booking> bookingList;
    private final boolean isLandlord;
    private final RentConnectDbHelper dbHelper;

    public BookingAdapter(Context context, List<Booking> bookingList, boolean isLandlord) {
        this.context = context;
        this.bookingList = bookingList;
        this.isLandlord = isLandlord;
        this.dbHelper = new RentConnectDbHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.tvRoomTitle.setText("Phòng: " + booking.getRoomTitle());
        holder.tvDateTime.setText("Thời gian: " + booking.getBookingDate() + " (" + booking.getTimeSlot() + ")");
        holder.tvPhoneNotes.setText("SĐT: " + booking.getTenantPhone() + " | Ghi chú: " + booking.getNotes());

        String statusText;
        if ("CONFIRMED".equalsIgnoreCase(booking.getStatus())) {
            statusText = "Đã duyệt";
            holder.tvStatus.setTextColor(0xFF2E7D32); // Xanh lá
        } else if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            statusText = "Đã hủy";
            holder.tvStatus.setTextColor(0xFFC62828); // Đỏ
        } else {
            statusText = "Chờ duyệt";
            holder.tvStatus.setTextColor(0xFFEF6C00); // Cam
        }
        holder.tvStatus.setText("Trạng thái: " + statusText);

        // Hiển thị nút Duyệt/Từ chối nếu là Chủ trọ và trạng thái là PENDING
        if (isLandlord && "PENDING".equalsIgnoreCase(booking.getStatus())) {
            holder.btnApprove.setVisibility(View.VISIBLE);
            holder.btnReject.setVisibility(View.VISIBLE);

            holder.btnApprove.setOnClickListener(v -> {
                dbHelper.updateBookingStatus(booking.getId(), "CONFIRMED");
                Toast.makeText(context, "Đã xác nhận lịch hẹn!", Toast.LENGTH_SHORT).show();
                booking.setStatus("CONFIRMED");
                notifyItemChanged(position);
            });

            holder.btnReject.setOnClickListener(v -> {
                dbHelper.updateBookingStatus(booking.getId(), "CANCELLED");
                Toast.makeText(context, "Đã từ chối lịch hẹn!", Toast.LENGTH_SHORT).show();
                booking.setStatus("CANCELLED");
                notifyItemChanged(position);
            });
        } else {
            holder.btnApprove.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomTitle, tvDateTime, tvPhoneNotes, tvStatus;
        Button btnApprove, btnReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomTitle = itemView.findViewById(R.id.tvBookingRoomTitle);
            tvDateTime = itemView.findViewById(R.id.tvBookingDateTime);
            tvPhoneNotes = itemView.findViewById(R.id.tvBookingPhoneNotes);
            tvStatus = itemView.findViewById(R.id.tvBookingStatus);
            btnApprove = itemView.findViewById(R.id.btnApproveBooking);
            btnReject = itemView.findViewById(R.id.btnRejectBooking);
        }
    }
}