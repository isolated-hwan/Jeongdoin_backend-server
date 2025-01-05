package com.kosa.jungdoin.common.aspect;

import com.kosa.jungdoin.common.annotation.ValidateEntity;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class EntityValidationAspect {

    private final ApplicationContext applicationContext;

    @Around("@annotation(validateEntity)")
    public Object validateEntity(ProceedingJoinPoint joinPoint, ValidateEntity validateEntity) throws Throwable {
        Long idValue = getIdValueFromArgs(joinPoint, validateEntity);  // 어노테이션의 idField 값으로 ID 추출

        if (idValue != null) {
            // 리포지토리 빈 이름 생성
            String repositoryBeanName = getRepositoryBeanName(validateEntity.entityClass());

            // 리포지토리 빈을 가져옴
            JpaRepository<?, Long> repository = (JpaRepository<?, Long>) applicationContext.getBean(repositoryBeanName);

            // 리포지토리에서 ID 존재 여부 확인
            if (!repository.existsById(idValue)) {
                throw new IllegalArgumentException(
                        validateEntity.entityClass().getSimpleName() + " with ID " + idValue + " not found"
                );
            }
        }
        return joinPoint.proceed();
    }

    // 리포지토리 빈 이름 생성 함수
    private String getRepositoryBeanName(Class<?> entityClass) {
        String entityClassName = entityClass.getSimpleName();
        return Character.toLowerCase(entityClassName.charAt(0)) + entityClassName.substring(1) + "Repository";
    }

    // 메서드 인자 중 idField에 해당하는 값을 추출
    private Long getIdValueFromArgs(ProceedingJoinPoint joinPoint, ValidateEntity validateEntity) {
        Object[] args = joinPoint.getArgs();
        String idFieldName = validateEntity.idField();

        // 메서드 파라미터의 이름을 알아내기 위해 리플렉션을 사용
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();  // 파라미터 이름 가져오기
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(idFieldName)) {
                // ID 필드명과 일치하는 인자를 찾으면 해당 값을 반환
                return (Long) args[i];
            }
        }
        return null;  // 해당하는 필드를 찾지 못한 경우 null 반환
    }
}
