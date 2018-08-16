package com.fma.closingrepclient.facade;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fma.closingrepclient.R;
import com.fma.closingrepclient.controller.ControllerRequest;
import com.fma.closingrepclient.controller.ControllerRest;
import com.fma.closingrepclient.controller.ControllerSetting;
import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.helper.GsonRequest;
import com.fma.closingrepclient.model.ModelArea;
import com.fma.closingrepclient.model.ModelUser;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnReset;
    EditText txtUserName;
    EditText txtPassword;
    EditText txtRestServer;
    ControllerRequest controllerRequest;
    ControllerRest controllerRest;
    ControllerSetting controllerSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getLayoutInflater().inflate(R.layout.activity_login, this.mainframe);

        btnLogin = findViewById(R.id.btnLogin);
        btnReset = findViewById(R.id.btnReset);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        txtRestServer = findViewById(R.id.txtRestServer);
        controllerRequest = ControllerRequest.getInstance(this);
        controllerRest = new ControllerRest(this);
        controllerSetting = new ControllerSetting(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDatabase();
            }
        });

        txtUserName.setText("teknisi");
        txtPassword.setText("teknisi");

        txtRestServer.setText(controllerSetting.getSettingStr("rest_url"));
        if (txtRestServer.getText().toString().equals(""))
            txtRestServer.setText("10.0.2.2/admin/index.php");



    }

    private void resetDatabase() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Reset Confirmation");
        builder.setMessage("Anda yakin melakukan reset database ?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
                        dbHelper.resetDatabase(dbHelper.getWritableDatabase());
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void attemptLogin() {
        txtUserName.setError(null);
        txtPassword.setError(null);
        String username = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();
        View focusView = txtUserName;
        controllerSetting.updateSetting( "rest_url",txtRestServer.getText().toString() );

        showProgress(true);
        String url = controllerRest.url_login();
        url += '/' + username;
        url += '/' + password;

        GsonRequest<ModelUser> gsonReq = new GsonRequest<ModelUser>(url, ModelUser.class,
                new Response.Listener<ModelUser>() {
                    @Override
                    public void onResponse(ModelUser response) {
                        try {
                            finishLogin(response);
                        }catch(Exception e){
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        showProgress(false);
                    }
                }
        );
        ControllerRequest.getInstance(this).addToRequestQueue(gsonReq,url);


    }

    private void showProgress(final boolean show) {
    }
    private void finishLogin(ModelUser response) {
        Toast.makeText(this, response.getNama(), Toast.LENGTH_SHORT).show();

        ModelArea modelArea = new ModelArea();
        modelArea.loadByUID(DBHelper.getInstance(this).getReadableDatabase(), response.getArea_uid());

        controllerSetting.updateSetting( "user_name",response.getUsername() );

        //user id pakai id external
        controllerSetting.updateSetting( "user_id", Integer.toString(response.getId()) );

        //area pakai id internetl
        controllerSetting.updateSetting( "area_ id", Integer.toString(modelArea.getId()) );
        controllerSetting.updateSetting( "area", modelArea.getNama() );

        startActivity(new Intent(this, MainActivity.class));
        //finish();

    }

}
