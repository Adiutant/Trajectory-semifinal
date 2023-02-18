package com.study.trajectory_semifinal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.study.trajectory_semifinal.R;
import com.study.trajectory_semifinal.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment  {

    private FragmentSecondBinding binding;



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView serviceName = requireView().findViewById(R.id.name_field);
        TextView serviceDescription = requireView().findViewById(R.id.description_field);
        ImageView serviceImageHolder = requireView().findViewById(R.id.icon_view);
        TextView serviceUrl = requireView().findViewById(R.id.url_field);
        Bundle bundle = getArguments();
       if (bundle == null)
       {return;}

        serviceName.setText(bundle.getString("name"));
        serviceDescription.setText(bundle.getString("description"));
        Glide.with(this.requireContext())
                .load(bundle.getString("icon_url"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(serviceImageHolder);
        serviceUrl.setText(bundle.getString("service_url"));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
