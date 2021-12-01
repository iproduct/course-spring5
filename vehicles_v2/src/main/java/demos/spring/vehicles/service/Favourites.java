package demos.spring.vehicles.service;

import demos.spring.vehicles.model.Offer;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service("favs")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Favourites {
    private Map<Long, Offer> favs = new ConcurrentHashMap<>();

    public void addOffer(Offer offer) {
        favs.put(offer.getId(), offer);
    }

    public Offer getOfferById(Long offerId) {
        return favs.get(offerId);
    }

    public Offer removeOfferById(Long offerId) {
        return favs.remove(offerId);
    }

    public Set<Long> getAllOfferIds() {
        return favs.keySet();
    }

    public Collection<Offer> getAllOffers() {
        return favs.values();
    }
}
