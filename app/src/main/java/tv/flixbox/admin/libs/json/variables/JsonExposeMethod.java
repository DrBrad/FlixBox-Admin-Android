package tv.flixbox.admin.libs.json.variables;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonExposeMethod {

    String key();
}
