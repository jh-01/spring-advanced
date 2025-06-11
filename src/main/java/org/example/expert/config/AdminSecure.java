package org.example.expert.config;

import org.example.expert.domain.user.enums.UserRole;

import javax.management.relation.Role;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AdminSecure {
    UserRole role() default UserRole.ADMIN;
}
