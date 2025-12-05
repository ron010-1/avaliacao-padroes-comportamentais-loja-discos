package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.customer.CustomerType;
import br.edu.ifpb.padroes.music.Album;
import br.edu.ifpb.padroes.music.MediaType;

import java.util.ArrayList;
import java.util.List;
import br.edu.ifpb.padroes.validators.PurchaseValidationChain;



public class MusicStore {

    private List<Album> inventory = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    public void addMusic(Album album) {
        inventory.add(album);
        System.out.println("Added: " + album.getTitle());
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Album> searchMusic(SearchType searchType, String searchTerm) {
        List<Album> results = new ArrayList<>();

        if (searchType.equals(SearchType.TITLE)) {
            for (Album album : inventory) {
                if (album.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(album);
                }
            }
        } else if (searchType.equals(SearchType.ARTIST)) {
            for (Album album : inventory) {
                if (album.getArtist().toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(album);
                }
            }
        } else if (searchType.equals(SearchType.GENRE)) {
            for (Album album : inventory) {
                if (album.getGenre().toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(album);
                }
            }
        } else if (searchType.equals(SearchType.TYPE)) {
            for (Album album : inventory) {
                if (album.getType().name().equalsIgnoreCase(searchTerm)) {
                    results.add(album);
                }
            }
        }

        return results;
    }

    public double calculateDiscount(Album album, CustomerType customerType) {
        double discount = 0;

        if (customerType.equals(CustomerType.VIP)) {
            discount = album.getPrice() * 0.20;
        } else if (customerType.equals(CustomerType.PREMIUM)) {
            discount = album.getPrice() * 0.15;
        } else if (customerType.equals(CustomerType.REGULAR)) {
            discount = album.getPrice() * 0.05;
        }

        // Additional discounts
        if (album.getType().equals(MediaType.VINYL) && album.getReleaseDate().getYear() < 1980) {
            discount += album.getPrice() * 0.10;
        }

        if (album.getGenre().equalsIgnoreCase("Pop Punk") && customerType.equals(CustomerType.VIP)) {
            discount += album.getPrice() * 0.05;
        }

        return discount;
    }

    public void purchaseMusic(Customer customer, Album album) {

        PurchaseValidationChain chain = new PurchaseValidationChain();

        if (chain.validate(customer, album)) {

            double discount = calculateDiscount(album, customer.getType());
            double finalPrice = album.getPrice() - discount;

            PurchaseNote purchaseNote = new PurchaseNote(album,customer, discount, finalPrice);
            purchaseNote.generateNote();

            album.decreaseStock();
            customer.addPurchase(album);

            NotifyInterested notifyInterested = new NotifyInterested(customers, album, customer);
            notifyInterested.notifyAllInterestedCustomers();
            
        } else {
            System.out.println("Out of stock!");
        }
    }

    public List<Album> getInventory() {
        return inventory;
    }

}
