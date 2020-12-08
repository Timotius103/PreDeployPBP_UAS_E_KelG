package platformpbp.uajy.quickresto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platformpbp.uajy.quickresto.API.PesanAPI;
import platformpbp.uajy.quickresto.API.RateAPI;
import platformpbp.uajy.quickresto.dabase.DatabaseClient;
import platformpbp.uajy.quickresto.model.Pesan;
import platformpbp.uajy.quickresto.model.Ratting;
import platformpbp.uajy.quickresto.model.Reservation;
import platformpbp.uajy.quickresto.model.User;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.POST;

public class RateMeMenu extends AppCompatActivity {
    private FloatingActionButton back;
    private Button submit,delete;
    private float ratting;
    private String username;
    private Ratting currentRate;
    private RatingBar ratingRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_me_menu);
        Reservation reserv=new Reservation();
        SharePreferenceClass sp=new SharePreferenceClass(this); //mySql dinyalain
        username = sp.getUsernameS();

        delete = (Button) findViewById(R.id.deleteRate);
        //
        back = (FloatingActionButton) findViewById(R.id.floating_back);
        submit = (Button) findViewById(R.id.submit);
        ratingRatingBar = (RatingBar) findViewById(R.id.rating_rating_bar);
        findRatting(username);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRate(currentRate);
                deleteRateFromAPI(username);
            }
        });

        ratingRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float nilai, boolean b) {
                ratting=nilai;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RateMeMenu.this,Home.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RateMeMenu.this,ThankYouMenu.class);
                startActivity(intent);
                if(currentRate!=null){
                    deleteRate(currentRate);
                    deleteRateFromAPI(username);
                }
                addRatting();
                addRatetoAPI(username, ratting);
            }
        });
    }
    private void addRatting() {
        final float rattingfloat = ratting;
        final String email = username;


        class AddRatting extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Ratting rate = new Ratting();
                rate.setMailRattingPengguna(email);
                rate.setRatting(rattingfloat);


                DatabaseClient.getInstance2(getApplicationContext()).getDatabase()
                        .rateDAO()
                        .insert(rate);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        AddRatting add = new AddRatting();
        add.execute();
    }
    public void setRettingView(float ratting){
        ratingRatingBar.setRating(ratting);
    }

    private void findRatting(final String cari){
        class GetRatting extends AsyncTask<Void, Void, List<Ratting>> {

            @Override
            protected List<Ratting> doInBackground(Void... voids) {
                List<Ratting> rateList = DatabaseClient
                        .getInstance2(getApplicationContext())
                        .getDatabase()
                        .rateDAO()
                        .getCari(cari);
                return rateList;
            }

            @Override
            protected void onPostExecute(List<Ratting> rate) {
                super.onPostExecute(rate);
                if(rate.size()!=0){
                    currentRate=rate.get(rate.size()-1);
                    setRettingView(currentRate.getRatting());
                    if (rate.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Empty List", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    currentRate=null;
                    Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        }
        GetRatting get = new GetRatting();
        get.execute();
    }

    private void deleteRate(final Ratting rate){
        class DeleteRate extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance2(getApplicationContext()).getDatabase()
                        .rateDAO()
                        .delete(rate);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(RateMeMenu.this.getApplicationContext(), "Rate deleted", Toast.LENGTH_SHORT).show();
            }
        }

        DeleteRate delete = new DeleteRate();
        delete.execute();
    }

    private void addRatetoAPI(final String nama, final float rate){
        System.out.println("EMAIL"+ nama);
        System.out.println("RATE"+ rate);
        String ratting = Float.toString(rate);
        Double rateku = Double.parseDouble(ratting);
        System.out.println("ASU"+ rateku);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(POST, RateAPI.URL_STORE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("status").equals("Success"))
                    {
//                        loadFragment(new ViewsBuku());
                    }

                    Toast.makeText(RateMeMenu.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RateMeMenu.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nama_pengguna", nama);
                params.put("rating", rateku.toString());

                return params;
            }
        };

        queue.add(stringRequest);
    }


    public void deleteRateFromAPI(String name) {
        //Tambahkan hapus buku disini
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(DELETE, RateAPI.URL_DELETE + name, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(RateMeMenu.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RateMeMenu.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}
