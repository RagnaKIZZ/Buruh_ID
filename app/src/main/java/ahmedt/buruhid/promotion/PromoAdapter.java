package ahmedt.buruhid.promotion;

import android.content.Context;
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
import ahmedt.buruhid.promotion.modelPromo.DataItem;
import ahmedt.buruhid.utils.HelperClass;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DataItem> list = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    public PromoAdapter(Context context, ArrayList<DataItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<DataItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PromoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoAdapter.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final DataItem item = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            Locale locale = new Locale("in", "ID");
            NumberFormat format1 = NumberFormat.getCurrencyInstance(locale);
            String time = item.getStartDate();
            double isiDisc = 100 * Double.parseDouble(item.getIsiPromo());
            int dicount = (int) isiDisc;
            String disc = " " + String.valueOf(dicount) + "%";
            String endTime = item.getEndDate();
            String kode = item.getKodePromo();
            double min = Double.parseDouble(item.getMinHarga());
            String minTrans = " " + format1.format(min);
            String desc = "";
            String title = item.getNamaPromo();
            Date date = null;

            genericViewHolder.txtTitle.setText(title);
            genericViewHolder.txtDesc.setText("");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = format.parse(time);
            } catch (Exception e) {
                Log.d("ASD", "onBindViewHolder: " + e.getMessage());
            }
            HelperClass.getDate(date, time, genericViewHolder.txtDate);

            SimpleDateFormat time1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date waktu = time1.parse(endTime);
                SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy");
                String realTime = " " + format2.format(waktu);
                desc = "Dapatkan discount" + disc + " dengan kode promo " + kode + ", minimum transaksi sebesar" + minTrans + ", berlaku sampai" + realTime;
                genericViewHolder.txtDesc.setText(desc);
            } catch (Exception e) {
                Log.d("ASD", "onBindViewHolder: " + e.getMessage());
            }

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private DataItem getItem(int position) {
        return list.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, DataItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtDesc, txtDate;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.txtTitle = (TextView) itemView.findViewById(R.id.txt_title_promo);
            this.txtDesc = (TextView) itemView.findViewById(R.id.txt_detail_promo);
            this.txtDate = (TextView) itemView.findViewById(R.id.txt_date_promo);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }
    }
}
