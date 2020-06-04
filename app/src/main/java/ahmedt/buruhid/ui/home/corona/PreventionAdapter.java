package ahmedt.buruhid.ui.home.corona;

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
import ahmedt.buruhid.ui.home.corona.modelprevention.PreventionModel;

public class PreventionAdapter extends RecyclerView.Adapter<PreventionAdapter.ViewHolder> {
    private static final String TAG = "WhatsNewAdapter";
    private Context context;
    private ArrayList<PreventionModel> list;

    public PreventionAdapter(Context context, ArrayList<PreventionModel> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<PreventionModel> modeList){
        this.list = modeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prevention, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (holder instanceof ViewHolder){

            final PreventionModel item = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txtDesc.setText(item.getDesc());
            genericViewHolder.imgConten.setImageResource(item.getImg());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private PreventionModel getItem(int position){ return list.get(position); }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtDesc;
        private ImageView imgConten;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.txtDesc = (TextView) itemView.findViewById(R.id.txt_preventation);
            this.imgConten = (ImageView) itemView.findViewById(R.id.img_prevention);
        }
    }
}
