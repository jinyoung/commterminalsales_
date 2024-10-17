package commterminalsales.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

//<<< DDD / Value Object
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comparation {

    private String productId;

    @ElementCollection
    @ElementCollection
    private List<String> options;

    private String manufacturer;
}
//>>> DDD / Value Object
