package com.kosa.jungdoin.common.aspect;

import com.kosa.jungdoin.common.annotation.RequireAuthorization;
import com.kosa.jungdoin.common.exception.UnauthorizedException;
import com.kosa.jungdoin.entity.BaseMember;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class AuthorizationAspect {

    @Before("@annotation(com.kosa.jungdoin.common.annotation.RequireAuthorization)")
    public void checkAuthorization(JoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication instanceof BaseMember)) {
            throw new UnauthorizedException();
        }

        BaseMember currentMember = (BaseMember) authentication.getPrincipal();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequireAuthorization annotation = method.getAnnotation(RequireAuthorization.class);
        String idField = annotation.idField();

        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            Object arg = args[0];
            try {
                Method getIdMethod = arg.getClass().getMethod("get" + capitalize(idField));
                Object id = getIdMethod.invoke(arg);
                if (!currentMember.getMemberId().equals(id)) {
                    throw new UnauthorizedException("해당 작업을 수행할 권한이 없습니다.");
                }
            } catch (NoSuchMethodException e) {
                throw new UnauthorizedException("권한을 확인할 수 없습니다: " + idField + " 필드를 찾을 수 없습니다.");
            }
        } else {
            throw new UnauthorizedException("권한을 확인할 수 없습니다: 메서드 인자가 없습니다.");
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}