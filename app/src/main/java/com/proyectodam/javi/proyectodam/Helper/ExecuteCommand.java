package com.proyectodam.javi.proyectodam.Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;

public class ExecuteCommand extends AsyncTask<String, Void, String> {

    private Session session;
    private String command;
    private String response = "";
    public AsyncResponseInterface delegate = null;
    public Channel channel;
    private ProgressDialog progressDialog;
    private Activity activity;

    public ExecuteCommand(AsyncResponseInterface delegate, Session session, String command, Activity activity) {
        this.delegate = delegate;
        this.session = session;
        this.command = command;
        this.activity = activity;
        this.progressDialog = new ProgressDialog(activity);
    }

    public interface AsyncResponseInterface {
        void executeCommandProcessFinish(String response);
    }

    @Override
    protected String doInBackground(String... command) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            this.channel = session.openChannel("exec");
            channel.setOutputStream(baos);
            ((ChannelExec) channel).setCommand(this.command);
            Thread.sleep(1000);
            channel.connect();
            Thread.sleep(1000);
            this.response = baos.toString();
            return baos.toString();

        } catch (Exception e) {
            return "ERROR";
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Cargando");
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        delegate.executeCommandProcessFinish(response);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }

        //TODO
//        channel.disconnect();
//        Toast toast = Toast.makeText(pruebaActivity, s, Toast.LENGTH_SHORT);

//        toast.show();
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled();
        delegate.executeCommandProcessFinish(s);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
            channel.disconnect();
        }
        //TODO

//        Toast toast = Toast.makeText(pruebaActivity,
//                s, Toast.LENGTH_SHORT);
//
//        toast.show();
    }
}

