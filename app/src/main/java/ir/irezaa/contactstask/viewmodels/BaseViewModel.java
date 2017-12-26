package ir.irezaa.contactstask.viewmodels;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by rezapilehvar on 27/12/2017 AD.
 */

public abstract class BaseViewModel<T extends IView> {
    protected CompositeDisposable compositeDisposable;
    protected T view;

    public BaseViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    public void attach(T view) {
        this.view = view;
    }

    public void detach() {
        view = null;
    }

    public void clearSubscriptions() {
        compositeDisposable.clear();
    }
}
