package org.ahilmi.pro2_sm_2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCourseDTO {

    /*
    burası kullanıcıya vereceğimiz cevap. diyelim ki bir GET işlemi yaptı, aşağıdaki bilgileri göstereceğiz kullanıcıya.
    kullanıcı hangi kayıt üzerinde işlem yaptığını bilmeli, bu yüzden id de veriyorum.
    */
    private Integer id;
    private String name;
    private Integer credit;

}
