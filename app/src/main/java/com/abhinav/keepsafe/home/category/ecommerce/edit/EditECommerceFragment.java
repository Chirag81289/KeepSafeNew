package com.abhinav.keepsafe.home.category.ecommerce.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.abhinav.keepsafe.BaseFragment;
import com.abhinav.keepsafe.Constants;
import com.abhinav.keepsafe.R;
import com.abhinav.keepsafe.entities.ECommerce;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by abhinav.sharma on 22/10/17.
 */

public class EditECommerceFragment extends BaseFragment implements EditECommerceView {

    @BindView(R.id.tv_platform_name)
    TextView tvPlatformName;
    @BindView(R.id.et_email_id_used)
    EditText etEmailIdUsed;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_ecommerce_username)
    EditText etEcommerceUsername;
    @BindView(R.id.fab_delete)
    FloatingActionButton fabDelete;
    @BindView(R.id.fab_save)
    FloatingActionButton fabSave;
    Unbinder unbinder;
    private ECommerce eCommerce;
    private int eCommerceId;
    private EditECommercePresenter presenter;


    public static EditECommerceFragment getInstance(int eCommerceId) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ExtrasKey.ECOMMERCE_ID, eCommerceId);
        EditECommerceFragment editECommerceFragment = new EditECommerceFragment();
        editECommerceFragment.setArguments(bundle);
        return editECommerceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_ecommerce, container, false);
        unbinder = ButterKnife.bind(this, view);
        eCommerceId = getArguments().getInt(Constants.ExtrasKey.ECOMMERCE_ID, -1);
        presenter = new EditECommercePresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.fetchECommerceDetails(eCommerceId);
        setupEditMode();
    }

    private void setupEditMode() {
        etPassword.setEnabled(true);
        etEmailIdUsed.setEnabled(true);
        etEcommerceUsername.setEnabled(true);
    }

    @Override
    public void popFragment() {
        getFragmentManager().popBackStackImmediate();
    }

    @Override
    public void showECommerceDetails(ECommerce eCommerce) {
        this.eCommerce = eCommerce;
        tvPlatformName.setText(eCommerce.getPlatformName());
        etEcommerceUsername.setText(eCommerce.getUsername());
        etEmailIdUsed.setText(eCommerce.getEmailIdUsed());
        etPassword.setText(eCommerce.getPassword());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    @OnClick({R.id.fab_delete, R.id.fab_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_delete:
                presenter.deleteECommerceAccount(eCommerce);
                break;
            case R.id.fab_save:
                presenter.updateECommerceAccount(getECommerceObj());
                break;
        }
    }

    private ECommerce getECommerceObj() {
        eCommerce.setUsername(etEcommerceUsername.getText().toString().trim());
        eCommerce.setPassword(etPassword.getText().toString().trim());
        eCommerce.setEmailIdUsed(etEmailIdUsed.getText().toString().trim());
        return eCommerce;
    }
}
