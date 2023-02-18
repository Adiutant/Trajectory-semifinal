package com.study.trajectory_semifinal.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.study.trajectory_semifinal.R;
import com.study.trajectory_semifinal.activities.MainActivity;
import com.study.trajectory_semifinal.adapter.RecyclerItemClickListener;
import com.study.trajectory_semifinal.adapter.ServiceAdapter;
import com.study.trajectory_semifinal.api.JsonAPI;
import com.study.trajectory_semifinal.databinding.FragmentFirstBinding;
import com.study.trajectory_semifinal.model.Data;
import com.study.trajectory_semifinal.model.ServiceInfo;
import com.study.trajectory_semifinal.model.ServiceViewInfo;

public class FirstFragment extends Fragment implements Callback<Data> {

    private static final int NOTIFY_ID = 101;
    private static final String CHANNEL_ID = "NOTIFICATION" ;
    private FragmentFirstBinding binding;
    private ArrayList<ServiceViewInfo> services = new ArrayList<>();
    private ServiceAdapter viewAdapter;
    private RecyclerView servicesView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String BASE_URL = "https://mobile-olympiad-trajectory.hb.bizmrg.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonAPI githubAPI = retrofit.create(JsonAPI.class);
        Call<Data> call = githubAPI.findAll();
        call.enqueue(this);
    }
    @Override
    public void onStart() {
        super.onStart();

        servicesView = requireView().findViewById(R.id.servicesView);
        viewAdapter = new ServiceAdapter(requireContext(), services);

        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        servicesView.setLayoutManager(manager);
        servicesView.setAdapter(viewAdapter);
        start();

    }
    @SuppressLint("NotifyDataSetChanged")
    private void updateRecyclerView(Data response){
        services.clear();
        viewAdapter.notifyDataSetChanged();
        System.out.println(response.items.get(0).name);
        for (ServiceInfo serviceInfo: response.items) {
            services.add(new ServiceViewInfo(serviceInfo.icon_url, serviceInfo.name));
            viewAdapter.notifyItemInserted(0);

        }

    }

    @Override
    public void onResponse(@NonNull Call<Data> call, Response<Data> response) {
        if (response.isSuccessful() && response.body() !=null) {
            updateRecyclerView(response.body());
            servicesView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), servicesView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    NavController nav = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",response.body().items.get(position).name);
                    bundle.putString("description",response.body().items.get(position).description);
                    bundle.putString("icon_url", response.body().items.get(position).icon_url);
                    bundle.putString("service_url", response.body().items.get(position).service_url);
                    nav.navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }

            }));

        }
    }

    @Override
    public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(requireActivity(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Внимание")
                        .setContentText("Ошибка подключения")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(requireActivity());
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

}