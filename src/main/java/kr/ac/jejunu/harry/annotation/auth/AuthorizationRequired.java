package kr.ac.jejunu.harry.annotation.auth;

import java.lang.annotation.*;

/**
 * Created by jhkang on 2016-06-13.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizationRequired {
}
