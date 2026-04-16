# Mock Billing App (Java 8 + JBoss) for Migration Agent Development

This repository is intentionally shaped like a legacy banking billing service to support automation experiments.

## Included APIs (3 of 138 representative endpoints)

- `GET /billingapp/api/plans` → `getplan`
- `GET /billingapp/api/plans/{planId}` → `getplanId`
- `POST /billingapp/api/plans/payments` → `postpayment`

## Legacy stack represented

- Java 8 source/target in Maven.
- JBoss deployment descriptors (`jboss-web.xml`, `jboss-ejb3.xml`).
- EJB service layer + JNDI mapping.
- JPA with Hibernate 4 provider.
- XA datasource topology with three schemas: `infra`, `billing`, `acct`.
- Conjur-style secret references in datasource configuration and mock Conjur provider in code.

## Useful migration automation ideas

1. Java 8 to Java 17 code transforms.
2. JBoss descriptor analysis to Open Liberty config generation.
3. Hibernate 4 to 5+ compatibility updates.
4. XA datasource translation (`standalone.xml` fragments to Liberty `server.xml`).
5. Identify hardcoded legacy APIs (`javax.*`) and suggest Jakarta migration paths.

