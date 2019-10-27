package pers.te.sa.ws.demo;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.regex.Pattern;

@Endpoint
public class WsEndpoint {
    private static final String NAMESPACE_URI = "sa.te.pers/ws/demo";
    static final String From = "fromMail";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ValidateRequest")
    @ResponsePayload
    public UserResponse validate(@RequestPayload ValidateRequest validateRequest){
        UserResponse soapResult = new UserResponse();
        String pattern = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
        if(validateRequest.getUrl() == null){
            soapResult.setMsg("N");
        }
        boolean valid = Pattern.matches(pattern, validateRequest.getUrl());
        if(valid){
            soapResult.setMsg("Y");
        }
        else {
            soapResult.setMsg("N");
        }
        return soapResult;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UserRequest")
    @ResponsePayload
    public UserResponse sendEmail(@RequestPayload UserRequest userRequest){
        UserResponse soapResult = new UserResponse();
        String Subject = "Test";
        String HtmlBody = "<h1>Mail send service</h1>"
                + "<p>" + userRequest.getPayload() + "</p>";
        String TextBody = userRequest.getPayload();
        try{
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "accessKeyId", "secret");
            IAcsClient client = new DefaultAcsClient();
            SingleSendMailRequest request = new SingleSendMailRequest();

            request.setAccountName(From);
            request.setFromAlias("Yi");
            request.setAddressType(1);
            request.setReplyToAddress(false);
            request.setToAddress(userRequest.getUrl());
            request.setSubject(Subject);
            request.setHtmlBody(HtmlBody);
            request.setTextBody(TextBody);

            SingleSendMailResponse response = client.getAcsResponse(request);
            System.out.println("email sent");
            soapResult.setMsg("Y");
        }
        catch (ServerException e){
            System.out.println(e.getErrCode());
            e.printStackTrace();
            soapResult.setMsg("N");
        }
        catch (ClientException e){
            System.out.println(e.getErrCode());
            e.printStackTrace();
            soapResult.setMsg("N");
        }
        catch (Exception e){
            e.printStackTrace();
            soapResult.setMsg("Unknown error");
        }
        return soapResult;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UserBatchRequest")
    @ResponsePayload
    public UserResponse sendEmailBatch(@RequestPayload UserBatchRequest userRequest) {
        UserResponse soapResult = new UserResponse();
        soapResult.setMsg("Y");
        List<String> urls = userRequest.getUrl();

        String Subject = "Test";
        String HtmlBody = "<h1>Mail send service</h1>"
                + "<p>" + userRequest.getPayload() + "</p>";
        String TextBody = userRequest.getPayload();

        IClientProfile profile;
        IAcsClient client;
        SingleSendMailRequest request;

        try {
            profile = DefaultProfile.getProfile("cn-hangzhou", "accessKeyId", "secret");
            client = new DefaultAcsClient();
            request = new SingleSendMailRequest();
            request.setAccountName(From);
            request.setFromAlias("Yi");
            request.setAddressType(1);
            request.setReplyToAddress(false);
            request.setSubject(Subject);
            request.setHtmlBody(HtmlBody);
            request.setTextBody(TextBody);
        }catch (Exception e){
            soapResult.setMsg("Unknown error");
            return soapResult;
        }

        for(String url:urls){
            try {
                request.setToAddress(url);
                SingleSendMailResponse response = client.getAcsResponse(request);
            }catch (ServerException e){
                System.out.println(e.getCause());
                e.printStackTrace();
                soapResult.setMsg("N");
            }catch (ClientException e){
                System.out.println(e.getCause());
                e.printStackTrace();
                soapResult.setMsg("N");
            }
        }

        return soapResult;
    }

}
