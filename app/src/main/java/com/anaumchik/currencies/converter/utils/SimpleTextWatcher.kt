package com.anaumchik.currencies.converter.utils

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher(val afterTextChangeAction: (Editable?) -> Unit) : TextWatcher {

    override fun afterTextChanged(s: Editable?) = afterTextChangeAction(s)
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
}