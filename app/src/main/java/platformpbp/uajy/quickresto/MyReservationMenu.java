package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import platformpbp.uajy.quickresto.API.PesanAPI;
import platformpbp.uajy.quickresto.API.RegistAPI;
import platformpbp.uajy.quickresto.dabase.DatabaseClient;
import platformpbp.uajy.quickresto.model.Pesan;
import platformpbp.uajy.quickresto.model.Reservation;
import platformpbp.uajy.quickresto.model.User;

import static com.android.volley.Request.Method.POST;

public class MyReservationMenu extends AppCompatActivity {
    private FloatingActionButton backmr;
    private Button back,delete;
    TextView nameResto,numb,address,date,time,user;
    String name,no,tgl,wktu,alamat;
//    Reservation reservt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);
        String username;
        Reservation reserv=new Reservation();
        SharePreferenceClass sp=new SharePreferenceClass(this); //mySql dinyalain
        nameResto=findViewById(R.id.subtitle_reserve);
        address=findViewById(R.id.address);
        numb =findViewById(R.id.numberPepel);
        date=findViewById(R.id.datereserveproses);
        time=findViewById(R.id.timeproses);

        username = sp.getUsernameS();
//        username = sp.getUsernameS();
//        user=findViewById(R.id.UserName);
//        user.setText(username);

        //sleect dari mysql

        reserv = sp.getReservation();
        name=reserv.getNameRest();
        alamat=reserv.getAddress();
        tgl=reserv.getDateResrv();
        no=String.valueOf(reserv.getJmlhOrg());
        wktu=reserv.getTimeResrv();


        nameResto.setText(name);
        address.setText(alamat);
        numb.setText(no);
        date.setText(tgl);
        time.setText(wktu);

//        nameResto.setText(reservt.nameRest);
//        address.setText(reservt.address);
//        numb.setText(reservt.jmlhOrg);
//        date.setText(reservt.dateResrv);
//        time.setText(reservt.timeResrv);

        back=(Button) findViewById(R.id.backtohome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyReservationMenu.this,Home.class);
                startActivity(intent);
                addReserveAPI(username,nameResto.getText().toString(),address.getText().toString(), numb.getText().toString(), date.getText().toString(), time.getText().toString());
                addPesananToPresisten(username,nameResto.getText().toString(),address.getText().toString(), numb.getText().toString(), date.getText().toString(), time.getText().toString());

            }
        });

        backmr = (FloatingActionButton) findViewById(R.id.floating_backs);
        backmr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyReservationMenu.this,Home.class);
                intent.putExtra("resto4",name);
                intent.putExtra("alamat4",alamat);
                intent.putExtra("number",no);
                intent.putExtra("Date1",tgl);
                intent.putExtra("Time1",wktu);
                String masuk="yes";
                intent.putExtra("masuk",masuk);
                startActivity(intent);
            }
        });
    }
    private void addPesananToPresisten(final String nama, final String resto, final String alamat, final String no, final String tanggal, final String waktu) {

        class AddPesanan extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Pesan pesan = new Pesan();
                pesan.setMailpengguna(nama);
                pesan.setAlamat(alamat);
                pesan.setNamaResto(resto);
                pesan.setJmlhOrang(no);
                pesan.setTanggal(tanggal);
                pesan.setWaktu(waktu);


                DatabaseClient.getInstance2(getApplicationContext()).getDatabase()
                        .reservDAO()
                        .insert(pesan);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        AddPesanan add = new AddPesanan();
        add.execute();
    }

    private void addReserveAPI(final String nama, final String alamat, final String resto, final String no, final String tanggal, final String waktu){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(POST, PesanAPI.URL_STOREPESAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("status").equals("Success"))
                    {
//                        loadFragment(new ViewsBuku());
                    }

                    Toast.makeText(MyReservationMenu.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyReservationMenu.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nama_pengguna", nama);
                params.put("nama_resto", alamat);
                params.put("alamat",resto);
                params.put("jumlah_orang", no);
                params.put("tanggal", tanggal);
                params.put("jam", waktu);

                return params;
            }
        };

        queue.add(stringRequest);
    }
}
