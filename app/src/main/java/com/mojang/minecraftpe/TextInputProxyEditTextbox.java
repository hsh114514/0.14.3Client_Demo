package com.mojang.minecraftpe;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/TextInputProxyEditTextbox.class */
public class TextInputProxyEditTextbox extends EditText {
    private MCPEKeyWatcher _mcpeKeyWatcher;
    public final int allowedLength;
    public final boolean limitInput;

    /* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/TextInputProxyEditTextbox$MCPEInputConnection.class */
    private class MCPEInputConnection extends InputConnectionWrapper {
        TextInputProxyEditTextbox textbox;
        final TextInputProxyEditTextbox this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MCPEInputConnection(TextInputProxyEditTextbox textInputProxyEditTextbox, InputConnection inputConnection, boolean z, TextInputProxyEditTextbox textInputProxyEditTextbox2) {
            super(inputConnection, z);
            this.this$0 = textInputProxyEditTextbox;
            this.textbox = textInputProxyEditTextbox2;
        }

        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
        public boolean sendKeyEvent(KeyEvent keyEvent) {
            boolean zSendKeyEvent;
            if (this.textbox.getText().length() == 0 && keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 67) {
                if (this.this$0._mcpeKeyWatcher != null) {
                    this.this$0._mcpeKeyWatcher.onDeleteKeyPressed();
                }
                zSendKeyEvent = false;
            } else {
                zSendKeyEvent = super.sendKeyEvent(keyEvent);
            }
            return zSendKeyEvent;
        }
    }

    /* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/TextInputProxyEditTextbox$MCPEKeyWatcher.class */
    public interface MCPEKeyWatcher {
        void onBackKeyPressed();

        void onDeleteKeyPressed();
    }

    public TextInputProxyEditTextbox(Context context, int i, boolean z) {
        super(context);
        this._mcpeKeyWatcher = null;
        this.allowedLength = i;
        this.limitInput = z;
        setFilters(z ? new InputFilter[]{new InputFilter.LengthFilter(this.allowedLength), new InputFilter() {
            @Override // android.text.InputFilter
            public CharSequence filter(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
                if (charSequence.equals("")) {
                }
                return charSequence;
            }
        }} : new InputFilter[]{new InputFilter.LengthFilter(this.allowedLength)});
    }

    public TextInputProxyEditTextbox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._mcpeKeyWatcher = null;
        this.allowedLength = 160;
        this.limitInput = false;
    }

    public TextInputProxyEditTextbox(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this._mcpeKeyWatcher = null;
        this.allowedLength = 160;
        this.limitInput = false;
    }

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return new MCPEInputConnection(this, super.onCreateInputConnection(editorInfo), true, this);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        boolean zOnKeyPreIme;
        if (i == 4 && keyEvent.getAction() == 1) {
            if (this._mcpeKeyWatcher != null) {
                this._mcpeKeyWatcher.onBackKeyPressed();
            }
            zOnKeyPreIme = false;
        } else {
            zOnKeyPreIme = super.onKeyPreIme(i, keyEvent);
        }
        return zOnKeyPreIme;
    }

    public void setOnMCPEKeyWatcher(MCPEKeyWatcher mCPEKeyWatcher) {
        this._mcpeKeyWatcher = mCPEKeyWatcher;
    }
}
