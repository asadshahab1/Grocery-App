package com.example.grocera;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddToCart {
    public AddToCart(){}
    private static int inc = 0;
    public static void addToCart(String name, String price, String company, String image){
        inc+=1;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Cart");
        database.child(UserId.getUserId()).child(Integer.toString(inc))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            CartStructure cartStructure = snapshot.getValue(CartStructure.class);
                            assert cartStructure != null;
                            cartStructure.setQuantity(cartStructure.getQuantity()+1);
                            Map<String, Object> updateData = new HashMap<>();
                            updateData.put("quantity", cartStructure.getQuantity());
                            updateData.put("total_price",cartStructure.getQuantity()*Float.parseFloat(cartStructure.getPrice()));
                            database.child(UserId.getUserId()).child(Integer.toString(inc)).updateChildren(updateData);
                        }
                        {
                            CartStructure cartStructure = new CartStructure();
                            cartStructure.setName(name);
                            cartStructure.setImage(image);
                            cartStructure.setPrice(price);
                            cartStructure.setCompany_name(company);
                            cartStructure.setQuantity(1);
                            cartStructure.setTotal_price(Float.parseFloat(price));
                            database.child(UserId.getUserId()).child(Integer.toString(inc)).setValue(cartStructure);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
