package com.kuwai.ysy.module.chat.bean;

public class StsBean {


    /**
     * code : 200
     * data : {"AccessKeyId":"STS.NHjBfBnpsgSycwbLsmaPXr5uK","AccessKeySecret":"2YWC9d9ujp8v2jjPk2ifD7Lb9WSJcq4Cv6sMsPyTxhQc","SecurityToken":"CAIS7AF1q6Ft5B2yfSjIr4vfCdz2g69S0JGSYVHTqHM4bd90nfDeqTz2IH9JfnJpAuwavv4zm2lX7vwTlq8qFcIYGBCcpgmifAITo22beIPkl5Gfz95t0e+IewW6Dxr8w7WhAYHQR8/cffGAck3NkjQJr5LxaTSlWS7OU/TL8+kFCO4aRQ6ldzFLKc5LLw950q8gOGDWKOymP2yB4AOSLjIx41Ak1z4gtPzjk5TGt0CPtjCglL9J/baWC4O/csxhMK14V9qIx+FsfsLDqnUPtkIRrfwq0PwepWie4YnHUkM+/RKKK+7d482WzZzSuCeschqAAVjBYM3X2GCYD8eRawtmGS2Xx5oHKtMWpsmGsnfC506yz78f/74Ck+X2FalnruDHnMwXGkHcHxo9Gl3exOvDfXNSfuv6AkOqedoZl8JlJGGbaxkFZxyMD0MGx8vreD34bnjGepx1APjlWcJWQrVO5nyf0RzkHrOnuj1ziMpAQ5TR","Expiration":"2018-12-27T10:56:03Z"}
     * msg : success
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * AccessKeyId : STS.NHjBfBnpsgSycwbLsmaPXr5uK
         * AccessKeySecret : 2YWC9d9ujp8v2jjPk2ifD7Lb9WSJcq4Cv6sMsPyTxhQc
         * SecurityToken : CAIS7AF1q6Ft5B2yfSjIr4vfCdz2g69S0JGSYVHTqHM4bd90nfDeqTz2IH9JfnJpAuwavv4zm2lX7vwTlq8qFcIYGBCcpgmifAITo22beIPkl5Gfz95t0e+IewW6Dxr8w7WhAYHQR8/cffGAck3NkjQJr5LxaTSlWS7OU/TL8+kFCO4aRQ6ldzFLKc5LLw950q8gOGDWKOymP2yB4AOSLjIx41Ak1z4gtPzjk5TGt0CPtjCglL9J/baWC4O/csxhMK14V9qIx+FsfsLDqnUPtkIRrfwq0PwepWie4YnHUkM+/RKKK+7d482WzZzSuCeschqAAVjBYM3X2GCYD8eRawtmGS2Xx5oHKtMWpsmGsnfC506yz78f/74Ck+X2FalnruDHnMwXGkHcHxo9Gl3exOvDfXNSfuv6AkOqedoZl8JlJGGbaxkFZxyMD0MGx8vreD34bnjGepx1APjlWcJWQrVO5nyf0RzkHrOnuj1ziMpAQ5TR
         * Expiration : 2018-12-27T10:56:03Z
         */

        private String AccessKeyId;
        private String AccessKeySecret;
        private String SecurityToken;
        private String Expiration;

        public String getAccessKeyId() {
            return AccessKeyId;
        }

        public void setAccessKeyId(String AccessKeyId) {
            this.AccessKeyId = AccessKeyId;
        }

        public String getAccessKeySecret() {
            return AccessKeySecret;
        }

        public void setAccessKeySecret(String AccessKeySecret) {
            this.AccessKeySecret = AccessKeySecret;
        }

        public String getSecurityToken() {
            return SecurityToken;
        }

        public void setSecurityToken(String SecurityToken) {
            this.SecurityToken = SecurityToken;
        }

        public String getExpiration() {
            return Expiration;
        }

        public void setExpiration(String Expiration) {
            this.Expiration = Expiration;
        }
    }
}
