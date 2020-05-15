package ahmedt.buruhid.ui.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import ahmedt.buruhid.R;

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HistoryOrderItem> list = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    public HistoryOrderAdapter(Context context, ArrayList<HistoryOrderItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<HistoryOrderItem> list){
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
        if (holder instanceof ViewHolder){
         final HistoryOrderItem item = getItem(position);
         ViewHolder genericViewHolder = (ViewHolder) holder;

         genericViewHolder.txtType.setText(item.getType());
         genericViewHolder.txtDesc.setText(item.getDesc());
         genericViewHolder.txtDate.setText(item.getDate());
         genericViewHolder.txtPrice.setText(item.getPrice());

            Glide.with(context)
                    .load(item.getPhoto())
                    .apply(new RequestOptions().override(70,70))
                    .into(genericViewHolder.imgHistoryOrder);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private HistoryOrderItem getItem(int position){
        return list.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, HistoryOrderItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtType, txtDesc, txtDate, txtPrice;
        private ImageView imgHistoryOrder;
        private RatingBar ratingBar;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.txt_title_history_order);
            this.txtDesc = (TextView) itemView.findViewById(R.id.txt_desc_history_order);
            this.txtDate = (TextView) itemView.findViewById(R.id.txt_date_history_order);
            this.txtPrice = (TextView) itemView.findViewById(R.id.txt_price_history_order);
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
