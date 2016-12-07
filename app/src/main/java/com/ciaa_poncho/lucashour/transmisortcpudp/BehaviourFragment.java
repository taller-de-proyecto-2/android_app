package com.ciaa_poncho.lucashour.transmisortcpudp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BehaviourFragment extends Fragment implements View.OnClickListener{

    private Button submit;
    private EditText ssid_input;
    private EditText password_input;

    public BehaviourFragment(){}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.fragment_behaviour, container, false);

        if(v != null){
            LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout);
            submit = ((Button) v.findViewById(R.id.submit));
            ssid_input = ((EditText) v.findViewById(R.id.ssid));
            password_input = ((EditText) v.findViewById(R.id.password));
        }

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submit.setOnClickListener(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false); //Indicamos que este Fragment no tiene su propio menu de opciones
    }

    public void onClick(View view) {

        String ip = GlobalData.getInstance().getIpAddress();
        String port = String.valueOf(GlobalData.getInstance().getPortNumber());
        String ssid = ssid_input.getText().toString();
        String password = password_input.getText().toString();

        HttpRequestAsyncTask http_request;

        if (GlobalData.getInstance().getIpAddress() == null){
            Toast.makeText(this.getActivity().getApplicationContext(),"Configuración de dirección IP destino requerida.", Toast.LENGTH_SHORT).show();
        }
        else if (ssid.isEmpty() || password.isEmpty()) {
            Toast.makeText(this.getActivity().getApplicationContext(),"Campos requeridos: nombre de red y contraseña.", Toast.LENGTH_SHORT).show();
        }
        else if (view.getId() == R.id.submit){
            String params = "ssid=" + ssid + "&password=" + password;
            http_request = new HttpRequestAsyncTask(this.getActivity().getApplicationContext(), params, ip, port);
            http_request.executeOnExecutor(HttpRequestAsyncTask.THREAD_POOL_EXECUTOR);
        }

    }
}