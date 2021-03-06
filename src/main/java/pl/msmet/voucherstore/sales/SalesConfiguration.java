package pl.msmet.voucherstore.sales;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.msmet.payu.PayU;
import pl.msmet.payu.PayUCredentials;
import pl.msmet.payu.http.JavaHttpPayUApiClient;
import pl.msmet.voucherstore.productcatalog.ProductCatalogFacade;
import pl.msmet.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.msmet.voucherstore.sales.offer.OfferMaker;
import pl.msmet.voucherstore.sales.ordering.ReservationRepository;
import pl.msmet.voucherstore.sales.payment.PayUPaymentGateway;
import pl.msmet.voucherstore.sales.payment.PaymentGateway;
import pl.msmet.voucherstore.sales.product.ProductCatalogProductDetailsProvider;
import pl.msmet.voucherstore.sales.product.ProductDetailsProvider;

@Configuration
public class SalesConfiguration {

    @Bean
    SalesFacade salesFacade(ProductCatalogFacade productCatalogFacade, OfferMaker offerMaker, PaymentGateway paymentGateway, ReservationRepository reservationRepository) {
        return new SalesFacade(
                productCatalogFacade,
                new InMemoryBasketStorage(),
                () -> "customer_1",
                (productId) -> true,
                offerMaker,
                paymentGateway,
                reservationRepository);
    }

    @Bean
    PaymentGateway payUPaymentGateway() {
        return new PayUPaymentGateway(new PayU(
                PayUCredentials.productionOfEnv(),
                new JavaHttpPayUApiClient()
        ));
    }

    @Bean
    OfferMaker offerMaker(ProductDetailsProvider productDetailsProvider) {
        return new OfferMaker(productDetailsProvider);
    }

    @Bean
    ProductDetailsProvider productDetailsProvider(ProductCatalogFacade productCatalogFacade) {
        return new ProductCatalogProductDetailsProvider(productCatalogFacade);
    }
}
