<div align="center">
    <picture>
        <source media="(prefers-color-scheme: light)" srcset="https://user-images.githubusercontent.com/7659/174594540-5e29e523-396a-465b-9a6e-6cab5b15a568.svg">
        <source media="(prefers-color-scheme: dark)" srcset="https://user-images.githubusercontent.com/7659/174594559-0b3ddaa7-e75b-4f10-9dee-b51431a9fd4c.svg">
        <img src="https://user-images.githubusercontent.com/7659/174594540-5e29e523-396a-465b-9a6e-6cab5b15a568.svg" alt="Dependabot" width="336">
    </picture>
</div>

## Dependabot Demo Repository

This repo contains some projects with outdated dependencies. Fork it to try out
Dependabot :dependabot:!

### Example projects

The repository includes dependency examples for Python (`python/`) and Java
(`java/`). Dependabot checks both projects weekly for dependency updates.

### Security updates that need code changes

These two intentionally vulnerable examples let you try a security update that
also needs an application-code migration. The sample inputs are local and
harmless; do not use these pinned dependency versions or parsing patterns in a
production application.

| Project | Vulnerable dependency | Security fix | Example code migration |
| --- | --- | --- | --- |
| Python | PyJWT 1.7.1 (`CVE-2022-29217`) | PyJWT 2.4.0 | `jwt.encode()` now returns text, so remove `.decode("utf-8")`; `jwt.decode()` also needs an explicit algorithm allowlist. |
| Java | SnakeYAML 1.33 (`CVE-2022-1471`) | SnakeYAML 2.0 | Pass `LoaderOptions` to the `Constructor` when creating the YAML parser. |

The vulnerable versions are pinned in `python/requirements.txt` and
`java/pom.xml`; the example call sites are in `python/example.py` and
`java/src/main/java/com/github/dependabot/DemoConfig.java`. Enable Dependabot
alerts and security updates to create the alerts and dependency-update PRs, then
assign an alert to Lightwell to demonstrate the source-code migration.

### Enabling Security Updates

- In your fork, click the **Settings** tab
- In the left hand side navigation, click **Code security and analysis**
- Enable **Dependabot security updates** or **Grouped security updates**
- Dependabot will now start creating PRs for detected security vulnerabilities
- Go into the **Security** tab and click **Dependabot** in the left hand side navigation to see what Dependabot is working on

<img width="929" alt="screenshot showing Dependabot working on Security Updates" src="https://github.com/dependabot/demo/assets/886768/9295c61a-631b-4c56-9c00-ff078874f362">

After about 5 minutes you should see some PRs open. Merge them and the Security Alerts will close 🎉

### Enabling Version Updates

This demo includes a `dependabot.yml` which configures [Version Updates](https://docs.github.com/github/administering-a-repository/keeping-your-dependencies-updated-automatically), but forks don't automatically start with Dependabot enabled.

To enable Dependabot on your fork:
- Click the **Insights** tab
- In the left hand side navigation, click **Dependency Graph**
- Click on the **Dependabot** tab
- Click on the **Enable Dependabot** button
- After a moment, refresh the page and you should see Dependabot hard at work

<img width="917" alt="screenshot showing Dependabot working on Version Updates" src="https://github.com/dependabot/demo/assets/886768/4adf5727-255a-4ae1-97f7-70e94dc1134b">

After a few minutes, you should get some more PRs!
