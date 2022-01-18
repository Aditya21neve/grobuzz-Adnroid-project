package com.example.storetodoore;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.storetodoore.Delivery_Activity.SELECT_ADDRESS;
import static com.example.storetodoore.MyAddressesActivity.refreshitem;
import static com.example.storetodoore.My_Account_Fragment.MANAGE_ADDRESS;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.Viewholder> {
    private List<AddressesModel> addressesModelList;
    private int MODE;
    private int preselectedPosition = -1;

    public AddressesAdapter(List<AddressesModel> addressesModelList, int MODE) {
        this.addressesModelList = addressesModelList;
        this.MODE = MODE;
    }


    @NonNull
    @Override
    public AddressesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addresses_item_layout, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.Viewholder holder, int position) {
        String name = addressesModelList.get(position).getFullName();
        String address = addressesModelList.get(position).getAddress();
        String pincode = addressesModelList.get(position).getPinCode();
        Boolean selected = addressesModelList.get(position).isSelected();
        holder.setData(name, address, pincode, selected, position);


    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView fullname;
        private TextView address;
        private TextView pincode;
        private ImageView icon;
        private LinearLayout optionContainer;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.adname);
            address = itemView.findViewById(R.id.adaddress);
            pincode = itemView.findViewById(R.id.adpincode);
            icon = itemView.findViewById(R.id.iconView);
            optionContainer = itemView.findViewById(R.id.option_Container);


        }

        private void setData(String username, String userAddress, String userPincode, Boolean selected, int position) {
            fullname.setText(username);
            address.setText(userAddress);
            pincode.setText(userPincode);

            if (MODE == SELECT_ADDRESS) {
                icon.setImageResource(R.drawable.check);
                if (selected) {
                    icon.setVisibility(View.VISIBLE);
                    preselectedPosition = position;
                } else {
                    icon.setVisibility(View.GONE);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (preselectedPosition != position) {
                            addressesModelList.get(position).setSelected(true);
                            addressesModelList.get(preselectedPosition).setSelected(false);
                            refreshitem(preselectedPosition, position);
                            preselectedPosition = position;
                        }
                    }
                });
            } else if (MODE == MANAGE_ADDRESS) {
                optionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.vertical_dot);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshitem(preselectedPosition,preselectedPosition);
                        preselectedPosition = position;

                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshitem(preselectedPosition,preselectedPosition);
                        preselectedPosition = -1;
                    }
                });

            }
        }

    }
}
