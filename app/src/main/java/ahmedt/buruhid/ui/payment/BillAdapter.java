package ahmedt.buruhid.ui.payment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.payment.modelPayment.DataItem;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DataItem> list = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    public BillAdapter(Context context, ArrayList<DataItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<DataItem> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
         final DataItem item = getItem(position);
         ViewHolder genericViewHolder = (ViewHolder) holder;
            String type = "";
            String time = item.getCreateDate();
            String status = "";
            int color = 0;
            double price = Double.parseDouble(item.getNominal());
            Locale locale = new Locale("in", "ID");
            NumberFormat form = NumberFormat.getCurrencyInstance(locale);

            if (item.getStatusPembayaran().matches("0")){
                status = "Waiting for payment..";
                color = Color.GRAY;
            }else if (item.getStatusPembayaran().matches("1")){
                status = "Rejected! Please send clear proof of payment!";
                color = Color.RED;
            }else if (item.getStatusPembayaran().matches("2")){
                status = "Accepted to workers";
                color = Color.GREEN;
            }else{
                status = "error!";
                color = Color.RED;
            }
            if (item.getAnggota().matches("1")){
                type = "Individu worker";
            }else{
                type = "Team worker : "+item.getAnggota()+" people";
            }


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                Date date = format.parse(time);
                SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                String realTime = format1.format(date);
                genericViewHolder.txtDate.setText(realTime);
            }catch(Exception e){
                Log.d("ASD", "onBindViewHolder: "+e.getMessage());
            }
            genericViewHolder.txtStatus.setTextColor(color);
            genericViewHolder.txtStatus.setText(status);
            genericViewHolder.txtType.setText(type);
            genericViewHolder.txtPrice.setText(form.format(price));
            genericViewHolder.txtWorkU.setText("Work until");
            genericViewHolder.txtCode.setText(item.getCodePembayaran());


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private DataItem getItem(int position){
        return list.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, DataItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtType, txtDate, txtPrice, txtStatus, txtWorkU, txtCode;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.txtCode = (TextView) itemView.findViewById(R.id.txt_code_payment);
            this.txtStatus = (TextView) itemView.findViewById(R.id.txt_status_payment);
            this.txtWorkU = (TextView) itemView.findViewById(R.id.txt_workuntil_payment);
            this.txtType = (TextView) itemView.findViewById(R.id.txt_type_payment);
            this.txtDate = (TextView) itemView.findViewById(R.id.txt_date_payment);
            this.txtPrice = (TextView) itemView.findViewById(R.id.txt_price_payment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }
    }
}
