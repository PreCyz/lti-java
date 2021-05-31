package nn.lti.poc.message;

import nn.lti.poc.message.error.LtiError;

import java.util.*;
import java.util.stream.Collectors;

public class ParamExtractor {

    private Collection<? extends Map.Entry<String, String>> params;

    public ParamExtractor(Collection<? extends Map.Entry<String, String>> params) {
        this.params = params;
    }

    public Optional<String> getFirst(String name) {
        return this.params.stream()
          .filter(e -> e.getKey().equalsIgnoreCase(name))
          .map(Map.Entry::getValue)
          .findFirst();
    }

    public LtiErrorable<String> getFirst(String name, LtiError orError) {
        return getFirst(name)
          .map(LtiErrorable::success)
          .orElseGet(() -> LtiErrorable.error(orError));
    }

    Map<String, String> getParamsWithPrefix(String prefix) {
        return this.params.stream()
          .filter(e -> e.getKey().startsWith(prefix))
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
