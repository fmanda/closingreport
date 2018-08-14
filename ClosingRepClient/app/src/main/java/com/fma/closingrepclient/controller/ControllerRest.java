package com.fma.closingrepclient.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.helper.GsonRequest;
import com.fma.closingrepclient.helper.ImageHelper;
import com.fma.closingrepclient.model.ModelArea;
import com.fma.closingrepclient.model.ModelCustomer;
import com.fma.closingrepclient.model.ModelMaterial;
import com.fma.closingrepclient.model.ModelProduct;

import org.json.JSONObject;

import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by fma on 10/26/2017.
 */

public class ControllerRest {
    private Context context;
    private DBHelper db;
    private ControllerSetting controllerSetting;

//    public String base_url;

//    private static ControllerRest mInstance;
//
//    public static synchronized ControllerRest getInstance(Context context) {
//        if (mInstance == null) {
//            mInstance = new ControllerRest(context.getApplicationContext());
//        }
//        return mInstance;
//    }

    private int company_id;
    private int unit_id;

    private ControllerRequest controllerRequest;
    private Listener listener;



    public void SyncData(){
//        //reconcile
        this.DownloadArea();
        this.DownloadMaterial();
        this.DownloadProduct();
        this.DownloadCustomer();

//        this.UploadCashTrans();
//
//        //master
//        this.DownloadProducts();
//        this.DownloadOrderCategory();
//        this.UploadCustomers(); //modified customer only
//        this.DownloadCustomers();
//        this.UploadOrders();
    }



    public interface Listener {
        void onSuccess(String msg);
        void onError(String msg);
    }

    public void setListener(ControllerRest.Listener listener) {
        this.listener = listener;
    }

    public ControllerRest(Context context) {
        this.context = context;
        this.db = DBHelper.getInstance(context);
        this.controllerRequest = ControllerRequest.getInstance(context);
        this.controllerSetting = new ControllerSetting((this.context));

//        this.base_url = "http://" + controllerSetting.getSettingStr("rest_url") + "/";
    }

    public String base_url(){
        return "http://" + controllerSetting.getSettingStr("rest_url") + "/";
    }

    public String url_login(){
        return base_url() + "teknisilogin";
    }

    public String url_area(){
        return base_url() + "area";
    }

    public String url_customer(){
        return base_url() + "customer";
    }

    public String url_product(){
        return base_url() + "product";
    }

    public String url_material(){
        return base_url() + "material";
    }

    public void DownloadArea(){
        GsonRequest<ModelArea[]> gsonReq = new GsonRequest<ModelArea[]>(url_area(), ModelArea[].class,
                new Response.Listener<ModelArea[]>() {
                    @Override
                    public void onResponse(ModelArea[] response) {
                        try {
                            for (ModelArea modelArea : response) {
                                modelArea.setIDFromUID(db.getReadableDatabase(), modelArea.getUid());
                                modelArea.saveToDB(db.getWritableDatabase());
                                if (modelArea.getId() == 0) {
                                    listener.onSuccess("[area] " + modelArea.getNama() + " inserted");
                                }else{
                                    listener.onSuccess("[area] " + modelArea.getNama() + " updated");
                                }
                            }
                        }catch(Exception e){
                            listener.onError(e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        }
        );
        this.controllerRequest.addToRequestQueue(gsonReq,url_area());
    }

    public void DownloadMaterial(){
        GsonRequest<ModelMaterial[]> gsonReq = new GsonRequest<ModelMaterial[]>(url_material(), ModelMaterial[].class,
                new Response.Listener<ModelMaterial[]>() {
                    @Override
                    public void onResponse(ModelMaterial[] response) {
                        try {
                            for (ModelMaterial modelMaterial : response) {
                                modelMaterial.setIDFromUID(db.getReadableDatabase(), modelMaterial.getUid());
                                modelMaterial.saveToDB(db.getWritableDatabase());
                                if (modelMaterial.getId() == 0) {
                                    listener.onSuccess("[material] " + modelMaterial.getNama() + " inserted");
                                }else{
                                    listener.onSuccess("[material] " + modelMaterial.getNama() + " updated");
                                }
                            }
                        }catch(Exception e){
                            listener.onError(e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        }
        );
        this.controllerRequest.addToRequestQueue(gsonReq,url_area());
    }

    public void DownloadProduct(){
        GsonRequest<ModelProduct[]> gsonReq = new GsonRequest<ModelProduct[]>(url_product(), ModelProduct[].class,
                new Response.Listener<ModelProduct[]>() {
                    @Override
                    public void onResponse(ModelProduct[] response) {
                        try {
                            for (ModelProduct modelProduct : response) {
                                modelProduct.setIDFromUID(db.getReadableDatabase(), modelProduct.getUid());
                                modelProduct.saveToDB(db.getWritableDatabase());
                                if (modelProduct.getId() == 0) {
                                    listener.onSuccess("[product] " + modelProduct.getNama() + " inserted");
                                }else{
                                    listener.onSuccess("[product] " + modelProduct.getNama() + " updated");
                                }
                            }
                        }catch(Exception e){
                            listener.onError(e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        }
        );
        this.controllerRequest.addToRequestQueue(gsonReq,url_area());
    }

    public void DownloadCustomer(){
        GsonRequest<ModelCustomer[]> gsonReq = new GsonRequest<ModelCustomer[]>(url_customer(), ModelCustomer[].class,
                new Response.Listener<ModelCustomer[]>() {
                    @Override
                    public void onResponse(ModelCustomer[] response) {
                        try {
                            for (ModelCustomer modelCustomer : response) {
                                modelCustomer.setIDFromUID(db.getReadableDatabase(), modelCustomer.getUid());
                                if (modelCustomer.getArea_id() > 0){
                                    ModelArea modelArea = new ModelArea();
                                    modelArea.setIDFromUID(db.getReadableDatabase(), modelCustomer.getArea_uid());
                                    modelCustomer.setArea_id(modelArea.getId());

                                    listener.onSuccess("[customer] " + modelCustomer.getNama() + " set area_id");
                                }
                                modelCustomer.saveToDB(db.getWritableDatabase());
                                if (modelCustomer.getId() == 0) {
                                    listener.onSuccess("[customer] " + modelCustomer.getNama() + " inserted");
                                }else{
                                    listener.onSuccess("[customer] " + modelCustomer.getNama() + " updated");
                                }
                            }
                        }catch(Exception e){
                            listener.onError(e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        }
        );
        this.controllerRequest.addToRequestQueue(gsonReq,url_area());
    }

    //PRODUCT
//    public void DownloadProducts(){
//        GsonRequest<ModelProduct[]> gsonReq = new GsonRequest<ModelProduct[]>(product_get_url(), ModelProduct[].class,
//                new Response.Listener<ModelProduct[]>() {
//                    @Override
//                    public void onResponse(ModelProduct[] response) {
//                        try {
//                            ImageHelper img = new ImageHelper(context);
//                            for (ModelProduct prod : response) {
//                                prod.setIDFromUID(db.getReadableDatabase(), prod.getUid());
//
//                                ModelProduct oldProduct = new ModelProduct();
//                                oldProduct.loadFromDB(db.getReadableDatabase(), prod.getId());
//
//                                //delete old modifier
//                                Boolean isFound = false;
//                                for (ModelModifier oldmodifier : oldProduct.modifiers){
//                                    isFound = false;
//                                    for (ModelModifier newmodifier : prod.modifiers) {
//                                        if (newmodifier.getUid() == oldmodifier.getUid()){
//                                            isFound = true;
//                                            continue;
//                                        }
//                                    }
//                                    if (!isFound) oldmodifier.deleteFromDB(db.getWritableDatabase());
//                                }
//
//                                prod.setImg(prod.getImg().replace(".jpg","")); //server contain .jpg
//                                prod.saveToDB(db.getWritableDatabase());
//
//                                for (ModelModifier modelModifier : prod.modifiers){
//                                    modelModifier.setIDFromUID(db.getReadableDatabase(), modelModifier.getUid());
//                                    modelModifier.setProduct_id(prod.getId());
//                                    modelModifier.saveToDB(db.getWritableDatabase());
//                                }
//
//
//                                //replace format to png
//                                if (!prod.getImg().equals("")) {
//                                    img.setFileName(prod.getImg());
//                                    if (!img.checFileExist()) {
//                                        DownloadProductImage(prod.getImg());
//                                    }
//                                }
//                                listener.onSuccess(prod.getName() + " updated");
//                            }
//                        }catch(Exception e){
//                            listener.onError(e.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        listener.onError(error.toString());
//                    }
//                }
//        );
//        this.controllerRequest.addToRequestQueue(gsonReq,product_get_url());
//    }


//    public void UploadOrders(){
//        ControllerOrder controllerorder = new ControllerOrder(this.context);
//        List<ModelOrder> orders = controllerorder.getModifiedOrders();
//        final SQLiteDatabase db = DBHelper.getInstance(this.context).getReadableDatabase();
//        final SQLiteDatabase dbw = DBHelper.getInstance(this.context).getWritableDatabase();
//
//        //tambahkan yg diedit saja
//
////        GsonBuilder gsonBuilder;
////        Gson gson;
////        gsonBuilder = new GsonBuilder().setDateFormat("yyyy-mm-dd hh:mm:ss");
////        gson = gsonBuilder.create();
//
//        for (final ModelOrder modelOrder : orders) {
//            modelOrder.prepareUpload(db);
//            modelOrder.setCompany_id(this.company_id);
//            modelOrder.setUnit_id(this.unit_id);
////            Toast.makeText(context, debug, Toast.LENGTH_SHORT).show();
//
//            GsonRequest<ModelOrder> gsonReq = new GsonRequest<ModelOrder>(order_post_url(), modelOrder,
//                    new Response.Listener<ModelOrder>() {
//                        @Override
//                        public void onResponse(ModelOrder response) {
//                            //update ID
//                            try{
//                                modelOrder.setuid(response.getuid());
//                                modelOrder.setUploaded(1);
//                                modelOrder.saveToDB(dbw);
//                                listener.onSuccess(response.getOrderno() + " updated");
//                            }catch (Exception e){
//                                listener.onError(e.toString());
//                            }
//
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            listener.onError(error.toString());
//                        }
//                    }
//            );
//            this.controllerRequest.addToRequestQueue(gsonReq,customer_post_url());
//        }
//
//        //test
//
//
//    }
//
//
//




//    public void DownloadProductImage(final String img_name){
//        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
//        String get_url = image_get_url(img_name);
//
//        // Initialize a new ImageRequest
//        ImageRequest imageRequest = new ImageRequest(
//                get_url, // Image URL
//                new Response.Listener<Bitmap>() { // Bitmap listener
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        // Do something with response
//                        ImageHelper img = new ImageHelper(context);
//                        img.setFileName(img_name);
//                        img.save(response);
//                    }
//                },
//                0, // Image width
//                0, // Image height
//                CENTER_CROP, // Image scale type
//                Bitmap.Config.RGB_565, //Image decode configuration
//                new Response.ErrorListener() { // Error listener
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Do something with error response
//                        listener.onError(error.toString());
////                        error.printStackTrace();
//                    }
//                }
//        );
//
//        this.controllerRequest.addToRequestQueue(imageRequest,image_get_url(img_name));
//    }
}
