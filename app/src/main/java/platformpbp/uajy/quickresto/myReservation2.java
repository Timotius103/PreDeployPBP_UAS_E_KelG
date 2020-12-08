package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
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
import java.util.List;
import java.util.Map;

import platformpbp.uajy.quickresto.API.PesanAPI;
import platformpbp.uajy.quickresto.dabase.DatabaseClient;
import platformpbp.uajy.quickresto.model.Pesan;
import platformpbp.uajy.quickresto.model.Reservation;
import platformpbp.uajy.quickresto.model.User;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.POST;

public class myReservation2 extends AppCompatActivity {
    private FloatingActionButton backmr;
    private Button edit,delete;
    TextView nameResto,numb,address,date,time,user;
    String name,no,tgl,wktu,alamat;
    private Pesan currentpesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation2);
        String username;
        Reservation reserv=new Reservation();
        SharePreferenceClass sp=new SharePreferenceClass(this); //mySql dinyalain
        nameResto=findViewById(R.id.subtitle_reserve);
        address=findViewById(R.id.address);
        numb =findViewById(R.id.numberPepel);
        date=findViewById(R.id.datereserveproses);
        time=findViewById(R.id.timeproses);

        username = sp.getUsernameS();
        findEmail(username);
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


//        nameResto.setText(name);
//        address.setText(alamat);
//        numb.setText(no);
//        date.setText(tgl);
//        time.setText(wktu);

//        nameResto.setText(reservt.nameRest);
//        address.setText(reservt.address);
//        numb.setText(reservt.jmlhOrg);
//        date.setText(reservt.dateResrv);
//        time.setText(reservt.timeResrv);
        delete=findViewById(R.id.deleteMyRes);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(currentpesan);
                deletePesandariAPI(username);
            }
        });
        edit=(Button) findViewById(R.id.editMyRes);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myReservation2.this,EditReservaton.class);
                startActivity(intent);
//                addReserveAPI(username,nameResto.getText().toString(),address.getText().toString(), numb.getText().toString(), date.getText().toString(), time.getText().toString());
            }
        });

        backmr = (FloatingActionButton) findViewById(R.id.floating_backs);
        backmr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myReservation2.this,Home.class);
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

    private void findEmail(final String cari){
        class GetPesanan extends AsyncTask<Void, Void, List<Pesan>> {

            @Override
            protected List<Pesan> doInBackground(Void... voids) {
                List<Pesan> userList = DatabaseClient
                        .getInstance2(getApplicationContext())
                        .getDatabase()
                        .reservDAO()
                        .getCari(cari);
                return userList;
            }

            @Override
            protected void onPostExecute(List<Pesan> pesans) {
                super.onPostExecute(pesans);
                if(pesans.size()!=0){
                    currentpesan=pesans.get(0);
                    nameResto.setText(pesans.get(0).getNamaResto());
                    address.setText(pesans.get(0).getAlamat());
                    numb.setText(pesans.get(0).getJmlhOrang());
                    date.setText(pesans.get(0).getTanggal());
                    time.setText(pesans.get(0).getWaktu());
                    if (pesans.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Empty List", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetPesanan get = new GetPesanan();
        get.execute();
    }

    private void delete(final Pesan pesan){
        class DeletePesanan extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance2(getApplicationContext()).getDatabase()
                        .reservDAO()
                        .delete(pesan);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(myReservation2.this.getApplicationContext(), "User deleted", Toast.LENGTH_SHORT).show();
            }
        }

        DeletePesanan delete = new DeletePesanan();
        delete.execute();
    }

    public void deletePesandariAPI(String name) {
        //Tambahkan hapus buku disini
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(DELETE, PesanAPI.URL_DESTROY + name, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(myReservation2.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(myReservation2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}