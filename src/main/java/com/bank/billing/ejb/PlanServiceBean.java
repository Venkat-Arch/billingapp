package com.bank.billing.ejb;

import com.bank.billing.entity.PaymentRequest;
import com.bank.billing.entity.Plan;
import com.bank.billing.persistence.PlanRepository;
import com.bank.billing.security.ConjurSecretProvider;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Stateless(name = "PlanServiceBean")
public class PlanServiceBean implements PlanService {

    @EJB
    private PlanRepository planRepository;

    @EJB
    private ConjurSecretProvider conjurSecretProvider;

    @Override
    public List<Plan> getPlan() {
        return planRepository.findAllPlans();
    }

    @Override
    public Plan getPlanId(String planId) {
        return planRepository.findPlanById(planId);
    }

    @Override
    public String postPayment(PaymentRequest paymentRequest) {
        String paymentUser = conjurSecretProvider.getSecret("billing/payment-user");
        if (paymentUser == null || paymentUser.isEmpty()) {
            paymentUser = "fallback-service-user";
        }

        String confirmation = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + "-" + UUID.randomUUID().toString().substring(0, 8);

        planRepository.recordPayment(
                paymentRequest.getPlanId(),
                paymentRequest.getAmount(),
                paymentRequest.getCurrency(),
                paymentUser,
                confirmation
        );
        return confirmation;
    }
}
