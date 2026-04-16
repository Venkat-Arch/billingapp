# billingapp mock codebase

Mock Java EE billing application to emulate a Java 8 + JBoss + Hibernate 4 production baseline.

This is **not** production-ready code. It is meant for developing automation agents and prompts that perform migration tasks to Java 17, Open Liberty 21.x, and newer Hibernate/JPA stacks.

## Quick structure

- `src/main/java/com/bank/billing/api` - REST APIs (`getplan`, `getplanId`, `postpayment`)
- `src/main/java/com/bank/billing/ejb` - EJB service and interface
- `src/main/java/com/bank/billing/persistence` - JPA repository touching 3 XA-backed schemas
- `src/main/resources/META-INF/persistence.xml` - Hibernate 4 + JTA persistence units
- `src/main/webapp/WEB-INF/jboss-*.xml` - JBoss deployment and JNDI mapping
- `jboss/standalone-datasources.xml` - XA datasource + pooling/throttling + Conjur placeholders

## Build

```bash
mvn -q clean package
```

## API summary

| API name | Method | Path |
|---|---|---|
| getplan | GET | `/billingapp/api/plans` |
| getplanId | GET | `/billingapp/api/plans/{planId}` |
| postpayment | POST | `/billingapp/api/plans/payments` |

## Notes

- Data access calls are intentionally simple and include native SQL stubs to simulate cross-schema writes under XA.
- `ConjurSecretProvider` loads mock secrets from a local properties file for local testing.
- `scripts/sample-requests.http` can be used from IDE REST clients.
