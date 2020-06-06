package ahmedt.buruhid.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ahmedt.buruhid.R;

public class WhatsNewAdapter extends RecyclerView.Adapter<WhatsNewAdapter.ViewHolder> {
    private static final String TAG = "WhatsNewAdapter";
    private Context context;
    private ArrayList<WhatsNewItem> list;

    private OnItemClickListener mItemClickListener;

    public WhatsNewAdapter(Context context, ArrayList<WhatsNewItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<WhatsNewItem> modeList){
        this.list = modeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_whats_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (holder instanceof ViewHolder){

            final WhatsNewItem item = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.imgConten.setImageResource(item.getPhoto());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private WhatsNewItem getItem(int position){ return list.get(position); }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, WhatsNewItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtDesc;
        private ImageView imgConten;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.imgConten = (ImageView) itemView.findViewById(R.id.img_whats_new);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mItemClickListener.onItemClick(itemView, getAdapterPosition(), list.get(getAdapterPosition()));
               }
           });
        }
    }
}
