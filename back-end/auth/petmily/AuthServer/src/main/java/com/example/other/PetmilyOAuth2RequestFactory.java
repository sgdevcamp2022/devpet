//package com.example.shoh_oauth.config.auth;
//
//
//import org.springframework.security.oauth2.provider.AuthorizationRequest;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.TokenRequest;
//import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//@Component
//public class PetmilyOAuth2RequestFactory extends DefaultOAuth2RequestFactory {
//    public PetmilyOAuth2RequestFactory(ClientDetailsService clientDetailsService) {
//        super(clientDetailsService);
//    }
//
//    @Override
//    public TokenRequest createTokenRequest(AuthorizationRequest authorizationRequest, String grantType) {
//        Map<String,String> parameter = authorizationRequest.getRequestParameters();
//        boolean isEmail = isEmail(parameter.get("username"));
//        if (isEmail)
//            return super.createTokenRequest(authorizationRequest,grantType);
//        // TODO: 사용자 정보 가져와서 username, password 수정
//        HashMap<String,String> socialParameter = new HashMap<>(parameter);
//        socialParameter.put("username","social email");
//        socialParameter.put("password","password");
//        authorizationRequest.setRequestParameters(socialParameter);
//        return new TokenRequest(authorizationRequest.getRequestParameters(), authorizationRequest.getClientId(), authorizationRequest.getScope(), grantType);
//    }
//
//
//    @Override
//    public TokenRequest createTokenRequest(Map<String, String> requestParameters, ClientDetails authenticatedClient) {
//        boolean isEmail = isEmail(requestParameters.get("username"));
//        TokenRequest tokenRequest = super.createTokenRequest(requestParameters, authenticatedClient);
//        if (isEmail)
//            return tokenRequest;
//        // TODO: 사용자 정보 가져와서 username, password 수정
//        HashMap<String,String> socialParameter = new HashMap<>(requestParameters);
//        socialParameter.put("username","social email");
//        socialParameter.put("password","password");
//        tokenRequest.setRequestParameters(socialParameter);
//        return tokenRequest;
//    }
//
//    private boolean isEmail(String username)
//    {
//        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
//        return !emailPattern.matcher(username).matches();
//    }
//}
