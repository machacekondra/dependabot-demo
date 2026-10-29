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

The repository includes a Java dependency example (`java/`). Dependabot checks
it weekly for dependency updates.

### Security updates

Spring Web 5.3.37 is pinned to demonstrate CVE-2024-38809. The sample parses
benign local `If-Match` and `If-None-Match` values; it does not start a server.
Do not use this vulnerable dependency version in a production application.

| Project | Vulnerable dependency | Security fix | Remediation |
| --- | --- | --- | --- |
| Java | Spring Web 5.3.37 (`CVE-2024-38809`) | Spring Web 5.3.38 | Upgrade `spring-web`; no application-code change is required. |

The vulnerable version is pinned in `java/pom.xml`; the example call sites for
`If-Match` and `If-None-Match` are in
`java/src/main/java/com/github/dependabot/ConditionalRequestDemo.java`. The
[advisory](https://github.com/advisories/GHSA-2rmj-mq67-h97g) affects
applications that parse ETags from these request headers. Enable Dependabot
alerts and security updates to create the alert and dependency-update PR, then
assign it to an AI coding agent such as Codex to demonstrate the remediation.

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

### Running Renovate with GitHub Actions

The `.github/workflows/renovate.yml` workflow runs Renovate daily and can also
be started manually from the **Actions** tab. It uses the `renovate.json`
configuration to check Maven dependencies for security updates and query the
Lightwell remediated Maven registry.

Before the first run, add these repository secrets:

- `RENOVATE_TOKEN`: a fine-grained token scoped to this repository with the
  permissions Renovate requires, including read access to Dependabot alerts
  and write access to contents, issues, pull requests, and commit statuses.
- `LIGHTWELL_USERNAME` and `LIGHTWELL_TOKEN`: credentials for the Lightwell
  Maven registry.

See the
[Renovate GitHub platform permissions](https://docs.renovatebot.com/modules/platform/github/)
for the complete permission list.
