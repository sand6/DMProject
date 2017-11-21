package com.example.ivan.minibar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Activity2 extends Activity {

    private ArrayList<String> productos = new ArrayList<String>();;
    private Map<String,Producto> mapProductos = new LinkedHashMap<>();
    private ArrayAdapter<String> adapter ;
    private ListView listaPedidos ;
    private TextView lblTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        DBManager gestorDB = new DBManager( this.getApplicationContext() );

        final Button btPagar = findViewById(R.id.btPagar);
        final ImageButton btRefrescos = findViewById(R.id.btRefresco);
        final ImageButton btCervezas = findViewById(R.id.btCervexa);
        final ImageButton btCafes = findViewById(R.id.btCafe);
        final ImageButton btCombinados = findViewById(R.id.btCombinado);
        final ImageButton btZumos = findViewById(R.id.btZumos);
        final ImageButton btChupitos = findViewById(R.id.btChupitos);
        final Producto refresco = gestorDB.getProducto(1);
        final Producto cerveza = gestorDB.getProducto(2);
        final Producto zumo = gestorDB.getProducto(3);
        final Producto cafe = gestorDB.getProducto(4);
        final Producto combinado = gestorDB.getProducto(5);
        final Producto chupito = gestorDB.getProducto(6);


        lblTotal = (TextView) findViewById(R.id.txtTotal);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,productos);
        listaPedidos = (ListView) findViewById(R.id.listPedidos);

        listaPedidos.setAdapter(adapter);

        btRefrescos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresco.añadir();
                mapProductos.put(refresco.getNombre(), refresco);
                actualizaListView();
            }
        });

        btCervezas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerveza.añadir();
                mapProductos.put(cerveza.getNombre(), cerveza);
                actualizaListView();
            }
        });

        btCafes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cafe.añadir();
                mapProductos.put(cafe.getNombre(), cafe);
                actualizaListView();
            }
        });

        btZumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zumo.añadir();
                mapProductos.put(zumo.getNombre(), zumo);
                actualizaListView();
            }
        });

        btCombinados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combinado.añadir();
                mapProductos.put(combinado.getNombre(), combinado);
                actualizaListView();
            }
        });

        btChupitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chupito.añadir();
                mapProductos.put(chupito.getNombre(), chupito);
                actualizaListView();
            }
        });

        btPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void actualizaListView(){
        productos.clear();

        Iterator it = mapProductos.keySet().iterator();
        Producto producto;
        while(it.hasNext()){
            String clave = (String) it.next();
            producto = (Producto) mapProductos.get(clave);

            productos.add(String.format(producto.getCantidad() +
                    "x    " + producto.getNombre() +
                    "       " + String.format("%.2f", producto.getCantidad() * producto.getPrecio())));
        }

        adapter.notifyDataSetChanged();
        actualizarTotal();
    }

    private void actualizarTotal(){
        Iterator it = mapProductos.keySet().iterator();
        double total = 0.0;
        Producto producto;

        while(it.hasNext()){
            String clave = (String) it.next();
            producto = (Producto) mapProductos.get(clave);

            total += (producto.getCantidad() * producto.getPrecio());
        }
        lblTotal.setText("TOTAL:  "+ String.format("%.2f", total));
    }

}