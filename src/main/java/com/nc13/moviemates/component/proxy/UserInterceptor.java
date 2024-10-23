package com.nc13.moviemates.component.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInterceptor implements HandlerInterceptor {

    public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext().getAuthentication()
                    .getName().equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        // log.info("=============== preHandle 진입 =========================");
        // if (isUserLogged()) {
        //     log.info("=============== Security Name is not anonymousUser =========================");
        //     addToModelUserDetails(request.getSession());
        // }else{
        //     log.info("=============== Security Name is anonymousUser =========================");
        //     // return false;
        // }

        return true;
    }

    private void addToModelUserDetails(HttpSession session) {
        log.info("=============== addToModelUserDetails =========================");

        String loggedUsername
                = SecurityContextHolder.getContext().getAuthentication().getName();
        session.setAttribute("username", loggedUsername);

        log.info("user(" + loggedUsername + ") session : " + session);
        log.info("=============== addToModelUserDetails =========================");
    }

    @Override
    public void postHandle(
            HttpServletRequest req,
            HttpServletResponse res,
            Object o,
            ModelAndView model) throws Exception {
        log.info("=============== postHandle 진입 0 =========================");
        if (model != null && !isRedirectView(model)) {
            if (isUserLogged()) {
                log.info("=============== postHandle 1 =========================");
                addToModelUserDetails(model);
            }else{
                log.info("=============== postHandle 2=========================");
            }
        }else{
            log.info("=============== postHandle 3 =========================");
        }
    }

    public static boolean isRedirectView(ModelAndView mv) {
        String viewName = mv.getViewName();
        if (viewName.startsWith("redirect:/")) {
            return true;
        }
        View view = mv.getView();
        return (view != null && view instanceof SmartView
                && ((SmartView) view).isRedirectView());
    }

    private void addToModelUserDetails(ModelAndView model) {
        log.info("=============== addToModelUserDetails =========================");

        String loggedUsername = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        model.addObject("loggedUsername", loggedUsername);

        log.trace("session : " + model.getModel());
        log.info("=============== addToModelUserDetails =========================");
    }

}