package ahmedt.buruhid.notification;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ahmedt.buruhid.R;
import ahmedt.buruhid.notification.modelNotification.DataItem;
import ahmedt.buruhid.utils.HelperClass;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DataItem> list = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    public NotifAdapter(Context context, ArrayList<DataItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<DataItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotifAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notif, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final DataItem item = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            String time = item.getCreateDate();
            String desc = item.getMessage();
            String title = item.getTitle();
            Date date = null;
            genericViewHolder.txtTitle.setText(title);
            genericViewHolder.txtDesc.setText(desc);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = format.parse(time);
            } catch (Exception e) {
                Log.d("ASD", "onBindViewHolder: " + e.getMessage());
            }
            HelperClass.getDate(date, time, genericViewHolder.txtDate);
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

            this.txtTitle = (TextView) itemView.findViewById(R.id.txt_title_notif);
            this.txtDesc = (TextView) itemView.findViewById(R.id.txt_detail_notif);
            this.txtDate = (TextView) itemView.findViewById(R.id.txt_date_notif);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }
    }
}
