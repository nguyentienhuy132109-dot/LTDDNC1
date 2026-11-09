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
import com.example.rentconnect.models.Invoice;
import java.util.List;
import java.util.Locale;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private final Context context;
    private final List<Invoice> invoiceList;
    private final boolean isLandlord;
    private final RentConnectDbHelper dbHelper;

    public InvoiceAdapter(Context context, List<Invoice> invoiceList, boolean isLandlord) {
        this.context = context;
        this.invoiceList = invoiceList;
        this.isLandlord = isLandlord;
        this.dbHelper = new RentConnectDbHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_invoice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Invoice inv = invoiceList.get(position);
        holder.tvRoomMonth.setText(inv.getRoomTitle() + " - Tháng " + inv.getMonthYear());
        holder.tvDetails.setText(String.format(Locale.getDefault(),
                "Tiền nhà: %,.0fđ | Điện: %,.0fđ | Nước: %,.0fđ | Phí: %,.0fđ",
                inv.getRoomFee(), inv.getElecFee(), inv.getWaterFee(), inv.getServiceFee()));
        holder.tvTotal.setText(String.format(Locale.getDefault(), "Tổng tiền: %,.0f VNĐ", inv.getTotalAmount()));
        holder.tvStatus.setText("Trạng thái: " + (inv.getStatus().equals("PAID") ? "Đã thanh toán" : "Chưa thanh toán"));

        if (isLandlord && inv.getStatus().equals("UNPAID")) {
            holder.btnConfirmPaid.setVisibility(View.VISIBLE);
            holder.btnConfirmPaid.setOnClickListener(v -> {
                dbHelper.updateInvoiceStatus(inv.getId(), "PAID");
                Toast.makeText(context, "Đã xác nhận thanh toán!", Toast.LENGTH_SHORT).show();
                holder.tvStatus.setText("Trạng thái: Đã thanh toán");
                holder.btnConfirmPaid.setVisibility(View.GONE);
            });
        } else {
            holder.btnConfirmPaid.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomMonth, tvDetails, tvTotal, tvStatus;
        Button btnConfirmPaid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomMonth = itemView.findViewById(R.id.tvInvoiceRoomMonth);
            tvDetails = itemView.findViewById(R.id.tvInvoiceDetails);
            tvTotal = itemView.findViewById(R.id.tvInvoiceTotal);
            tvStatus = itemView.findViewById(R.id.tvInvoiceStatus);
            btnConfirmPaid = itemView.findViewById(R.id.btnConfirmPaid);
        }
    }
}
