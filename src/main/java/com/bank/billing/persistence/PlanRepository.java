package com.bank.billing.persistence;

import com.bank.billing.entity.Plan;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

@Stateless
public class PlanRepository {

    @PersistenceContext(unitName = "billingPU")
    private EntityManager billingEntityManager;

    @PersistenceContext(unitName = "infraPU")
    private EntityManager infraEntityManager;

    @PersistenceContext(unitName = "acctPU")
    private EntityManager acctEntityManager;

    @SuppressWarnings("unchecked")
    public List<Plan> findAllPlans() {
        return billingEntityManager.createQuery("select p from Plan p order by p.planId").getResultList();
    }

    public Plan findPlanById(String planId) {
        return billingEntityManager.find(Plan.class, planId);
    }

    public void recordPayment(String planId,
                              BigDecimal amount,
                              String currency,
                              String serviceUser,
                              String confirmationNumber) {
        Query billingInsert = billingEntityManager.createNativeQuery(
                "insert into payment_txn(plan_id, amount, currency, confirmation_no, created_by) values (?, ?, ?, ?, ?)");
        billingInsert.setParameter(1, planId);
        billingInsert.setParameter(2, amount);
        billingInsert.setParameter(3, currency);
        billingInsert.setParameter(4, confirmationNumber);
        billingInsert.setParameter(5, serviceUser);
        billingInsert.executeUpdate();

        Query acctInsert = acctEntityManager.createNativeQuery(
                "insert into acct_payment_audit(plan_id, confirmation_no, status_cd) values (?, ?, ?)");
        acctInsert.setParameter(1, planId);
        acctInsert.setParameter(2, confirmationNumber);
        acctInsert.setParameter(3, "POSTED");
        acctInsert.executeUpdate();

        Query infraInsert = infraEntityManager.createNativeQuery(
                "insert into api_audit(api_name, entity_id, result_cd) values (?, ?, ?)");
        infraInsert.setParameter(1, "postpayment");
        infraInsert.setParameter(2, planId);
        infraInsert.setParameter(3, "SUCCESS");
        infraInsert.executeUpdate();
    }
}
