package com.mobbeel.mobbsign.teknei.demo.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobbeel.mobbsign.teknei.demo.R;
import com.mobbeel.mobbsign.teknei.demo.model.responses.Customer;
import com.mobbeel.mobbsign.teknei.demo.model.responses.Document;
import com.mobbeel.mobbsign.teknei.demo.service.ClientWS;
import com.mobbeel.mobbsign.teknei.demo.service.CustomerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase principal de la búsqueda de datos mediante un identificador de usuario
 */
public class GetUserDataActivity extends AppCompatActivity {

    private Button btnGetUserData;
    private TextView lblNombre;
    private TextView lblApellidos;
    private TextView lblCurp;
    private TextView lblOcr;

    private ClientWS clientWS;
    private CustomerService customerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_data);

        clientWS = ClientWS.getInstance();
        customerService = clientWS.buildRetrofit().create(CustomerService.class);

        initUI();
    }

    /**
     * Inicializa los componentes de la interfaz
     */
    private void initUI() {
        btnGetUserData = (Button) findViewById(R.id.btnGetData);
        btnGetUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "4d23ec31-5151-4060-a344-e00692a6e0d4";
                if (query != null && !query.isEmpty()) {
                    String licenseId = "a64a304e-b13f-4f69-a0f9-512cc6c85cad";
                    getUserData(licenseId, query);
                } else {
                    showAlert("Advertencia", "Se necesita un identificador de usuario para consultar");
                }
            }
        });

        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblApellidos = (TextView) findViewById(R.id.lblApellidos);
        lblCurp = (TextView) findViewById(R.id.lblCurp);
        lblOcr = (TextView) findViewById(R.id.lblOcr);
    }

    /**
     * Método para obtener los datos del usuario en base a su identificador u ocr
     * @param id
     */
    private void getUserData(String licenseId, String id) {
        Call<Customer> customer = customerService.getUserData(licenseId, id);
        customer.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                int statusCode = response.code();
                Customer customer = response.body();
                switch (statusCode) {
                    case 200:
                        setUserData(customer.getDocument());
                        Log.d(this.getClass().getName(), customer.toString());
                        break;
                    case 400:
                        showAlert("Error", "El identificador de usuario no puede ser nulo");
                        break;
                    case 404:
                        showAlert("Error", "No se ha encontrar el usuario en el sistema");
                        break;
                    default:
                        showAlert("Error", "Ha ocurrido un error en la petición, intente de nuevo");
                        break;
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                showAlert("Error", "Ha ocurrido un error en la petición, intente de nuevo");
            }
        });
    }

    /**
     * Muestra una alerta con el título y mensaje indicados
     * @param title
     * @param message
     */
    private void showAlert(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alert = new AlertDialog.Builder(GetUserDataActivity.this);
                alert.setTitle(title);
                alert.setMessage(message);
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create();
                alert.show();
            }
        });
    }

    /**
     * Establece los valores recibidos de la búsqueda en las etiquetas
     * dentro del layout activity_get_user_data
     * @param document
     */
    private void setUserData(final Document document) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lblNombre.setText("Nombre: " + document.getName());
                lblApellidos.setText("Apellidos: " + document.getSurname());
                lblCurp.setText("C.U.R.P.: " + document.getCurp());
                lblOcr.setText("O.C.R.: " + document.getOcr());
            }
        });
    }
}
