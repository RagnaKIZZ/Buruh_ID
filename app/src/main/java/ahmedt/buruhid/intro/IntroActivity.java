package ahmedt.buruhid.intro;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ahmedt.buruhid.R;
import ahmedt.buruhid.login.LoginActivity;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private IntroPagerAdapter introPagerAdapter;
    private TabLayout tabLayout;
    private CardView cdNext, cdStart;
    private TextView txtSkip;
    private int positionTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        final List<ScreenItem> list = new ArrayList<>();
        list.add(new ScreenItem(getString(R.string.buruh_id), getString(R.string.desc_intro_3), R.drawable.load_screen_3));
        list.add(new ScreenItem(getString(R.string.intro_title_1), getString(R.string.desc_intro_1), R.drawable.load_screen));
        list.add(new ScreenItem(getString(R.string.intro_title_2), getString(R.string.desc_intro_2), R.drawable.load_screen_2));


        viewPager = findViewById(R.id.view_pager_intro);
        tabLayout = findViewById(R.id.tab_view_intro);
        cdNext = findViewById(R.id.cd_next_intro);
        cdStart = findViewById(R.id.cd_getstart);
        txtSkip = findViewById(R.id.txt_skip_intro);
        introPagerAdapter = new IntroPagerAdapter(this, list);
        viewPager.setAdapter(introPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        cdNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionTab = viewPager.getCurrentItem();
                if (positionTab < list.size()) {
                    positionTab++;
                    viewPager.setCurrentItem(positionTab);
                }

                //when reach to the last page
                if (positionTab == list.size() - 1) {
                    loadEnd();
                }
            }
        });

        cdStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                finish();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == list.size() - 1) {
                    loadEnd();
                } else {
                    loadStart();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private void expand(CardView mLinearLayout) {
        mLinearLayout.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mLinearLayout.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight(), mLinearLayout);
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, final CardView mLinearLayout) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    private void collapse(final CardView mLinearLayout) {
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator animator = slideAnimator(finalHeight, 0, mLinearLayout);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void loadStart() {
        collapse(cdStart);
        txtSkip.setVisibility(View.VISIBLE);
        cdNext.setVisibility(View.VISIBLE);
    }

    private void loadEnd() {
        expand(cdStart);
        txtSkip.setVisibility(View.GONE);
        cdNext.setVisibility(View.GONE);
    }
}
