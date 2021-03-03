package za.asa_media.awesome_sa.support.url_keys;

public class URLListKeys {


    /*******
     * for Login view  (auth/login)
     ******/
    public static final String USER_TOKEN = "token";
    public static final String LOGIN_EMAIL = "email";
    public static final String LOGIN_PASSWORD = "password";

    public static final String USER_ID = "userid";
    /******
     * for  user Signup
     ******/

    public static final String SIGNUP_EMAIL = "email";
    public static final String SIGNUP_FIRSTNAME = "firstname";
    public static final String SIGNUP_LASTNAME = "lastname";
    public static final String SIGNUP_PASSWORD = "password";


    /******
     * for Forgot password (u
     ******/
    public static final String FORGOT_PASSWORD_EMAIL = "email";

    /******
     * for forget password part 2
     *****/

    public static final String FORGOT_PASSWORD_CODE = "code";
    public static final String NEW_PASSWORD = "newpass";
    public static final String CONFIRM_PASSWORD = "confirmpass";

    /******
     * for add user info
     *****/
    public static final String ADD_USER_INFO_USER_ID = "userid";
    public static final String ADD_USER_INFO_DOB = "dob";
    public static final String ADD_USER_INFO_PHONE = "phone";
    public static final String ADD_USER_INFO_CITY = "city";
    public static final String ADD_USER_INFO_COUNTRY = "country";
    public static final String ADD_USER_INFO_LAT = "lat";
    public static final String ADD_USER_INFO_LONG = "long";
    public static final String ADD_USER_INFO_IMAGE = "image";

    /******
     * for add business
     ******/
    public static final String ADD_BUSINESS_NAME = "name";
    public static final String ADD_BUSINESS_CATEGORY = "category";
    public static final String ADD_BUSINESS_ADDRESS = "address";
    public static final String ADD_BUSINESS_CITY = "city";
    public static final String ADD_BUSINESS_STATE = "state";
    public static final String ADD_BUSINESS_COUNTRY = "country";
    public static final String ADD_BUSINESS_PINCODE = "pincode";
    public static final String ADD_BUSINESS_PHONE = "phone";
    public static final String ADD_BUSINESS_WEBSITE_URL = "website";
    public static final String ADD_BUSINESS_ID = "businessid";

                 /*  facebook
                     google
                     twitter
                     instagram
                     linkdin
                     youtube
                     vimeo
                     snapchat */

    public static final String ADD_FACEBOOK = "facebook";
    public static final String ADD_GOOGLE = "google";
    public static final String ADD_TWITTER = "twitter";
    public static final String ADD_INSTAGRAM = "instagram";
    public static final String ADD_LINKEDIN = "linkdin";
    public static final String ADD_YOUTUBE = "youtube";
    public static final String ADD_VIMEO = "vimeo";
    public static final String ADD_SNAPCHAT = "snapchat";

/*
token:cc049423ea673e9d15c2b0c09f9203ec
userid:3
name:Mybusiness
category:restaurent
address:street no 1
city:mohali
state:mohali
country:INDIA
pincode:144401
phone:9876543210
website:http://myreatuarent.com
businessid:
*/

//////........////////////////////////////........./////////////.........//////////////......//////////
    /******
     * for show stores  (store/showstores)
     ******/
    public static final String SHOW_STORES_SESSION_ID = "sessionId";
    public static final String SHOW_STORES_USER_ID = "userId";

    /*
    {        "sessionId":"",
            "userId":1
    }
    */


    /******
     * for Add Customer (customer/upsertcustomer)
     ******/
    public static final String UPSERT_CUSTOMER_SESSION_ID = "sessionId";
    public static final String UPSERT_CUSTOMER_CUSTOMER_NUMBER = "customerNumber";
    public static final String UPSERT_CUSTOMER_CUSTOMER_NAME = "customerName";
    public static final String UPSERT_CUSTOMER_ADDRESS = "address";
    public static final String UPSERT_CUSTOMER_CITY = "city";
    public static final String UPSERT_CUSTOMER_COUNTRY = "country";
    public static final String UPSERT_CUSTOMER_CONTACT_PERSON = "contactPerson";
    public static final String UPSERT_CUSTOMER_PHONE_NUMBER = "phoneNumber";
    public static final String UPSERT_CUSTOMER_EMAIL = "email";
    public static final String UPSERT_CUSTOMER_MOBILE_NUMBER = "mobileNumber";
    public static final String UPSERT_CUSTOMER_NOTES = "notes";
    public static final String UPSERT_CUSTOMER_USER_ID = "userId";

/*
  {
"sessionId":"",
"customerNumber":"Cust12",
"customerName":"Savu12",
"address":"41 Madison Ave",
"city":"New York",
"country":"US",
"contactPerson":"Savita",
"phoneNumber":"786814567",
"email":"customer12@gmail.com",
"mobileNumber":"7663276673",
"notes":"Here are my notes",
"userId":1
}

*/

    /******
     * for setting/settings   (setting/settings)
     ******/
    public static final String STORE_SETTING_SETTING_SESSION_ID = "sessionId";
    public static final String STORE_SETTING_SETTING_USER_ID = "userId";
    public static final String STORE_SETTING_SETTING_COMPANY_NAME = "companyName";
    public static final String STORE_SETTING_SETTING_LOGO = "logo";
    public static final String STORE_SETTING_SETTING_PHONE = "phone";
    public static final String STORE_SETTING_SETTING_CURRENCY = "currency";
    public static final String STORE_SETTING_SETTING_DISCOUNT = "discount";
    public static final String STORE_SETTING_SETTING_TAX = "tax";
    public static final String STORE_SETTING_SETTING_NUMBER_DECIMAL = "numberDecimal";
    public static final String STORE_SETTING_SETTING_TIMEZONE = "timezone";
    public static final String STORE_SETTING_SETTING_RECEIPT_HEADER = "receiptHeader";
    public static final String STORE_SETTING_SETTING_RECEIPT_FOOTER = "receiptFooter";
    public static final String STORE_SETTING_SETTING_LOGIN_ID = "loginId";
    public static final String STORE_SETTING_SETTING_TRANSACTION_KEY = "transactionKey";
    public static final String STORE_SETTING_SETTING_KEYBOARD = "keyboard"; // STATIC VALUE 0
    public static final String STORE_SETTING_SETTING_AUTHORIZE = "authorize";

    /*
  SEND AS FORM DATA
sessionId
userId
companyName
logo
phone
currency
discount
tax
numberDecimal
timezone
receiptHeader
receiptFooter
loginId
transactionKey
keyboard
authorize (true,false)
    */

    /******
     * for add warehouses (warehouse/upsertwarehouse
     ******/
    public static final String UPSERT_WAREHOUSE_SESSION_ID = "sessionId";
    public static final String UPSERT_WAREHOUSE_USER_ID = "userId";
    public static final String UPSERT_WAREHOUSE_NAME = "name";
    public static final String UPSERT_WAREHOUSE_PHONE = "phone";
    public static final String UPSERT_WAREHOUSE_EMAIL = "email";
    public static final String UPSERT_WAREHOUSE_ADDRESS = "address";

    /*
   {
    "sessionId":"",
    "userId":"1",
    "name":"wqeqwe",
    "phone":"Warehouse1",
    "email":"warehouse@testing.com",
    "address":"D-151 Industrial Area Mohali"
    }
    */


    /******
     * for show products    (product/showproducts)
     ******/
    public static final String SHOW_PRODUCTS_SESSION_ID = "sessionId";
    public static final String SHOW_PRODUCTS_USER_ID = "userId";


    /*
    {
    "sessionId":"",
    "userId":"1"
}
    */

    /******
     * for show customers (customer/showcustomers)
     ******/
    public static final String SHOW_CUSTOMER_SESSION_ID = "sessionId";
    public static final String SHOW_CUSTOMER_USER_ID = "userId";

    /*
   {
    "sessionId":"",
    "userId":"1"
   }
    */


    /******
     * for add store  (store/upsertstore)
     ******/
    public static final String UPSERT_STORE_SESSION_ID = "sessionId";
    public static final String UPSERT_STORE_USER_ID = "userId";
    public static final String UPSERT_STORE_NAME = "name";
    public static final String UPSERT_STORE_EMAIL = "email";
    public static final String UPSERT_STORE_PHONE = "phone";
    public static final String UPSERT_STORE_COUNTRY = "country";
    public static final String UPSERT_STORE_CITY = "city";
    public static final String UPSERT_STORE_ADDRESS = "address";
    public static final String UPSERT_STORE_CUSTOM_FOOTER = "customFooter";


  /*
   {
    "sessionId":"",
    "name":"RELIANCE FOOTPRINTS",
    "email":"reliancefootprint@pos.com",
    "phone":"7696090671",
    "country":"India",
    "city":"dsfdsf",
    "address":"ewewewew",
    "customFooter":"Here is Custom Footer",
    "userId":10
    }

    */


    /******
     * for ADD SUB USER  (user/addsubuser)
     ******/
    public static final String ADD_SUB_USER_SESSION_ID = "sessionId";
    public static final String ADD_SUB_USER_USER_ID = "userId";
    public static final String ADD_SUB_USER_FIRSTNAME = "firstName";
    public static final String ADD_SUB_USER_LASTNAME = "lastName";
    public static final String ADD_SUB_USER_EMAIL = "email";
    public static final String ADD_SUB_USER_PASSWORD = "password";
    public static final String ADD_SUB_USER_ROLE = "role";
    public static final String ADD_SUB_USER_IMAGE = "image";
    public static final String ADD_SUB_USER_STORE_ID = "storeId";

    /*
SEND AS FORM DATA
sessionId
userId
firstName
lastName
email
password
role
image
storeId

    */


    /******
     * for show ware houses  (warehouse/showwarehouses)
     ******/
    public static final String SHOW_WAREHOUSE_SESSION_ID = "sessionId";
    public static final String SHOW_WAREHOUSE_USER_ID = "userId";

    /*
   {
    "sessionId":"",
    "userId":"10"
    }
    */


    /******
     * for show sub users (/user/showsubusers)
     ******/
    public static final String SHOW_SUBUSERS_SESSION_ID = "sessionId";
    public static final String SHOW_SUBUSERS_USER_ID = "userId";

    /*
   {
    "sessionId":"",
    "userId":"10"
    }
    */

    /******
     * for edit profile picture (user/editprofilepicture)
     ******/
    public static final String EDIT_PROFILE_PICTURE_SESSION_ID = "sessionId";
    public static final String EDIT_PROFILE_PICTURE_USER_ID = "userId";
    public static final String EDIT_PROFILE_PICTURE_IMAGE = "image";
    /*
   {
    sessionId
    userId
    image
    }
    */


    /******
     * for show suppliers (/supplier/showsuppliers)
     ******/
    public static final String SHOW_SUPPLIERS_SESSION_ID = "sessionId";
    public static final String SHOW_SUPPLIERS_USER_ID = "userId";

    /*
   {
    "sessionId":"",
    "userId":"10"
    }
    */


    /******
     * for show measurements units (/unit/showmeasurementunits)
     ******/
    public static final String SHOW_MEASUREMENTS_UNITS_SESSION_ID = "sessionId";
    public static final String SHOW_MEASUREMENTS_UNITS_USER_ID = "userId";

    /*
   {
    "sessionId":"",
    "userId":"10"
    }
    */

    /******
     * for show categories (/category/showcategories)
     ******/
    public static final String SHOW_CATEGORIES_SESSION_ID = "sessionId";
    public static final String SHOW_CATEGORIES_USER_ID = "userId";

    /*
  {
   "sessionId":"",
   "userId":"1"
  }*/

    /******
     * for upsert products (product/upsertproduct)
     ******/
    public static final String UPSERT_PRODUCT_SESSION_ID = "sessionId";
    public static final String UPSERT_PRODUCT_USER_ID = "userId";
    public static final String UPSERT_PRODUCT_SUPPLIER_NUMBER = "supplierNumber";
    public static final String UPSERT_PRODUCT_PRODUCT_NUMBER = "productNumber";
    public static final String UPSERT_PRODUCT_PRODUCT_NAME = "productName";
    public static final String UPSERT_PRODUCT_UNIT_OF_MEASUREMENT = "unitOfMeasurement";
    public static final String UPSERT_PRODUCT_CATEGORY = "category";
    public static final String UPSERT_PRODUCT_PURCHASE_PRICE = "purchasePrice";
    public static final String UPSERT_PRODUCT_SELLING_PRICE = "sellingPrice";
    public static final String UPSERT_PRODUCT_NOTES = "notes";
    public static final String UPSERT_PRODUCT_QUANTITY = "quantity";
    public static final String UPSERT_PRODUCT_IMAGE = "image";

    /*
   SEND AS FORM DATA
userId
sessionId
supplierNumber
productNumber
productName
unitOfMeasurement
category
purchasePrice
sellingPrice
notes
quantity
******** Multiple Image Files with same Name (If 3 images then 3 parameters with image name)********
image
image
image
image
    */

    /******
     * for upsert unit of measurement (unit/upsertunitofmeasurement)
     ******/
    public static final String UPSERT_UNIT_OF_MEASUREMENT_SESSION_ID = "sessionId";
    public static final String UPSERT_UNIT_OF_MEASUREMENT_UNIT_NAME = "unitName";
    public static final String UPSERT_UNIT_OF_MEASUREMENT_USER_ID = "userId";
    public static final String UPSERT_UNIT_OF_MEASUREMENT_STATUS = "status";

    /*
  {
    "sessionId":"",
    "unitName":"litreswewewe",
    "userId":2,
    "status":"1"
  }
  */

    /******
     * for upsert categories (category/upsertcategory)
     ******/
    public static final String UPSERT_CATEGORY_SESSION_ID = "sessionId";
    public static final String UPSERT_CATEGORY_CATEGORY_NAME = "categoryName";
    public static final String UPSERT_CATEGORY_USER_ID = "userId";
    public static final String UPSERT_CATEGORY_STATUS = "status";
    /*
 {
"sessionId":"",
"categoryName":"Cups",
"userId":1,
"status":1
}
*/

    /******
     * for upsert suppliers (supplier/upsertsupplier)
     ******/
    public static final String UPSERT_SUPPLIER_SESSION_ID = "sessionId";
    public static final String UPSERT_SUPPLIER_SUPPLIER_NUMBER = "supplierNumber";
    public static final String UPSERT_SUPPLIER_SUPPLIER_NAME = "supplierName";
    public static final String UPSERT_SUPPLIER_ADDRESS = "address";
    public static final String UPSERT_SUPPLIER_CITY = "city";
    public static final String UPSERT_SUPPLIER_COUNTRY = "country";
    public static final String UPSERT_SUPPLIER_CONTACT_PERSON = "contactPerson";
    public static final String UPSERT_SUPPLIER_PHONE_NUMBER = "phoneNumber";
    public static final String UPSERT_SUPPLIER_EMAIL = "email";
    public static final String UPSERT_SUPPLIER_MOBILE_NUMBER = "mobileNumber";
    public static final String UPSERT_SUPPLIER_NOTES = "notes";
    public static final String UPSERT_SUPPLIER_BALANCE = "balance";
    public static final String UPSERT_SUPPLIER_STOCK_AVAILABLE = "stockAvailable";
    public static final String UPSERT_SUPPLIER_USER_ID = "userId";

    /*
 {
"sessionId":"",
"supplierNumber":"Supplier23324324",
"supplierName":"Savita1234234",
"address":"41 Madison Ave",
"city":"New York",
"country":"US",
"contactPerson":"Savita",
"phoneNumber":"786814567",
"email":"supplier123432424@gmail.com",
"mobileNumber":"7663276673",
"notes":"Here are my notes",
"balance":"123232",
"stockAvailable":"23231",
"userId":5
}
*/


    /******
     * for show sales (sale/showsales)
     ******/
    public static final String SHOW_SALES_SESSION_ID = "sessionId";
    public static final String SHOW_SALES_USER_ID = "userId";

    /*
    http://54.149.213.162/sale/showsales
{
    "sessionId":"",
    "userId":1
}
    */

    /******
     * for for show sales details (saledetail/showsaledetails)
     ******/
    public static final String SHOW_SALES_DETAIL_SESSION_ID = "sessionId";
    public static final String SHOW_SALES_DETAIL_USER_ID = "userId";
    public static final String SHOW_SALES_DETAIL_SALE_NUMBER = "saleNumber";
    /*
    http://54.149.213.162/saledetail/showsaledetails
   {
    "sessionId":"",
    "userId":"1",
    "saleNumber":"SALE112"
    }
    */


    /******
     * for for show sales of customer (sale/showsalesofcustomer)
     ******/
    public static final String SHOW_SALES_OF_CUSTOMER_SESSION_ID = "sessionId";
    public static final String SHOW_SALES_OF_CUSTOMER_USER_ID = "userId";
    public static final String SHOW_SALES_OF_CUSTOMER_CUSTOMER_ID = "customerId";

    /*
     http://54.149.213.162/sale/showsalesofcustomer
{
    "sessionId":"",
    "userId":"1",
    "customerId":""
}

    */

    /******
     * for show demo damages  (demodamage/showdemodamages)
     ******/
    public static final String SHOW_DEMO_DAMAGE_SESSION_ID = "sessionId";
    public static final String SHOW_DEMO_DAMAGE_USER_ID = "userId";


    /*
     http://54.149.213.162/demodamage/showdemodamages
{
    "sessionId":"",
    "userId":"1"
}
    */


    //....................//........................///........................//................


    /******
     * for update response (Phones/UpdateResponse)
     ******/
    public static final String UPDATE_RESPONSE_CASE_ID = "case_id";
    public static final String UPDATE_RESPONSE_RESPONSE = "Response";

    /*
    params[@"case_id"]=self.CasesString;
    params[@"Response"]=rsspondStr;
    */


    /******
     * for ..............
     ******/
    //......................

    /*
     .........................
    */

}
