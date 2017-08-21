package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/19.
 */

public class PersonalAssetsCodeOut  {
    /**
     * hxbalance : 5129050.24
     * assetList : [{"total":"355.00"},{"productName":"小贷通","total":"12555.56"},{"productName":"在测试一哈","total":"1479.68"}]
     * availablebal : 5100446.42
     */

    public float hxbalance;
    public float availablebal;
    public List<AssetListBean> assetList;

    public PersonalAssetsCodeOut(float hxbalance, float availablebal, List<AssetListBean> assetList) {
        this.hxbalance = hxbalance;
        this.availablebal = availablebal;
        this.assetList = assetList;
    }

    public float getHxbalance() {
        return hxbalance;
    }

    public void setHxbalance(float hxbalance) {
        this.hxbalance = hxbalance;
    }

    public float getAvailablebal() {
        return availablebal;
    }

    public void setAvailablebal(float availablebal) {
        this.availablebal = availablebal;
    }

    public List<AssetListBean> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<AssetListBean> assetList) {
        this.assetList = assetList;
    }

    @Override
    public String toString() {
        return "PersonalAssetsCodeOut{" +
                "hxbalance=" + hxbalance +
                ", availablebal=" + availablebal +
                ", assetList=" + assetList +
                '}';
    }

    public static class AssetListBean {
        /**
         * total : 355.00
         * productName : 小贷通
         */

        public String total;
        public String productName;
        public String productId;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        @Override
        public String toString() {
            return "AssetListBean{" +
                    "total='" + total + '\'' +
                    ", productName='" + productName + '\'' +
                    '}';
        }
    }
}
