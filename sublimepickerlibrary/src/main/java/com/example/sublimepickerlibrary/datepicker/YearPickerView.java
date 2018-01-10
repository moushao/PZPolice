/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright 2015 Vikram Kakkar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sublimepickerlibrary.datepicker;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sublimepickerlibrary.R;
import com.example.sublimepickerlibrary.utilities.SUtils;

import java.util.Calendar;

/**
 * Displays a selectable list of years.
 */
public class YearPickerView extends ListView {
    private YearAdapter mAdapter;
    private int mViewSize;
    private int mChildSize;

    private OnYearSelectedListener mOnYearSelectedListener;

    public YearPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.listViewStyle);
    }

    public YearPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public YearPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        final LayoutParams frame = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(frame);

        final Resources res = getContext().getResources();
        mViewSize = res.getDimensionPixelOffset(R.dimen.datepicker_view_animator_height);
        mChildSize = res.getDimensionPixelOffset(R.dimen.datepicker_year_label_height);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int year = mAdapter.getYearForPosition(position);
                mAdapter.setSelection(year);

                if (mOnYearSelectedListener != null) {
                    mOnYearSelectedListener.onYearChanged(YearPickerView.this, year);
                }
            }
        });

        mAdapter = new YearAdapter(getContext());
        setAdapter(mAdapter);
    }

    public void setOnYearSelectedListener(OnYearSelectedListener listener) {
        mOnYearSelectedListener = listener;
    }

    /**
     * Sets the currently selected year. Jumps immediately to the new year.
     *
     * @param year the target year
     */
    public void setYear(final int year) {
        mAdapter.setSelection(year);

        post(new Runnable() {
            @Override
            public void run() {
                final int position = mAdapter.getPositionForYear(year);
                if (position >= 0 && position < getCount()) {
                    setSelectionCentered(position);
                }
            }
        });
    }

    private void setSelectionCentered(int position) {
        final int offset = mViewSize / 2 - mChildSize / 2;
        setSelectionFromTop(position, offset);
    }

    public void setRange(Calendar min, Calendar max) {
        mAdapter.setRange(min, max);
    }

    private static class YearAdapter extends BaseAdapter {

        private static final int ITEM_LAYOUT = R.layout.year_label_text_view;
        private static final int ITEM_TEXT_APPEARANCE =
                R.style.SPYearLabelTextAppearance;
        private static final int ITEM_TEXT_ACTIVATED_APPEARANCE =
                R.style.SPYearLabelActivatedTextAppearance;

        private final Context mContext;
        private final LayoutInflater mInflater;

        private int mActivatedYear;
        private int mMinYear;
        private int mCount;

        public YearAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public void setRange(Calendar minDate, Calendar maxDate) {
            final int minYear = minDate.get(Calendar.YEAR);
            final int count = maxDate.get(Calendar.YEAR) - minYear + 1;

            if (mMinYear != minYear || mCount != count) {
                mMinYear = minYear;
                mCount = count;
                notifyDataSetInvalidated();
            }
        }

        public boolean setSelection(int year) {
            if (mActivatedYear != year) {
                mActivatedYear = year;
                notifyDataSetChanged();
                return true;
            }
            return false;
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Integer getItem(int position) {
            return getYearForPosition(position);
        }

        @Override
        public long getItemId(int position) {
            return getYearForPosition(position);
        }

        public int getPositionForYear(int year) {
            return year - mMinYear;
        }

        public int getYearForPosition(int position) {
            return mMinYear + position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @SuppressLint("SetTextI18n")
        @SuppressWarnings("deprecation")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView v;
            final boolean hasNewView = convertView == null;
            if (hasNewView) {
                v = (TextView) mInflater.inflate(ITEM_LAYOUT, parent, false);
            } else {
                v = (TextView) convertView;
            }

            final int year = getYearForPosition(position);
            final boolean activated = mActivatedYear == year;

            if (hasNewView || v.isActivated() != activated) {
                final int textAppearanceResId;
                if (activated && ITEM_TEXT_ACTIVATED_APPEARANCE != 0) {
                    textAppearanceResId = ITEM_TEXT_ACTIVATED_APPEARANCE;
                } else {
                    textAppearanceResId = ITEM_TEXT_APPEARANCE;
                }

                if (SUtils.isApi_23_OrHigher()) {
                    v.setTextAppearance(textAppearanceResId);
                } else {
                    v.setTextAppearance(mContext, textAppearanceResId);
                }

                v.setActivated(activated);
            }

            v.setText(Integer.toString(year));
            return v;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }
    }

    public int getFirstPositionOffset() {
        final View firstChild = getChildAt(0);
        if (firstChild == null) {
            return 0;
        }
        return firstChild.getTop();
    }

    // TODO: Might have to build the chain all the way
    // TODO: up to View#onInitializeAccessibilityEventInternal(AccessibilityEvent event)
    /*@Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
        super.onInitializeAccessibilityEventInternal(event);

        // There are a bunch of years, so don't bother.
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            event.setFromIndex(0);
            event.setToIndex(0);
        }
    }*/

    /**
     * The callback used to indicate the user changed the year.
     */
    public interface OnYearSelectedListener {
        /**
         * Called upon a year change.
         *
         * @param view The view associated with this listener.
         * @param year The year that was set.
         */
        void onYearChanged(YearPickerView view, int year);
    }
}
