package pro.comhelps.readdatajson.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.activities.Cart2ViewModel;
import pro.comhelps.readdatajson.adapters.CartAdapter;
import pro.comhelps.readdatajson.models.Cart2;


import java.util.List;

import static pro.comhelps.readdatajson.R.layout.fragment_get_products;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetProductsFragment extends Fragment {

    ImageView cartImg;
    TextView cartTitle, cartPrice, cartQty;
    private Cart2ViewModel cart2ViewModel;

    public static final String EXTRA_IMG =
            "pro.comhelps.readdatajson.fragments.EXTRA_IMG";
    public static final String EXTRA_TITLE =
            "pro.comhelps.readdatajson.fragments.EXTRA_TITLE";
    public static final String EXTRA_PRICE =
            "pro.comhelps.readdatajson.fragments.EXTRA_PRICE";
    public GetProductsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(fragment_get_products, container, false);



        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final CartAdapter cartAdapter = new CartAdapter();
        recyclerView.setAdapter(cartAdapter);

        Cart2ViewModel cart2ViewModel = ViewModelProviders.of(this).get(Cart2ViewModel.class);

        cart2ViewModel.getGoods().observe(this, new Observer<List<Cart2>>() {
            @Override
            public void onChanged(@Nullable List<Cart2> cart2s) {



                cartAdapter.setCart2s(cart2s);
                //update RecyclerView
               // Toast.makeText(getActivity(), "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}