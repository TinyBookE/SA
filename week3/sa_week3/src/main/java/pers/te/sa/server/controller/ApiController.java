package pers.te.sa.server.controller;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.web.bind.annotation.*;
import pers.te.sa.server.item.RestResult;
import pers.te.sa.server.item.UserBatchDO;
import pers.te.sa.server.item.UserDO;

import java.util.regex.Pattern;


@RestController
@RequestMapping("/api/rest")
public class ApiController implements Control {

    static final String From = "fromMail";

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public RestResult sendEmail(@RequestBody UserDO userDO){
        RestResult restResult = new RestResult();
        String Subject = "Test";
        String HtmlBody = "<h1>Mail send service</h1>"
                + "<p>" + userDO.getPayload() + "</p>";
        String TextBody = userDO.getPayload();
        try{
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "accessKeyId", "secret");
            IAcsClient client = new DefaultAcsClient();
            SingleSendMailRequest request = new SingleSendMailRequest();

            request.setAccountName(From);
            request.setFromAlias("Yi");
            request.setAddressType(1);
            request.setReplyToAddress(false);
            request.setToAddress(userDO.getUrl());
            request.setSubject(Subject);
            request.setHtmlBody(HtmlBody);
            request.setTextBody(TextBody);

            SingleSendMailResponse response = client.getAcsResponse(request);
            System.out.println("email sent");
            restResult.setMsg("Y");
        }
        catch (ServerException e){
            System.out.println(e.getErrCode());
            e.printStackTrace();
            restResult.setMsg("N");
        }
        catch (ClientException e){
            System.out.println(e.getErrCode());
            e.printStackTrace();
            restResult.setMsg("N");
        }
        catch (Exception e){
            e.printStackTrace();
            restResult.setMsg("Unknown error");
        }
        return restResult;
    }

    @RequestMapping(value = "/sendEmailBatch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public RestResult sendEmailBatch(@RequestBody UserBatchDO userDO){
        RestResult restResult = new RestResult();
        restResult.setMsg("Y");
        String[] urls = userDO.getUrl();

        String Subject = "Test";
        String HtmlBody = "<h1>Mail send service</h1>"
                + "<p>" + userDO.getPayload() + "</p>";
        String TextBody = userDO.getPayload();

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
            restResult.setMsg("Unknown error");
            return restResult;
        }

        for(String url:urls){
            try {
                request.setToAddress(url);
                SingleSendMailResponse response = client.getAcsResponse(request);
            }catch (ServerException e){
                System.out.println(e.getCause());
                e.printStackTrace();
                restResult.setMsg("N");
            }catch (ClientException e){
                System.out.println(e.getCause());
                e.printStackTrace();
                restResult.setMsg("N");
            }
        }
        return restResult;
    }

    @RequestMapping(value = "/validateEmail", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public RestResult validateEmailAddress(@RequestBody UserDO userDO){
        RestResult restResult = new RestResult();
        String pattern = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
        if(userDO.getUrl() == null){
            restResult.setMsg("N");
            return restResult;
        }
        boolean valid = Pattern.matches(pattern, userDO.getUrl());
        if(valid){
            restResult.setMsg("Y");
        }
        else {
            restResult.setMsg("N");
        }
        return restResult;
    }
}
