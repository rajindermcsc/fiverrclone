package dreamguys.in.co.gigs;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import dreamguys.in.co.gigs.Model.POSTLanguageModel;
import dreamguys.in.co.gigs.fragment.BuyFragment;
import dreamguys.in.co.gigs.fragment.ChatFragment;
import dreamguys.in.co.gigs.fragment.HomeFragment;
import dreamguys.in.co.gigs.fragment.SellFragment;
import dreamguys.in.co.gigs.fragment.SettingsFragment;
import dreamguys.in.co.gigs.interfaces.callnavigationHme;
import dreamguys.in.co.gigs.utils.AppConstants;
import dreamguys.in.co.gigs.utils.SessionHandler;
import dreamguys.in.co.gigs.utils.Utils;
import dreamguys.in.co.gigs.wallet.WalletDashBoard;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, callnavigationHme {


    @Override
    public void callHome() {
        FragmentTransaction fragmentTransaction;
        Fragment fragment = null;
        fragment = new HomeFragment();
        removeHomeHighlight();
        if (fragment != null) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_frame_layout, fragment);
            fragmentTransaction.commit();
        }
    }

    private ImageView ivHome, ivExplore, ivBuy, ivChat, ivSettings;
    private CircleImageView iv_user_image;
    private TextView txtHome, txtExplore, txtBuy, txtChat, txtSettings, txt_username;
    private LinearLayout llHome;
    private LinearLayout llExplore;
    private LinearLayout llBuy;
    private LinearLayout llChat;
    private LinearLayout llSettings;
    String username;
    private Menu drawerMenu;
    public POSTLanguageModel.Common_strings commonStrings = new POSTLanguageModel().new Common_strings();
    public POSTLanguageModel.Tabbar_screen tabbarScreen = new POSTLanguageModel().new Tabbar_screen();
    public POSTLanguageModel.Navigation_screen navigationScreen = new POSTLanguageModel().new Navigation_screen();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        Utils.setLanguageLocale(this, SessionHandler.getInstance().get(this, "locale"));
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Utils.freeMemory();


        // if savedInstanceState is null we do some cleanup
        if (savedInstanceState != null) {
            // cleanup any existing fragments in case we are in detailed mode
            getFragmentManager().executePendingTransactions();
            Fragment fragmentById = getSupportFragmentManager().
                    findFragmentById(R.id.fragment_frame_layout);
            if (fragmentById != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(fragmentById).commit();
            }

        }
        initLayouts();
        setLanguageValues();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationDrawerSetContent();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                /*if (SessionHandler.getInstance().get(MainActivity.this, AppConstants.TOKEN_ID) != null && SessionHandler.getInstance().get(MainActivity.this, AppConstants.USER_IMAGE) != null) {
                    username = SessionHandler.getInstance().get(MainActivity.this, AppConstants.USER_NAME);
                    txt_username.setText("Hi " + username.substring(0, 1).toUpperCase() + username.substring(1));
                    *//*Picasso.with(MainActivity.this)
                            .load(AppConstants.BASEURL + SessionHandler.getInstance().get(MainActivity.this, AppConstants.USER_IMAGE))
                            .error(R.drawable.ic_no_image_100)
                            .placeholder(R.drawable.ic_no_image_100)
                            .into(iv_user_image);*//*
                    //iv_user_image.setImageURI(Uri.parse(AppConstants.BASEURL + SessionHandler.getInstance().get(MainActivity.this, AppConstants.USER_IMAGE)));
                } else {
                    txt_username.setText("Hi Guest");
                }*/
                drawer.invalidate();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //setDefaultIcons();
        setLanguageValues();
        removeHomeHighlight();

        if (getIntent() != null && getIntent().getStringExtra("From") != null && getIntent().getStringExtra("From").equalsIgnoreCase("MainPage")) {
            int loadFragment = getIntent().getIntExtra("LoadFragment", 0);
            Fragment fragment = null;
            switch (loadFragment) {
                case 0:
                default:
                    fragment = new HomeFragment();
                    removeHomeHighlight();
                    break;
                case 1://TODO:
                    this.getSupportActionBar().setTitle(commonStrings.getTitle_sell_services().getName());
                    fragment = new SellFragment();
                    removeExploreHighlight();
                    break;
                case 2:
                    this.getSupportActionBar().setTitle(commonStrings.getTitle_buy_services().getName());
                    fragment = new BuyFragment();
                    removeBuyHighlight();
                    break;
                case 3:
                    this.getSupportActionBar().setTitle(commonStrings.getTitle_chat().getName());
                    fragment = new ChatFragment();
                    removeChatHighlight();
                    break;
                case 4:
                    this.getSupportActionBar().setTitle(commonStrings.getTitle_settings().getName());
                    fragment = new SettingsFragment();
                    removeSettingsHighlight();
                    break;
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame_layout, fragment).commit();
            }

        } else if (getIntent() != null && getIntent().getStringExtra("From") != null && getIntent().getStringExtra("From").equalsIgnoreCase("NavPage")) {
            callNavIntent(getIntent().getIntExtra("LoadNav", 0));
        } else {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_frame_layout, new HomeFragment()).commit();
//            removeHomeHighlight();
            llHome.performClick();
        }
    }

    private void NavigationDrawerSetContent() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        iv_user_image = (CircleImageView) header.findViewById(R.id.iv_userimage);
        txt_username = (TextView) header.findViewById(R.id.input_username);

        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_camera).setTitle(navigationScreen.getLbl_favourites().getName());
        menu.findItem(R.id.nav_gallery).setTitle(navigationScreen.getLbl_last_visited_gigs().getName());
        menu.findItem(R.id.nav_search).setTitle(navigationScreen.getLbl_search_gigs().getName());
        menu.findItem(R.id.nav_myactivity).setTitle(navigationScreen.getLbl_my_activity().getName());
        menu.findItem(R.id.nav_myGigs).setTitle(navigationScreen.getLbl_my_gigs().getName());

        if (SessionHandler.getInstance().get(MainActivity.this, AppConstants.TOKEN_ID) != null && SessionHandler.getInstance().get(MainActivity.this, AppConstants.USER_IMAGE) != null) {
            username = SessionHandler.getInstance().get(MainActivity.this, AppConstants.USER_NAME);
            txt_username.setText(commonStrings.getLbl_fld_hi().getName() + " " + username.substring(0, 1).toUpperCase() + username.substring(1));
            Picasso.with(MainActivity.this)
                    .load(AppConstants.BASEURL + SessionHandler.getInstance().get(MainActivity.this, AppConstants.USER_IMAGE))
                    .error(R.drawable.ic_no_image_100)
                    .placeholder(R.drawable.ic_no_image_100)
                    .into(iv_user_image);
            //iv_user_image.setImageURI(Uri.parse(AppConstants.BASEURL + SessionHandler.getInstance().get(MainActivity.this, AppConstants.USER_IMAGE)));
        } else {
            txt_username.setText(commonStrings.getLbl_guest().getName());
            txt_username.setText(commonStrings.getLbl_guest().getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLanguageLocale(this, SessionHandler.getInstance().get(this, "locale"));
        OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);
        NavigationDrawerSetContent();
    }

    private void setDefaultIcons() {
        txtExplore.setTextColor(getResources().getColor(android.R.color.black));
        ivExplore.setBackgroundResource(R.drawable.ic_explore_black_24dp);
        txtBuy.setTextColor(getResources().getColor(android.R.color.black));
        ivBuy.setBackgroundResource(R.drawable.ic_add_shopping_cart_black_24dp);
        txtChat.setTextColor(getResources().getColor(android.R.color.black));
        ivChat.setBackgroundResource(R.drawable.ic_chat_black_24dp);
        txtSettings.setTextColor(getResources().getColor(android.R.color.black));
        ivSettings.setBackgroundResource(R.drawable.ic_settings_black_24dp);
        txtHome.setTextColor(getResources().getColor(R.color.colorAppTheme));
        ivHome.setBackgroundResource(R.drawable.ic_home_purple_24dp);
    }

    private void initLayouts() {
        ivHome = (ImageView) findViewById(R.id.iv_home);
        ivExplore = (ImageView) findViewById(R.id.iv_explore);
        ivBuy = (ImageView) findViewById(R.id.iv_cart);
        ivChat = (ImageView) findViewById(R.id.iv_chat);
        ivSettings = (ImageView) findViewById(R.id.iv_settings);


        txtHome = (TextView) findViewById(R.id.txt_home);
        txtExplore = (TextView) findViewById(R.id.txt_explore);
        txtBuy = (TextView) findViewById(R.id.txt_cart);
        txtChat = (TextView) findViewById(R.id.txt_chat);
        txtSettings = (TextView) findViewById(R.id.txt_settings);


        llHome = (LinearLayout) findViewById(R.id.ll_home);
        llExplore = (LinearLayout) findViewById(R.id.ll_explore);
        llBuy = (LinearLayout) findViewById(R.id.ll_buy);
        llChat = (LinearLayout) findViewById(R.id.ll_chat);
        llSettings = (LinearLayout) findViewById(R.id.ll_settings);

        llHome.setOnClickListener(this);
        llExplore.setOnClickListener(this);
        llBuy.setOnClickListener(this);
        llChat.setOnClickListener(this);
        llSettings.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
//        this.drawerMenu = menu;
//        drawerMenu.findItem(R.id.nav_camera).setTitle(navigationScreen.getLbl_favourites().getName());
//        drawerMenu.findItem(R.id.nav_gallery).setTitle(navigationScreen.getLbl_last_visited_gigs().getName());
//        drawerMenu.findItem(R.id.nav_search).setTitle(navigationScreen.getLbl_search_gigs().getName());
//        drawerMenu.findItem(R.id.nav_myactivity).setTitle(navigationScreen.getLbl_my_activity().getName());
//        drawerMenu.findItem(R.id.nav_myGigs).setTitle(navigationScreen.getLbl_my_gigs().getName());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
                startActivity(new Intent(this, Favourites.class));
            } else {
                Intent loginIntent = new Intent(this, Login.class);
                loginIntent.putExtra("From", "NavPage");
                loginIntent.putExtra("LoadNav", 1);
                startActivity(loginIntent);
                finish();
            }
        } else if (id == R.id.nav_gallery) {
            if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
                startActivity(new Intent(this, LastVisitedGigs.class));
            } else {
                Intent loginIntent = new Intent(this, Login.class);
                loginIntent.putExtra("From", "NavPage");
                loginIntent.putExtra("LoadNav", 2);
                startActivity(loginIntent);
                finish();

            }
        } else if (id == R.id.nav_search) {
            if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
                startActivity(new Intent(this, Search_Gigs.class));
            } else {
                Intent loginIntent = new Intent(this, Login.class);
                loginIntent.putExtra("From", "NavPage");
                loginIntent.putExtra("LoadNav", 3);
                startActivity(loginIntent);
                finish();
            }
        } else if (id == R.id.nav_myactivity) {
            if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
                startActivity(new Intent(this, MyActivity.class));
            } else {
                Intent loginIntent = new Intent(this, Login.class);
                loginIntent.putExtra("From", "NavPage");
                loginIntent.putExtra("LoadNav", 4);
                startActivity(loginIntent);
                finish();
            }
        } else if (id == R.id.nav_myGigs) {
            if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
                startActivity(new Intent(this, MyGigs.class));
            } else {
                Intent loginIntent = new Intent(this, Login.class);
                loginIntent.putExtra("From", "NavPage");
                loginIntent.putExtra("LoadNav", 5);
                startActivity(loginIntent);
                finish();
            }
        }
//        else if (id == R.id.nav_wallet) {
////            if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
////                startActivity(new Intent(this, MyGigs.class));
////            } else {
////                Intent loginIntent = new Intent(this, Login.class);
////                loginIntent.putExtra("From", "NavPage");
////                loginIntent.putExtra("LoadNav", 5);
////                startActivity(loginIntent);
////                finish();
////            }
//            startActivity(new Intent(this, WalletDashBoard.class));
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callNavIntent(int value) {
        switch (value) {
            case 1:
                startActivity(new Intent(this, Favourites.class));
                break;
            case 2:
                startActivity(new Intent(this, LastVisitedGigs.class));
                break;
            case 3:
                startActivity(new Intent(this, Search_Gigs.class));
                break;
            case 4:
                startActivity(new Intent(this, MyActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, MyGigs.class));
                break;

        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction;
        Fragment fragment = null;
        //LoadFragment : 0 - Home, 1- Sell, 2- Buy,3-Chat,4-Settings
        switch (v.getId()) {
            case R.id.ll_home:
                removeHomeHighlight();
                fragment = new HomeFragment();
                this.getSupportActionBar().setTitle(commonStrings.getTitle_home().getName());
                break;
            case R.id.ll_explore:
                if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
                    this.getSupportActionBar().setTitle(commonStrings.getTitle_sell_services().getName());
                    fragment = new SellFragment();
                } else {
                    //NetworkAlertDialog.networkAlertDialog(MainActivity.this, "", "Please Login to continue...", null, null);
                    Intent loginIntent = new Intent(this, Login.class);
                    loginIntent.putExtra("From", "MainPage");
                    loginIntent.putExtra("LoadFragment", 1);
                    startActivity(loginIntent);
                    finish();
                }
                removeExploreHighlight();
                break;
            case R.id.ll_buy:
                if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
                    this.getSupportActionBar().setTitle(commonStrings.getTitle_buy_services().getName());
                    fragment = new BuyFragment();
                } else {
                    Intent loginIntent = new Intent(this, Login.class);
                    loginIntent.putExtra("From", "MainPage");
                    loginIntent.putExtra("LoadFragment", 2);
                    startActivity(loginIntent);
                    finish();
                }
                removeBuyHighlight();
                break;
            case R.id.ll_chat:
                if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
                    this.getSupportActionBar().setTitle(commonStrings.getTitle_chat().getName());
                    fragment = new ChatFragment();
                } else {
                    Intent loginIntent = new Intent(this, Login.class);
                    loginIntent.putExtra("From", "MainPage");
                    loginIntent.putExtra("LoadFragment", 3);
                    startActivity(loginIntent);
                    finish();
                }
                removeChatHighlight();
                break;
            case R.id.ll_settings:
                if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
                    this.getSupportActionBar().setTitle(commonStrings.getTitle_settings().getName());
                    fragment = new SettingsFragment();
                } else {
                    Intent loginIntent = new Intent(this, Login.class);
                    loginIntent.putExtra("From", "MainPage");
                    loginIntent.putExtra("LoadFragment", 4);
                    startActivity(loginIntent);
                    finish();
                }
                removeSettingsHighlight();
                break;
        }
        if (fragment != null) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_frame_layout, fragment);
            fragmentTransaction.commit();
        }
    }

    private void removeHomeHighlight() {
//        this.getSupportActionBar().setTitle(getString(R.string.title_home));
        txtHome.setTextColor(getResources().getColor(R.color.colorAppTheme));
        ivHome.setBackgroundResource(R.drawable.ic_home_purple_24dp);
        txtExplore.setTextColor(getResources().getColor(android.R.color.black));
        ivExplore.setBackgroundResource(R.drawable.ic_explore_black_24dp);
        txtBuy.setTextColor(getResources().getColor(android.R.color.black));
        ivBuy.setBackgroundResource(R.drawable.ic_add_shopping_cart_black_24dp);
        txtChat.setTextColor(getResources().getColor(android.R.color.black));
        ivChat.setBackgroundResource(R.drawable.ic_chat_black_24dp);
        txtSettings.setTextColor(getResources().getColor(android.R.color.black));
        ivSettings.setBackgroundResource(R.drawable.ic_settings_black_24dp);


//        if(SessionHandler.getInstance().get(this,"locale").equalsIgnoreCase("ar")){
//            this.getSupportActionBar().setTitle("الصفحة الرئيسية");
//            txtHome.setText("الصفحة الرئيسية");
//            txtExplore.setText("يبيع");
//            txtBuy.setText("يشترى");
//            txtChat.setText("دردشة");
//            txtSettings.setText("الإعدادات");
//        }else {
//            this.getSupportActionBar().setTitle(getString(R.string.title_home));
//            txtHome.setText(getResources().getString(R.string.bottom_menu_home));
//            txtExplore.setText(getResources().getString(R.string.bottom_menu_sell));
//            txtBuy.setText(getResources().getString(R.string.bottom_menu_buy));
//            txtChat.setText(getResources().getString(R.string.bottom_menu_chat));
//            txtSettings.setText(getResources().getString(R.string.bottom_menu_settings));
//        }
        this.getSupportActionBar().setTitle(commonStrings.getTitle_home().getName());
        txtHome.setText(commonStrings.getTitle_home().getName());
        txtExplore.setText(tabbarScreen.getLbl_sell().getName());
        txtBuy.setText(tabbarScreen.getLbl_buy().getName());
        txtChat.setText(commonStrings.getTitle_chat().getName());
        txtSettings.setText(commonStrings.getTitle_settings().getName());


        ivHome.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAppThemeSecondary)));
        ivExplore.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivBuy.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivChat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivSettings.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
    }

    private void removeExploreHighlight() {
        txtExplore.setTextColor(getResources().getColor(R.color.colorAppTheme));
        ivExplore.setBackgroundResource(R.drawable.ic_explore_purple_24dp);
        txtHome.setTextColor(getResources().getColor(android.R.color.black));
        ivHome.setBackgroundResource(R.drawable.ic_home_black_24dp);
        txtBuy.setTextColor(getResources().getColor(android.R.color.black));
        ivBuy.setBackgroundResource(R.drawable.ic_add_shopping_cart_black_24dp);
        txtChat.setTextColor(getResources().getColor(android.R.color.black));
        ivChat.setBackgroundResource(R.drawable.ic_chat_black_24dp);
        txtSettings.setTextColor(getResources().getColor(android.R.color.black));
        ivSettings.setBackgroundResource(R.drawable.ic_settings_black_24dp);

        this.getSupportActionBar().setTitle(commonStrings.getTitle_sell_services().getName());
        ivHome.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivExplore.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAppThemeSecondary)));
        ivBuy.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivChat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivSettings.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));

        txtHome.setText(commonStrings.getTitle_home().getName());
        txtExplore.setText(tabbarScreen.getLbl_sell().getName());
        txtBuy.setText(tabbarScreen.getLbl_buy().getName());
        txtChat.setText(commonStrings.getTitle_chat().getName());
        txtSettings.setText(commonStrings.getTitle_settings().getName());
    }

    private void removeBuyHighlight() {

        txtBuy.setTextColor(getResources().getColor(R.color.colorAppTheme));
        ivBuy.setBackgroundResource(R.drawable.ic_add_shopping_cart_purple_24dp);
        txtExplore.setTextColor(getResources().getColor(android.R.color.black));
        ivExplore.setBackgroundResource(R.drawable.ic_explore_black_24dp);
        txtHome.setTextColor(getResources().getColor(android.R.color.black));
        ivHome.setBackgroundResource(R.drawable.ic_home_black_24dp);
        txtChat.setTextColor(getResources().getColor(android.R.color.black));
        ivChat.setBackgroundResource(R.drawable.ic_chat_black_24dp);
        txtSettings.setTextColor(getResources().getColor(android.R.color.black));
        ivSettings.setBackgroundResource(R.drawable.ic_settings_black_24dp);

        ivHome.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivExplore.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivBuy.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAppThemeSecondary)));
        ivChat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivSettings.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));

        this.getSupportActionBar().setTitle(commonStrings.getTitle_buy_services().getName());
        txtHome.setText(commonStrings.getTitle_home().getName());
        txtExplore.setText(tabbarScreen.getLbl_sell().getName());
        txtBuy.setText(tabbarScreen.getLbl_buy().getName());
        txtChat.setText(commonStrings.getTitle_chat().getName());
        txtSettings.setText(commonStrings.getTitle_settings().getName());
    }

    private void removeChatHighlight() {

        txtChat.setTextColor(getResources().getColor(R.color.colorAppTheme));
        ivChat.setBackgroundResource(R.drawable.ic_chat_purple_24dp);
        txtExplore.setTextColor(getResources().getColor(android.R.color.black));
        ivExplore.setBackgroundResource(R.drawable.ic_explore_black_24dp);
        txtBuy.setTextColor(getResources().getColor(android.R.color.black));
        ivBuy.setBackgroundResource(R.drawable.ic_add_shopping_cart_black_24dp);
        txtHome.setTextColor(getResources().getColor(android.R.color.black));
        ivHome.setBackgroundResource(R.drawable.ic_home_black_24dp);
        txtSettings.setTextColor(getResources().getColor(android.R.color.black));
        ivSettings.setBackgroundResource(R.drawable.ic_settings_black_24dp);

        ivHome.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivExplore.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivBuy.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivChat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAppThemeSecondary)));
        ivSettings.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));

        this.getSupportActionBar().setTitle(commonStrings.getTitle_chat().getName());
        txtHome.setText(commonStrings.getTitle_home().getName());
        txtExplore.setText(tabbarScreen.getLbl_sell().getName());
        txtBuy.setText(tabbarScreen.getLbl_buy().getName());
        txtChat.setText(commonStrings.getTitle_chat().getName());
        txtSettings.setText(commonStrings.getTitle_settings().getName());
    }

    private void removeSettingsHighlight() {

        txtSettings.setTextColor(getResources().getColor(R.color.colorAppTheme));
        ivSettings.setBackgroundResource(R.drawable.ic_settings_purple_24dp);
        txtExplore.setTextColor(getResources().getColor(android.R.color.black));
        ivExplore.setBackgroundResource(R.drawable.ic_explore_black_24dp);
        txtBuy.setTextColor(getResources().getColor(android.R.color.black));
        ivBuy.setBackgroundResource(R.drawable.ic_add_shopping_cart_black_24dp);
        txtHome.setTextColor(getResources().getColor(android.R.color.black));
        ivHome.setBackgroundResource(R.drawable.ic_home_black_24dp);
        txtChat.setTextColor(getResources().getColor(android.R.color.black));
        ivChat.setBackgroundResource(R.drawable.ic_chat_black_24dp);

        ivHome.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivExplore.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivBuy.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivChat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
        ivSettings.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAppThemeSecondary)));

        this.getSupportActionBar().setTitle(commonStrings.getTitle_settings().getName());
        txtHome.setText(commonStrings.getTitle_home().getName());
        txtExplore.setText(tabbarScreen.getLbl_sell().getName());
        txtBuy.setText(tabbarScreen.getLbl_buy().getName());
        txtChat.setText(commonStrings.getTitle_chat().getName());
        txtSettings.setText(commonStrings.getTitle_settings().getName());

    }


    public void setLanguageValues() {
        commonStrings = new Gson().fromJson(SessionHandler.getInstance().get(this, AppConstants.COMMONSTRINGS), POSTLanguageModel.Common_strings.class);
        tabbarScreen = new Gson().fromJson(SessionHandler.getInstance().get(this, AppConstants.TABBAR), POSTLanguageModel.Tabbar_screen.class);
        navigationScreen = new Gson().fromJson(SessionHandler.getInstance().get(this, AppConstants.NAVSCREEN), POSTLanguageModel.Navigation_screen.class);
        txtHome.setText(commonStrings.getTitle_home().getName());
        txtExplore.setText(tabbarScreen.getLbl_sell().getName());
        txtBuy.setText(tabbarScreen.getLbl_buy().getName());
        txtChat.setText(commonStrings.getTitle_chat().getName());
        txtSettings.setText(commonStrings.getTitle_settings().getName());

    }


}
