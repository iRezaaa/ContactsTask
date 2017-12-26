package ir.irezaa.contactstask.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ir.irezaa.contactstask.viewmodels.BaseViewModel;
import ir.irezaa.contactstask.viewmodels.IView;

/**
 * Created by rezapilehvar on 26/12/2017 AD.
 */

public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity implements IView {
    protected T viewModel;

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.clearSubscriptions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.detach();
    }

    @Override
    public void error(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void error() {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
    }
}
