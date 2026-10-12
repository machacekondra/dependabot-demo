"""Local-only PyJWT API migration example for the Dependabot demo."""

import jwt


DEMO_KEY = "local-demo-key-do-not-use-in-production"


def create_token(payload):
    # PyJWT 1.7.1 returns bytes here. PyJWT 2.x returns str, so the decode()
    # call must be removed when upgrading.
    return jwt.encode(payload, DEMO_KEY, algorithm="HS256").decode("utf-8")


def read_token(token):
    # PyJWT 2.x requires an explicit algorithm allowlist here.
    return jwt.decode(token, DEMO_KEY)


if __name__ == "__main__":
    demo_token = create_token({"demo": True})
    print(read_token(demo_token))
