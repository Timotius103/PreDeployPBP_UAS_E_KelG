//package platformpbp.uajy.quickresto.UnitTesting;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textfield.TextInputEditText;
//
//import static android.widget.Toast.LENGTH_SHORT;
//
//public class ProfileActivity {
//    private MaterialButton btnLogin;
//    private TextInputEditText nim, password;
//    private LoginPresenter presenter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        btnLogin = findViewById(R.id.btnLogin);
//        nim = findViewById(R.id.loginNim);
//        password = findViewById(R.id.loginPassword);
//        presenter = new LoginPresenter(this, new LoginService());
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                presenter.onLoginClicked();
//            }
//        });
//    }
//    @Override
//    public String getNim() {
//        return nim.getText().toString();
//    }
//    @Override
//    public void showNimError(String message) {
//        nim.setError(message);
//    }
//    @Override
//    public String getPassword() {
//        return password.getText().toString();
//    }
//    @Override
//    public void showPasswordError(String message) {
//        password.setError(message);
//    }
//    @Override
//    public void startMainActivity() {
//        new ActivityUtil(this).startMainActivity();
//    }
//    @Override
//    public void startUserProfileActivity(UserDAO user) {
//        new ActivityUtil(this).startUserProfile(user);
//    }
//    @Override
//    public void showLoginError(String message) {
//        Toast.makeText(this, message, LENGTH_SHORT).show();
//    }
//    @Override
//    public void showErrorResponse(String message) {
//        Toast.makeText(this, message, LENGTH_SHORT).show();
//    }
//}
