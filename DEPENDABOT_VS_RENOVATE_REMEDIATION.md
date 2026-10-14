# Dependabot vs. Renovate for vulnerability remediation

**Scope:** this comparison focuses on the workflow we just demonstrated: take a dependency vulnerability, update the dependency, and make any required application-code changes. **Checked:** October 12, 2026.

## At a glance

| Capability | Dependabot + GitHub coding agent | Renovate |
| --- | --- | --- |
| Vulnerability signal | GitHub Dependabot alert, shown on the repository's Security page. | Renovate can read GitHub Dependabot alerts and use them to create security-fix PRs. The dependency graph and Dependabot alerts must be enabled; a Renovate app needs read access to Dependabot alerts. |
| Requirements | GitHub Code Security and a Copilot plan that includes coding-agent access; third-party agents such as Codex must be enabled for the repository. | A Renovate app or self-hosted instance with repository access; for the app workflow, grant read access to Dependabot alerts. |
| How remediation starts | From an alert, choose **Assign to Agent**, then choose an enabled agent such as Codex. You can add a prompt and select the model. | Renovate detects an alert and raises a dependency-fix PR according to its configuration. There is no equivalent alert-page step in Renovate's documented workflow for assigning the alert to an AI coding agent. |
| What the PR can change | The agent analyzes the alert and repository code, then proposes a draft PR. It can update the dependency and make related source changes, such as adapting to a breaking API. | Renovate's vulnerability workflow selects and applies a dependency version update. `postUpgradeTasks` can run configured commands to edit or generate files, but those commands must be explicitly allowed by the Renovate administrator. This is scripted automation, not an agent reasoning through a code migration by default. |
| Human review | The agent PR is a proposal. Review the source diff and run the project's checks before merging. | Review the dependency update and any configured task output before merging. Renovate warns that incorrect vulnerability data can produce repeated or looping fix PRs. |

## What the demo showed

This repository's Java example pins SnakeYAML 1.33, affected by [CVE-2022-1471](https://github.com/advisories/GHSA-mjmj-j48q-9wg2). The patched version is 2.0. Updating `java/pom.xml` alone is insufficient: `DemoConfig.java` constructs the parser with `new Constructor(DemoConfig.class)`, and the 2.0 API requires `LoaderOptions` to be passed to the constructor.

The Python sample is another code-migration case: it pins PyJWT 1.7.1 for [CVE-2022-29217](https://github.com/advisories/GHSA-ffqj-6fqr-9h24). Moving to PyJWT 2.x changes `jwt.encode()` to return text, so the old `.decode("utf-8")` call must be removed; decoding should also explicitly allow the expected algorithm. PyJWT 2.4.0 fixes that specific 2022 advisory, but later advisories affect versions below 2.13.0, so use a current secure target when running a live remediation demo ([later advisory](https://github.com/advisories/GHSA-xgmm-8j9v-c9wx)).

With Dependabot, the alert is the starting point. Assigning it to Codex gives the agent the alert context and repository to work in; the prompt can explicitly ask for both the version bump and the `LoaderOptions` migration. GitHub documents that this assignment starts an agent session and opens a draft PR with a proposed fix. This is the workflow that directly demonstrates a vulnerability remediation PR changing both the manifest and application code.

With Renovate, its `vulnerabilityAlerts` configuration can produce a security PR from GitHub's vulnerability-alert feed and lets you customize settings such as labels, assignees, and whether to choose the lowest fixed version or a higher one. If the migration is known and repeatable, an administrator could configure an allowed `postUpgradeTasks` command to make it. For a repository-specific, reasoning-based API migration, Renovate's documented vulnerability PR flow does not itself provide the same alert-to-Codex handoff; that would require a separate agent integration or custom automation.

## Which fits this remediation use case?

- **Choose Dependabot + Codex** when the goal is to start at a GitHub security alert and have an AI coding agent propose a fix that may include source changes.
- **Choose Renovate** when the goal is broad, configurable dependency maintenance and vulnerability-update PRs across repositories, with optional approved scripts for repeatable follow-up edits.
- **Using both:** decide which tool owns security-update PR creation for each repository. If both are configured to open a fix for the same alert, duplicate PRs can result. Dependabot alerts can remain enabled as the shared GitHub signal even if Dependabot's automatic security-update PRs are disabled.

## Sources

- [GitHub changelog: Dependabot alerts are now assignable to AI agents for remediation](https://github.blog/changelog/2026-04-07-dependabot-alerts-are-now-assignable-to-ai-agents-for-remediation/)
- [GitHub Docs: Viewing and updating Dependabot alerts](https://docs.github.com/en/code-security/how-tos/manage-security-alerts/manage-dependabot-alerts/view-dependabot-alerts)
- [GitHub Docs: Dependabot security updates](https://docs.github.com/en/code-security/concepts/supply-chain-security/dependabot-security-updates)
- [Renovate Docs: Configuration options (`vulnerabilityAlerts` and `postUpgradeTasks`)](https://docs.renovatebot.com/configuration-options/)
- [Renovate Docs: Security and permissions](https://docs.renovatebot.com/security-and-permissions/)
