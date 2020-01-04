package com.example.form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Form form = findViewById(R.id.form);
        form.setOrientation(LinearLayout.VERTICAL);

        LayoutInflater lf = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lf.inflate(R.layout.formfields, null);

        View nameField = view.findViewById(R.id.name);
        View surnameField = view.findViewById(R.id.surname);
        EditText emailField = view.findViewById(R.id.email);
        emailField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        final PasswordStrengthMeter psm = new PasswordStrengthMeter(this);
        psm.getPasswordField().setMinimumHeight(130);
        psm.getPasswordField().setPadding(10,0,0,5);
        psm.getPasswordField().setBackgroundResource(R.drawable.shape);
        psm.getStrengthMeter().setPadding(10,-11,10,10);
        psm.getDescriptionField().setId(R.id.descriptionField);
        psm.getPasswordField().setId(R.id.passwordField);

        PasswordValidation passwordValidation = new PasswordValidation() {
            @Override
            public void validatePassword(String s) {
                String numberPattern = "(?=.*\\d)";
                String upperCasePattern = "(?=.*[A-Z])";
                if(s.length() == 0){
                    psm.setProgress(0);
                }
                if(s.length()<psm.getMinimumPasswordLength()){
                    psm.getStrengthMeter().setProgress(0);
                    psm.getDescriptionField().setText("Password must be atleast " + psm.getMinimumPasswordLength() + " long.");
                    psm.setValid(false);
                    return;
                }
                Pattern nPattern = Pattern.compile(numberPattern);
                Pattern ucPattern = Pattern.compile(upperCasePattern);
                Matcher mNumber = nPattern.matcher(s);
                Matcher mUppercase = ucPattern.matcher(s);
                Boolean num = mNumber.find();
                Boolean up = mUppercase.find();

                if((!mNumber.find() && !mUppercase.find())){
                    psm.setProgress(30);
                    psm.getStrengthMeter().setProgress(30);
                    psm.getStrengthMeter().getProgressDrawable().setColorFilter(psm.getWeakColor(), PorterDuff.Mode.MULTIPLY);
                    psm.getDescriptionField().setText("Password is weak");
                    psm.setValid(true);
                    return;
                }
                if(mNumber.find() ^ mUppercase.find()){
                    psm.setProgress(60);
                    psm.getStrengthMeter().setProgress(60);
                    psm.getStrengthMeter().getProgressDrawable().setColorFilter(psm.getDecentColor(), PorterDuff.Mode.MULTIPLY);
                    psm.getDescriptionField().setText("Password is decent");
                    psm.setValid(true);
                    return;
                }
                psm.setProgress(100);
                psm.getStrengthMeter().setProgress(100);
                psm.getStrengthMeter().getProgressDrawable().setColorFilter(psm.getStrongColor(), PorterDuff.Mode.MULTIPLY);
                psm.getDescriptionField().setText("Password is STRONG");
                psm.setValid(true);
                return;
            }
        };

        psm.setValidationMethod(passwordValidation);

        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(form);
            }
        });


        ValidateField validateField = new ValidateField() {
            @Override
            public boolean isFieldValid(View field) {
                if (field instanceof EditText) {
                    EditText temp = (EditText) field;
                    if(temp.getInputType() == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS){
                        if(Patterns.EMAIL_ADDRESS.matcher(temp.getText()).matches()){
                            return true;
                        }
                        return false;
                    }
                    if (!temp.getText().toString().equals("")) {
                        return true;
                    }
                    return false;
                } else if (field instanceof PasswordStrengthMeter) {
                    PasswordStrengthMeter ps = (PasswordStrengthMeter) field;
                    if (ps.getValid()) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }

        };


        form.setValidateField(validateField);

        form.setFormFieldHandler(new FormFieldHandler() {
            @Override
            public void addField(TextView title, final View field) {
                final View arg = field;

                if(field instanceof EditText) {
                    final EditText tempo =(EditText)field;
                    tempo.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            form.getProgressMap().put(arg, form.validateField.isFieldValid(tempo));
                            form.updateFormProgress();

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                } else if(field instanceof PasswordStrengthMeter){
                    final EditText tempo = ((PasswordStrengthMeter) field).getPasswordField();
                    tempo.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            form.getProgressMap().put(arg, form.validateField.isFieldValid(field));
                            form.updateFormProgress();

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }

               form.addToView(title, field);
            }
        });
        TextView name = view.findViewById(R.id.nameTitle);
        name.setText("Namn");
        TextView surname = view.findViewById(R.id.surnameTitle);
        surname.setText("Efternamn");
        TextView mail = view.findViewById(R.id.mailTitle);
        mail.setText("Mail");
        TextView password = view.findViewById(R.id.passwordTitle);
        password.setText("Lösenord");
        
        form.addField(name, nameField);
        form.addField(surname, surnameField);
        form.addField(mail, emailField);
        form.addField(password, psm);
    }

    public void signUp(Form form) {
        if(form.getProgress() != 100){
            for(View v: form.getItemList()){
                if(form.getProgressMap().get(v))
                    if (v instanceof PasswordStrengthMeter) {
                        PasswordStrengthMeter ps = (PasswordStrengthMeter) v;
                        //ps.getPasswordField().setBackgroundColor(Color.TRANSPARENT);
                        ps.getPasswordField().setBackgroundResource(R.drawable.shape);
                        //form.setState(field, Form.NOT_VALID)

                    }
                    else
                        v.setBackgroundResource(R.drawable.shape);
                else {
                    if (v instanceof PasswordStrengthMeter) {
                        PasswordStrengthMeter ps = (PasswordStrengthMeter) v;
                        ps.getPasswordField().setBackgroundResource(R.drawable.invalid_shape);
                    }
                    else
                        v.setBackgroundResource(R.drawable.invalid_shape);
                }
            }
            Toast.makeText(this,"Please fill out remaining fields", Toast.LENGTH_SHORT).show();
            return;
        }else {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
    }

}
