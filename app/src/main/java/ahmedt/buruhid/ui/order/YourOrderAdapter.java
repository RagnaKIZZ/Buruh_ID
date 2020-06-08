package ahmedt.buruhid.ui.order;

import android.content.Context;
import android.graphics.Color;
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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.order.modelOrder.DataItem;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.UrlServer;

public class YourOrderAdapter extends RecyclerView.Adapter<YourOrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DataItem> list = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    public YourOrderAdapter(Context context, ArrayList<DataItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<DataItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public YourOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_your_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YourOrderAdapter.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final DataItem item = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            Locale locale = new Locale("in", "ID");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);

            String type = "";
            String status = "";
            int color = Color.WHITE;

            if (item.getAnggota().matches("1")) {
                type = context.getString(R.string.individu_worker);
            } else {
                type = context.getString(R.string.tim_work) + item.getAnggota() + context.getString(R.string.people);
            }

            if (item.getStatusOrder().matches("1")) {
                status = context.getString(R.string.wait_confirm);
                color = Color.parseColor("#ffd600");
            } else if (item.getStatusOrder().matches("2")) {
                status = context.getString(R.string.order_acc);
                color = Color.GREEN;
            } else if (item.getStatusOrder().matches("3")) {
                status = context.getString(R.string.workinggg);
                color = Color.GREEN;
            } else {
                status = "error!";
                color = Color.RED;
            }
            genericViewHolder.txtCode.setText(context.getString(R.string.orderr_codee) + item.getCodeOrder());
            genericViewHolder.txtName.setText(item.getNama());
            genericViewHolder.txtType.setText(type);
            genericViewHolder.txtDesc.setText(item.getJobdesk());
            genericViewHolder.txtStatus.setText(status);
            genericViewHolder.txtStatus.setTextColor(color);
            genericViewHolder.progressBar.setVisibility(View.GONE);

            if (item.getFoto().isEmpty()) {
                Glide.with(context)
                        .load(R.drawable.blank_profile)
                        .apply(new RequestOptions().override(120, 120))
                        .into(genericViewHolder.imgYourOrder);
            } else {
                HelperClass.loadGambar(context, UrlServer.URL_FOTO_TUKANG + item.getFoto(), genericViewHolder.progressBar, genericViewHolder.imgYourOrder);
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
        private TextView txtName, txtType, txtDesc, txtStatus, txtCode;
        private ImageView imgYourOrder;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            this.txtName = (TextView) itemView.findViewById(R.id.txt_nama_your_order);
            this.txtType = (TextView) itemView.findViewById(R.id.txt_jenis_your_order);
            this.txtDesc = (TextView) itemView.findViewById(R.id.txt_desc_your_order);
            this.txtStatus = (TextView) itemView.findViewById(R.id.txt_status_your_order);
            this.txtCode = (TextView) itemView.findViewById(R.id.txt_code_your_order);
            this.imgYourOrder = (ImageView) itemView.findViewById(R.id.img_your_order);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }
    }
}
