package com.proyectodam.javi.proyectodam.Entity.Helper;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.Serializable;
import java.util.Properties;

public class ConectionSsh extends AsyncTask<Void, Void, Session> implements Parcelable {

    private Session session;
    private StringHelper stringHelper;
    public AsyncSessionResponseInterface delegate = null;

    public ConectionSsh(AsyncSessionResponseInterface delegate)
    {
        this.delegate = delegate;
        this.stringHelper = new StringHelper();
    }

    protected ConectionSsh(Parcel in) {
        session = getSession();

    }

    public static final Creator<ConectionSsh> CREATOR = new Creator<ConectionSsh>() {
        @Override
        public ConectionSsh createFromParcel(Parcel in) {
            return new ConectionSsh(in);
        }

        @Override
        public ConectionSsh[] newArray(int size) {
            return new ConectionSsh[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public interface AsyncSessionResponseInterface
    {
        void connectionSshProcessFinish(Session session);
    }

    @Override
    protected Session doInBackground(Void... voids)
    {
       return getConection();

    }

    @Override
    protected void onPostExecute(Session session)
    {
        super.onPostExecute(session);
        delegate.connectionSshProcessFinish(session);
    }

    @Override
    protected void onCancelled(Session session) {
        super.onCancelled(session);
        disconnectSession();
        delegate.connectionSshProcessFinish(session);

    }

    public Session getConection() {

        try {
            JSch sshChannel = new JSch();
            this.session = sshChannel.getSession(stringHelper.USER, stringHelper.HOST, 22);
            this.session.setPassword(stringHelper.PASSWORD);
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            this.session.setConfig(prop);
            try {
                this.session.connect(10000);
            } catch (Exception e) {
                String error =  "ERROR: " + e.toString();
            }
        } catch (JSchException e1) {
            String error = "ERROR de Conexión: " + e1.toString();
        }

        return session;
    }

    public String disconnectSession() {

        try {
            this.session.disconnect();

            return "Desconectado con éxito";

        } catch (Exception e) {

            return "Error al desconectar: " + e;
        }
    }

    public Session getSession() {
        return session;
    }
}
