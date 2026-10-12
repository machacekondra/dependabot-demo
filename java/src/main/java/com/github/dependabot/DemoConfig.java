package com.github.dependabot;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * Local-only SnakeYAML API migration example for the Dependabot demo.
 */
public class DemoConfig {
    public String message;

    public static void main(String[] args) {
        // Keep the input benign and local. SnakeYAML 2.0 requires LoaderOptions
        // when creating Constructor, so upgrading this dependency needs a code
        // change as well as the version bump.
        String yaml = "message: hello from the demo";
        DemoConfig config = new Yaml(new Constructor(DemoConfig.class)).load(yaml);
        System.out.println(config.message);
    }
}
