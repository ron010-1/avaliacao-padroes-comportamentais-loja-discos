package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.customer.CustomerType;
import br.edu.ifpb.padroes.discount.DiscountStrategy;
import br.edu.ifpb.padroes.discount.PremiumDiscountStrategy;
import br.edu.ifpb.padroes.discount.RegularDiscountStrategy;
import br.edu.ifpb.padroes.discount.VipDiscountStrategy;
import br.edu.ifpb.padroes.music.Album;
import br.edu.ifpb.padroes.music.MediaType;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.padroes.search.*;
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
        SearchStrategy strategy;

        switch (searchType) {
            case TITLE:
                strategy = new TitleSearchStrategy();
                break;
            case ARTIST:
                strategy = new ArtistSearchStrategy();
                break;
            case GENRE:
                strategy = new GenreSearchStrategy();
                break;
            case TYPE:
                strategy = new TypeSearchStrategy();
                break;
            default:
                return results;
        }

        for (Album album : inventory) {
            if (strategy.matches(album, searchTerm)) {
                results.add(album);
            }
        }
        return results;
    }

    public double calculateDiscount(Album album, CustomerType customerType) {

        DiscountStrategy strategy;
        switch (customerType) {
            case VIP:
                strategy = new VipDiscountStrategy();
                break;
            case PREMIUM:
                strategy = new PremiumDiscountStrategy();
                break;
            default:
                strategy = new RegularDiscountStrategy();
                break;
        }

        double discount = strategy.calculate(album);

        if (album.getType().equals(MediaType.VINYL) && album.getReleaseDate().getYear() < 1980) {
            discount += album.getPrice() * 0.10;
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