package dreamguys.in.co.gigs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import dreamguys.in.co.gigs.Model.POSTLanguageModel;
import dreamguys.in.co.gigs.utils.AppConstants;
import dreamguys.in.co.gigs.utils.SessionHandler;

/**
 * Created by Prasad on 10/24/2017.
 */

public class Introscreen extends BaseActivity {
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private MyViewPagerAdapter myViewPagerAdapter;
    public POSTLanguageModel.Intro_screen introScreen = new POSTLanguageModel().new Intro_screen();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SessionHandler.getInstance().getBoolean(Introscreen.this, AppConstants.IS_WELCOME_FIRST_TIME)) {
            launchHomeScreen();
            finish();
        }
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);

        layouts = new int[]{
                R.layout.welcome_intro_one,
                R.layout.welcome_intro_two,
        };
        addBottomDots(0);

        changeStatusBarColor();
        setLanguageValues();
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionHandler.getInstance().saveBoolean(Introscreen.this, AppConstants.IS_WELCOME_FIRST_TIME, true);
                launchHomeScreen();
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    SessionHandler.getInstance().saveBoolean(Introscreen.this, AppConstants.IS_WELCOME_FIRST_TIME, true);
                    launchHomeScreen();
                    finish();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private final ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);


            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(introScreen.getLbl_got_it().getName());
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(introScreen.getLbl_next().getName());
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view;
            TextView tvTitle, tvSubTitle;
            view = layoutInflater.inflate(layouts[position], container, false);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvSubTitle = (TextView) view.findViewById(R.id.tv_subtitle);
//            if (position == 0) {
//                tvTitle.setText(introScreen.getLbl_showcase().getName());
//                tvSubTitle.setText(introScreen.getLbl_destination().getName());
//            } else {
////                view = layoutInflater.inflate(layouts[position], container, false);
////                tvTitle = (TextView) view.findViewById(R.id.tv_title);
////                tvSubTitle = (TextView) view.findViewById(R.id.tv_subtitle);
//                tvTitle.setText(introScreen.getLbl_get_rewarded().getName());
//                tvSubTitle.setText(introScreen.getLbl_earn_cash().getName());
//            }
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void launchHomeScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void setLanguageValues() {
        introScreen = new Gson().fromJson(SessionHandler.getInstance().get(this, AppConstants.INTROSCREEN), POSTLanguageModel.Intro_screen.class);
        btnNext.setText(introScreen.getLbl_next().getName());
        btnSkip.setText(introScreen.getLbl_skip().getName());
    }
}
