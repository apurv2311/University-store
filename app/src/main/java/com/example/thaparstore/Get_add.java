package com.example.thaparstore;

import android.widget.ImageView;

public class Get_add {
    private String name;
    private String detail;
    private String price;
    private String image_url;
    public Get_add() {}
    public Get_add(String mName,String mDetail,String mPrice)
    {
        name=mName;
        detail=mDetail;
        price=mPrice;
        image_url="";
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Get_add(String mName, String mDetail, String mPrice, String url)
    {
        name=mName;
        detail=mDetail;
        price=mPrice;
        image_url=url;
    }

    public String getDetail() {
        return detail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
