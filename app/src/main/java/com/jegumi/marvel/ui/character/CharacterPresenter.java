package com.jegumi.marvel.ui.character;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.R;
import com.jegumi.marvel.adapters.ItemsAdapter;
import com.jegumi.marvel.adapters.UrlsAdapter;
import com.jegumi.marvel.model.DataWrapperItem;
import com.jegumi.marvel.model.Item;
import com.jegumi.marvel.model.ItemList;
import com.jegumi.marvel.model.URL;
import com.jegumi.marvel.network.Api;
import com.jegumi.marvel.ui.base.BasePresenter;
import com.jegumi.marvel.ui.views.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class CharacterPresenter extends BasePresenter {

    private static final String TAG = CharacterPresenter.class.getSimpleName();
    private Api api;

    public CharacterPresenter(Context context) {
        super(context);
        api = MarvelApplication.getApi();
    }

    public void setSection(View root, int resIdSection, int resIdTitle, ItemList itemList) {
        ItemsAdapter adapter = new ItemsAdapter(context, new ArrayList<Item>());

        View container = root.findViewById(resIdSection);
        TextView titleTextView = (TextView) container.findViewById(R.id.section_title_text_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) container.findViewById(R.id.section_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        titleTextView.setText(resIdTitle);
        loadItems(itemList.collectionURI, adapter);
    }

    public void setUrls(View root, int resIdSection, int resIdTitle, ArrayList<URL> urls) {
        View container = root.findViewById(resIdSection);
        TextView titleTextView = (TextView) container.findViewById(R.id.section_title_text_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) container.findViewById(R.id.section_recycler_view);
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.divider, null);
        recyclerView.addItemDecoration(new DividerItemDecoration(drawable));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new UrlsAdapter(urls));

        titleTextView.setText(resIdTitle);
    }

    private void loadItems(String url, final ItemsAdapter adapter) {
        api.loadItems(api.getItemsEndPoint(url), new Response.Listener<DataWrapperItem>() {
            @Override
            public void onResponse(DataWrapperItem response) {
                adapter.updateData(response.data.results);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error loading list", volleyError);
                int resIdMessage = volleyError.getCause() instanceof UnknownHostException ?
                        R.string.error_no_connectivity : R.string.error_loading_items;

                Toast.makeText(context, context.getString(resIdMessage), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showItemDialog(Item item) {
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        dialog.setContentView(R.layout.full_screen_item);
        TextView closeDialog = (TextView) dialog.findViewById(R.id.close_text_view);
        TextView title = (TextView) dialog.findViewById(R.id.name_text_view);
        ImageView thumbnail = (ImageView) dialog.findViewById(R.id.thumbnail);

        title.setText(item.title);
        Picasso.with(context).load(item.thumbnail.getPictureUrl()).into(thumbnail);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}
