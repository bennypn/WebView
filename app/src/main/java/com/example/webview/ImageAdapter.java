package com.example.webview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.type.DateTime;

import java.io.InputStream;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class ImageAdapter extends FirebaseRecyclerAdapter<Image,ImageAdapter.ImagesViewholder> {

    Long epochh;
    Float db1;
    protected static String date, imageLink, db;
    public ImageAdapter(
            @NonNull FirebaseRecyclerOptions<Image> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "Image.xml") iwth data in
    // model class(here "Image.class")
    @Override
    protected void
    onBindViewHolder(@NonNull ImagesViewholder holder,
                     int position, @NonNull Image model)
    {
        epochh = model.getEpoch();
        date = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm:s").format(new java.util.Date (epochh*1000));
        // epochtv = Long.toString(model.getEpoch());
        db1 = model.getDb();
        db = db1.toString();
        imageLink = model.getLink();

        // Add firstname from model class (here
        // "Image.class")to appropriate view in Card
        // view (here "Image.xml")
        holder.epoch.setText(date);

        // Add lastname from model class (here
        // "Image.class")to appropriate view in Card
        // view (here "Image.xml")
        holder.dbtv.setText(db);

        new DownloadImageTask(holder.imageView)
                .execute(imageLink);

    }

    // Function to tell the class about the Card view (here
    // "Image.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public ImagesViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ImageAdapter.ImagesViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "Image.xml")
    class ImagesViewholder
            extends RecyclerView.ViewHolder {
        TextView epoch, dbtv;
        ImageView imageView;
        public ImagesViewholder(@NonNull View itemView)
        {
            super(itemView);

            epoch = itemView.findViewById(R.id.epoch_tv);
            dbtv = itemView.findViewById(R.id.db_tv);
            imageView = itemView.findViewById(R.id.imageView2);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
