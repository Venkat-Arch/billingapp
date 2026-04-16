package com.bank.billing.api;

import com.bank.billing.ejb.PlanServiceBean;
import com.bank.billing.entity.PaymentRequest;
import com.bank.billing.entity.Plan;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/plans")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlanResource {

    @EJB(mappedName = "java:global/billingapp/PlanServiceBean!com.bank.billing.ejb.PlanService")
    private PlanServiceBean planService;

    @GET
    public Response getPlan() {
        List<Plan> plans = planService.getPlan();
        return Response.ok(plans).build();
    }

    @GET
    @Path("/{planId}")
    public Response getPlanId(@PathParam("planId") String planId) {
        Plan plan = planService.getPlanId(planId);
        if (plan == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Plan not found: " + planId)
                    .build();
        }
        return Response.ok(plan).build();
    }

    @POST
    @Path("/payments")
    public Response postPayment(PaymentRequest paymentRequest) {
        String confirmationNumber = planService.postPayment(paymentRequest);
        return Response.status(Response.Status.CREATED)
                .entity("Payment submitted. Confirmation=" + confirmationNumber)
                .build();
    }
}
