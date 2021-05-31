package nn.lti.poc.message;

import java.lang.annotation.Target;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MessageExtractor {
    KnownMessageType value();
}
