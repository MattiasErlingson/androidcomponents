package com.example.form;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.annotation.Nullable;

public class PasswordStrengthMeter extends LinearLayout {

    private Boolean isValid = false;
    private TextView descriptionField;
    private SeekBar strengthMeter;
    private EditText passwordField;
    private int minimumPasswordLength = 6;
    private int progress;

    /*Default colors for progressbar*/
    private int weakColor = Color.rgb(255,69,0);
    private int decentColor = Color.rgb(255,215, 0);
    private int strongColor = Color.rgb(124,252,0);


    /**
     * the default implementation of the abstract
     * PasswordValidation. Sets Valid to true if password length is >= minimumPasswordLength
     */
    private PasswordValidation valid = new PasswordValidation() {
        @Override
        public void validatePassword(String s) {
            if(s.length() == 0){
                setProgress(0);
            }
            if(s.length()<minimumPasswordLength){
                strengthMeter.setProgress(0);
                descriptionField.setText("Password must be atleast " + minimumPasswordLength + " long.");
                setValid(false);
                return;
            }
            setProgress(100);
            strengthMeter.setProgress(100);
            strengthMeter.getProgressDrawable().setColorFilter(strongColor, PorterDuff.Mode.MULTIPLY);
            descriptionField.setText("Password is STRONG");
            setValid(true);
            return;
        }
    };


    public PasswordStrengthMeter(Context context, @Nullable AttributeSet attrs, @Nullable EditText passwordField, @Nullable TextView descriptionField,@Nullable SeekBar strengthMeter){
        super(context);

        if( passwordField == null){ this.passwordField = new EditText(context, attrs); }
        else{ this.passwordField = passwordField; }

        if( descriptionField == null) {this.descriptionField = new TextView(context, attrs); }
        else { this.descriptionField = descriptionField; }

        if(strengthMeter == null){this.strengthMeter = new SeekBar(context, attrs);}
        else{this.strengthMeter = strengthMeter;}

        /*add component to view*/
        this.addView(this.passwordField);
        this.addView(this.descriptionField);
        this.addView(this.strengthMeter);

    }


    public PasswordStrengthMeter(Context context) {
        super(context);
        init(context,null);
    }

    public PasswordStrengthMeter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordStrengthMeter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public PasswordStrengthMeter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    /* Sets default values for all fields*/
    void init(Context context, AttributeSet attrs){

        this.passwordField = new EditText(context, attrs);
        this.passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.descriptionField = new TextView(context, attrs);
        this.strengthMeter = new SeekBar(context, attrs);
        this.setOrientation(this.VERTICAL);
        this.addView(passwordField);
        this.addView(descriptionField);
        this.addView(strengthMeter);
        strengthMeter.setMax(100);
        strengthMeter.setMin(0);
        strengthMeter.setThumb(null);
        strengthMeter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                strengthMeter.setProgress(getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    /*If Validator is set, validation method is reach
    * from this internal method*/
    private void calculatePasswordStrength(String s){
        valid.validatePassword(s);

    }
    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
    public int getWeakColor() {
        return weakColor;
    }

    public void setWeakColor(int weakColor) {
        this.weakColor = weakColor;
    }

    public int getDecentColor() {
        return decentColor;
    }

    public void setDecentColor(int decentColor) {
        this.decentColor = decentColor;
    }

    public int getStrongColor() {
        return strongColor;
    }

    public void setStrongColor(int strongColor) {
        this.strongColor = strongColor;
    }

    public String getPassword(){
        return passwordField.getText().toString();
    }

    public EditText getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(EditText passwordField) {
        this.passwordField = passwordField;
    }

    public void setDescriptionField(TextView descriptionField) {
        this.descriptionField = descriptionField;
    }

    public TextView getDescriptionField() {
        return descriptionField;
    }

    public SeekBar getStrengthMeter() {
        return strengthMeter;
    }

    public void setStrengthMeter(SeekBar strengthMeter) {
        this.strengthMeter = strengthMeter;
    }

    public int getMinimumPasswordLength() {
        return minimumPasswordLength;
    }

    public void setMinimumPasswordLength(int minimumPasswordLength) {
        this.minimumPasswordLength = minimumPasswordLength;
    }
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    public void setValidationMethod(PasswordValidation passwordValidation) {
        this.valid = passwordValidation;
    }


}
