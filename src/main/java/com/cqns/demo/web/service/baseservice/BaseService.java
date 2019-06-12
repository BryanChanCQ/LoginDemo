package com.cqns.demo.web.service.baseservice;

import com.cqns.demo.dao.entity.User;
import com.cqns.demo.dao.repository.UserRepository;
import com.cqns.demo.dao.entity.NsUser;
import com.cqns.demo.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class BaseService implements UserDetailsService  {
    private static Logger logger = LoggerFactory.getLogger(BaseService.class);

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private UserRepository userRepository;

    public String login(String username, String password) {

   try{
    UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

    Authentication authentication = authenticationManager.authenticate(upToken);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    return JwtTokenUtil.generateToken(userDetails);

}catch (Exception e){
    logger.error("Error",e);
    throw new RuntimeException();
}


    }
    public String refreshToken(String oldToken) {

        if (!JwtTokenUtil.isTokenExpired(oldToken)) {

            return JwtTokenUtil.refreshToken(oldToken);

        }

        return "error";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User sysUser = this.userRepository.findByUserName(username);

        if (Objects.nonNull(sysUser)) {

            return new NsUser(sysUser.getId(), sysUser.getUserName(), sysUser.getPassword(), sysUser.getDisplayName(), sysUser.getEnabled(), true, true, true, AuthorityUtils.NO_AUTHORITIES);

        } else {

            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));

        }

    }
}

