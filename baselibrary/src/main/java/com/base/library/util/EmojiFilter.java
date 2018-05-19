package com.base.library.util;

import android.text.InputFilter;
import android.text.Spanned;

import com.base.library.application.BaseApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：王东一
 * 创建时间：2018/1/30.
 */

public class EmojiFilter implements InputFilter {

    //关键的正则表达式
    Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\ud83e\udd00-\ud83e\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            BaseApplication.getToastUtil().showMiddleToast("禁止输入Emoji表情");
            return "";
        }
        return null;
    }
}
