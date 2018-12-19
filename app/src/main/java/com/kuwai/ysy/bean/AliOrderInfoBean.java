package com.kuwai.ysy.bean;

public class AliOrderInfoBean {


    /**
     * code : 200
     * data : alipay_sdk=alipay-sdk-php-20180705&app_id=2017101709350324&biz_content=%7B%22body%22%3A%22%5Cu5546%5Cu54c1%5Cu4fe1%5Cu606f%22%2C%22subject%22%3A%22%5Cu8863%5Cu670d%22%2C%22out_trade_no%22%3A%22123456%22%2C%22total_amount%22%3A%229.88%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fapi.yushuiyuan%2Fapi%2FPayment%2Fnotify_url&sign_type=RSA&timestamp=2018-12-19+14%3A45%3A36&version=1.0&sign=Dsuq6XvDPMhkoqeux9DsAPIgXD2FGSl7p8%2Ba9yFnsFvds2bInjxHoaObSp%2BITgD0MVYnXAXNAlT%2F1CO72UinW7NS3i1bWmvQFJu93SZXIRP1l2LjJwbDkSVSo%2BL7czPjtBJHqmRCV%2BzoNsQxK8DmTWWcBfR5%2BKrAHic%2FgWdfU%2BSJLolBWHxtY%2BdJbf%2B4ME1BUyEtVTrWc6gqmAG2nDJkU%2FSYH%2BcOoMCpv14YF9ZibavkLrVCil9TexHLfRDkayogKtScIeG7Pl69CJs%2BaBuj9WTxgTFJtjAxoX4a1Onk7%2Bkr%2BtZZeXdn1ujtH49SUHQbsfoE1ESg4LyT5v4z3Wat6A%3D%3D
     * msg : success
     */

    private int code;
    private String data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
