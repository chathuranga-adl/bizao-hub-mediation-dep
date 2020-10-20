/*
 *
 *  Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.wso2telco.dep.mediator.impl.credit;

import com.wso2telco.dep.mediator.RequestExecutor;
import com.wso2telco.dep.oneapivalidation.exceptions.CustomException;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.json.JSONException;
import org.json.JSONObject;

public class CreditExecutor extends RequestExecutor {

    private Log log = LogFactory.getLog(CreditExecutor.class);

   private CreditHandler handler;

    @Override
    public boolean execute(MessageContext context) throws CustomException, AxisFault, Exception {

        try {

            CreditHandler handler = getCreditHandler(getSubResourcePath());
            return handler.handle(context);
        } catch (JSONException e) {

            log.error(e.getMessage());
            throw new CustomException("SVC0001", "", new String[]{"Request is missing required URI components"});
        }
    }

    @Override
    public boolean validateRequest(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception {

        CreditHandler handler = getCreditHandler(requestPath);
        return handler.validate(httpMethod, requestPath, jsonBody, context);
    }

    private CreditHandler getCreditHandler(String requestPath) {

        if (handler == null) {

            handler = CreditHandlerFactory.createCreditHandler(requestPath, this);
        }

        return handler;
    }
}
