package ir.irezaa.contactstask.ui.viewholders;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.irezaa.contactstask.R;
import ir.irezaa.contactstask.models.Contact;

/**
 * Created by rezapilehvar on 26/12/2017 AD.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.viewholder_contact_userFullnameTextView)
    AppCompatTextView fullnameTextView;

    @BindView(R.id.viewholder_contact_phoneNumberTextView)
    AppCompatTextView phoneNumberTextView;

    @BindView(R.id.viewholder_contact_bottomDivider)
    View dividerView;

    public ContactViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.viewholder_contact, parent, false));
        ButterKnife.bind(this, itemView);
    }

    public void bindViews(List<Contact> contactList, int position) {
        if (position != -1) {
            Contact contact = contactList.get(position);

            if (contact != null) {

                if (position != contactList.size() - 1) {
                    dividerView.setVisibility(View.VISIBLE);
                } else {
                    dividerView.setVisibility(View.GONE);
                }

                fullnameTextView.setText(contact.getFullName());
                phoneNumberTextView.setText(contact.getPhoneNumber());
            }
        }
    }
}
