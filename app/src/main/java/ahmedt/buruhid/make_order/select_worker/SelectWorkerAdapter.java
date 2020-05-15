package ahmedt.buruhid.make_order.select_worker;

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
import ahmedt.buruhid.make_order.select_worker.SelectWorkerItem;

public class SelectWorkerAdapter extends RecyclerView.Adapter<SelectWorkerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SelectWorkerItem> list = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    public SelectWorkerAdapter(Context context, ArrayList<SelectWorkerItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<SelectWorkerItem> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectWorkerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_worker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectWorkerAdapter.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
         final SelectWorkerItem item = getItem(position);
         ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.ratingBar.setRating((float) item.getRating());
            genericViewHolder.txtName.setText(item.getName());
            genericViewHolder.txtAddress.setText(item.getAddress());
            genericViewHolder.txtType.setText(item.getType());
            genericViewHolder.txtRating.setText("("+String.valueOf(item.getRating()+")"));

            Glide.with(context)
                    .load(item.getImg())
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

    private SelectWorkerItem getItem(int position){
        return list.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, SelectWorkerItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtType, txtName, txtAddress, txtRating;
        private ImageView imgHistoryOrder;
        private RatingBar ratingBar;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.txt_type_of_worker);
            this.txtName = (TextView) itemView.findViewById(R.id.txt_name_worker);
            this.txtAddress = (TextView) itemView.findViewById(R.id.txt_address_worker);
            this.imgHistoryOrder = (ImageView) itemView.findViewById(R.id.img_selected_worker);
            this.ratingBar = (RatingBar) itemView.findViewById(R.id.rating_select_worker);
            this.txtRating = (TextView) itemView.findViewById(R.id.txt_rating_selected_worker);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }
    }
}
