public CreditCardReversalBO getCreditCardAPI(CreditCardReversalBO model) {
	   CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();
       String jsonString = "";
       String sResponseString = "";
       String errorCode = "";
       HttpResponse httpResponse = null;
       HttpPost post = null;

       try {

           JSONObject json = new JSONObject();
           json.put("encryptedPassword", model.getEncryptedPassword());
           json.put("key", model.getKey());
           json.put("itclTranID", model.getItclTranID());
           json.put("requestNo", model.getRequestNo());
           json.put("reason", model.getReason());
           
           

           System.out.println("json = " + json);
           String url ="http://192.183.155.22/ERABAsiaCreditCardTransfer/creditCardTransferReversal";
           System.out.println("url = " + url);

           CloseableHttpClient client = HttpClients.createDefault();
           int StatusCode = httpResponse.getStatusLine().getStatusCode();
           System.out.println("StatusCode ======= >>>>>>> " + StatusCode);
           String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
           System.out.println("responseString ======= >>>>>>> " + responseString);
          
           org.json.JSONObject obj = new org.json.JSONObject(responseString);


           String responseCode = "";
           String responseMessage = "";
           String encryptedPassword = "";
           String key = "";
           String itclTranID = "";
           String requestNo = "";
           String reason = "";
           
           if (StatusCode == 200) {
               JSONObject ob = new JSONObject(jsonString);
               System.out.println("ob = " + ob);
               if (ob.has("responseMessage") && !ob.isNull("responseMessage")) {
                   responseMessage = ob.getString("responseMessage");
                   oCreditCardReversalBO.setErrorMessage(responseMessage);
               }

               if (ob.has("responseCode") && !ob.isNull("responseCode")) {
                   responseCode = ob.getString("responseCode");
                   oCreditCardReversalBO.setErrorCode(responseCode);

               }

               if ("0".equals(responseCode)) {
                   if (ob.has("encryptedPassword") && !ob.isNull("encryptedPassword")) {
                	   encryptedPassword = ob.getString("encryptedPassword");
                	   oCreditCardReversalBO.setEncryptedPassword(encryptedPassword);

                   }
                   if (ob.has("key") && !ob.isNull("key")) {
                	   key = ob.getString("key");
                	   oCreditCardReversalBO.setKey(key);

                   }

                   if (ob.has("itclTranID") && !ob.isNull("itclTranID")) {
                	   itclTranID = ob.getString("itclTranID");
                	   oCreditCardReversalBO.setItclTranID(itclTranID);

                   }

                   if (ob.has("requestNo") && !ob.isNull("requestNo")) {
                	   requestNo = ob.getString("requestNo");
                	   oCreditCardReversalBO.setRequestNo(requestNo);
                   }
                   if (ob.has("reason") && !ob.isNull("reason")) {
                	   reason = ob.getString("reason");
                	   oCreditCardReversalBO.setReason(reason);
                   }

                  
               } else {
            	   oCreditCardReversalBO.setErrorCode(responseCode);
            	   oCreditCardReversalBO.setErrorMessage(responseMessage);
               }

           } else {
        	   oCreditCardReversalBO.setErrorCode("1");
        	   oCreditCardReversalBO.setErrorMessage("Credit Card API problem " + StatusCode);
           }

       }
       catch (Exception var17) {
	    	  oCreditCardReversalBO.setErrorCode("1");
	    	  oCreditCardReversalBO.setErrorMessage("Server not Found.. Please check Internet Connection...");
       }
       return oCreditCardReversalBO;
   }