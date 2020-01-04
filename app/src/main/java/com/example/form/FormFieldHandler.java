package com.example.form;

import android.view.View;
import android.widget.TextView;

public abstract class FormFieldHandler {
    /**
     *
     * @param title title of the field.
     * @param field field view getting displayed.
     *              add field method must setup custom listeners that changes the value in
     *              progressmap and calls updateProgress. Finally call the addToView method.
     */
    public abstract void addField(TextView title, View field);
}
