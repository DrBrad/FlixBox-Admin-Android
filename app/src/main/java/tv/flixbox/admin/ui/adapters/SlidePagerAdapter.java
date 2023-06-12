package tv.flixbox.admin.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class SlidePagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;

    public SlidePagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, List<Fragment> fragments){
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    public SlidePagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle){
        super(fragmentManager, lifecycle);
        fragments = new ArrayList<>();
    }

    public void insertFragment(Fragment fragment, int position){
        fragments.add(position, fragment);
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    public void removeFragment(int position){
        fragments.remove(position);
    }

    public Fragment getFragmentAt(int position){
        return fragments.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        return fragments.get(position);
    }

    @Override
    public int getItemCount(){
        return fragments.size();
    }
}
