package nn.lti.poc.message;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TargetTest {

    @Test
    public void valueTest() {
        assertThat(Target.valueOf("iframe")).isEqualTo(Target.iframe);
        assertThat(Target.valueOf("window")).isEqualTo(Target.window);
    }
}
