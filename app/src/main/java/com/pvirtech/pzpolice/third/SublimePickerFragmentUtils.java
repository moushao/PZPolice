package com.pvirtech.pzpolice.third;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;

import com.example.sublimepickerlibrary.helpers.SublimeOptions;

/**
 * Created by Administrator on 2017/3/31.
 */

public class SublimePickerFragmentUtils {

    public void show(boolean isRange, boolean isDatePicker, boolean isTimePicker, FragmentManager fragmentManager, SublimePickerFragment.Callback
            mFragmentCallback) {
        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(mFragmentCallback);
        Pair<Boolean, SublimeOptions> optionsPair = getOptions(isRange, isDatePicker, isTimePicker);
        if (!optionsPair.first) { // If options are not valid
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);
        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(fragmentManager, "SUBLIME_PICKER");
    }


    Pair<Boolean, SublimeOptions> getOptions(boolean isRange, boolean isDatePicker, boolean isTimePicker) {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;

        if (isTimePicker) {
            displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;
            options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
        }
        if (isDatePicker) {
            displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;
            options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
        }
        options.setDisplayOptions(displayOptions);
        if (isRange) {
            options.setCanPickDateRange(isRange);
        }
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }


}
