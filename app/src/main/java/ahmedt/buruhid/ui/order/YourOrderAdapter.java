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

public class YourOrderAdapter extends RecyclerView.Adapter<YourOrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<YourOrderItem> list = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    public YourOrderAdapter(Context context, ArrayList<YourOrderItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<YourOrderItem> list){
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
        if (holder instanceof ViewHolder){
         final YourOrderItem item = getItem(position);
         ViewHolder genericViewHolder = (ViewHolder) holder;

         genericViewHolder.txtName.setText(item.getName());
         genericViewHolder.txtType.setText(item.getType());
         genericViewHolder.txtDesc.setText(item.getDesc());
         genericViewHolder.txtStatus.setText(item.getStatus());
         genericViewHolder.txtPrice.setText(item.getPrice());

            Glide.with(context)
                    .load(item.getPhoto())
                    .apply(new RequestOptions().override(100,100))
                    .into(genericViewHolder.imgYourOrder);

            genericViewHolder.ratingBar.setRating(item.getRating());
            genericViewHolder.txtRating.setText(String.valueOf(item.getRating()));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private YourOrderItem getItem(int position){
        return list.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, YourOrderItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtType, txtDesc, txtStatus, txtPrice, txtRating;
        private ImageView imgYourOrder;
        private RatingBar ratingBar;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.txtName = (TextView) itemView.findViewById(R.id.txt_nama_your_order);
            this.txtType = (TextView) itemView.findViewById(R.id.txt_jenis_your_order);
            this.txtDesc = (TextView) itemView.findViewById(R.id.txt_desc_your_order);
            this.txtStatus = (TextView) itemView.findViewById(R.id.txt_status_your_order);
            this.txtPrice = (TextView) itemView.findViewById(R.id.txt_price_your_order);
            this.imgYourOrder = (ImageView) itemView.findViewById(R.id.img_your_order);
            this.ratingBar = (RatingBar) itemView.findViewById(R.id.rating_your_order);
            this.txtRating = (TextView) itemView.findViewById(R.id.txt_rating_your_order);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }
    }
}
