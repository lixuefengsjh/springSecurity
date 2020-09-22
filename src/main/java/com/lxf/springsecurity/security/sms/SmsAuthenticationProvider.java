package com.lxf.springsecurity.security.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author: lxf
 * @create: 2020-09-21 11:03
 * @description: 短信相关认证接口
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsChecker preAuthenticationChecks = new SmsAuthenticationProvider.DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new SmsAuthenticationProvider.DefaultPostAuthenticationChecks();
    private UserDetailsService userDetailsService;

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone =null;
        UserDetails user=null;
        if(authentication instanceof SmsAuthenticationToken ){
            phone=((SmsAuthenticationToken) authentication).getPhone();
            user=retrieveUser(phone);
        }
        return this.createSuccessAuthentication(phone, authentication, user);
    }
    protected  UserDetails retrieveUser(String var1) throws AuthenticationException{

        UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(var1);
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        } else {
            return loadedUser;
        }
    };
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        SmsAuthenticationToken result = new SmsAuthenticationToken((String) principal, user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        private DefaultPostAuthenticationChecks() {
        }

        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                SmsAuthenticationProvider.this.logger.debug("User account credentials have expired");
                throw new CredentialsExpiredException(SmsAuthenticationProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
            }
        }
    }

    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        private DefaultPreAuthenticationChecks() {
        }

        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                SmsAuthenticationProvider.this.logger.debug("User account is locked");
                throw new LockedException(SmsAuthenticationProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
            } else if (!user.isEnabled()) {
                SmsAuthenticationProvider.this.logger.debug("User account is disabled");
                throw new DisabledException(SmsAuthenticationProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
            } else if (!user.isAccountNonExpired()) {
                SmsAuthenticationProvider.this.logger.debug("User account is expired");
                throw new AccountExpiredException(SmsAuthenticationProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
            }
        }
    }
}
