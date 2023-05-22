package com.example.mynotes.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.mynotes.fragments.AddPlacesFragment;
import com.example.mynotes.fragments.MapsFragment;
import com.example.mynotes.fragments.PlacesListFragment;

public class MyPagerAdapter extends FragmentStateAdapter {

    public MyPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AddPlacesFragment();
            case 1:
                return new PlacesListFragment();
            case 2:
                return new MapsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}

