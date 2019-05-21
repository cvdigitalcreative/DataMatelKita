package com.digitalcreative.aplikasidatamining.Controller;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputConnection;

import com.digitalcreative.aplikasidatamining.R;

import org.w3c.dom.Text;

public class KeyboardMethod extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    @Override
    public View onCreateInputView() {
        KeyboardView keyboardview = (KeyboardView)getLayoutInflater().inflate(R.layout.customkeyboard, null);
        Keyboard keyboard = new Keyboard(getApplicationContext(), R.xml.customkeyboard);
        keyboardview.setKeyboard(keyboard);
        keyboardview.setOnKeyboardActionListener((KeyboardView.OnKeyboardActionListener) getApplicationContext());
        return keyboardview;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null){
            switch (primaryCode){
                case Keyboard.KEYCODE_DELETE:
                    CharSequence selectedText = inputConnection.getSelectedText(0);
                    if (TextUtils.isEmpty(selectedText)){
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("",1);
                    }
                    break;
                    default:
                        char code = (char) primaryCode;
                        inputConnection.commitText(String.valueOf(code),1);
            }
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
