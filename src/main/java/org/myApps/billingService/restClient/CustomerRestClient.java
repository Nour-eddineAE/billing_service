package org.myApps.billingService.restClient;

import lombok.Getter;
import org.myApps.billingService.entities.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//si j'ai le port et l'adresse ip de l'app je peut directement utiliser FeignClient avec l'attribut url
// sinon je passe par eureka, qui est beacoup plus pratique en terme de scalabilté
@FeignClient(name = "CUSTOMER-SERVICE")/*c'est preferable de nommer les microservices en majuscule
car même si on le nommes en miniscule, le discovery client les publie en majuscule*/
public interface CustomerRestClient {/*cette interface qui va récuperer les info d'un objet Customer*/

    @GetMapping(path = "/api/customers/{id}")
    /*l'appel de cette method envoi une requete via l'url en dessus, puis l'api customers utilise sa propre
    * methode pour recuperer le client et l'envoyer en retour sous format JSON*/

    public Customer getCustomer(@PathVariable(name = "id") String customerId);

    /*@PathVariable est obligatoire sauf si les variables portent le même nom */

    @GetMapping(path = "/api/customers")
    public List<Customer> getAllCustomers();
}
