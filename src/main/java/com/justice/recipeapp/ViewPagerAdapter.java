package com.justice.recipeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return AddFoodActivity.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return AddFoodActivity.fragmentNames.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return AddFoodActivity.fragmentNames.get(position);
    }
}
