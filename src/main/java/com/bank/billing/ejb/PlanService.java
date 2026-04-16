package com.bank.billing.ejb;

import com.bank.billing.entity.PaymentRequest;
import com.bank.billing.entity.Plan;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PlanService {
    List<Plan> getPlan();

    Plan getPlanId(String planId);

    String postPayment(PaymentRequest paymentRequest);
}
