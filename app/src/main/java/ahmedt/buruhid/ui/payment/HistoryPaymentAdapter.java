package ahmedt.buruhid.ui.payment;

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

public class HistoryPaymentAdapter extends RecyclerView.Adapter<HistoryPaymentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HistoryPaymentItem> list = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    public HistoryPaymentAdapter(Context context, ArrayList<HistoryPaymentItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<HistoryPaymentItem> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryPaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryPaymentAdapter.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
         final HistoryPaymentItem item = getItem(position);
         ViewHolder genericViewHolder = (ViewHolder) holder;

         genericViewHolder.txtType.setText(item.getType());
         genericViewHolder.txtDate.setText(item.getDate());
         genericViewHolder.txtPrice.setText("Rp. "+item.getPrice());


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private HistoryPaymentItem getItem(int position){
        return list.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, HistoryPaymentItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtType, txtDate, txtPrice;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

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
