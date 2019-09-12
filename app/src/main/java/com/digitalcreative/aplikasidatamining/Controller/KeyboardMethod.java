package com.digitalcreative.aplikasidatamining.Controller;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digitalcreative.aplikasidatamining.R;

public class KeyboardMethod extends LinearLayout implements View.OnClickListener {
    private LinearLayout button1, button2, button3, button4, button5, button6, button7, button8, button9, button0,
            buttonq, buttonw, buttone, buttonr, buttont, buttony, buttonu, buttoni, buttono, buttonp,
            buttona, buttons, buttond, buttonf, buttong, buttonh, buttonj, buttonk, buttonl,
            buttonz, buttonx, buttonc, buttonv, buttonb, buttonn, buttonm,  buttoncancel, buttondelete, button_keyboard;

    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;
    private ChangeKeyboardAlert alertKeyboard;
    private Context context;
    private AttributeSet attributeSet;

    private TextView key1,key2,key3;
    private View view;

    public KeyboardMethod(Context context) {
        this(context, null, 0, R.layout.customkeyboard2);
    }

    public KeyboardMethod(Context context, AttributeSet attrs) {
        this(context, attrs, 0, R.layout.customkeyboard);
    }

    public KeyboardMethod(Context context, AttributeSet attrs, int defStyleAttr, int keyboardId) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attributeSet = attrs;
        keyboardChooseInit(context);
        init(context, attrs, keyboardId);
    }

    private void init(Context context, AttributeSet attrs, int keyboardId) {
        view = LayoutInflater.from(context).inflate(keyboardId, this, true);
        //Number
        button1 = findViewById(R.id.btn_1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.btn_2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.btn_3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.btn_4);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.btn_5);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.btn_6);
        button6.setOnClickListener(this);
        button7 = findViewById(R.id.btn_7);
        button7.setOnClickListener(this);
        button8 = findViewById(R.id.btn_8);
        button8.setOnClickListener(this);
        button9 = findViewById(R.id.btn_9);
        button9.setOnClickListener(this);
        button0 = findViewById(R.id.btn_0);
        button0.setOnClickListener(this);

        //Alphabet
        //1st Row
        buttonq = findViewById(R.id.btn_q);
        buttonq.setOnClickListener(this);
        buttonw = findViewById(R.id.btn_w);
        buttonw.setOnClickListener(this);
        buttone = findViewById(R.id.btn_e);
        buttone.setOnClickListener(this);
        buttonr = findViewById(R.id.btn_r);
        buttonr.setOnClickListener(this);
        buttont = findViewById(R.id.btn_t);
        buttont.setOnClickListener(this);
        buttony = findViewById(R.id.btn_y);
        buttony.setOnClickListener(this);
        buttonu = findViewById(R.id.btn_u);
        buttonu.setOnClickListener(this);
        buttoni = findViewById(R.id.btn_i);
        buttoni.setOnClickListener(this);
        buttono = findViewById(R.id.btn_o);
        buttono.setOnClickListener(this);
        buttonp = findViewById(R.id.btn_p);
        buttonp.setOnClickListener(this);

        //2nd Row
        buttona = findViewById(R.id.btn_a);
        buttona.setOnClickListener(this);
        buttons = findViewById(R.id.btn_s);
        buttons.setOnClickListener(this);
        buttond = findViewById(R.id.btn_d);
        buttond.setOnClickListener(this);
        buttonf = findViewById(R.id.btn_f);
        buttonf.setOnClickListener(this);
        buttong = findViewById(R.id.btn_g);
        buttong.setOnClickListener(this);
        buttonh = findViewById(R.id.btn_h);
        buttonh.setOnClickListener(this);
        buttonj = findViewById(R.id.btn_j);
        buttonj.setOnClickListener(this);
        buttonk = findViewById(R.id.btn_k);
        buttonk.setOnClickListener(this);
        buttonl = findViewById(R.id.btn_l);
        buttonl.setOnClickListener(this);

        //3rd Row
        buttonz = findViewById(R.id.btn_z);
        buttonz.setOnClickListener(this);
        buttonx = findViewById(R.id.btn_x);
        buttonx.setOnClickListener(this);
        buttonc = findViewById(R.id.btn_c);
        buttonc.setOnClickListener(this);
        buttonv = findViewById(R.id.btn_v);
        buttonv.setOnClickListener(this);
        buttonb = findViewById(R.id.btn_b);
        buttonb.setOnClickListener(this);
        buttonn = findViewById(R.id.btn_n);
        buttonn.setOnClickListener(this);
        buttonm = findViewById(R.id.btn_m);
        buttonm.setOnClickListener(this);

        buttoncancel = findViewById(R.id.btn_cancel);
        buttoncancel.setOnClickListener(this);
        buttondelete = findViewById(R.id.btn_deletes);
        buttondelete.setOnClickListener(this);
        button_keyboard = findViewById(R.id.btn_keyboard);
        button_keyboard.setOnClickListener(this);

        keyValues.put(R.id.btn_0, "0");
        keyValues.put(R.id.btn_1, "1");
        keyValues.put(R.id.btn_2, "2");
        keyValues.put(R.id.btn_3, "3");
        keyValues.put(R.id.btn_4, "4");
        keyValues.put(R.id.btn_5, "5");
        keyValues.put(R.id.btn_6, "6");
        keyValues.put(R.id.btn_7, "7");
        keyValues.put(R.id.btn_8, "8");
        keyValues.put(R.id.btn_9, "9");

        keyValues.put(R.id.btn_q, "Q");
        keyValues.put(R.id.btn_w, "W");
        keyValues.put(R.id.btn_e, "E");
        keyValues.put(R.id.btn_r, "R");
        keyValues.put(R.id.btn_t, "T");
        keyValues.put(R.id.btn_y, "Y");
        keyValues.put(R.id.btn_u, "U");
        keyValues.put(R.id.btn_i, "I");
        keyValues.put(R.id.btn_o, "O");
        keyValues.put(R.id.btn_p, "P");

        keyValues.put(R.id.btn_a, "A");
        keyValues.put(R.id.btn_s, "S");
        keyValues.put(R.id.btn_d, "D");
        keyValues.put(R.id.btn_f, "F");
        keyValues.put(R.id.btn_g, "G");
        keyValues.put(R.id.btn_h, "H");
        keyValues.put(R.id.btn_j, "J");
        keyValues.put(R.id.btn_k, "K");
        keyValues.put(R.id.btn_l, "L");

        keyValues.put(R.id.btn_z, "Z");
        keyValues.put(R.id.btn_x, "X");
        keyValues.put(R.id.btn_c, "C");
        keyValues.put(R.id.btn_v, "V");
        keyValues.put(R.id.btn_b, "B");
        keyValues.put(R.id.btn_n, "N");
        keyValues.put(R.id.btn_m, "M");
        keyValues.put(R.id.btn_space, " ");

    }

    @Override
    public void onClick(View view) {
        if (inputConnection == null)
            return;

        if (view.getId() == R.id.btn_deletes) {
            CharSequence selectedText = inputConnection.getSelectedText(0);

            if (TextUtils.isEmpty(selectedText)) {
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                inputConnection.commitText("", 1);
            }
        } else  if (view.getId() == R.id.btn_cancel) {
            CharSequence currentText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;
            CharSequence beforCursorText = inputConnection.getTextBeforeCursor(currentText.length(), 0);
            CharSequence afterCursorText = inputConnection.getTextAfterCursor(currentText.length(), 0);
            inputConnection.deleteSurroundingText(beforCursorText.length(), afterCursorText.length());
//            inputConnection.deleteSurroundingText(1, 0);
        } else if(view.getId()== R.id.btn_keyboard){
            alertKeyboard.showAlert();
        } else if(view.getId() == R.id.keyboard1_choose){
            alertKeyboard.hideAlert();
            this.removeAllViews();
            keyboardChooseInit(context);
            init(context, attributeSet, R.layout.customkeyboard);
        } else if(view.getId() == R.id.keyboard2_choose){
            alertKeyboard.hideAlert();
            this.removeAllViews();
            keyboardChooseInit(context);
            init(context, attributeSet, R.layout.customkeyboard2);
        } else if(view.getId() == R.id.keyboard3_choose){
            alertKeyboard.hideAlert();
            this.removeAllViews();
            keyboardChooseInit(context);
            init(context, attributeSet, R.layout.customkeyboard3);
        } else {
            String value = keyValues.get(view.getId());
            inputConnection.commitText(value, 1);
        }
    }

    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;
    }

    public void keyboardChooseInit(Context context){
        alertKeyboard = new ChangeKeyboardAlert(context);
        key1 = alertKeyboard.getAlertView().findViewById(R.id.keyboard1_choose);
        key2 = alertKeyboard.getAlertView().findViewById(R.id.keyboard2_choose);
        key3 = alertKeyboard.getAlertView().findViewById(R.id.keyboard3_choose);

        key1.setOnClickListener(this);
        key2.setOnClickListener(this);
        key3.setOnClickListener(this);
    }
}
