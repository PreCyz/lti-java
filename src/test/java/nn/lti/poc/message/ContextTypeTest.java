package nn.lti.poc.message;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ContextTypeTest {

    @Test
    public void fromStringTest() {
        assertThat(ContextType.CourseOffering).isEqualTo(ContextType.fromString("CourseOffering").get());
        assertThat(ContextType.CourseOffering).isEqualTo(ContextType.fromString("urn:lti:context-type:ims/lis/CourseOffering").get());

        assertThat(ContextType.CourseSection).isEqualTo(ContextType.fromString("CourseSection").get());
        assertThat(ContextType.CourseSection).isEqualTo(ContextType.fromString("urn:lti:context-type:ims/lis/CourseSection").get());

        assertThat(ContextType.CourseTemplate).isEqualTo(ContextType.fromString("CourseTemplate").get());
        assertThat(ContextType.CourseTemplate).isEqualTo(ContextType.fromString("urn:lti:context-type:ims/lis/CourseTemplate").get());

        assertThat(ContextType.Group).isEqualTo(ContextType.fromString("Group").get());
        assertThat(ContextType.Group).isEqualTo(ContextType.fromString("urn:lti:context-type:ims/lis/Group").get());

        assertThat(Optional.empty()).isEqualTo(ContextType.fromString("nonexistent"));
    }
}
