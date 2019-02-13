package com.example.olskr.weathermvp.navigation;

import android.support.v4.app.Fragment;

import com.example.olskr.weathermvp.ui.fragment.HomeFragment;
import com.example.olskr.weathermvp.ui.fragment.OtherFragment;
import com.example.olskr.weathermvp.ui.fragment.PlacesFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static class MainScreen extends SupportAppScreen {
        private static final String HOME = "Home";
        private static final String PLACES = "Places";
        private static final String OTHER = "Other";

        String arg;

        public MainScreen(String arg) {
            this.arg = arg;
        }

        @Override
        public Fragment getFragment() {
            switch (arg) {
                case HOME:
                    return HomeFragment.getInstance(arg);
                case PLACES:
                    return PlacesFragment.getInstance(arg);
                case OTHER:
                    return OtherFragment.getInstance(arg);
            }
            return HomeFragment.getInstance(arg);
        }
    }

}
