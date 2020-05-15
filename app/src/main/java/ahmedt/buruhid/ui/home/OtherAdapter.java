package ahmedt.buruhid.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import ahmedt.buruhid.R;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.ViewHolder> {
    private static final String TAG = "WhatsNewAdapter";
    private Context context;
    private ArrayList<OtherItem> list;

    private OnItemClickListener mItemClickListener;

    public OtherAdapter(Context context, ArrayList<OtherItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<OtherItem> modeList){
        this.list = modeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (holder instanceof ViewHolder){

            final OtherItem item = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            Glide.with(context)
                    .load(item.getPhoto())
                    .apply(new RequestOptions().override(200,300))
                    .into(genericViewHolder.imgConten);

            genericViewHolder.txtTitle.setText(item.getTitle());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private OtherItem getItem(int position){ return list.get(position); }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, OtherItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private ImageView imgConten;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.txtTitle = (TextView) itemView.findViewById(R.id.txt_title_other);
            this.imgConten = (ImageView) itemView.findViewById(R.id.img_other);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mItemClickListener.onItemClick(itemView, getAdapterPosition(), list.get(getAdapterPosition()));
               }
           });
        }
    }
}
