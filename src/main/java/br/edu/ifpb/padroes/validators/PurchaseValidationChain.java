package br.edu.ifpb.padroes.validators;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;

import java.util.List;

public class PurchaseValidationChain {

    private final List<PurchaseValidator> validators = List.of(
            new StockValidator(),
            new CreditValidator(),
            new AgeRestrictionValidator()
    );

    public boolean validate(Customer customer, Album album) {
        for (PurchaseValidator validator : validators) {
            if (!validator.validate(customer, album)) {
                return false;
            }
        }
        return true;
    }
}
