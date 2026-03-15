package org.ahilmi.pro2_sm_2.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCourseDTO {

    /*
    kullanıcı bir istek attığında (insert, update) aşağıdaki alanlara ait bilgileri bize vermeli.
    id yok çünkü db tarafından otomatik veriliyor.
    */

    /*
    @NotNull: @NotNull ensures that a field is not null but allows empty values (e.g., an empty string or an empty collection).
    @NotEmpty: @NotEmpty ensures that a field is not null and also not empty, meaning it must contain at least one element (for collections) or at least one character (for strings).
    @NotBlank: @NotBlank applies only to strings and ensures they are not null, not empty and contain at least one non-whitespace character (i.e., spaces alone are not allowed).
    */

    @NotBlank(message = "name field cannot be null")
    private String name;

    @NotNull(message = "credit field cannot be null")
    private Integer credit;

}
