package ir.irezaa.contactstask.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.irezaa.contactstask.App;
import ir.irezaa.contactstask.R;
import ir.irezaa.contactstask.api.ContactsApi;
import ir.irezaa.contactstask.data.ContactsService;
import ir.irezaa.contactstask.models.Contact;
import ir.irezaa.contactstask.ui.adapters.ContactsAdapter;
import ir.irezaa.contactstask.viewmodels.contacts.ContactsActivityView;
import ir.irezaa.contactstask.viewmodels.contacts.ContactsActivityViewModel;

public class ContactsActivity extends BaseActivity<ContactsActivityViewModel> implements ContactsActivityView {

    @BindView(R.id.toolbar_activity_contacts_backButton)
    AppCompatImageView backButton;

    @BindView(R.id.toolbar_activity_contacts_searchButton)
    AppCompatImageView searchButton;

    @BindView(R.id.toolbar_activity_contacts_searchContainer)
    RelativeLayout searchContainer;

    @BindView(R.id.toolbar_activity_contacts_searchEditText)
    AppCompatEditText searchEditText;

    @BindView(R.id.toolbar_activity_contacts_clearButton)
    AppCompatImageView searchClearButton;

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

        ContactsService contactsService = new ContactsService(App.getInstance().getRetrofitInstance().create(ContactsApi.class));
        viewModel = new ContactsActivityViewModel(contactsService);
        viewModel.attach(this);

        viewModel.getAllContacts();
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
            viewModel.getAllContacts();

            searchEditText.clearFocus();
            searchContainer.setVisibility(View.GONE);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);

            mainSwipeRefreshLayout.setEnabled(true);

        });

        backButton.setOnClickListener(view -> onBackPressed());

        searchButton.setOnClickListener(view -> {
            searchContainer.setVisibility(View.VISIBLE);
            searchEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);

            searchEditText.setText("");
            mainSwipeRefreshLayout.setEnabled(false);
        });

        searchClearButton.setOnClickListener(view -> {
            searchEditText.clearFocus();
            searchContainer.setVisibility(View.GONE);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);

            searchEditText.setText("");
            mainSwipeRefreshLayout.setEnabled(true);
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.getFilteredContacts(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void showContacts(List<Contact> contactList, boolean isCache) {
        if (contactList != null) {
            this.contactList.clear();
            this.contactList.addAll(contactList);
            this.contactsAdapter.notifyDataSetChanged();
        }

        if (!isCache) {
            mainSwipeRefreshLayout.setRefreshing(false);
        }
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
