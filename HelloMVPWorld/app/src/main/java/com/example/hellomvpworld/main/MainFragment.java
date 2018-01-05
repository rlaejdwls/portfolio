package com.example.hellomvpworld.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hellomvpworld.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Hwang on 2018-01-04.
 *
 * Description :
 */
public class MainFragment extends Fragment implements MainContract.View {
    private MainContract.Presenter presenter;

//    private TextView txtMessage;
//
//    private View.OnClickListener onClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (v instanceof Button) {
//                presenter.setMessage((String)((Button) v).getText());
//            }
//        }
//    };

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.result(requestCode, resultCode, data);
    }
    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        txtMessage = view.findViewById(R.id.txt_message);
//
//        view.findViewById(R.id.btn_select).setOnClickListener(onClick);
//        view.findViewById(R.id.btn_apply).setOnClickListener(onClick);
        return view;
    }
}
