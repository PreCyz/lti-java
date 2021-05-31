package nn.lti.poc.contentitem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentItemSelectionTest {

    private ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.registerModule(new Jdk8Module());
        return mapper;
    }

    private String getItemSelectionJson() {
        try {
            return IOUtils.toString(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("itemSelection.json")),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            return "";
        }
    }

    @Test
    public void contentItemSelectionShouldSerializeCorrectly() throws Exception {
        ContentItemSelection selection =
            ContentItemSelection.builder()
              .addGraph(
                ContentItem.builder()
                  .url("http://www.google.com")
                  .mediaType("text/html")
                  .thumbnail(Image.builder()
                    .id("https://www.google.com/icon.png")
                    .width(150)
                    .height(150).build()
                  )
                  .title("Search Engine")
                  .placementAdvice(PlacementAdvice.builder()
                    .presentationDocumentTarget("window")
                    .windowTarget("_blank").build()
                  ).build(),
                LtiLinkItem.builder()
                  .url("https://www.example.com/activity/3/lti")
                  .icon(Image.builder()
                    .id("https://www.example.com/activity/1/icon.png")
                    .width(100)
                    .height(100).build()
                  )
                  .title("Quiz #1")
                  .text("You will have 30 minutes to complete this assessment")
                  .putCustom("custom_time_to_complete", "900")
                  .build()
              ).build();

        assertThat(getMapper().writeValueAsString(selection)).isEqualTo(getItemSelectionJson());
    }

    @Test
    public void contentItemSelectionShouldDeSerializeCorrectly() throws Exception {
        ContentItemSelection selection = getMapper().readValue(getItemSelectionJson(), ContentItemSelection.class);

        assertThat(selection.graph().size()).isEqualTo(2);

        assertThat(selection.graph().get(1).url()).isEqualTo(Optional.of("https://www.example.com/activity/3/lti"));

        assertThat(((LtiLinkItem) selection.graph().get(1)).custom().get("custom_time_to_complete")).isEqualTo("900");
    }
}
