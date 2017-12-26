package ir.irezaa.contactstask.api;

import java.util.List;

import io.reactivex.Flowable;
import ir.irezaa.contactstask.models.Contact;
import retrofit2.http.GET;

/**
 * Created by rezapilehvar on 26/12/2017 AD.
 */

public interface ContactsApi {
    @GET("contacts.php")
    Flowable<List<Contact>> getAllContacts();
}
