package ir.irezaa.contactstask.data;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import ir.irezaa.contactstask.api.ContactsApi;
import ir.irezaa.contactstask.models.Contact;

/**
 * Created by rezapilehvar on 27/12/2017 AD.
 */

public class ContactsService {
    private ContactsApi contactsApi;

    public ContactsService(ContactsApi contactsApi) {
        this.contactsApi = contactsApi;
    }

    public Observable<List<Contact>> syncContacts() {
        return contactsApi.getAllContacts()
                .map(contactList -> {
                    // copy the result into realm for future use
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    for (Contact contact : contactList) {
                        contact.setPrimaryKey();
                        realm.copyToRealmOrUpdate(contact);
                    }
                    realm.copyToRealmOrUpdate(contactList);
                    realm.commitTransaction();
                    realm.close();

                    return contactList;
                });
    }

    public List<Contact> getAllContacts() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Contact> query = realm.where(Contact.class);
        RealmResults<Contact> items = query.findAll();
        List<Contact> results = realm.copyFromRealm(items);
        realm.close();
        return results;
    }

    public List<Contact> getFilteredByNumberContact(String number) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Contact> query = realm.where(Contact.class).beginsWith("phoneNumber", number);
        RealmResults<Contact> items = query.findAll();
        List<Contact> results = realm.copyFromRealm(items);
        realm.close();
        return results;
    }
}
