const username = process.env.LIGHTWELL_USERNAME;
const password = process.env.LIGHTWELL_TOKEN;

if (!username || !password) {
  throw new Error("Set LIGHTWELL_USERNAME and LIGHTWELL_TOKEN repository secrets.");
}

module.exports = {
  hostRules: [
    {
      hostType: "maven",
      matchHost: "https://packages.redhat.com/lightwell/java/remediated/",
      username,
      password,
    },
  ],
};
