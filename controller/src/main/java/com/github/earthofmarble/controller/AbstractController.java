package com.github.earthofmarble.controller;

import com.github.earthofmarble.model.dto.IDto;
import com.github.earthofmarble.model.filter.IFilter;
import com.github.earthofmarble.model.filter.impl.CommonFilter;
import com.github.earthofmarble.utility.exception.NullDtoObjectException;
import com.github.earthofmarble.utility.exception.RedirectFailedException;
//import com.senla.kedaleanid.controller.configuration.security.principal.UserPrincipal;
//import com.senla.kedaleanid.dto.IModelDto;
//import com.senla.kedaleanid.utility.exception.RedirectFailedException;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@SuppressWarnings("unchecked")
public abstract class AbstractController {

    protected <T> List<T> castModelDtoList(List<IDto> modelDtoList, Class<T> clazz) {
        List<T> dtos = new ArrayList<>();
        for (IDto listElement : modelDtoList) {
            dtos.add((T) listElement);
        }
        return dtos;
    }

    protected IFilter fillFilterPagination(IFilter filter, Integer first, Integer size){
        if (filter==null){
            filter = new CommonFilter();
        }
        filter.setFirstElement(0);
        filter.setPageSize(5);
        if (first!=null){
            filter.setFirstElement(first);
        }
        if (size!=null){
            filter.setPageSize(size);
        }
        return filter;
    }

    protected void sendRedirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RedirectFailedException("Redirect failed! \n" + e.getMessage());
        }
    }


//    protected void authenticateUserAndSetSession(String username, String password, HttpServletRequest request, AuthenticationManager authenticationManager) {
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
//        authToken.setDetails(new WebAuthenticationDetails(request));
//        Authentication authentication = authenticationManager.authenticate(authToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//
//    protected Pagination configurePagination(Integer firstElement, Integer pageSize) {
//        Pagination pagination = new Pagination(firstElement, pageSize);
//        if (pagination.getFirstElement() == null) {
//            pagination.setFirstElement(0);
//        }
//        if (pagination.getPageSize() == null) {
//            pagination.setPageSize(5);
//        }
//        return pagination;
//    }
//
//    protected void checkAuthority(Integer id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication.getPrincipal() instanceof String) {
//            throw new AccessDeniedException("Login to access this functionality");
//        }
//
//        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//
//        if (!id.equals(principal.getId())) {
//            throw new AccessDeniedException("Access Denied. Principal's id: [" + principal.getId() + "] doesn't match required [" + id + "]");
//        }
//    }
//

}
