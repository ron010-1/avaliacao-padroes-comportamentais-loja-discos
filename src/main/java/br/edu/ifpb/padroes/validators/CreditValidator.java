package br.edu.ifpb.padroes.validators;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;

import javax.xml.validation.Validator;

public class CreditValidator implements PurchaseValidator{

    @Override
    public boolean validate(Customer customer, Album album) {
        if (customer.getCredit() < album.getPrice()) {
            System.out.println("Validation failed: Insufficient credit");
            return false;
        }
        return true;
    }
}
