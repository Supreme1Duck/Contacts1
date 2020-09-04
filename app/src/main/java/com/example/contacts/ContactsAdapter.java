package com.example.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsHolder> implements Filterable {

    private OnNoteClickListener clickListener;
    private ArrayList<Contact> phones;
    private ArrayList<Contact> newphones;
    private Filter phoneFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Contact> filteredlist = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredlist.addAll(newphones);
            } else {
                String filternpattern = charSequence.toString().toLowerCase().trim();

                for (Contact phone : newphones) {
                    if (phone.getHolderName().toLowerCase().contains(filternpattern)) {
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
            phones.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    ContactsAdapter(ArrayList<Contact> phones, OnNoteClickListener listener) {
        this.phones = phones;
        newphones = new ArrayList<>(phones);
        clickListener = listener;
    }

    @NonNull
    @Override
    public ContactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactsview, parent, false);
        return new ContactsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsHolder holder, int position) {
        holder.text_number.setText(phones.get(position).getPhoneNumber());
        holder.text_name.setText(phones.get(position).getHolderName());
        holder.image.setImageResource(phones.get(position).getResourceId());
    }

    @Override
    public int getItemCount() {
        return phones == null ? 0 : phones.size();
    }

    @Override
    public Filter getFilter() {
        return phoneFilter;
    }

    public class ContactsHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text_name;
        TextView text_number;

        ContactsHolder(View view) {
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
}
