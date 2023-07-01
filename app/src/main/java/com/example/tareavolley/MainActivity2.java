package com.example.tareavolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = this.getIntent().getExtras();
        TextView txtMostrar = (TextView)findViewById(R.id.txtMostrar2);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/productos/search";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String lista = "";
                        JSONObject objecto = null;
                        try {
                            objecto = new JSONObject(response);

                        JSONArray JSONlista = objecto.getJSONArray("productos");
                        for(int i=0; i< JSONlista.length();i++)
                        {
                            JSONObject JSONObjecto = JSONlista.getJSONObject(i);
                            lista = lista + "\n\n ID: " + JSONObjecto.getString("id").toString()
                                    + "\n Barcode: " + JSONObjecto.getString("barcode").toString()
                                    + "\n Descripción: " + JSONObjecto.getString("descripcion").toString()
                                    + "\n Clase: " + JSONObjecto.getString("clase").toString()
                                    + "\n Costo: " + JSONObjecto.getString("costo").toString()
                                    + "\n Precio Unidad: " + JSONObjecto.getString("precio_unidad").toString()
                                    + "\n Precio Minorista: " + JSONObjecto.getString("precio_minorista").toString()
                                    + "\n Precio Mayorista: " + JSONObjecto.getString("precio_mayorista").toString()
                                    + "\n Impuesto: " + JSONObjecto.getString("impuesto").toString();
                        }
                        txtMostrar.setText(lista);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtMostrar.setText("Error");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "Bearer " + bundle.getString("TOKEN"));
                return headerMap;
            }
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fuente", "1");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
