package dreamguys.in.co.gigs;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.Locale;

import dreamguys.in.co.gigs.utils.SessionHandler;

/**
 * Created by Hari on 12-12-2018.
 */

public class BaseFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localeChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void localeChanged() {
        Configuration newConfig = new Configuration();
        if (SessionHandler.getInstance().get(getActivity(), "locale") != null) {
            newConfig.locale = new Locale(SessionHandler.getInstance().get(getActivity(), "locale"));
        } else {
            newConfig.locale = new Locale("en");
        }
        onConfigurationChanged(newConfig);
    }

}
