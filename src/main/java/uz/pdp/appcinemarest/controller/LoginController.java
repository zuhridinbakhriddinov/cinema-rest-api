package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import uz.pdp.appcinemarest.entity.User;
import uz.pdp.appcinemarest.entity.enums.Role_enum;
import uz.pdp.appcinemarest.repository.RoleRepository;
import uz.pdp.appcinemarest.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Zuhridin Bakhriddinov 4/7/2022 9:52 AM
@Controller
@RequestMapping("/")
public class LoginController {

    private static String authorizationRequestBaseUri = "oauth2/authorization";

    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;


    @GetMapping("success-url")
    public String getSuccessPage(Model model, UsernamePasswordAuthenticationToken authenticationToken) {

        User user = (User) authenticationToken.getPrincipal();
//        System.out.println(authenticationToken);
//        System.out.println();
        model.addAttribute("name", user.getFullName());

        return "success-page";

    }

    @GetMapping("success-url-oauth")
    public String getSuccessPageOAuth(Model model, OAuth2AuthenticationToken authentication) {

        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<Map> response = restTemplate
                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody(); // User haqidagi ma'lumotlar

            // TODO: 4/7/2022 userni db ga save qilish;

            model.addAttribute("name", userAttributes.get("name"));
            model.addAttribute("imgUrl", userAttributes.get("picture"));
            System.out.println(userAttributes.get("email"));

        }

        return "success-page";
    }

    @GetMapping("login")
    public String getLoginPage(Model model) {

        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        model.addAttribute("test", "Working!!!");
        return "login";
    }

    @GetMapping("error")
    public String getErrorPage() {
        return "error";
    }


}
