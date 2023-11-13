package com.buylify.buylifyapp.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category_details")
public class CategoryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_details_sequence")
    @SequenceGenerator(name = "category_details_sequence", sequenceName = "category_details_sequence", allocationSize = 1)
    private Long id;

    @NotBlank
    private String detailName;

    @NotBlank
    private String valueName;

}
