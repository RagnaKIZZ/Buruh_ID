package ahmedt.buruhid.ui.order;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.order.modelHistoryOrder.DataItem;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.UrlServer;

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DataItem> list = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    public HistoryOrderAdapter(Context context, ArrayList<DataItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<DataItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryOrderAdapter.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final DataItem item = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            String time = "";
            String type = "";
            String status = "";
            String waktu = "";
            int color = 0;
            if (item.getAnggota().matches("1")) {
                type = context.getString(R.string.individu_worker);
            } else {
                type = context.getString(R.string.tim_work) + item.getAnggota() + context.getString(R.string.people);
            }

            if (item.getFinishDate() != null) {
                time = item.getFinishDate();
            } else {
                time = item.getOrderDate();
            }

            if (item.getStatusOrder().matches("0")) {
                status = context.getString(R.string.canceled);
                color = Color.RED;
            } else if (item.getStatusOrder().matches("4")) {
                status = context.getString(R.string.fin);
                color = Color.GREEN;
            } else {
                status = "error!";
                color = Color.RED;
            }
            Date date = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = format.parse(time);
            } catch (Exception e) {
                Log.d("ASD", "onBindViewHolder: " + e.getMessage());
            }
            HelperClass.getDate(context, date, waktu, genericViewHolder.txtDate);
            genericViewHolder.txtType.setText(type);
            genericViewHolder.txtDesc.setText(item.getJobdesk());
            genericViewHolder.txtStatus.setText(status);
            genericViewHolder.txtStatus.setTextColor(color);
            genericViewHolder.progressBar.setVisibility(View.GONE);

            if (item.getFoto().isEmpty()) {
                Glide.with(context)
                        .load(R.drawable.blank_profile)
                        .apply(new RequestOptions().override(120, 120))
                        .into(genericViewHolder.imgHistoryOrder);
            } else {
                HelperClass.loadGambar(context, UrlServer.URL_FOTO_TUKANG + item.getFoto(), genericViewHolder.progressBar, genericViewHolder.imgHistoryOrder, R.drawable.blank_profile);

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
        private TextView txtType, txtDesc, txtDate, txtStatus;
        private ImageView imgHistoryOrder;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            this.txtStatus = (TextView) itemView.findViewById(R.id.txt_status_order_history);
            this.txtType = (TextView) itemView.findViewById(R.id.txt_title_history_order);
            this.txtDesc = (TextView) itemView.findViewById(R.id.txt_desc_history_order);
            this.txtDate = (TextView) itemView.findViewById(R.id.txt_date_history_order);
            this.imgHistoryOrder = (ImageView) itemView.findViewById(R.id.img_history_order);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }
    }
}
