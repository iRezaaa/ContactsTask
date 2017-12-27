package ir.irezaa.contactstask.viewmodels.contacts;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.irezaa.contactstask.data.ContactsService;
import ir.irezaa.contactstask.viewmodels.BaseViewModel;

/**
 * Created by rezapilehvar on 27/12/2017 AD.
 */

public class ContactsActivityViewModel extends BaseViewModel<ContactsActivityView> {
    private Disposable getContactsDisposable;
    private ContactsService contactsService;

    public ContactsActivityViewModel(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    public void getAllContacts() {
        if (getContactsDisposable != null) {
            getContactsDisposable.dispose();
        }
        view.showLoading();
        view.showContacts(contactsService.getAllContacts(), true);

        getContactsDisposable = contactsService.syncContacts()
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getContactResponse -> {
                    view.showContacts(getContactResponse, false);
                }, throwable -> view.error(throwable));

    }

    public void getFilteredContacts(String filter){
        if(filter.length() > 0) {
            view.showContacts(contactsService.getFilteredByNumberContact(filter), true);
        }else{
            view.showContacts(contactsService.getAllContacts(),true);
        }
    }
}
