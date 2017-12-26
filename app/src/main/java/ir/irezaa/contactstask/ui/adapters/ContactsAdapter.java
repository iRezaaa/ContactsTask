package ir.irezaa.contactstask.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import ir.irezaa.contactstask.models.Contact;
import ir.irezaa.contactstask.ui.viewholders.ContactViewHolder;

/**
 * Created by rezapilehvar on 26/12/2017 AD.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public ContactsAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(context, parent);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.bindViews(contactList, position);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public long getItemId(int position) {
        return contactList.get(position).hashCode();
    }
}
