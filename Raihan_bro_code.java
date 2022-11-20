public DpdcBillModel getDpdcBillInfo(DpdcBillModel model) {
        DpdcBillModel outModel = new DpdcBillModel();
        String jsonString = "";

        readProperties();
        try {

            JSONObject json = new JSONObject();

            json.put("customerNo", model.getCustomerCode().trim());
            json.put("username", userName.trim());
            json.put("password", password.trim());
            json.put("miscId", MISC_CD.trim());
            json.put("payChannel", payChannel.trim());
            json.put("bankCode", bankCode.trim());
            json.put("billMonth", model.getBillMonth().trim());
            json.put("billType", billType);

            System.out.println("json = " + json);
            String dpdc_bill_info_url = dpdc_base_url + "dpdc-info";
            System.out.println("dpdc_bill_info_url = " + dpdc_bill_info_url);

            Client restClient = Client.create();
            WebResource webResource = restClient.resource(dpdc_bill_info_url);
            ClientResponse response = webResource
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .type(MediaType.APPLICATION_JSON)
                    //.type(MediaType.APPLICATION_FORM_URLENCODED)
                    .post(ClientResponse.class, json.toString());

            jsonString = response.getEntity(String.class);

            System.out.println("jsonString-: " + jsonString);

            String responseCode = "";
            String responseMessage = "";
            String customerName = "";
            String locationCode = "";
            String customerCode = "";
            String billNo = "";
            String dueDate = "";
            String billStatus = "";
            String dpdcAmount = "";
            String vatAmount = "";
            String totalAmount = "";

            if (response.getStatus() == 200) {
                JSONObject obj = new JSONObject(jsonString);
                System.out.println("obj = " + obj);
                if (obj.has("responseMessage") && !obj.isNull("responseMessage")) {
                    responseMessage = obj.getString("responseMessage");
                    outModel.setOutMessage(responseMessage);
                }

                if (obj.has("responseCode") && !obj.isNull("responseCode")) {
                    responseCode = obj.getString("responseCode");
                    outModel.setOutCode(responseCode);

                }

                if ("0".equals(responseCode)) {
                    if (obj.has("billStatus") && !obj.isNull("billStatus")) {
                        billStatus = obj.getString("billStatus");
                        outModel.setBillStatus(billStatus);

                    }
                    if (obj.has("customerCode") && !obj.isNull("customerCode")) {
                        customerCode = obj.getString("customerCode");
                        outModel.setCustomerCode(customerCode);

                    }

                    if (obj.has("billNo") && !obj.isNull("billNo")) {
                        billNo = obj.getString("billNo");
                        outModel.setBillNo(billNo);

                    }

                    if (obj.has("locationCode") && !obj.isNull("locationCode")) {
                        locationCode = obj.getString("locationCode");
                        outModel.setLocationCode(locationCode);
                    }
                    if (obj.has("customerName") && !obj.isNull("customerName")) {
                        customerName = obj.getString("customerName");
                        outModel.setCustomerName(customerName);
                    }

                    if (obj.has("dueDate") && !obj.isNull("dueDate")) {
                        dueDate = obj.getString("dueDate");
                        outModel.setDueDate(dueDate);
                    }

                    if (obj.has("dpdcAmount") && !obj.isNull("dpdcAmount")) {
                        dpdcAmount = obj.getString("dpdcAmount");
                        outModel.setBillAmount(dpdcAmount);
                    }
                    if (obj.has("vatAmount") && !obj.isNull("vatAmount")) {
                        vatAmount = obj.getString("vatAmount");
                        outModel.setVatAmount(vatAmount);
                    }
                    if (obj.has("totalAmount") && !obj.isNull("totalAmount")) {
                        totalAmount = obj.getString("totalAmount");
                        outModel.setTotalAmount(totalAmount);
                    }
                } else {
                    outModel.setOutCode(responseCode);
                    outModel.setOutMessage(responseMessage);
                }

            } else {
                outModel.setOutCode("1");
                outModel.setOutMessage("DPDC API problem " + response.getStatus() + jsonString);
            }

        } catch (Exception ex) {
            Logger.getLogger(DpdcBillDao.class.getName()).log(Level.SEVERE, null, ex);
            outModel.setOutCode("1");
            outModel.setOutMessage(jsonString + "-" + ex.getMessage());
            //outModel.setOutMessage("No Bill Information Found for :- " + model.getCustomerCode());
        }
        return outModel;
    }