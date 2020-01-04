package com.example.form;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class representing a form as a linear layout.
 */
public class Form extends LinearLayout {

    private HashMap<View, Boolean> progressMap = new HashMap<>();
    private List<View> itemList = new ArrayList();
    private Context context;
    private float progress;
    private Form TAG = this;
    TextView text;


    /**
     *
     * @param title title of the field.
     * @param field field view getting displayed.
     *              add field method must setup custom listeners that changes the value in
     *              progressmap and calls updateProgress. Finally call the addToView method.
     */
    private FormFieldHandler formFieldHandler = new FormFieldHandler() {
        @Override
        public void addField(TextView title, View field) {
            final View arg = field;
            if(field instanceof EditText) {
                final EditText tempo =(EditText)field;
                tempo.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        progressMap.put(arg, TAG.validateField.isFieldValid(tempo));
                        updateFormProgress();

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
            addToView(title, field);
        }
    };


    public void addToView(TextView title, View field){
        progressMap.put(field, false);
        itemList.add(field);

        /*If field already has a layout parent, remove this to enable it to
        * be added to this view*/
        if(field.getParent() != null) {
            ((ViewGroup)field.getParent()).removeView(field);
        }
        if(title.getParent() != null) {
            ((ViewGroup)title.getParent()).removeView(title);
        }
        /*add new field to view*/
        TAG.addView(title);
        TAG.addView(field);
    }

    /**
     * Validator that should be overriden by user.
     */
    public ValidateField validateField = new ValidateField() {
        /**
         * @param field View that wants to be validated
         * @return  true if valid, else false.
         */
        @Override
        public boolean isFieldValid(View field) {
            EditText temp = (EditText)field;
            if(!temp.getText().toString().equals("")){
                return true;
            }
            return false;
        }
    };


    public Form(Context context) {
        super(context);
        this.context = context;
        text = new TextView(context);
        this.addView(text);
    }

    public Form(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        text = new TextView(context);
        this.addView(text);
    }

    public Form(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        text = new TextView(context);
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        text.setTypeface(boldTypeface);
        this.addView(text);
    }

    public Form(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        text = new TextView(context);

    }

    /*this addField calls the formfieldHandlers method*/
    public void addField(TextView title, View field){
        this.formFieldHandler.addField(title, field);
    }

    /**
     * iterates list of field and counts number of valid fields,
     * this is set in an global value, progress.
     */
    public void updateFormProgress() {
        progress = 0;
        float count = 0;
        for(View e: itemList){
           if(progressMap.get(e)){
               count++;
           }
           progress = (count/(float)itemList.size())*100;
           text.setText(Float.toString(progress));

        }

    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public ValidateField getValidateField() {
        return validateField;
    }

    public void setValidateField(ValidateField validateField) {
        this.validateField = validateField;
    }
    public List<View> getItemList() {
        return itemList;
    }

    public void setItemList(List<View> itemList) {
        this.itemList = itemList;
    }
    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public FormFieldHandler getFormFieldHandler() {
        return formFieldHandler;
    }

    public void setFormFieldHandler(FormFieldHandler formFieldHandler) {
        this.formFieldHandler = formFieldHandler;
    }

    public HashMap<View, Boolean> getProgressMap() {
        return progressMap;
    }

    public void setProgressMap(HashMap<View, Boolean> progressMap) {
        this.progressMap = progressMap;
    }

}
