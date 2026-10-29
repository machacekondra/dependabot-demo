# Dependabot vs. Renovate for vulnerability remediation

**Scope:** this comparison focuses on remediating a dependency vulnerability, including application-code changes when they are required. **Checked:** October 29, 2026.

## At a glance

| Capability | Dependabot + GitHub coding agent | Renovate |
| --- | --- | --- |
| Vulnerability signal | GitHub Dependabot alert, shown on the repository's Security page. | Renovate can read GitHub Dependabot alerts and use them to create security-fix PRs. The dependency graph and Dependabot alerts must be enabled; a Renovate app needs read access to Dependabot alerts. |
| Requirements | GitHub Code Security and a Copilot plan that includes coding-agent access; third-party agents such as Codex must be enabled for the repository. | A Renovate app or self-hosted instance with repository access; for the app workflow, grant read access to Dependabot alerts. |
| How remediation starts | From an alert, choose **Assign to Agent**, then choose an enabled agent such as Codex. You can add a prompt and select the model. | Renovate detects an alert and raises a dependency-fix PR according to its configuration. There is no equivalent alert-page step in Renovate's documented workflow for assigning the alert to an AI coding agent. |
| What the PR can change | The agent analyzes the alert and repository code, then proposes a draft PR. It can update the dependency and make related source changes, such as adapting to a breaking API. | Renovate's vulnerability workflow selects and applies a dependency version update. `postUpgradeTasks` can run configured commands to edit or generate files, but those commands must be explicitly allowed by the Renovate administrator. This is scripted automation, not an agent reasoning through a code migration by default. |
| Human review | The agent PR is a proposal. Review the source diff and run the project's checks before merging. | Review the dependency update and any configured task output before merging. Renovate warns that incorrect vulnerability data can produce repeated or looping fix PRs. |

## What the demo showed

This repository's Java example pins `org.springframework:spring-web` 5.3.18, an affected base version in the [Lightwell OSV advisory RHLW-2026-a9afa2d7b2973961](https://osv.dev/vulnerability/RHLW-2026-a9afa2d7b2973961). The advisory includes CVE-2024-38809; `ConditionalRequestDemo.java` calls the ETag parsing accessors for `If-Match` and `If-None-Match` with harmless local values. Lightwell's fixed build is `5.3.18.rhlw-00010`. This is a dependency-only remediation.

With Dependabot, the alert is the starting point. Assigning it to Codex gives the agent the alert context and repository to work in; the agent can review the advisory and propose the dependency update. GitHub documents that this assignment starts an agent session and opens a draft PR with a proposed fix. This example needs only a manifest update, while the same workflow can also handle issues that require application-code changes.

With Renovate, `vulnerabilityAlerts` can produce security PRs from GitHub's vulnerability-alert feed, while the experimental `osvVulnerabilityAlerts` option supports OSV advisories for direct dependencies. This repository pins the affected base version and points Maven lookups only at Lightwell's remediated registry. The current Renovate OSV integration maps Maven dependencies to the standard `Maven` ecosystem, but the Lightwell advisory is published under `Red Hat Lightwell:Maven`; Renovate cannot match this advisory to the dependency, so this setup does not yet produce a Lightwell OSV security PR. The fixed Lightwell artifact is `5.3.18.rhlw-00010`.

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
