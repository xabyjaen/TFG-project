package com.proyectodam.javi.proyectodam.Dialogs;

import android.app.ProgressDialog;
import android.content.Context;

public class TransferProgressDialog {
    private ProgressDialog progress;

    public TransferProgressDialog(Context context) {
        progress = ProgressDialog.show(context, "dialog title",
                "dialog message", true);

        new Thread(new Runnable() {
            @Override
            public void run()
            {
            Thread thread = new Thread();
                try {
                    thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}