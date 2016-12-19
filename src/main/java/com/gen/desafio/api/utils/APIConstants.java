package com.gen.desafio.api.utils;


public interface APIConstants {

	public static final String APP_ISSUER_NAME = "DesafioAPI";
    
    public static final String DESAFIO_REST_API_DEV_ENDPOINT = "http://localhost:8080/desafio/api";
    public static final String DESAFIO_REST_API_PROD_ENDPOINT = "https:/desafio-app.herokuapp.com/api";
    
    public static final String API_PUBLIC_KEY = "G10590E8C8N2E482FB37A254275C312FD";
	public static final String API_SECRET_KEY = "da8d0d9daa+7a14b86ab425cc2f3009b6fcf43GENLabs2016";
	
	public static final String PUSHBOTS_REST_API_URL = "https://api.pushbots.com";
	public static final String X_PUSHBOTS_APPID = "56e86cc137d9b038028b4567";
	public static final String X_PUSHBOTS_SECRET = "67dfd8e9488828e07565f152761116d8";
	
	public static final String HMAC_SHA256 = "HmacSHA256";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DIGITAL_SIGNATURE_UTF8_ENCODING = "UTF-8";

    public static final String TRUE_STRING = "true";
    public static final String FALSE_STRING = "false";

    /** Service HTTP Headers. */
    public static final String X_DESAFIO_API_KEY = "X-DesafioAPI-Key";
    public static final String AUTHORIZATION = "Authorization";
    public static final String X_AUTH_TOKEN = "X-AUTH-TOKEN";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_DATE = "Date";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String ON = "ON";
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	
    /** RegExp Patterns **/
    public static final String PATTERN_NAMES = "[a-z-A-Z]*"; 
    public static final String PATTERN_FULL_NAMES = "^[\\p{L} .'-]+$";
    public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{4,12}$";
    public static final String PATTERN_MOBILE_PHONES = "\\d{3}-\\d{7}";
    
}
