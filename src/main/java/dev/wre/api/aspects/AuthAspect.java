package dev.wre.api.aspects;

import dev.wre.api.services.UsersService;
import dev.wre.api.util.TokenUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class AuthAspect {

    @Autowired
    private UsersService usersService;

    private Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Around("within(dev.wre.api.controllers.UsersController) || within(dev.wre.api.controllers.BookmarkController)" +
            "|| within(dev.wre.api.controllers.GoogleSearchController)")
    public ResponseEntity<?> authorizeRequest(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String username = request.getHeader("Username");
        String token = request.getHeader("Authorization");

        if (token == null) {
            logger.warn("no token received from request");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else if (TokenUtil.isValidToken(username, token)) {
            logger.info("token is valid");
            return (ResponseEntity) pjp.proceed();
        } else {
            logger.warn("token is invalid");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }


}
