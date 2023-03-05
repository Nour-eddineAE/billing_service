package org.myApps.billingService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {/*A noter que ce n'est pas une entité JPA, on l'utilise juste pour faciliter la gestion*/
    private String id;
    private String name;
    private String email;
}
