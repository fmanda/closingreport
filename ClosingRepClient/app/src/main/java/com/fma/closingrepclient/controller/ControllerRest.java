package com.fma.closingrepclient.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.helper.GsonRequest;
import com.fma.closingrepclient.model.ModelArea;
import com.fma.closingrepclient.model.ModelCustomer;
import com.fma.closingrepclient.model.ModelDetailOrder;
import com.fma.closingrepclient.model.ModelMaterial;
import com.fma.closingrepclient.model.ModelOrder;
import com.fma.closingrepclient.model.ModelProduct;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    private int user_id;

    private ControllerRequest controllerRequest;
    protected Listener listener;

    public interface Listener {
        void onSuccess(String msg);
        void onError(String msg);
    }

    private AsyncTaskListener asyncTaskListener;

    public interface AsyncTaskListener {
        void onProgressUpdate(String msg);
    }


    public void setListener(ControllerRest.Listener listener) {
        this.listener = listener;
    }

    public void setAsyncTaskListenerListener(ControllerRest.AsyncTaskListener listener) {
        this.asyncTaskListener = listener;
    }

    public ControllerRest(Context context) {
        this.context = context;
        this.db = DBHelper.getInstance(context);
        this.controllerRequest = ControllerRequest.getInstance(context);
        this.controllerSetting = new ControllerSetting((this.context));

        this.user_id = controllerSetting.getUserID();

//        this.base_url = "http://" + controllerSetting.getSettingStr("rest_url") + "/";
    }

    protected String base_url(){
        return "http://" + controllerSetting.getSettingStr("rest_url") + "/";
    }

    public String url_login(){
        return base_url() + "teknisilogin";
    }

    private String url_area(){
        return base_url() + "area";
    }

    private String url_customer(){
        return base_url() + "customer";
    }

    private String url_product(){
        return base_url() + "product";
    }

    private String url_material(){
        return base_url() + "material";
    }

    private String url_order(){
        return base_url() + "orderof/" + this.user_id;
    }

    private String url_detailorder(){
        return base_url() + "detailorderclient";
    }

    private void log(String s) {
        asyncTaskListener.onProgressUpdate(s);
    }

    private void SaveAreas(ModelArea[] areas){
        try {
            for (ModelArea area : areas) {
                area.setIDFromUID(db.getReadableDatabase(), area.getUid());
                area.saveToDB(db.getWritableDatabase());
                if (area.getId() == 0) {
                    log("[area] " + area.getNama() + " inserted");
                }else{
                    log("[area] " + area.getNama() + " updated");
                }
            }
        }catch(Exception e){
            log(e.toString());
        }
    }



    private void SaveMaterials(ModelMaterial[] materials){
        try {
            for (ModelMaterial material : materials) {
                material.setIDFromUID(db.getReadableDatabase(), material.getUid());
                material.saveToDB(db.getWritableDatabase());
                if (material.getId() == 0) {
                    log("[material] " + material.getNama() + " inserted");
                }else{
                    log("[material] " + material.getNama() + " updated");
                }
                Log.d("","executed");
            }
        }catch(Exception e){
            log(e.toString());
        }
    }

    private void SaveProducts(ModelProduct[] products){
        try {
            for (ModelProduct product : products) {
                product.setIDFromUID(db.getReadableDatabase(), product.getUid());
                product.saveToDB(db.getWritableDatabase());
                if (product.getId() == 0) {
                    log("[product] " + product.getNama() + " inserted");
                }else{
                    log("[product] " + product.getNama() + " updated");
                }
            }
        }catch(Exception e){
            log(e.toString());
        }
    }

    private void SaveCustomers(ModelCustomer[] customers){
        try {
            for (ModelCustomer customer : customers) {
                customer.setIDFromUID(db.getReadableDatabase(), customer.getUid());
                if (customer.getArea_id() > 0){
                    ModelArea modelArea = new ModelArea();
                    modelArea.setIDFromUID(db.getReadableDatabase(), customer.getArea_uid());
                    customer.setArea_id(modelArea.getId());

                    log("[customer] " + customer.getNama() + " set relationship");
                }
                customer.saveToDB(db.getWritableDatabase());
                if (customer.getId() == 0) {
                    log("[customer] " + customer.getNama() + " inserted");
                }else{
                    log("[customer] " + customer.getNama() + " updated");
                }
            }
        }catch(Exception e){
            log(e.toString());
        }
    }

    private void SaveOrders(ModelOrder[] orders){
        try {
            for (ModelOrder order : orders) {
                order.setIDFromUID(db.getReadableDatabase(), order.getUid());

                ModelArea area = new ModelArea(db.getReadableDatabase(), order.getArea_uid());
                ModelProduct product = new ModelProduct(db.getReadableDatabase(), order.getProduct_uid());
                ModelCustomer customer = new ModelCustomer(db.getReadableDatabase(), order.getCustomer_uid());
                log("[order] " + customer.getNama() + " set relationship");

                order.setArea_id(area.getId());
                order.setProduct_id(product.getId());
                order.setCustomer_id(customer.getId());

                order.saveToDB(db.getWritableDatabase());
                if (order.getId() == 0) {
                    log("[order] " + order.getOrderno() + " inserted");
                }else{
                    log("[order] " + order.getOrderno() + " updated");
                }
            }
        }catch(Exception e){
            log(e.toString());
        }
    }

    public void SyncData(final Boolean async){
        AsyncRestRunner runner = new AsyncRestRunner(this);
        runner.execute(async);
    }


    public void DownloadArea(Boolean async){
        if (async) {
            GsonRequest<ModelArea[]> gsonReq = new GsonRequest<>(url_area(), ModelArea[].class,
                new Response.Listener<ModelArea[]>() {
                    @Override
                    public void onResponse(ModelArea[] response) {
                        SaveAreas(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        log(error.toString());
                    }
                }
            );
            this.controllerRequest.addToRequestQueue(gsonReq, url_area());
        }else {
            RequestFuture<ModelArea[]> future = RequestFuture.newFuture();
            GsonRequest<ModelArea[]> gsonReq = new GsonRequest<>(url_area(), ModelArea[].class, future, future);
            this.controllerRequest.addToRequestQueue(gsonReq, url_area());

            try {
                ModelArea[] response = future.get(10, TimeUnit.SECONDS);
                SaveAreas(response);
            } catch (InterruptedException|ExecutionException| TimeoutException e) {
                log(e.getMessage());
            }
        }
    }

    public void DownloadMaterial(Boolean async){
        if (async) {
            GsonRequest<ModelMaterial[]> gsonReq = new GsonRequest<>(url_material(), ModelMaterial[].class,
                new Response.Listener<ModelMaterial[]>() {
                    @Override
                    public void onResponse(ModelMaterial[] response) {
                        SaveMaterials(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        log(error.toString());
                    }
                }
            );
            this.controllerRequest.addToRequestQueue(gsonReq, url_material());
        }else {
            RequestFuture<ModelMaterial[]> future = RequestFuture.newFuture();
            GsonRequest<ModelMaterial[]> gsonReq = new GsonRequest<>(url_material(), ModelMaterial[].class, future, future);
            this.controllerRequest.addToRequestQueue(gsonReq, url_material());

            try {
                ModelMaterial[] response = future.get(30, TimeUnit.SECONDS);
                SaveMaterials(response);
            } catch (InterruptedException|ExecutionException| TimeoutException e) {
                log(e.getMessage());
            }
        }
    }

    public void DownloadProduct(Boolean async) {
        if (async) {
            GsonRequest<ModelProduct[]> gsonReq = new GsonRequest<>(url_product(), ModelProduct[].class,
                new Response.Listener<ModelProduct[]>() {
                    @Override
                    public void onResponse(ModelProduct[] response) {
                        SaveProducts(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        log(error.toString());
                    }
                }
            );
            this.controllerRequest.addToRequestQueue(gsonReq, url_product());
//            return Boolean.TRUE;
        }else {
            RequestFuture<ModelProduct[]> future = RequestFuture.newFuture();
            GsonRequest<ModelProduct[]> gsonReq = new GsonRequest<>(url_product(), ModelProduct[].class, future, future);
            this.controllerRequest.addToRequestQueue(gsonReq, url_product());

            try {
                ModelProduct[] response = future.get(30, TimeUnit.SECONDS);
                SaveProducts(response);
            } catch (InterruptedException|ExecutionException| TimeoutException e) {
                log(e.getMessage());
            }
        }
    }

    public void DownloadCustomer(Boolean async){
        if (async) {
            GsonRequest<ModelCustomer[]> gsonReq = new GsonRequest<>(url_customer(), ModelCustomer[].class,
                new Response.Listener<ModelCustomer[]>() {
                    @Override
                    public void onResponse(ModelCustomer[] response) {
                        SaveCustomers(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        log(error.toString());
                    }
                }
            );
            this.controllerRequest.addToRequestQueue(gsonReq, url_customer());
        }else {
            RequestFuture<ModelCustomer[]> future = RequestFuture.newFuture();
            GsonRequest<ModelCustomer[]> gsonReq = new GsonRequest<>(url_customer(), ModelCustomer[].class, future, future);
            this.controllerRequest.addToRequestQueue(gsonReq, url_customer());

            try {
                ModelCustomer[] response = future.get(30, TimeUnit.SECONDS);
                SaveCustomers(response);
            } catch (InterruptedException|ExecutionException| TimeoutException e) {
                log(e.getMessage());
            }
        }
    }

    public void DownloadOrder(Boolean async){
        if (async) {
            GsonRequest<ModelOrder[]> gsonReq = new GsonRequest<>(url_order(), ModelOrder[].class,
                    new Response.Listener<ModelOrder[]>() {
                        @Override
                        public void onResponse(ModelOrder[] response) {
                            SaveOrders(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            log(error.toString());
                        }
                    }
            );
            this.controllerRequest.addToRequestQueue(gsonReq, url_order());
        }else {
            RequestFuture<ModelOrder[]> future = RequestFuture.newFuture();
            GsonRequest<ModelOrder[]> gsonReq = new GsonRequest<>(url_order(), ModelOrder[].class, future, future);
            this.controllerRequest.addToRequestQueue(gsonReq, url_order());

            try {
                ModelOrder[] response = future.get(30, TimeUnit.SECONDS);
                SaveOrders(response);
            } catch (InterruptedException|ExecutionException| TimeoutException e) {
                log(e.getMessage());
            }
        }
    }


    public void UploadDetailOrder(Boolean async){
        ControllerDetailOrder controllerDetailOrder = new ControllerDetailOrder(this.context);
        List<ModelDetailOrder> detailOrders = controllerDetailOrder.getDetailOrders("",0);
        final SQLiteDatabase db = DBHelper.getInstance(this.context).getReadableDatabase();
        final SQLiteDatabase dbw = DBHelper.getInstance(this.context).getWritableDatabase();


        for (final ModelDetailOrder detailOrder : detailOrders) {
            detailOrder.prepareUpload(db);
            detailOrder.setUser_id(this.user_id);
            if (async) {
                GsonRequest<ModelDetailOrder> gsonReq = new GsonRequest<>(url_detailorder(), detailOrder,
                        new Response.Listener<ModelDetailOrder>() {
                            @Override
                            public void onResponse(ModelDetailOrder response) {
                                detailOrder.setUid(response.getUid());
                                detailOrder.setUploaded(1);
                                detailOrder.saveToDB(dbw);
                                log("[detail_order] " + detailOrder.getNobukti() + " uploaded");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                log(error.toString());
                            }
                        }
                );
                this.controllerRequest.addToRequestQueue(gsonReq, url_detailorder());
            } else {
                RequestFuture<ModelDetailOrder> future = RequestFuture.newFuture();
                GsonRequest<ModelDetailOrder> gsonReq = new GsonRequest<>(url_detailorder(), detailOrder, future, future);
                this.controllerRequest.addToRequestQueue(gsonReq, url_detailorder());

                try {
                    ModelDetailOrder response = future.get(30, TimeUnit.SECONDS);
                    detailOrder.setUid(response.getUid());
                    detailOrder.setUploaded(1);
                    detailOrder.saveToDB(dbw);
                    log("[detail_order] " + detailOrder.getNobukti() + " uploaded");

                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    log(e.getMessage());
                }
            }
        }
    }
}


class AsyncRestRunner extends AsyncTask<Boolean, String, Void> {
    private ControllerRest controllerRest;

    AsyncRestRunner(ControllerRest controllerRest) {
        this.controllerRest = controllerRest;
        this.controllerRest.setAsyncTaskListenerListener(new ControllerRest.AsyncTaskListener() {
            @Override
            public void onProgressUpdate(String msg) {
                publishProgress(msg);
            }
        });
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        controllerRest.listener.onSuccess("FINISH");
    }

    @Override
    protected void onProgressUpdate(String... values) {
        controllerRest.listener.onSuccess(values[0]);
    }

    @Override
    protected Void doInBackground(Boolean... booleans) {
        boolean async = booleans[0];
        publishProgress("Connecting to Rest API : " + controllerRest.base_url());
        publishProgress("Upload Detail Order");
        controllerRest.UploadDetailOrder(async);

        publishProgress("Download Master");
        controllerRest.DownloadArea(async);
        controllerRest.DownloadMaterial(async);
        controllerRest.DownloadProduct(async);
        controllerRest.DownloadCustomer(async);
        publishProgress("Download Order");
        controllerRest.DownloadOrder(async);
        return null;
    }
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
