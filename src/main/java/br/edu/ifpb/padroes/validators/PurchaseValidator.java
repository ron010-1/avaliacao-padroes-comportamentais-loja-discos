package br.edu.ifpb.padroes.validators;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;

public interface PurchaseValidator {
    boolean validate(Customer customer, Album album);
}
