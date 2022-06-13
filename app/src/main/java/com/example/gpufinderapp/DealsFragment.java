package com.example.gpufinderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DealsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String name;

    TextView nameText;
    ListView listView;
    ImageView refreshImage;

    public DealsFragment(String name) {
        this.name = name;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DealsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DealsFragment newInstance(String param1, String param2, String name) {
        DealsFragment fragment = new DealsFragment(name);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deals, container, false);

        nameText = (TextView) view.findViewById(R.id.id_text_gpu_name);
        listView = view.findViewById(R.id.id_list_view);
        refreshImage = view.findViewById(R.id.id_image_refresh);

        nameText.setText("RTX " + name);
        refreshImage.setImageResource(R.drawable.refresh);

        // ADD INITIAL DATA
        ArrayList<Deal> dealList = new ArrayList<Deal>();
        ArrayAdapter<Deal> dealAdapter = new DealAdapter(getContext(), R.layout.deal_adapter, dealList);
        listView.setAdapter(dealAdapter);

        DataFetchTask fetchTask = new DataFetchTask(dealList, dealAdapter);
        fetchTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri uri = Uri.parse(dealAdapter.getItem(i).getUrl());
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        // REFRESH DATA
        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataFetchTask fetchTask = new DataFetchTask(dealList, dealAdapter);
                fetchTask.execute();
            }
        });

        return view;
    }

    private class DealAdapter extends ArrayAdapter<Deal> {
        Context mainContext;
        List<Deal> myList;
        ImageLoadTask imageLoadTask;

        public DealAdapter(@NonNull Context context, int resource, List<Deal> objects) {
            super(context, resource, objects);
            mainContext = context;
            myList = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) mainContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.deal_adapter, null);

            ImageView image = view.findViewById(R.id.id_image);
            TextView nameText = view.findViewById(R.id.id_text_name);
            TextView priceText = view.findViewById(R.id.id_text_price);
            TextView linkText = view.findViewById(R.id.id_text_link);

            nameText.setText(myList.get(position).getName());
            priceText.setText("$" + myList.get(position).getPrice());
            linkText.setText("Click Here");

            imageLoadTask = new ImageLoadTask(myList.get(position).getImageUrl(), image);
            imageLoadTask.execute();

            return view;
        }
    }

    private class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap myBitmap = null;
            
            try {
                URL connection = new URL(url);

                InputStream input = connection.openStream();
                myBitmap = BitmapFactory.decodeStream(input);

                return myBitmap;
            } catch(Exception e) {
                Log.d("err", e.getMessage());
            }

            return myBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    private class DataFetchTask extends AsyncTask<Void, Void, Void> implements com.example.gpufinderapp.DataFetchTask {
        private List<Deal> list;
        private ArrayAdapter<Deal> dealAdapter;

        public DataFetchTask(List<Deal> list, ArrayAdapter<Deal> dealAdapter) {
            this.list = list;
            this.dealAdapter = dealAdapter;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(name);

            int index = 0;
            list.clear();

            while(reference.child(String.valueOf(index)).getKey() != null && index < 9) {
                reference.child(String.valueOf(index)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean upload = true;

                        String gpu = (String) snapshot.child("name").getValue();

                        gpu = gpu.substring(0, gpu.indexOf(name) + 4);

                        if (gpu.length() > 50)
                            upload = false;

                        String price = String.valueOf(snapshot.child("price").getValue());
                        String url = (String) snapshot.child("link").getValue();
                        String imageUrl = (String) snapshot.child("image").getValue();

                        if (upload) {
                            Deal deal = new Deal(gpu, price, url, imageUrl);
                            list.add(deal);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                index++;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            dealAdapter.notifyDataSetChanged();
        }
    }
}