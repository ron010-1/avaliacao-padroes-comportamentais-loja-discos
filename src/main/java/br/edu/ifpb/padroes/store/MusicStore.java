package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.customer.Customer;
import br.edu.ifpb.padroes.customer.CustomerType;
import br.edu.ifpb.padroes.music.AgeRestriction;
import br.edu.ifpb.padroes.music.Album;
import br.edu.ifpb.padroes.music.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    // Open closed principle -> princípio que foi quebrado por esse método
    // solução sugerida é ter uma classe CalculatorDiscount genérico e um para cada um dos tipos do enum
    public double calculateDiscount(Album album, CustomerType customerType) {
        double discount = 0;

        // dúvida quanto a implementação do if se deve ficar desse jeito ou não
        if (customerType.equals(CustomerType.VIP)) {
            discount = new CalculatorDiscountVip(album).calculateDiscount();
        } else if (customerType.equals(CustomerType.PREMIUM)) {
            discount = new CalculatorDiscountPremium(album).calculateDiscount();
        } else if (customerType.equals(CustomerType.REGULAR)) {
            discount = new CalculatorDiscountRegular(album).getTotalDiscount();
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
        if (validatePurchase(customer, album)) {
            double discount = calculateDiscount(album, customer.getType());
            double finalPrice = album.getPrice() - discount;

            System.out.println("Purchase: " + album.getFormattedName() + " by " + customer.getName());
            System.out.println("Original price: $" + album.getPrice());
            System.out.println("Discount: $" + discount);
            System.out.println("Final price: $" + finalPrice);

            album.decreaseStock();
            customer.addPurchase(album);

            for (Customer c : customers) {
                if (c.isInterestedIn(album.getGenre()) && !c.equals(customer)) {
                    System.out.println("Notifying " + c.getName() + " about popular " + album.getGenre() + " purchase");
                }
            }
        } else {
            System.out.println("Out of stock!");
        }
    }

    public boolean validatePurchase(Customer customer, Album album) {
        // Check stock
        if (album.getStock() <= 0) {
            System.out.println("Validation failed: Out of stock");
            return false;
        }

        // Check customer credit
        if (customer.getCredit() < album.getPrice()) {
            System.out.println("Validation failed: Insufficient credit");
            return false;
        }

        // Check age restriction for explicit content
        if (album.getAgeRestriction().equals(AgeRestriction.PARENTAL_ADVISORY) && customer.getDateOfBirth().isAfter(LocalDate.now().minusYears(18))) {
            System.out.println("Validation failed: Age restriction");
            return false;
        }

        return true;
    }

    public List<Album> getInventory() {
        return inventory;
    }

}
