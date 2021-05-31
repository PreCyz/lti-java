package nn.lti.poc.contentitem;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
@JsonDeserialize(builder = FileItem.Builder.class)
public interface FileItem extends Item {

    String TYPE = "FileItem";

    String copyAdvice();

    static Builder builder() {
        return new Builder();
    }

    Builder toBuilder();
    /** Builder of instances. */
    class Builder extends FileItem_Builder {
        Builder() {
            // Set defaults in the builder constructor.
            type(TYPE);
            copyAdvice("false");
        }
    }
}
