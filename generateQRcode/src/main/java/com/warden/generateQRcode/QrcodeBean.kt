package com.warden.generateQRcode

class QrcodeBean {
    /**
     * code : 1
     * msg : 数据返回成功！
     * data : {"qrCodeUrl":"http://www.mxnzp.com/api_file/qrcode/b/d/1/0/2/8/f/2/2c494b6328564ff3b5b0115ff27ec2b7.png","content":"baidu.com","type":0,"qrCodeBase64":null}
     */
    val code = 0
    val msg: String? = null
    val data: DataBean? = null

    class DataBean {
        /**
         * qrCodeUrl : http://www.mxnzp.com/api_file/qrcode/b/d/1/0/2/8/f/2/2c494b6328564ff3b5b0115ff27ec2b7.png
         * content : baidu.com
         * type : 0
         * qrCodeBase64 : null
         */
        val qrCodeUrl: String? = null
        val content: String? = null
        val type = 0
        val qrCodeBase64: Any? = null
    }
}