package uz.pdp.appcinemarest.aouth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

// Zuhridin Bakhriddinov 4/6/2022 9:12 PM
public class CustomOauthUser implements OAuth2User {
    private final OAuth2User oAuth2User;
    private String oauth2ClientName;

/*     public CustomOauthUser(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }*/

    public CustomOauthUser(OAuth2User oAuth2User, String oauth2ClientName) {
        this.oAuth2User = oAuth2User;
        this.oauth2ClientName = oauth2ClientName;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }
}
