package com.aidan.envelopetracker.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aidan on 2018/3/13.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected abstract int getLayoutResourceId();
    protected abstract void findView();
    protected abstract void setListener();
    protected abstract void onCreateView();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(getLayoutResourceId(), null);
        findView();
        setListener();
        onCreateView();
        return rootView;
    }
}
