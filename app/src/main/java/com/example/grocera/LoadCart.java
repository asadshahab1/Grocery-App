package com.example.grocera;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoadCart {
//    ExecutorService executor = Executors.newSingleThreadExecutor();
//    MutableLiveData<ArrayList<CartStructure>> cartList;
//    public MutableLiveData<ArrayList<CartStructure>> getCartData(){
//        if(cartList==null)
//            cartList = new MutableLiveData<>();
//       loadCartFromFirebase();
//    return cartList; }
    Context context;
    RecyclerView recyclerView;
    ListAdapter adapter;
    public LoadCart(Context context, RecyclerView view){
        this.context = context;
        this.recyclerView = view;
    }
    public void loadCartFromFirebase(){
        FirebaseDatabase.getInstance().getReference("Cart").child(UserId.getUserId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<CartStructure> cartStructureArrayList = new ArrayList<>();
                        if (snapshot.exists()){
                            for (DataSnapshot cartSnapshot:snapshot.getChildren()){
                                CartStructure cartStructure = cartSnapshot.getValue(CartStructure.class);
                                assert cartStructure!=null;
                                cartStructureArrayList.add(cartStructure);
                                adapter = new ListAdapter(context, cartStructureArrayList);
                                recyclerView.setAdapter(adapter);
                            }
//                            cartList.setValue(cartStructureArrayList);
//                            double sum = 0;
//                            for(CartStructure cartStructure: cartStructureArrayList)
//                            {
//                                sum+=cartStructure.getTotal_price();
//                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
   }
}
