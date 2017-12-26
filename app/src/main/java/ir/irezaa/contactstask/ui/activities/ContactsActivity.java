package ir.irezaa.contactstask.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.irezaa.contactstask.App;
import ir.irezaa.contactstask.R;
import ir.irezaa.contactstask.api.ContactsApi;
import ir.irezaa.contactstask.models.Contact;
import ir.irezaa.contactstask.ui.adapters.ContactsAdapter;
import ir.irezaa.contactstask.viewmodels.contacts.ContactsActivityView;
import ir.irezaa.contactstask.viewmodels.contacts.ContactsActivityViewModel;

public class ContactsActivity extends BaseActivity<ContactsActivityViewModel> implements ContactsActivityView {
    @BindView(R.id.activity_contacts_mainSwipeRefreshLayout)
    SwipeRefreshLayout mainSwipeRefreshLayout;

    @BindView(R.id.activity_contacts_contactsRecyclerView)
    RecyclerView contactsRecyclerView;
    private ContactsAdapter contactsAdapter;
    private List<Contact> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        initViews();

        viewModel = new ContactsActivityViewModel(App.getInstance().getRetrofitInstance().create(ContactsApi.class));
        viewModel.attach(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getContacts();
    }

    private void initViews() {
        if (contactsAdapter == null) {
            contactsAdapter = new ContactsAdapter(this, contactList);
            contactsAdapter.setHasStableIds(true);
        }

        if (contactsRecyclerView.getAdapter() == null) {
            contactsRecyclerView.setAdapter(contactsAdapter);
        }

        if (contactsRecyclerView.getLayoutManager() == null) {
            contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        mainSwipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.getContacts();
        });
    }

    @Override
    public void showContacts(List<Contact> contactList) {
        if (contactList != null) {
            this.contactList.clear();
            this.contactList.addAll(contactList);
            this.contactsAdapter.notifyDataSetChanged();
        }

        mainSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        mainSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void error() {
        super.error();
        mainSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void error(Throwable e) {
        super.error(e);
        mainSwipeRefreshLayout.setRefreshing(false);
    }
}
