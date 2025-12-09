package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;

import java.util.ArrayList;
import java.util.List;

public class NotifyInterested {

    private Album album;
    private Customer customerPurchased;
    private List<Customer> customers = new ArrayList<>();

    public NotifyInterested(List<Customer> customers, Album album, Customer customerPurchased) {
        this.customers = customers;
        this.album = album;
        this.customerPurchased = customerPurchased;
    }

    public void notifyAllInterestedCustomers(){
        for (Customer c : this.customers) {
            if (c.isInterestedIn(this.album.getGenre()) && !c.equals(this.customerPurchased)) {
                System.out.println("Notifying " + c.getName() + " about popular " + album.getGenre() + " purchase");
            }
        }
    }
}
