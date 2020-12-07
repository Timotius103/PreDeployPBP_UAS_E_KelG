package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platformpbp.uajy.quickresto.API.PesanAPI;
import platformpbp.uajy.quickresto.dabase.DatabaseClient;
import platformpbp.uajy.quickresto.model.Pesan;
import platformpbp.uajy.quickresto.model.Reservation;
import platformpbp.uajy.quickresto.model.User;
import platformpbp.uajy.quickresto.pdf.PdfViewModel;

import static com.android.volley.Request.Method.PUT;

public class EditReservaton extends AppCompatActivity {
    private FloatingActionButton backmr;
    private Button edit,delete;
    String name,no,tgl,wktu,alamat;
    private Pesan currentpesan;
    private AlertDialog.Builder builderbtn;

    Button editdata,chooseDate,btnCetak;
    TextInputEditText dateReserve,number,time;
    TextInputLayout banyakLayout,tanggalLayout,waktupesanLayout;
    TextView title,user;
    ImageView gambar;
    FloatingActionButton balik;
    double lon,la;
    private String namaresto,alamatRest,url,email,date,waktu;


    private PdfViewModel pdfViewModel;
    private static final String TAG = "PdfCreatorActivity";
    private File pdfFile;
    private PdfWriter writer;
    private List<Pesan> listPesan = new ArrayList<>();
    final private int REQUEST_CODE_ASK_PERMISSIONS = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservaton);

        String username;
        Reservation reserv=new Reservation();
        SharePreferenceClass sp=new SharePreferenceClass(this); //mySql dinyalain
        dateReserve=findViewById(R.id.edtdate);
        chooseDate=findViewById(R.id.edtchooseDate);
        number=findViewById(R.id.edtnumber);
        time=findViewById(R.id.edttime);

        reserv = sp.getReservation();
        name=reserv.getNameRest();
        alamat=reserv.getAddress();
        tgl=reserv.getDateResrv();
        no=String.valueOf(reserv.getJmlhOrg());
        wktu=reserv.getTimeResrv();

        username = sp.getUsernameS();
        findEmail(username);
//        username = sp.getUsernameS();
//        user=findViewById(R.id.UserName);
//        user.setText(username);

        //sleect dari mysql



//        number.setText(no);
//        dateReserve.setText(tgl);
//        time.setText(wktu);


        banyakLayout=findViewById(R.id.input_number_layoutEdit);
        tanggalLayout=findViewById(R.id.input_date_layoutEdt);
        waktupesanLayout=findViewById(R.id.input_time_layoutEdt);


//        username = sp.getUsernameS();
//        user=findViewById(R.id.UserName);
//        user.setText(username);

        editdata=findViewById(R.id.edit);
        balik=findViewById(R.id.floating_back);
        balik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditReservaton.this,myReservation2.class);
                intent.putExtra("longitude",lon);
                intent.putExtra("latitude",la);
                intent.putExtra("gambar",url);
                intent.putExtra("resto",namaresto);
                intent.putExtra("alamat",alamatRest);
                startActivity(intent);
            }
        });
        editdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String banyakbaru = number.getText().toString();
                String tanggalbaru= dateReserve.getText().toString();
                String wktubaru=time.getText().toString();
                currentpesan.setJmlhOrang(banyakbaru);
                currentpesan.setTanggal(tanggalbaru);
                currentpesan.setWaktu(wktubaru);
                updatePesanantoApi(username, banyakbaru,tanggalbaru,wktubaru);
                update(currentpesan);
            }
        });

        MaterialDatePicker.Builder builder= MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");

        MaterialDatePicker materialDatePicker=builder.build();
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");

            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dateReserve.setText(materialDatePicker.getHeaderText());
                date=dateReserve.getText().toString();
            }
        });

        btnCetak=findViewById(R.id.cetakpdf);
        btnCetak.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                builderbtn = new AlertDialog.Builder(EditReservaton.this);

                builderbtn.setCancelable(false);
                builderbtn.setMessage("Apakah anda yakin ingin mencetak surat pemesanan ?");
                builderbtn.setPositiveButton("Ya", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        try
                        {
                            createPdfWrapper();
                        }
                        catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                        catch (DocumentException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                builderbtn.setNegativeButton("Tidak", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builderbtn.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.WHITE);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE);
            }
        });
    }
    public void updatePesanantoApi(final String name, final String banyakbaru, final String tanggalbaru,final String wktubaru){
        //Pendeklarasian queue
        System.out.println("EMAIL"+ name);
        RequestQueue queue = Volley.newRequestQueue(this);

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(PUT, PesanAPI.URL_UPDATE + name, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);

                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(EditReservaton.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                Toast.makeText(EditReservaton.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nama_pengguna",name);
                params.put("nama_resto",currentpesan.getNamaResto());
                params.put("alamat",currentpesan.getAlamat());
                params.put("jumlah_orang", banyakbaru);
                params.put("tanggal", tanggalbaru);
                params.put("jam", wktubaru);

                return params;
            }
        };

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
    private void update(final Pesan pesan){
        class UpdatePesanan extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance2(EditReservaton.this).getDatabase()
                        .reservDAO()
                        .update(pesan);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(EditReservaton.this, "Pesan updated", Toast.LENGTH_SHORT).show();
            }
        }

        UpdatePesanan update = new UpdatePesanan();
        update.execute();
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
                currentpesan = pesans.get(0); //cp berisi id, pokoknya 1 row data

                number.setText(pesans.get(0).getJmlhOrang());
                dateReserve.setText(pesans.get(0).getTanggal());
                time.setText(pesans.get(0).getWaktu());
                if (pesans.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty List", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetPesanan get = new GetPesanan();
        get.execute();
    }

/////////////////// PDF DAN SEKITARNYA ///////////////
    private void createPdf() throws FileNotFoundException, DocumentException
    {
        //isikan code createPdf()
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Download/");
        if (!docsFolder.exists())
        {
            docsFolder.mkdir();
            Log.i(TAG, "Direktori baru untuk file pdf berhasil dibuat");
        }

        //TODO 2.1 - Ubah NPM menjadi NPM anda
        String pdfname = "Your Receipt"+".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        writer = PdfWriter.getInstance(document, output);
        document.open();
        //TODO 2.2 - Ubah XXXX menjadi NPM anda
        Paragraph judul = new Paragraph(" KELENGKAPAN KOLEKSI BUKU 9780 \n\n", new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 16,
                com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);
        PdfPTable tables = new PdfPTable(new float[]{16, 8});
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);
        //TODO 2.3 - Ubah Nama Praktikan menjadi nama anda
        Paragraph Kepada= new Paragraph(
                "Kepada Yth : \n" + "Hilarius Ryan Auxilio Benedecas"+"\n",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK)
        );
        cellSupplier.addElement(Kepada);
        tables.addCell(cellSupplier);
        PdfPCell cellPO = new PdfPCell();
        //TODO 2.4 - Ubah NPM Praktikan dengan NPM anda dan ubah Tanggal Praktikum sesuai tanggal praktikum modul 11 kelas anda
        Paragraph NomorTanggal = new Paragraph(
                "No : " + "180709780" + "\n\n" +
                        "Tanggal : " + "25 November 2020" + "\n",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK) );
        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan koleksi buku dari mahasiswa 9780 : \n\n",f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        PdfPTable tableHeader = new PdfPTable(new float[]{5,5,5});
        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);
        //TODO 2.5 - Bagian ini tidak perlu diubah
        PdfPCell h1 = new PdfPCell(new Phrase("Nama Buku"));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(5);
        PdfPCell h2 = new PdfPCell(new Phrase("Pengarang"));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        PdfPCell h4 = new PdfPCell(new Phrase("Harga Beli"));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);
        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h4);
        PdfPCell[] cells = tableHeader.getRow(0).getCells();

        for (int j = 0; j < cells.length; j++)
        {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{5,5,5});
        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        int arrLength = listPesan.size();
        for(int x=0; x<arrLength; x++)
        {
            for(int i=0;i<cells.length;i++)
            {
                if(i==0)
                {
                    tableData.addCell(listPesan.get(x).getNamaResto());
                }
                else if(i==1)
                {
                    tableData.addCell(listPesan.get(x).getTanggal());
                }
                else
                {
                    tableData.addCell(listPesan.get(x).getWaktu());
                }
            }
        }
        document.add(tableData);
        com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10, com.itextpdf.text.Font.NORMAL);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String tglDicetak = sdf.format(currentTime);
        Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener)
    {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException
    {
        //isikan code createPdfWrapper()
        int hasWriteStoragePermission = 0;
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS))
                {
                    showMessageOKCancel("Izinkan aplikasi untuk akses penyimpanan?",
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                    {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            }

            return;
        }
        else
        {
            createPdf();
        }
    }

    private void previewPdf()
    {
        //isikan code previewPdf()
        PackageManager packageManager = this.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0)
        {
            Uri uri;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                uri = FileProvider.getUriForFile(this, this.getPackageName()+".provider", pdfFile);
            }
            else
            {
                uri = Uri.fromFile(pdfFile);
            }

            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(uri, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //TODO 2.6 - Sesuaikan package dengan package yang anda buat
            this.grantUriPermission("com.pbp.gd11_a_9780.ui.pdf", uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(pdfIntent);
        }
        else
        {
            FancyToast.makeText(this,"Unduh pembuka PDF untuk menampilkan file ini",
                    FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
        }
    }

}