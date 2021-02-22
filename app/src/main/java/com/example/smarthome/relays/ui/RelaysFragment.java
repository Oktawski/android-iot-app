package com.example.smarthome.relays.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.AddDevicePagerActivity;
import com.example.smarthome.R;
import com.example.smarthome.adapter.GenericRVAdapter;
import com.example.smarthome.relays.models.Relay;
import com.example.smarthome.relays.viewModels.RelayViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RelaysFragment extends Fragment {

    private RelayViewModel model;
    private List<Relay> relays;
    private GenericRVAdapter<Relay> adapter;
    private RecyclerView rvRelaysFound;


    public RelaysFragment(){}

    public static RelaysFragment newInstance() {
        return new RelaysFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(RelayViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.relays_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bRefresh = view.findViewById(R.id.relays_button_refresh);
        rvRelaysFound = view.findViewById(R.id.relays_found_rv);
        FloatingActionButton fabAdd = view.findViewById(R.id.relays_fragment_add_fab);

        relays = new ArrayList<>();

        initAdapter(requireActivity());
        initViewModel();

        bRefresh.setOnClickListener(v -> model.getRelaysLD());

        fabAdd.setOnClickListener(v -> startActivity(
                new Intent(requireActivity(), AddDevicePagerActivity.class)));
    }

    @Override
    public void onResume() {
        super.onResume();
        model.getRelaysLD();
    }

    private void initViewModel(){
        model.getRelaysLD().observe(getViewLifecycleOwner(), relays -> adapter.update(relays));
    }

    private void initAdapter(Context context){
        adapter = new GenericRVAdapter<Relay>(context, relays){
            @SuppressWarnings("unchecked")
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ((RelayViewHolder)holder).setDetails(relays.get(position));
            }
        };

        // Setting adapter for RecyclerViewRelaysFound
        rvRelaysFound.setAdapter(adapter);
        rvRelaysFound.setLayoutManager(new LinearLayoutManager(context));

        // Vertical lines between items
        rvRelaysFound.addItemDecoration(
                new DividerItemDecoration(rvRelaysFound.getContext(),DividerItemDecoration.VERTICAL));
    }
}
