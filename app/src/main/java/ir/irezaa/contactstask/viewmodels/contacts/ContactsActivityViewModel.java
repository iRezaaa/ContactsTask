package ir.irezaa.contactstask.viewmodels.contacts;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.irezaa.contactstask.api.ContactsApi;
import ir.irezaa.contactstask.viewmodels.BaseViewModel;

/**
 * Created by rezapilehvar on 27/12/2017 AD.
 */

public class ContactsActivityViewModel extends BaseViewModel<ContactsActivityView> {
    private Disposable getContactsDisposable;
    private ContactsApi contactsApi;

    public ContactsActivityViewModel(ContactsApi contactsApi) {
        this.contactsApi = contactsApi;
    }

    public void getContacts() {
        if (getContactsDisposable != null) {
            getContactsDisposable.dispose();
        }
        view.showLoading();

        getContactsDisposable = contactsApi.getAllContacts()
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getContactResponse -> {
                    view.showContacts(getContactResponse);
                }, throwable -> view.error(throwable));
    }
}
