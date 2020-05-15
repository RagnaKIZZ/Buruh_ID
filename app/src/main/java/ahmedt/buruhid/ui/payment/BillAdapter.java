package ahmedt.buruhid.ui.payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ahmedt.buruhid.R;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BillItem> list = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    public BillAdapter(Context context, ArrayList<BillItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<BillItem> list){
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
         final BillItem item = getItem(position);
         ViewHolder genericViewHolder = (ViewHolder) holder;

         genericViewHolder.txtType.setText(item.getType());
         genericViewHolder.txtDate.setText(item.getDays());
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

    private BillItem getItem(int position){
        return list.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, BillItem model);
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
