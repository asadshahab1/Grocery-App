package com.example.grocera;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView preview;
//    private CartViewModel cartViewModel;
    Context context;
    ListAdapter adapter;
    Button proceedToCheckout;
    ArrayList<CartStructure> carts = new ArrayList<>();
    int noOfItems;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assert container != null;
        context = container.getContext();
//        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        proceedToCheckout = view.findViewById(R.id.proceedToCheckout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        proceedToCheckout.setVisibility(View.INVISIBLE);
        FirebaseDatabase.getInstance().getReference("Cart").child(UserId.getUserId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot cartSnapshot:snapshot.getChildren()){
                                CartStructure cartStructure = cartSnapshot.getValue(CartStructure.class);

                                carts.add(cartStructure);
                                if (adapter!=null)
                                    adapter.notifyDataSetChanged();
                            }
                            if (!carts.isEmpty())
                                proceedToCheckout.setVisibility(View.VISIBLE);
                            adapter = new ListAdapter(context,carts);
                            preview = view.findViewById(R.id.preview);
                            preview.setLayoutManager(new LinearLayoutManager(getActivity()));
                            preview.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        proceedToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("Items",noOfItems);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, ConfirmDetailsFragment.class,bundle)
                        .commit();

            }
        });
    }
}