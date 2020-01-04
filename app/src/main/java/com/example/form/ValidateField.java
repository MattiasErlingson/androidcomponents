package com.example.form;

import android.view.View;


public abstract class ValidateField {

    /**
     * @param field field that should be validated
     * @return  true or false depending on state.
     */
    public abstract boolean isFieldValid(View field);
}
