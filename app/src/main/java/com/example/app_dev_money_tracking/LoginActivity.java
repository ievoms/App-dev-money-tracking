package com.example.app_dev_money_tracking;

import static android.view.View.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    TextView emailError, passwordError;
    private User_settings settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(onLoginButtonClick());
        ImageView googleButton = findViewById(R.id.loginGoogleIcon);
        googleButton.setOnClickListener(onSocialButtonClick());
        ImageView facebookButton = findViewById(R.id.loginFacebookIcon);
        facebookButton.setOnClickListener(onSocialButtonClick());
        Button signupButton = findViewById(R.id.loginSignUp);
        signupButton.setOnClickListener(onSignupButtonClick());

        emailInput = findViewById(R.id.loginEmailEditText);
        emailError = findViewById(R.id.loginEmailError);
        passwordInput = findViewById(R.id.loginPasswordEditText);
        passwordError = findViewById(R.id.loginPasswordError);

        ClearErrorMessage(emailInput, emailError);
        ClearErrorMessage(passwordInput, passwordError);

        settings = User_settings.instanciate("user1", getApplicationContext());


    }

    private OnClickListener onSignupButtonClick() {
        return v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    private OnClickListener onSocialButtonClick() {
        return v -> Toast.makeText(LoginActivity.this, "Feature coming soon", Toast.LENGTH_SHORT).show();
    }

    private OnClickListener onLoginButtonClick() {
        return v -> {
            startActivity(new Intent(LoginActivity.this, Home_activity.class));
//            HelperFunctions.hideSoftKeyboard(LoginActivity.this, v);
//            LoginValidator loginValidator = new LoginValidator().invoke();
//            String email = loginValidator.getEmail();
//            String password = loginValidator.getPassword();
//            if (loginValidator.isValid()) {
//                LoginDatabaseHelper db = new LoginDatabaseHelper(LoginActivity.this);
//                UserModel userModel = db.getUser(email);
//                if (userModel != null) {
//                    if (userModel.getPassword().equals(password)) {
////                        Toast.makeText(LoginActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
//                        settings.setUserEmail(email);
//                        startActivity(new Intent(LoginActivity.this, Home_activity.class));
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(LoginActivity.this, "User with this email does not exists", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
        };
    }


    private void setErrorMessage(TextView errorField, String errorMessage) {
        errorField.setText(errorMessage);
    }

    private void ClearErrorMessage(EditText input, final TextView errorField) {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) setErrorMessage(errorField, "");

            }
        });
    }

    private class LoginValidator {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public LoginValidator invoke() {
            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();
            if (email.isEmpty()) setErrorMessage(emailError, "Email is required");
            if (password.isEmpty()) setErrorMessage(passwordError, "Password is required");
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                setErrorMessage(emailError, "Must be an email");

            return this;
        }

        private boolean isValid() {
            return !password.isEmpty() && !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}