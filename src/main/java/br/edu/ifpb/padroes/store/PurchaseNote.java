package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.music.Album;

public class PurchaseNote {

    private Album album;
    private Customer customer;
    private double discount;
    private double finalPrice;

    public PurchaseNote(Album album, Customer customer, double discount, double finalPrice) {
        this.album = album;
        this.customer = customer;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }

    public void generateNote(){
        System.out.println("Purchase: " + this.album.getFormattedName() + " by " + this.customer.getName());
        System.out.println("Original price: $" + this.album.getPrice());
        System.out.println("Discount: $" + this.discount);
        System.out.println("Final price: $" + this.finalPrice);
    }
}
