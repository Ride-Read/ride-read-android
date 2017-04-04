package com.rideread.rideread.common.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LineHeightSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;

/**
 * Created by SkyXiao on 2017/2/27.
 */

public class SpannableUtils {

    private SpannableUtils() {
        throw new AssertionError();
    }


    public static class SpannableBuilder {
        private final SpannableStringBuilder builder;
        private int start;
        private int end;

        public SpannableBuilder() {
            this(new SpannableStringBuilder());
        }

        public SpannableBuilder(SpannableStringBuilder builder) {
            this.builder = builder;
        }

        public SpannableBuilder append(@NonNull final String format, @NonNull final Object... args) {
            return append(String.format(format, args));
        }

        public SpannableBuilder append(@NonNull final CharSequence text) {
            start = builder.length();
            builder.append(text);
            end = builder.length();
            return this;
        }

        public CharSequence build() {
            return builder;
        }

        public SpannableBuilder setTypeface(@Nullable final String family) {
            builder.setSpan(new TypefaceSpan(family), start, end, SPAN_INCLUSIVE_EXCLUSIVE);
            return this;
        }

        public SpannableBuilder setUnderline() {
            builder.setSpan(new UnderlineSpan(), start, end, SPAN_INCLUSIVE_EXCLUSIVE);
            return this;
        }

        public SpannableBuilder setAbsoluteSize(final int size, final boolean dp) {
            builder.setSpan(new AbsoluteSizeSpan(size, dp), start, end, SPAN_INCLUSIVE_EXCLUSIVE);
            return this;
        }

        public SpannableBuilder setImage(Bitmap b) {
            builder.setSpan(new ImageSpan(b), start, end, SPAN_INCLUSIVE_EXCLUSIVE);
            return this;
        }

        public SpannableBuilder setImage(Drawable d) {
            builder.setSpan(new ImageSpan(d), start, end, SPAN_INCLUSIVE_EXCLUSIVE);
            return this;
        }

        public SpannableBuilder setForegroundColor(final int front) {
            builder.setSpan(new ForegroundColorSpan(front), start, end, SPAN_INCLUSIVE_EXCLUSIVE);
            return this;
        }

        public SpannableBuilder setLineHeight(final int height) {
            builder.setSpan((LineHeightSpan) (charSequence, i, i1, i2, i3, fm) -> {
                fm.bottom += height;
                fm.descent += height;
            }, start, end, SPAN_INCLUSIVE_EXCLUSIVE);
            return this;
        }

        public SpannableBuilder setStyle(final int style) {
            builder.setSpan(new StyleSpan(style), start, end, SPAN_INCLUSIVE_EXCLUSIVE);
            return this;
        }

        public SpannableBuilder setUrl(@NonNull final String url) {
            if (!TextUtils.isEmpty(url))
                builder.setSpan(new URLSpan(url), start, end, SPAN_INCLUSIVE_EXCLUSIVE);
            return this;
        }
    }

}
