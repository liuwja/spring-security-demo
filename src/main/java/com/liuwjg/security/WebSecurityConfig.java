package com.liuwjg.security;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
    @Autowired
    private CustomPermissionEvaluator CustomPermissionEvaluator;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider).userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //设置验证码,会话失效页面免登录
                .antMatchers("/getVerifyCode", "/login/invalid").permitAll()
                .anyRequest().authenticated()
                //设置登录跳转
                .and().formLogin().loginPage("/login")

                //设置登录陈工处理器
                .successHandler(customAuthenticationSuccessHandler)
                //设置登录失败处理器
                .failureHandler(customAuthenticationFailureHandler).permitAll()

                //设置登录成功跳转
//                .defaultSuccessUrl("/").permitAll()

                //设置登录失败跳转
//                .failureUrl("/login/error").loginProcessingUrl("/login")
                //设置验证码过滤器
                //.and().addFilterBefore(new VerifyFilter(), UsernamePasswordAuthenticationFilter.class)
                //手动设置authenticationDetailsSource
                .authenticationDetailsSource(authenticationDetailsSource)
                .and().logout().logoutUrl("/logout").deleteCookies("JSESSIONID").logoutSuccessHandler(customLogoutSuccessHandler).permitAll()
                //设置自动登录
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60).userDetailsService(userDetailsService)
                //这只会话超时1分钟
                .and().sessionManagement().invalidSessionUrl("/login/invalid")
                //设置单个用户登录数为1
                .maximumSessions(1)
                // 当达到最大值时，是否保留已经登录的用户
                .maxSessionsPreventsLogin(false)
                // 当达到最大值时，旧用户被踢出后的操作
                .expiredSessionStrategy(new CustomExpiredSessionStrategy())
                .sessionRegistry(sessionRegistry());
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    /**
     * 注入自定义的CustomPermissionEvaluator
     *
     * @return
     */
//    @Bean
//    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
//        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
//        handler.setPermissionEvaluator(CustomPermissionEvaluator);
//        return handler;
//    }

    /**
     *
     * @return
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
