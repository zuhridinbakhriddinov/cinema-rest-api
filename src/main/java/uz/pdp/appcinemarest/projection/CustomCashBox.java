package uz.pdp.appcinemarest.projection;


import org.springframework.data.rest.core.config.Projection;
import uz.pdp.appcinemarest.entity.CashBox;

@Projection(types = CashBox.class)
public interface CustomCashBox {
    Integer getId();
    String getName();
  Double getBalance();
}
