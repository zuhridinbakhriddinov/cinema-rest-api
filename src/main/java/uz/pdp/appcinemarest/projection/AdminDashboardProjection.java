package uz.pdp.appcinemarest.projection;

// Zuhridin Bakhriddinov 3/29/2022 9:58 AM
public interface AdminDashboardProjection {
    Integer getNewTicket();
    Integer getTransactionalHistoryTicket();
    Integer getRefundTicket();
    Double getIncome();
    Double getOutcome();
}
