package ir.irezaa.contactstask.viewmodels.contacts;

import java.util.List;

import ir.irezaa.contactstask.models.Contact;
import ir.irezaa.contactstask.viewmodels.IView;

/**
 * Created by rezapilehvar on 27/12/2017 AD.
 */

public interface ContactsActivityView extends IView {
    void showContacts(List<Contact> contactList);
    void showLoading();
}
