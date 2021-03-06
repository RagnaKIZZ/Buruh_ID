package ahmedt.buruhid.ui.home.howtouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import ahmedt.buruhid.R;


public class HowToPagerAdapter extends PagerAdapter {
    Context context;
    List<HowToItem> list;

    public HowToPagerAdapter(Context context, List<HowToItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content_howtouse, null);

        PhotoView imgIntro = view.findViewById(R.id.img_intro);
        TextView txtTitle = view.findViewById(R.id.txt_title_intro);
        TextView txtDesc = view.findViewById(R.id.txt_desc_intro);

        Glide.with(context)
                .load(list.get(position).getPhoto())
                .apply(new RequestOptions().override(1000,1200))
                .into(imgIntro);

        txtTitle.setText(list.get(position).getTitle());
        txtDesc.setText(list.get(position).getDescription());

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
