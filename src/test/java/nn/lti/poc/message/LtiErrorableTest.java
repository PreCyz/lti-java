package nn.lti.poc.message;

import nn.lti.poc.message.error.LtiError;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LtiErrorableTest {


    @Test
    public void combineThreeShouldCombineSuccesses() {
        LtiErrorable<String> result = LtiErrorable.success(5).combine(
          LtiErrorable.success("yes"),
          LtiErrorable.success(true),
          (five, yes, bool) -> LtiErrorable.success("works")
        );

        assertThat(result.getEntity().get()).isEqualTo("works");
    }

    @Test
    public void combineThreeShouldFailOutOnErrors() {
        LtiErrorable<String> result = LtiErrorable.error(LtiError.MISSING_LTI_VERSION).combine(
          LtiErrorable.success("yes"),
          LtiErrorable.success(true),
          (five, yes, bool) -> LtiErrorable.success("works")
        );

        assertThat(result.getErrors().get(0)).isEqualTo(LtiError.MISSING_LTI_VERSION);
    }


    @Test
    public void combineThreeShouldCombineErrors() {
        LtiErrorable<String> result = LtiErrorable.error(LtiError.MISSING_LTI_VERSION).combine(
          LtiErrorable.error(LtiError.MISSING_USER_ID),
          LtiErrorable.error(LtiError.MISSING_MESSAGE_TYPE),
          (five, yes, bool) -> {
              return LtiErrorable.success("works");
          }
        );

        assertThat(result.getErrors())
                .hasSize(3)
                .contains(LtiError.MISSING_LTI_VERSION, LtiError.MISSING_USER_ID, LtiError.MISSING_MESSAGE_TYPE);
    }

    @Test
    public void fromShouldConvertOptionals() {
        assertThat(LtiErrorable.from(Optional.of("yes"), LtiError.MISSING_MESSAGE_TYPE).getEntity().get()).isEqualTo("yes");

        assertThat(LtiErrorable.from(Optional.empty(), LtiError.MISSING_MESSAGE_TYPE).getErrors().get(0)).isEqualTo(LtiError.MISSING_MESSAGE_TYPE);
    }

    @Test
    public void collapseShouldMapValues() {
        final String collapse = LtiErrorable.success(5).collapse(five -> "success", error -> "failed");
        assertThat(collapse).isEqualTo("success");

        final String collapse2 = LtiErrorable.error(LtiError.MISSING_USER_ID).collapse(five -> "failed", error -> "error");
        assertThat(collapse2).isEqualTo("error");
    }


    @Test
    public void flatMapShouldTransformValues() {
        assertThat(LtiErrorable.success(5).flatMap(five -> LtiErrorable.success("yes")).getEntity().get()).isEqualTo("yes");

        assertThat(
                LtiErrorable.error(LtiError.MISSING_USER_ID).flatMap(any -> LtiErrorable.success("no")).getErrors().get(0)
        ).isEqualTo(LtiError.MISSING_USER_ID);
    }


}
