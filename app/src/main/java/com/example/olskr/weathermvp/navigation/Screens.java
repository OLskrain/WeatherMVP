package com.example.olskr.weathermvp.navigation;

import android.support.v4.app.Fragment;

import com.example.olskr.weathermvp.ui.fragment.HomeFragment;
import com.example.olskr.weathermvp.ui.fragment.OtherFragment;
import com.example.olskr.weathermvp.ui.fragment.PlacesFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static class MainScreen extends SupportAppScreen {
        String arg;

        public MainScreen(String arg) {
            this.arg = arg;
        }

        @Override
        public Fragment getFragment() {
            switch (arg) {
                case "Home":
                    return HomeFragment.getInstance(arg);
                case "Places":
                    return PlacesFragment.getInstance(arg);
                case "Other":
                    return OtherFragment.getInstance(arg);
            }
            return HomeFragment.getInstance(arg);
        }
    }

}
