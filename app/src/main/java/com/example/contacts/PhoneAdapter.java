package com.example.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneHolder> implements Filterable {

    private OnNoteClickListener clickListener;
    ArrayList<Phone> phones;
    private ArrayList<Phone> newphones;


    PhoneAdapter(ArrayList<Phone> phones, OnNoteClickListener listener){
        this.phones = phones;
        newphones = new ArrayList<>(phones);
        clickListener = listener;
    }

    @NonNull
    @Override
    public PhoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactsview, parent, false);
        return new PhoneHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneHolder holder, int position) {
        holder.text_number.setText(phones.get(position).getPhoneNumber());
        holder.text_name.setText(phones.get(position).getHolderName());
        holder.image.setImageResource(phones.get(position).getResourceId());

    }

    @Override
    public int getItemCount() {
        return phones.size();
    }


    public class  PhoneHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text_name;
        TextView text_number;

        PhoneHolder(View view){
            super(view);
            image = view.findViewById(R.id.KartinkaKontakta);
            text_name = view.findViewById(R.id.text_name);
            text_number = view.findViewById(R.id.text_number);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.OnClick(getAdapterPosition());
                }
            });
        }

    }

    @Override
    public Filter getFilter() {
        return PhoneFilter;
    }

    private Filter PhoneFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Phone> filteredlist = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredlist.addAll(newphones);
            }else{
                String filternpattern = charSequence.toString().toLowerCase().trim();

                for (Phone phone : newphones){
                    if (phone.getHolderName().toLowerCase().contains(filternpattern)){
                        filteredlist.add(phone);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredlist;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            phones.clear();
            phones.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
